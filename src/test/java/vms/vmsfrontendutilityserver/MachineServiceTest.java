package vms.vmsfrontendutilityserver;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import vms.vmsfrontendutilityserver.dto.OperationStatusEnum;
import vms.vmsfrontendutilityserver.dto.machines.MachineDTO;
import vms.vmsfrontendutilityserver.dto.machines.MachineStateCurrentDTO;
import vms.vmsfrontendutilityserver.dto.machines.MachineStateDTO;
import vms.vmsfrontendutilityserver.dto.machines.MachineStateEnum;
import vms.vmsfrontendutilityserver.dto.products.ProductDTO;
import vms.vmsfrontendutilityserver.machines.MachinesService;
import vms.vmsfrontendutilityserver.products.ProductsService;

@SpringBootApplication
class MachineServiceTest {
	
	MachinesService service;
	ProductsService serProd;
	ConfigurableApplicationContext ctx;
	Map<Integer, Integer> productSensor = new HashMap<>();
	MachineDTO mach1 ;
	MachineDTO mach2 ;
	ProductDTO prod1 = new ProductDTO(1, "water", 15, true);
	ProductDTO prod2 = new ProductDTO(2, "beer", 10, true);
	ProductDTO prod3 = new ProductDTO(3, "cola", 10, true);
	ProductDTO prod4 = new ProductDTO(4, "juice", 10, true);
	MachineStateCurrentDTO curDto; 
	
	
	
	
	@BeforeEach
	void setUp() {
		ctx = SpringApplication.run(MachineServiceTest.class);
		service = ctx.getBean(MachinesService.class);
		serProd = ctx.getBean(ProductsService.class);
		serProd.addProduct(prod1);
		serProd.addProduct(prod2);
		serProd.addProduct(prod3);
		serProd.addProduct(prod4);
		productSensor.put(100, 1);
		productSensor.put(101, 2);
		productSensor.put(102, 3);
		mach1 = new MachineDTO(1, "Berloga", "Beer-Sheva", productSensor);
		service.addMachine(mach1);
	}
	
	@AfterEach
	void tearDown() {
		ctx.close();
	}
	
	
	@Test
	void testGetAndAddMachine() {
		assertEquals(service.addMachine(mach1), OperationStatusEnum.EXISTS);
		assertEquals(mach1, service.getMachine(mach1.getMachineId()));
		Map<Integer, Integer> product = new HashMap<>();
		product.put(100, 1);
		product.put(101, 2);
		product.put(102, 3);
		product.put(103, 4);
		product.put(200, 30);
		
		MachineDTO mach200 = new MachineDTO(10, "Berloga", "Tel-Aviv", product);
		assertEquals(OperationStatusEnum.LIST_PRODUCTS_EMPTY, service.addMachine(mach200));
	}
	
	@Test
	void updateMachineTest() {
		assertEquals(service.updateMachine(new MachineDTO(100, "firmName", "location", new HashMap<>())),
				OperationStatusEnum.NOT_EXISTS);
		Map<Integer, Integer> product = new HashMap<>();
		product.put(100, 1);
		product.put(101, 2);
		product.put(102, 3);
		product.put(103, 4);
		
		mach1 = new MachineDTO(1, "Berloga", "Tel-Aviv", product);
		assertEquals(service.updateMachine(mach1), OperationStatusEnum.OK);
		assertEquals(mach1, service.getMachine(mach1.getMachineId()));
		
	}
	
	@Test
	void removeTest() {
		assertEquals(service.removeMachine(100), OperationStatusEnum.NOT_EXISTS);
		assertEquals(service.removeMachine(1), OperationStatusEnum.OK);
		assertNull(service.getMachine(mach1.getMachineId()));
	}
	
	@Test 
	void getMachineStateTest() {
		Map<Integer, Integer> sensorData1 = new HashMap<>();
		sensorData1.put(1, 0);
		sensorData1.put(2, 0);
		sensorData1.put(3, 0);
		sensorData1.put(100, 10);
		sensorData1.put(101, 20);
		sensorData1.put(102, 30);
		curDto = new MachineStateCurrentDTO(1, sensorData1);
		int machineId = 1;
		
		Map<String, Integer> productsBoxes = new HashMap<>();
		productsBoxes.put("water", 10);
		productsBoxes.put("beer", 20);
		productsBoxes.put("cola", 30);
		
		Map<String, Integer> sensorsDataActual = new HashMap<>();
		sensorsDataActual.put("smoke", 0);
		sensorsDataActual.put("fire", 0);
		sensorsDataActual.put("water", 0);
		
		
		MachineStateEnum state = MachineStateEnum.OK;
		//		service.saveMachineStateInDB(curDto);
		MachineStateDTO stateM = new MachineStateDTO(machineId, productsBoxes, sensorsDataActual, state );
		assertEquals(service.getMachineState(1), stateM);
		
		
	}
	
	@Test
	void getAllMachinesTest() {
		assertEquals(1, service.getAllMachines().size());
		mach2 = new MachineDTO(2, "Kanura", "Haifa", productSensor);
		assertEquals(service.addMachine(mach2), OperationStatusEnum.OK );
		assertEquals(2, service.getAllMachines().size());
		
	}

}
