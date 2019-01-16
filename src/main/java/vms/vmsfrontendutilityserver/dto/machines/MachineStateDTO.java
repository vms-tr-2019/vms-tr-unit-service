package vms.vmsfrontendutilityserver.dto.machines;

import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MachineStateDTO {

  public int machineId;
  public Map<String, Integer> productsBoxes;// percentages
  public Map<String, Integer> sensorsData;
  public MachineStateEnum state;

  public MachineStateDTO(int machineId, Map<String, Integer> productsBoxes, Map<String, Integer> sensorsData,
      MachineStateEnum state) {
    super();
    this.machineId = machineId;
    this.productsBoxes = productsBoxes;
    this.sensorsData = sensorsData;
    this.state = state;
  }

}
