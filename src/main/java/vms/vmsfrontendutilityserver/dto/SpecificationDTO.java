package vms.vmsfrontendutilityserver.dto;

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
public class SpecificationDTO {

  public int sensorId;
  public SensorTypeEnum type;
  public int sensorValue;
  public String discription;

  public SpecificationDTO(int sensorId, SensorTypeEnum type, int sensorValue, String discription) {
    super();
    this.sensorId = sensorId;
    this.type = type;
    this.sensorValue = sensorValue;
    this.discription = discription;
  }

}
