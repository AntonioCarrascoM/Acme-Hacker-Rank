
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Position;

@Repository
public interface PositionRepository extends JpaRepository<Position, Integer> {

	//Retrieves a list of positions with final mode = true and not cancelled
	@Query("select p from Position p where p.finalMode=1 and p.cancelled=0")
	Collection<Position> getPublicPositions();
}
