
package services;

import java.util.ArrayList;
import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ActorRepository;
import security.LoginService;
import security.UserAccount;
import security.UserAccountRepository;
import domain.Actor;

@Service
@Transactional
public class ActorService {

	// Managed repository

	@Autowired
	private ActorRepository			actorRepository;

	@Autowired
	private UserAccountRepository	userAccountRepository;


	//Supporting services --------------------------------

	//	@Autowired
	//	private ConfigurationService	configurationService;
	//
	//	@Autowired
	//	private MessageService			messageService;

	//Simple CRUD Methods --------------------------------

	public Collection<Actor> findAll() {
		return this.actorRepository.findAll();
	}

	public Actor findOne(final int id) {
		Assert.notNull(id);

		return this.actorRepository.findOne(id);
	}

	public Actor save(final Actor actor) {
		Assert.notNull(actor);

		final Actor saved = this.actorRepository.save(actor);

		return saved;
	}

	public void delete(final Actor actor) {
		Assert.notNull(actor);

		Assert.isTrue(this.findByPrincipal().getId() == actor.getId());

		this.actorRepository.delete(actor);
	}

	// Other business methods ----------------------

	public Actor findByPrincipal() {
		final UserAccount userAccount = LoginService.getPrincipal();
		Assert.notNull(userAccount);
		final Actor a = this.actorRepository.findByUserAccountId(userAccount.getId());
		return a;
	}

	public void hashPassword(final Actor a) {
		final String oldPs = a.getUserAccount().getPassword();
		final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
		final String hash = encoder.encodePassword(oldPs, null);
		final UserAccount old = a.getUserAccount();
		old.setPassword(hash);
		final UserAccount newOne = this.userAccountRepository.save(old);
		a.setUserAccount(newOne);
	}

	public boolean isBannable(final Actor a) {
		Boolean isSpam = false;

		final Collection<Actor> spammerActors = this.bannableActors();
		if (spammerActors.contains(a))
			isSpam = true;

		return isSpam;
	}

	//Method to check the users email adjusts to the given patterns.
	public boolean checkUserEmail(final String email) {
		String parts[] = null;
		String aliasParts[];
		String mailParts[];
		String domainParts[];
		int i = 0;
		Boolean result = true;

		if (!email.contains("@"))
			result = false;

		//Checking the "alias <identifier@domain>" 
		else if (email.contains("<") && email.contains(">")) {
			parts = email.split("\\<");
			final String alias = parts[0];
			aliasParts = alias.split("\\s+");
			//Creating a list to know a size to end the for.
			final Collection<String> aliasList = new ArrayList<String>();
			for (final String s : aliasParts)
				aliasList.add(s);

			//Checking that aliasParts are alpha-numeric
			for (i = 0; i < aliasList.size(); i++)
				if (!aliasParts[i].matches("[A-Za-z0-9]+"))
					result = false;
			final String mail = parts[1].substring(0, parts[1].length() - 1);
			mailParts = mail.split("\\@");
			final String identifier = mailParts[0];
			final String domain = mailParts[1];
			//Checking that identifier is alpha-numeric
			if (!identifier.matches("[A-Za-z0-9]+"))
				result = false;
			else {
				domainParts = domain.split("\\.");
				//Creating a list to know a size to end the for.
				final Collection<String> domainList = new ArrayList<String>();
				for (final String s : domainParts)
					domainList.add(s);
				for (i = 0; i < domainList.size(); i++)
					if (!domainParts[i].matches("[A-Za-z0-9]+"))
						result = false;
			}
		} else {
			//Checking the "identifier@domain" 
			mailParts = email.split("\\@");
			final String identifier = mailParts[0];
			if (!identifier.matches("[A-Za-z0-9]+"))
				result = false;
			else {
				final String domain = mailParts[1];
				domainParts = domain.split("\\.");
				if (domainParts.length <= 1)
					result = false;
				else {
					//Creating a list to know a size to end the for.
					final Collection<String> domainList = new ArrayList<String>();
					for (final String s : domainParts)
						domainList.add(s);
					for (i = 0; i < domainList.size(); i++)
						if (!domainParts[i].matches("[A-Za-z0-9]+"))
							result = false;
				}
			}
		}
		return result;
	}
	//Method to check the Admin email adjusts to the given patterns.
	public boolean checkAdminEmail(final String email) {
		String parts[] = null;
		String aliasParts[];
		String mailParts[];
		int i = 0;
		Boolean result = true;

		if (!email.contains("@"))
			result = false;

		//Checking the "alias <identifier@>" 
		else if (email.contains("<") && email.contains(">")) {
			parts = email.split("\\<");
			final String alias = parts[0];
			aliasParts = alias.split("\\s+");
			//Checking that aliasParts are alpha-numeric
			//Creating a list to know a size to end the for.
			final Collection<String> aliasList = new ArrayList<String>();
			for (final String s : aliasParts)
				aliasList.add(s);

			//Checking that aliasParts are alpha-numeric
			for (i = 0; i < aliasList.size(); i++)
				if (!aliasParts[i].matches("[A-Za-z0-9]+"))
					result = false;

			final String mail = parts[1].substring(0, parts[1].length() - 1);
			mailParts = mail.split("\\@");
			final String identifier = mailParts[0];
			//Checking that identifier is alpha-numeric
			if (!identifier.matches("[A-Za-z0-9]+"))
				result = false;
		} else {
			//Checking the "identifier@" 
			mailParts = email.split("\\@");
			final String identifier = mailParts[0];
			if (!identifier.matches("[A-Za-z0-9]+"))
				result = false;
		}
		return result;
	}

