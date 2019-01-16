package vms.vmsfrontendutilityserver.machines;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vms.vmsfrontendutilityserver.dto.ApiConstants;
import vms.vmsfrontendutilityserver.dto.OperationStatusEnum;
import vms.vmsfrontendutilityserver.dto.machines.MachineDTO;
import vms.vmsfrontendutilityserver.dto.machines.MachineStateDTO;

@RestController
@RequestMapping(ApiConstants.MACHINES)
public class MachinesController {

  @Autowired
  IMachines machines;

  @PostMapping(ApiConstants.ADD_MACHINE)
  public OperationStatusEnum addMachine(@RequestBody MachineDTO machine) {
    return machines.addMachine(machine);
  }

  @PostMapping(ApiConstants.UPDATE_MACHINE)
  public OperationStatusEnum updateMachine(@RequestBody MachineDTO machine) {
    return machines.updateMachine(machine);
  }

  @GetMapping(ApiConstants.GET_MACHINE + "/{machine_id}")
  public MachineDTO getMachine(@PathVariable("machine_id") int machineId) {
    return machines.getMachine(machineId);
  }

  @DeleteMapping(ApiConstants.REMOVE_MACHINE + "/{machine_id}")
  public OperationStatusEnum removeMachine(@PathVariable("machine_id") int machineId) {
    return machines.removeMachine(machineId);
  }

  @GetMapping(ApiConstants.GET_MACHINE_STATE + "/{machine_id}")
  public MachineStateDTO getMachineState(@PathVariable("machine_id") int machineId) {
    return machines.getMachineState(machineId);
  }

  @GetMapping(ApiConstants.GET_MACHINE_ALL)
  public List<MachineDTO> getAllMachine() {
    return machines.getAllMachines();
  }

}
