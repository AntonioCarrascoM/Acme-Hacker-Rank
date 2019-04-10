
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Problem;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Integer> {

	//Retrieves the problems for a certain company
	@Query("select pr from Problem pr where pr.company.id=?1")
	Collection<Problem> problemsOfACompany(int id);

	//Retrieves the problems for a certain position
	@Query("select pr from Problem pr join pr.positions p where pr.id=?1")
	Collection<Problem> problemsOfAPosition(int id);

	//Problems in final mode by position
	@Query("select pr from Problem pr join pr.positions p where pr.finalMode='1' and p.id=?1")
	Collection<Problem> problemsInFinalModeByPosition(int id);
}
