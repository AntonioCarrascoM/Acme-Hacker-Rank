
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
}
