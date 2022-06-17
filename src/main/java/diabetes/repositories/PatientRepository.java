package diabetes.repositories;

import diabetes.model.Patient;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

//Emil Pontoppidan Rasmussen, s204441
@Repository
public interface PatientRepository extends CrudRepository<Patient, Long > {
    List<Patient> findAll();
}