
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Hacker;

@Repository
public interface HackerRepository extends JpaRepository<Hacker, Integer> {

	@Query("select h from Hacker h where h.finder.id=?1")
	Hacker hackerByFinder(int id);

	//The average, the minimum, the maximum, and the standard deviation of the number of applications per hacker
	@Query("select avg((select count(a) from Application a where a.hacker.id=h.id)*1.), min((select count(a) from Application a where a.hacker.id=h.id)*1.), max((select count(a) from Application a where a.hacker.id=h.id)*1.), stddev((select count(a) from Application a where a.hacker.id=h.id)*1.) from Hacker h")
	Double[] avgMinMaxStddevApplicationsPerHacker();

	//The hackers who have made more applications
	@Query("select h from Hacker h order by ((select count(a) from Application a where a.hacker.id=h.id)*1.) desc")
	Collection<Hacker> hackersWithMoreApplications();

	//The minimum, the maximum, the average, and the standard deviation of the number of curricula per hacker
	@Query("select min((select count(c) from Curriculum c where c.hacker.id=h.id)*1.), max((select count(c) from Curriculum c where c.hacker.id=h.id)*1.), avg((select count(c) from Curriculum c where c.hacker.id=h.id)*1.), stddev((select count(c) from Curriculum c where c.hacker.id=h.id)*1.) from Hacker h")
	Double[] minMaxAvgStddevCurriculaPerHacker();
}
