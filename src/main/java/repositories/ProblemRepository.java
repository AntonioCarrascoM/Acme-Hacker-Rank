
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

	//Retrieves the final problems for a certain company
	@Query("select pr from Problem pr where pr.company.id=?1 and pr.finalMode='1'")
	Collection<Problem> finalProblemsOfACompany(int id);

	//Retrieves the problems for a certain position
	@Query("select p.problems from Position p where p.id=?1")
	Collection<Problem> problemsOfAPosition(int id);

	//Problems in final mode by position
	@Query("select distinct p.problems from Position p join p.problems pr where pr.finalMode='1' and p.id=?1")
	Collection<Problem> problemsInFinalModeByPosition(int id);
}
