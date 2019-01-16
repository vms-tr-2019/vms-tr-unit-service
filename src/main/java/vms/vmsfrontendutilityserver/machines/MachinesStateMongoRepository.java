package vms.vmsfrontendutilityserver.machines;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import vms.vmsfrontendutilityserver.dto.machines.MachineStateCurrentDTO;


@Repository
public interface MachinesStateMongoRepository extends MongoRepository<MachineStateCurrentDTO, Integer> {

}
