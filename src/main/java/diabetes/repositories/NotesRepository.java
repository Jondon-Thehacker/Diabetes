package diabetes.repositories;

import diabetes.model.Notes;
import Simple.NotesId;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotesRepository extends CrudRepository<Notes, Long> {
}
