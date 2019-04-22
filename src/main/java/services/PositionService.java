
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

import javax.validation.ConstraintDefinitionException;
import javax.validation.ValidationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;

import repositories.PositionRepository;
import security.Authority;
import domain.Company;
import domain.Finder;
import domain.Message;
import domain.Position;
import domain.Problem;

@Service
@Transactional
public class PositionService {

	//Managed repository

	@Autowired
	private PositionRepository	positionRepository;

	//Supporting services

	@Autowired
	private ActorService		actorService;

	@Autowired
	private Validator			validator;

	@Autowired
	private FinderService		finderService;

	@Autowired
	private MessageService		messageService;


	//Simple CRUD methods

	public Position create() {
		final Position p = new Position();

		final Company c = (Company) this.actorService.findByPrincipal();
		p.setCompany(c);
		p.setTicker(this.generateTicker(p));
		p.setProblems(new ArrayList<Problem>());
		p.setFinalMode(false);
		p.setCancelled(false);

		return p;
	}
	public Position findOne(final int id) {
		Assert.notNull(id);

		return this.positionRepository.findOne(id);
	}

	public Collection<Position> findAll() {
		return this.positionRepository.findAll();
	}

	public Position save(final Position p) {
		Assert.notNull(p);
		final Date date = p.getDeadline();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String year = campos[0];

		Assert.isTrue(!year.startsWith("00"));

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getCompany().getId());

		//A position can only be final mode if it has at least 2 problems
		if (p.getFinalMode() == true)
			Assert.isTrue(p.getFinalMode() == true && this.checkFinalProblems(p) == true);

		final Position saved = this.positionRepository.save(p);

		//TODO Broadcast al guardar finalMode = true la position, necesario? - Sending notification to members
		//		if (saved.getFinalMode() == true)
		//			this.messageService.positionPublished(saved);

		this.notificationForHacker(saved);

