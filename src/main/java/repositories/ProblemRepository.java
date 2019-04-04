
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	//Retrieves the problems for a certain position
	@Query("select p from Problem p where p.position.id=?1")
	Collection<Problem> problemsOfAPosition(int id);
}
