
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Hacker;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//Returns a certain Hacker given his finder id
	@Query("select h from Hacker h where h.finder.id=?1")
	Hacker getHackerByFinder(int id);
}