		return saved;

	}

	public void delete(final Position p) {
		Assert.notNull(p);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getCompany().getId());

		//Assertion to make sure that the entity is not final
		Assert.isTrue(p.getFinalMode() == false);

		this.positionRepository.delete(p);
	}

	public void cancel(final Position p) {
		Assert.notNull(p);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getCompany().getId());

		p.setCancelled(true);

		this.positionRepository.save(p);
	}

	public Position saveFromProblem(final Position p) {
		final Position saved = this.positionRepository.save(p);
		return saved;
	}

	//Reconstruct

	public Position reconstruct(final Position p, final BindingResult binding) {
		Assert.notNull(p);
		Position result;
		final Authority authCompany = new Authority();
		authCompany.setAuthority(Authority.COMPANY);

		if (p.getId() == 0)
			result = this.create();
		else
			result = this.positionRepository.findOne(p.getId());

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(result.getFinalMode() == false);

		//A position can only be final mode if it has at least 2 problems
		if (p.getFinalMode() == true && this.checkFinalProblems(p) == false)
			throw new ConstraintDefinitionException();

		result.setTitle(p.getTitle());
		result.setDescription(p.getDescription());
		result.setDeadline(p.getDeadline());
		result.setRequiredProfile(p.getRequiredProfile());
		result.setRequiredSkills(p.getRequiredSkills());
		result.setRequiredTech(p.getRequiredTech());
		result.setOfferedSalary(p.getOfferedSalary());
		result.setFinalMode(p.getFinalMode());
		result.setProblems(p.getProblems());

		this.validator.validate(result, binding);

		if (binding.hasErrors())
			throw new ValidationException();

		final Date date = result.getDeadline();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String year = campos[0];

		Assert.isTrue(!year.startsWith("00"));

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == result.getCompany().getId());

		return result;

	}
	//Other methods

	//Generates the first half of the unique tickers.
	private String generateName(final Position p) {
		final String name = p.getCompany().getCommercialName();
		final int length = name.length();
		if (length > 0) {
			if (length >= 4)
				return name.substring(0, 4);
			if (length == 3)
				return name + "X";
			if (length == 2)
				return name + "XX";
			if (length == 1)
				return name + "XXX";
		}
		return name;
	}
	//Generates the numeric part of the unique tickers.
	private String generateNumber() {
		final Random c = new Random();
		String randomString = "";
		int i = 0;
		final int longitud = 4;
		while (i < longitud) {
			randomString += ((char) ((char) c.nextInt(10) + 48)); //numeros
			i++;
		}
		return randomString;
	}

	//Generates both halves of the unique ticker and joins them with a dash.
	public String generateTicker(final Position p) {
		final String res = this.generateName(p) + "-" + this.generateNumber();
		return res;
	}

	//Check final problems
	public Boolean checkFinalProblems(final Position p) {
		int count = 0;
		Boolean res = false;
		if (p.getProblems() == null || p.getProblems().isEmpty())
			return res;
		else
			for (final Problem pr : p.getProblems()) {
				if (pr.getFinalMode() == true)
					count++;
				if (count >= 2) {
					res = true;
					break;
				}
			}
		return res;
	}

	public void notificationForHacker(final Position p) {
		Assert.notNull(p);

		final Collection<Finder> finders = this.finderService.findAll();
		final Message msg = this.messageService.create();

		msg.setSubject("New position / Nuevo puesto de trabajo");
		msg.setBody("A new offer that matches your finder search criteria is published  / Un nuevo puesto de trabajo coincide con tus parametros de busqueda");
		msg.setTags("New position / Nuevo puesto de trabajo");
		msg.setSent(new Date(System.currentTimeMillis() - 1));

		for (final Finder f : finders)
			if (this.checkParamsIsNulls(f) == false) {
				Collection<Position> positions = new ArrayList<>();
				positions = this.finderService.find(f);

				if (positions.contains(p))
					this.messageService.send(msg, this.finderService.getHackerByFinder(f.getId()));
			}

	}

	public Boolean checkParamsIsNulls(final Finder f) {
		if (f.getKeyWord() == null && f.getSpecificDeadline() == null && f.getMinSalary() == null && f.getMaxSalary() == null)
			return true;
		return false;
	}

	//Time for motion and queries

	//Retrieves a list of positions with final mode = true and not cancelled for a certain company
	public Collection<Position> getPublicPositionsForCompany(final int id) {
		return this.positionRepository.getPublicPositionsForCompany(id);
	}

	//Retrieves a list of all positions for a certain company
	public Collection<Position> getAllPositionsForCompany(final int id) {
		return this.positionRepository.getAllPositionsForCompany(id);
	}

	//Retrieves a list of positions with final mode = true and not cancelled
	public Collection<Position> getPublicPositions() {
		return this.positionRepository.getPublicPositions();
	}

	//Retrieves a list of positions given a certain problem
	public Collection<Position> getPositionsOfAProblem(final int id) {
		return this.positionRepository.getPositionsOfAProblem(id);
	}

	//Other methods´

	public void flush() {
		this.positionRepository.flush();
	}

	//The average, the minimum, the maximum, and the standard deviation of the number of positions per company.
	public Double[] avgMinMaxStddevPositionsPerCompany() {
		return this.positionRepository.avgMinMaxStddevPositionsPerCompany();
	}

	//The average, the minimum, the maximum, and the standard deviation of the salaries offered.
	public Double[] avgMinMaxStddevOfferedSalaries() {
		return this.positionRepository.avgMinMaxStddevOfferedSalaries();
	}

	//Positions which a hacker can do applications
	public Collection<Position> positionsForRequestsByHacker(final int id) {
		return this.positionRepository.positionsForRequestsByHacker(id);
	}

	//The best and the worst position in terms of salary
	public String bestAndWorstPositions() {
		final Collection<String> bestPositions = this.positionRepository.bestPositions();
		final Collection<String> worstPositions = this.positionRepository.worstPositions();

		return "[" + ((ArrayList<String>) bestPositions).get(0) + ", " + ((ArrayList<String>) worstPositions).get(0) + "]";
	}

	//Search positions 
	public Collection<Position> searchPosition(final String keyWord) {
		return this.positionRepository.searchPosition(keyWord);
	}
}
