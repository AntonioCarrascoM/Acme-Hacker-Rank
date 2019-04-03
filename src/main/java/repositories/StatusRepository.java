
package repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import domain.Status;

@Repository
public interface StatusRepository extends JpaRepository<Status, Integer> {

}
