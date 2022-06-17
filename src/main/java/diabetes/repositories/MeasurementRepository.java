package diabetes.repositories;

import diabetes.model.Measurement;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Jonathan Max Michelsen, s204437
@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, Long> {
}
