
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.Message;

@Repository
public interface MessageRepository extends JpaRepository<Message, Integer> {

	//Listing of the messages sent by a certain actor.
	@Query("select m from Message m where m.sender.id=?1")
	Collection<Message> sentMessagesForActor(int id);

	//Listing of the messages sent by a certain actor.
	@Query("select m from Message m where m.receiver.id=?1")
	Collection<Message> receivedMessagesForActor(int id);

}
