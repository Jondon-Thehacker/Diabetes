package diabetes.repositories;

import diabetes.model.Doctor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DoctorRepository extends CrudRepository<Doctor, Long > {
    //List<Patient> findAll();
}
