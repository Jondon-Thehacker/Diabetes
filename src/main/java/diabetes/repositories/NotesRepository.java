package diabetes.repositories;

import diabetes.model.Notes;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

//Simon Stampe Jensen, s204488
@Repository
public interface NotesRepository extends CrudRepository<Notes, Long> {
}
