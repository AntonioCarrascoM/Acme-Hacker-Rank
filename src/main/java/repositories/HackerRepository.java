
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

	//The hackers who have made more applications
	@Query("select u.username from Hacker h join h.userAccount u order by ((select count(a) from Application a where a.hacker.id=h.id)*1.) desc")
	Collection<String> hackersWithMoreApplications();

}
