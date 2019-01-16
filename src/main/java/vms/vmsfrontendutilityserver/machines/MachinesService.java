package vms.vmsfrontendutilityserver.machines;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import vms.vmsfrontendutilityserver.configuration.Configurator;
import vms.vmsfrontendutilityserver.configuration.SensorDescription;
import vms.vmsfrontendutilityserver.configuration.SensorProps;
import vms.vmsfrontendutilityserver.dto.OperationStatusEnum;
import vms.vmsfrontendutilityserver.dto.machines.MachineDTO;
import vms.vmsfrontendutilityserver.dto.machines.MachineStateCurrentDTO;
import vms.vmsfrontendutilityserver.dto.machines.MachineStateDTO;
import vms.vmsfrontendutilityserver.dto.machines.MachineStateEnum;
import vms.vmsfrontendutilityserver.jpa.MachineJPA;
import vms.vmsfrontendutilityserver.jpa.MachineProductSensorJPA;
import vms.vmsfrontendutilityserver.jpa.ProductJPA;
import vms.vmsfrontendutilityserver.products.ProductsRepository;

@Service
public class MachinesService implements IMachines {

	@Autowired
	MachinesSqlRepository SQLRepo;

	@Autowired
	MachinesStateMongoRepository MongoRepo;

	@Autowired
	ProductsRepository prodRepo;

	@Autowired
	Configurator config;

	@Autowired
	SensorProductRepository repo;

	@Override
	@Transactional
	public OperationStatusEnum addMachine(MachineDTO machine) {
		if (SQLRepo.existsById(machine.machineId))
			return OperationStatusEnum.EXISTS;
		MachineJPA machineJpa = convertDTOtoJPA(machine);
		if (machineJpa.getProducts().isEmpty()) {
			SQLRepo.save(machineJpa);
			return OperationStatusEnum.LIST_PRODUCTS_EMPTY;
		}
		SQLRepo.save(machineJpa);
		return OperationStatusEnum.OK;
	}

	private MachineJPA convertDTOtoJPA(MachineDTO machine) {

		int machineId = machine.machineId;
		String firmName = machine.firmName;
		String location = machine.location;
		MachineJPA machineJPA = new MachineJPA(machineId, firmName, location);
		machineJPA.setProducts(toMachineProdSens(machine.productSensor, machineJPA));
		return machineJPA;
	}

	private List<MachineProductSensorJPA> toMachineProdSens(Map<Integer, Integer> productSensor, MachineJPA machineJpa) {
		List<MachineProductSensorJPA> list = new ArrayList<>();

		for (Map.Entry<Integer, Integer> map : productSensor.entrySet()) {
			ProductJPA prod = prodRepo.findById(map.getValue()).orElse(null);
			if (prod == null) {
				return new ArrayList<>();
			}
			MachineProductSensorJPA sensorProduct = new MachineProductSensorJPA(machineJpa.machineId, map.getKey(),
					map.getValue(), prod.getName());
			list.add(sensorProduct);
		}
		return list;
	}

	@Override
	@Transactional
	public OperationStatusEnum updateMachine(MachineDTO machine) {
		if (!SQLRepo.existsById(machine.machineId))
			return OperationStatusEnum.NOT_EXISTS;
		System.out.println("put machineDTO  = " + machine);
		MachineJPA machineJpa = convertDTOtoJPA(machine);
		SQLRepo.save(machineJpa);
		return OperationStatusEnum.OK;
	}

	@Override
	public MachineDTO getMachine(int machineId) {
		MachineJPA machineJpa = SQLRepo.findById(machineId).orElse(null);
		System.out.println("print machineJPA = " + machineJpa);
		if (machineJpa != null) {
			return convertJPAtoDTO(machineJpa);
		}
		return null;
	}

	private MachineDTO convertJPAtoDTO(MachineJPA machineJpa) {

		int machineId = machineJpa.machineId;
		String firmName = machineJpa.firmName;
		String location = machineJpa.location;
		Map<Integer, Integer> productSensor = new HashMap<>();
		for (MachineProductSensorJPA set : machineJpa.getProducts()) {
			productSensor.put(set.getSensorId(), set.getProductId());
		}

		return new MachineDTO(machineId, firmName, location, productSensor);
	}

	@Override
	@Transactional
	public OperationStatusEnum removeMachine(int machineId) {
		if (!SQLRepo.existsById(machineId)) {
			return OperationStatusEnum.NOT_EXISTS;
		}
		SQLRepo.deleteById(machineId);
		return OperationStatusEnum.OK;
	}

	@Override
	public MachineStateDTO getMachineState(int machineId) {
		MachineStateCurrentDTO stateMachine = MongoRepo.findById(machineId).orElse(null);
		if (stateMachine != null) {
			return convertCurrentStateToDTO(stateMachine);
		}
		return null;
	}

	private MachineStateDTO convertCurrentStateToDTO(MachineStateCurrentDTO stateMachine) {
		MachineStateEnum state = MachineStateEnum.OK;

		System.out.println("machineId = " + stateMachine.machineId);
		List<MachineProductSensorJPA> sensorProd = repo.selectProductInMachine(stateMachine.machineId);
		System.out.println(sensorProd);
		Map<String, Integer> productsBoxes = new HashMap<>();
		Map<Integer, Integer> sensorValue = stateMachine.sensorsData;
		for (MachineProductSensorJPA sens : sensorProd) {
			productsBoxes.put(sens.getProductName(), sensorValue.get(sens.getSensorId()));
			if (sensorValue.get(sens.getSensorId()) == null)
				state = MachineStateEnum.DOWNTIME;
		}

		Map<String, Integer> sensorsData = new HashMap<>();
		List<SensorDescription> listSen = config.getSensorsDesc();
		System.out.println("sensorValue = " + sensorValue);
		System.out.println("listSen  = " + listSen);
		SensorProps props = config.getSensorProps().get(1);
		System.out.println("props = " + props);
		for(SensorDescription sensor : listSen) {
			if(sensor.getSensorId() < props.getFrom() || sensor.getSensorId()>props.getTo()) {
				if(sensorValue.get(sensor.getSensorId()) == null) {
					sensorsData.put(sensor.getDiscription(), -1 );
					state = MachineStateEnum.SENSOR_NO_SIGNAL;
				} else {
					sensorsData.put(sensor.getDiscription(), sensorValue.get(sensor.getSensorId()));
				if(sensorValue.get(sensor.getSensorId()) == 1) state = MachineStateEnum.ERROR;
				}
			}
		}

		return new MachineStateDTO(stateMachine.machineId, productsBoxes, sensorsData, state);

		// vms.sensorRanges.CRASH.from=0
		// vms.sensorRanges.CRASH.to=99
		// vms.sensorRanges.DECREASE.from=100
		// vms.sensorRanges.DECREASE.to=499
		// vms.sensorRanges.INCREASE.from=500
		// vms.sensorRanges.INCREASE.to=999
	}

	public OperationStatusEnum saveMachineStateInDB(MachineStateCurrentDTO dto) {
		MongoRepo.save(dto);
		return OperationStatusEnum.OK;
	}

	@Override
	public List<MachineDTO> getAllMachines() {
		return SQLRepo.findAll().stream().map(mach -> convertJPAtoDTO(mach)).collect(Collectors.toList());
	}
}
