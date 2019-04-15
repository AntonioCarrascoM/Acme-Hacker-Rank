
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.userAccount.id = ?1")
	Actor findByUserAccountId(int id);

	@Query("select a from Actor a where a.userAccount.username= ?1")
	Actor getActorByUsername(String username);

	//Listing of actors with spammer flag activated
	@Query("select distinct a from Actor a where a.spammer='1'")
	Collection<Actor> spammerActors();

	//Listing of actors with at least 10% of the messages flagged as spam.
	@Query("select distinct a from Actor a where (select count(m) from Message m where m.sender.id=a.id and m.spam='1')>(select count(m1)*0.1 from Message m1 where m1.sender.id=a.id)")
	Collection<Actor> bannableActors();

	//Listing of actors with at least 1 message sent.
	@Query("select distinct m.sender from Message m")
	Collection<Actor> actorWithSentMessages();
}
