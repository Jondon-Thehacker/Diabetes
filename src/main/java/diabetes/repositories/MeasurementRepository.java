package diabetes.repositories;

import diabetes.model.Measurement;
import Simple.MeasurementId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MeasurementRepository extends CrudRepository<Measurement, MeasurementId> {
}
