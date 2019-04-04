
package services;

import java.util.Collection;
import java.util.Date;

import javax.validation.ValidationException;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.MessageRepository;
import domain.Actor;
import domain.Application;
import domain.Configuration;
import domain.Finder;
import domain.Hacker;
import domain.Message;

@Service
@Transactional
public class MessageService {

	//Managed repository --------------------------------

	@Autowired
	private MessageRepository		messageRepository;

	//Supporting services ----------------------------

	@Autowired
	private ActorService			actorService;

	@Autowired
	private FinderService			finderService;

	@Autowired
	private ConfigurationService	configurationService;

	@Autowired
	private Validator				validator;


	//Simple CRUD methods --------------------------------

	public Message create() {

		final Message message = new Message();

		message.setSender(this.actorService.findByPrincipal());
		message.setSent(new Date(System.currentTimeMillis() - 1));
		message.setSpam(false);
		return message;
	}

	public Collection<Message> findAll() {
		return this.messageRepository.findAll();
	}

	public Message findOne(final int id) {
		Assert.notNull(id);

		return this.messageRepository.findOne(id);
	}

	public Message save(final Message message) {
		Assert.notNull(message);

		//Assertion that the user modifying this message has the correct privilege, that is, he or she is the sender or recipient.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == message.getSender().getId() || this.actorService.findByPrincipal().getId() == message.getRecipient().getId());

		if (message.getId() == 0)
			message.setSent(new Date(System.currentTimeMillis() - 1));

		final Boolean spamBody = this.checkSpam(message.getBody());
		final Boolean spamSubject = this.checkSpam(message.getSubject());
		final Boolean spamTags = this.checkSpam(message.getTags());

		//Sets message as spam if any of those parts contains a spam word.
		if (spamBody == true || spamSubject == true || spamTags == true)
			message.setSpam(true);

		final Message saved = this.messageRepository.save(message);

		return saved;
	}

	public void delete(final Message message) {
		Assert.notNull(message);

		//Assertion that the user modifying this message has the correct privilege, that is, he or she is the sender or recipient.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == message.getSender().getId() || this.actorService.findByPrincipal().getId() == message.getRecipient().getId());

		//Assert.isTrue(this.actorService.findByPrincipal().getId() == boxes.getId());
		if (!message.getTags().contains("DELETED")) {
			final String newTags = message.getTags() + "DELETED";
			message.setTags(newTags);
			this.save(message);

		} else
			this.messageRepository.delete(message);
	}

	//Other business methods ----------------------------

	public void send(final Message m, final Actor a) {
		Assert.notNull(m);
		Assert.notNull(a);

		m.setRecipient(a);
		this.save(m);

	}

	//Checks if a message is spam or not

	public boolean checkSpam(final String s) {
		final Configuration c = this.configurationService.findAll().iterator().next();
		boolean isSpam = false;
		if (s == null || StringUtils.isWhitespace(s))
			return isSpam;
		else {
			for (final String e : c.getSpamWords())
				if (s.contains(e))
					isSpam = true;
			return isSpam;
		}
	}

	//Sends a message to every actors in the system.

	public void broadcastMessage(final Message m) {
		Assert.notNull(m);
		final Collection<Actor> actors = this.actorService.findAll();
		final Actor principal = this.actorService.findByPrincipal();
		actors.remove(principal);

		for (final Actor a : actors)
			this.send(m, a);
	}

	//Sends a message to the member associated to an request.

	public void applicationStatusNotification(final Application p) {
		Assert.notNull(p);

		final Hacker h = p.getHacker();

		final Message msg = this.create();
		msg.setSubject("Application status changed / El estado de la solicitud ha cambiado");
		msg.setBody("Your application status has been changed / El estado de tu solicitud ha sido cambiado.");
		msg.setTags("Application status / Estado de la solicitud");
		msg.setSent(new Date(System.currentTimeMillis() - 1));

		this.send(msg, h);

	}

	//Sends a message to the member associated to an request.
	public void offerMatchesHackerFinderNotification(final Finder f) {
		Assert.notNull(f);

		final Message msg = this.create();
		msg.setSubject("There is a new offer for you / Hay una nueva oferta para ti");
		msg.setBody("There is a new offer that matches with your finder search criteria / Hay una nueva oferta que coincide con tu busqueda del buscador");
		msg.setTags("New offer / Nueva oferta");
		msg.setSent(new Date(System.currentTimeMillis() - 1));

		final Hacker h = this.finderService.getHackerByFinder(f.getId());
		this.send(msg, h);
	}

	//Reconstruct
	public Message reconstruct(final Message m, final BindingResult binding) {
		Message result;

		if (m.getId() == 0)
			result = this.create();
		else
			result = this.messageRepository.findOne(m.getId());

		result.setSubject(m.getSubject());
		result.setBody(m.getBody());
		result.setTags(m.getTags());
		result.setRecipient(m.getRecipient());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		//Assertion that the user modifying this message has the correct privilege, that is, he or she is the sender or recipient.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getSender().getId() || this.actorService.findByPrincipal().getId() == result.getRecipient().getId());

		return result;

	}

	//	//Reconstruct
	//	public Message reconstructBroadcast(final Message m, final BindingResult binding) {
	//		Message result;
	//		final Authority authAdmin = new Authority();
	//		authAdmin.setAuthority(Authority.ADMIN);
	//
	//		if (m.getId() == 0)
	//			result = this.create();
	//		else
	//			result = this.messageRepository.findOne(m.getId());
	//
	//		result.setSubject(m.getSubject());
	//		result.setBody(m.getBody());
	//		result.setTags(m.getTags());
	//
	//		this.validator.validate(result, binding);
	//
	//		if (binding.hasErrors())
	//			throw new ValidationException();
	//
	//		//Assertion that the user modifying this message has the correct privilege, that is, he or she is the sender or recipient.
	//		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getSender().getId() || this.actorService.findByPrincipal().getId() == result.getRecipient().getId());
	//
	//		return result;
	//
	//	}

	//Listing of the messages sent by a certain actor.
	public Collection<Message> sentMessagesForActor(final int id) {
		return this.messageRepository.sentMessagesForActor(id);
	}

	//Listing of the messages received by a certain actor.
	public Collection<Message> receivedMessagesForActor(final int id) {
		return this.messageRepository.receivedMessagesForActor(id);
	}

	public void flush() {
		this.messageRepository.flush();
	}
}
