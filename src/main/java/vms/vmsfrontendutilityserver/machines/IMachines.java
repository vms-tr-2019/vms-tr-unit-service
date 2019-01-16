package vms.vmsfrontendutilityserver.machines;

import java.util.List;

import vms.vmsfrontendutilityserver.dto.OperationStatusEnum;
import vms.vmsfrontendutilityserver.dto.machines.MachineDTO;
import vms.vmsfrontendutilityserver.dto.machines.MachineStateDTO;

public interface IMachines {

  public OperationStatusEnum addMachine(MachineDTO machine);

  public OperationStatusEnum updateMachine(MachineDTO machine);

  public MachineDTO getMachine(int machineId);

  public OperationStatusEnum removeMachine(int machineId);

  public MachineStateDTO getMachineState(int machineId);
  
  public List<MachineDTO> getAllMachines();

}
