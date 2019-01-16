package vms.vmsfrontendutilityserver.dto.machines;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString
@Entity
public class SensorProductDTO {
	
	@Id
	int sensorId;
	String name;
	
	
	public SensorProductDTO(int sensorId, String name) {
		super();
		this.sensorId = sensorId;
		this.name = name;
	}
	

}
