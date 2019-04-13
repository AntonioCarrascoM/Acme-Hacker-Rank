
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Application;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Integer> {

	//The applications given a hacker idd
	@Query("select app from Application app where app.hacker.id=?1")
	Collection<Application> applicationsOfAHacker(int id);

	//The average, the minimum, the maximum, and the standard deviation of the number of applications per hacker
	@Query("select avg((select count(a) from Application a where a.hacker.id=h.id)*1.), min((select count(a) from Application a where a.hacker.id=h.id)*1.), max((select count(a) from Application a where a.hacker.id=h.id)*1.), stddev((select count(a) from Application a where a.hacker.id=h.id)*1.) from Hacker h")
	Double[] avgMinMaxStddevApplicationsPerHacker();

}