	//Method to check the address is correct. Either null or has content.
	public boolean checkAddress(String address) {
		if (address.equals(""))
			address = null;
		return !StringUtils.isWhitespace(address);
	}
	//Method to check the phone only contains numbers
	public boolean checkPhone(final String phone) {
		Boolean result = true;
		String parts[] = null;
		String parts2[] = null;
		String parts3[] = null;
		String numero;

		if (phone.startsWith("+") && phone.contains("(") && phone.contains(")")) {
			numero = phone.substring(1);

			parts = numero.split("\\(");
			parts2 = parts[1].split("\\)");

			final String partesFinal = parts[0] + parts2[0] + parts2[1];
			if (parts2[0].length() > 3 || parts2[0].length() < 1)
				result = false;
			if (parts[0].length() > 3 || parts[0].length() < 1)
				result = false;
			if (!StringUtils.isNumericSpace(partesFinal))
				result = false;
		}

		if (phone.startsWith("+") && !phone.contains("(") && !phone.contains(")")) {
			numero = phone.substring(1);
			parts3 = numero.split("\\s+");
			if (parts3.length <= 1)
				result = false;
			if (!StringUtils.isNumericSpace(numero))
				result = false;
		}
		if (!phone.startsWith("+") && !StringUtils.isNumericSpace(phone))
			result = false;

		return result;
	}
	//Method to ban or unban an actor
	public void BanOrUnban(final int actorId) {
		final Actor a = this.actorRepository.findOne(actorId);

		final Collection<Actor> bannableActors = this.bannableActors();

		//Checking that the selected actor is bannable.
		Assert.isTrue(bannableActors.contains(a));

		final Boolean ban = a.getUserAccount().getBanned();
		if (ban == true)
			a.getUserAccount().setBanned(false);
		if (ban == false)
			a.getUserAccount().setBanned(true);
	}

	//Ancillary methods
	public Actor findByUserAccountId(final int id) {
		return this.actorRepository.findByUserAccountId(id);
	}

	public Actor getActorByUsername(final String username) {
		return this.actorRepository.getActorByUsername(username);
	}

	// TODO Listing of actors with at least 10% of the messages flagged as spam.
	public Collection<Actor> bannableActors() {
		return this.actorRepository.bannableActors();
	}

	//Listing of actors with at least 1 message sent.
	public Collection<Actor> actorWithSentMessages() {
		return this.actorRepository.actorWithSentMessages();
	}

}
