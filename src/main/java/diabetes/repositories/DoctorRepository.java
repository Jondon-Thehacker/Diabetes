package diabetes.repositories;

import diabetes.model.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Emil Løvstrand Mortensen, s204483
@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long > {
}
