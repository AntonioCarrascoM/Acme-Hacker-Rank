
package services;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Random;

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
	private ProblemService		problemService;

	@Autowired
	private Validator			validator;


	//Simple CRUD methods

	public Position create() {
		final Position p = new Position();

		final Company c = (Company) this.actorService.findByPrincipal();
		p.setCompany(c);
		p.setTicker(this.generateTicker(p));
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

	public Position save(final Position p, final boolean b) {
		Assert.notNull(p);
		final Date date = p.getDeadline();
		final DateFormat fecha = new SimpleDateFormat("yyyy/MM/dd");
		final String convertido = fecha.format(date);

		final String[] campos = convertido.split("/");
		final String year = campos[0];

		Assert.isTrue(!year.startsWith("00"));

		//Assertion to make sure that the position is not on final mode.
		Assert.isTrue(p.getFinalMode() == false);

		//Assertion that the user modifying this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getCompany().getId());

		//A position can only be final mode if it has at least 2 problems
		if (b == true && this.problemService.problemsOfAPosition(p.getId()).size() >= 2)
			p.setFinalMode(true);

		final Position saved = this.positionRepository.save(p);

		//TODO Broadcast al guardar finalMode = true la position, necesario? - Sending notification to members
		//		if (saved.getFinalMode() == true)
		//			this.messageService.positionPublished(saved);

		return saved;
	}

	public void delete(final Position p) {
		Assert.notNull(p);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getCompany().getId());

		final Collection<Problem> problems = this.problemService.problemsOfAPosition(p.getId());
		//Deleting problems
		if (!problems.isEmpty())
			for (final Problem prob : problems)
				this.problemService.delete(prob);

		this.positionRepository.delete(p);
	}

	public void cancel(final Position p) {
		Assert.notNull(p);

		//Assertion that the user deleting this task has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == p.getCompany().getId());

		p.setCancelled(true);

		this.positionRepository.save(p);
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
		result.setTitle(p.getTitle());
		result.setDescription(p.getDescription());
		result.setDeadline(p.getDeadline());
		result.setRequiredProfile(p.getRequiredProfile());
		result.setRequiredSkills(p.getRequiredSkills());
		result.setRequiredTech(p.getRequiredTech());
		result.setOfferedSalary(p.getOfferedSalary());
		result.setFinalMode(p.getFinalMode());

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
		return p.getCompany().getCommercialName().substring(0, 4);
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

	//Retrieves a list of positions with final mode = true and not cancelled
	public Collection<Position> getPublicPositions() {
		return this.positionRepository.getPublicPositions();
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

	//The best and the worst position in terms of salary
	public String[] bestAndWorstPositions() {
		final String[] results = null;
		final Collection<String> bestPositions = this.positionRepository.bestPositions();
		final Collection<String> worstPositions = this.positionRepository.worstPositions();

		results[0] = ((ArrayList<String>) bestPositions).get(0);
		results[1] = ((ArrayList<String>) worstPositions).get(0);

		return results;

	}
}
