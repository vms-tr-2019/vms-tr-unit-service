package vms.vmsfrontendutilityserver.machines;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import vms.vmsfrontendutilityserver.jpa.MachineJPA;
import vms.vmsfrontendutilityserver.jpa.MachineProductSensorJPA;

//@Repository
public interface MachinesSqlRepository extends JpaRepository<MachineJPA, Integer> {
	

//	@Query(value ="SELECT sensor_id, product_name FROM machine_sensor_product as msp JOIN products as p " + 
//			"ON msp.product_name = p.product_name WHERE machine_id = :machineId", nativeQuery=true)
//	Map<Integer, String> selectProductInMachine(@Param("machineId") int machineId);
	
//	@Query(value ="SELECT msp.sensor_id,p.product_name FROM machine_sensor_product as msp "
//			+ "JOIN products as p WHERE msp.product_id = p.product_id "
//			+ "and msp.machine_id = :machineId", nativeQuery=true)
//	@Query("SELECT new vms.vmsfrontendutilityserver.dto.machines.SnsorProductDTO(sp.sensorId, p.name) FROM SensorProductJpa sp"
//			+ " JOIN ProductJPA p ON  sp.product.productId = p.productId WHERE sp.machineId=:machineId")
	

	@Query(value ="SELECT * FROM machine_sensor_product  WHERE machine_id = :machineId", nativeQuery=true)
	List<MachineProductSensorJPA> selectProductInMachine(@Param("machineId") int machineId);

}
