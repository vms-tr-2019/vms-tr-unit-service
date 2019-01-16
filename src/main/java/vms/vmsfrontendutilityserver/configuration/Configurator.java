package vms.vmsfrontendutilityserver.configuration;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter @Setter @NoArgsConstructor @ToString @EqualsAndHashCode
@Component
@ConfigurationProperties("app")
public class Configurator {
	
	private List<SensorDescription> sensorsDesc;
	
	private List<SensorProps> sensorProps;
	

}
