
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Finder;
import domain.Hacker;
import domain.Position;

@Repository
public interface FinderRepository extends JpaRepository<Finder, Integer> {

	//Returns a certain Hacker given his finder id
	@Query("select h from Hacker h where h.finder.id=?1")
	Hacker getHackerByFinder(int id);

	//Search positions 
	@Query("select p from Position p where p.finalMode='1' and p.cancelled='0' and (p.ticker like %?1% or  p.title like %?1% or p.description like %?1% or p.requiredSkills like %?1% or p.requiredTech like %?1% or p.requiredProfile like %?1%) and (p.deadline <= ?2) and (p.offeredSalary between ?3 and ?4))")
	Collection<Position> findPosition(String keyWord, Date specificDeadline, Double minSalary, Double maxSalary);

	@Query("select min(f.positions.size), avg(f.positions.size), stddev(f.positions.size) from Finder f")
	Double[] minMaxAvgAndStddevOfResultsByFinder();

	//The ratio of empty versus non-empty finders
	@Query("select count(f)/(select count(f1) from Finder f1 where f1.positions.size>0) from Finder f where f.positions.size=0")
	Double ratioEmptyVsNonEmptyFinders();
}
