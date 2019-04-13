
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	//The companies that have offered more positions
	@Query("select c.name from Company c order by ((select count(p) from Position p where p.finalMode=true and p.company.id=c.id)*1.) desc")
	Collection<String> companiesWithMoreOfferedPossitions();

}
