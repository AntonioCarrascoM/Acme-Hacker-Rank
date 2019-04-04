
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FinderRepository;
import security.Authority;
import domain.Actor;
import domain.Finder;
import domain.Hacker;
import domain.Position;

@Service
@Transactional
public class FinderService {

	//Managed service

	@Autowired
	private FinderRepository		finderRepository;

	//Supporting service

	@Autowired
	private ActorService			actorService;

	@Autowired
	private HackerService			hackerService;

	@Autowired
	private ConfigurationService	configurationService;


	//Simple CRUD methods --------------------------------

	public Finder create() {
		final Finder f = new Finder();
		f.setPositions(new ArrayList<Position>());
		return f;
	}

	public Finder findOne(final int id) {
		Assert.notNull(id);
		return this.finderRepository.findOne(id);
	}

	public Collection<Finder> findAll() {
		return this.finderRepository.findAll();
	}

	public Finder save(final Finder f) {
		Assert.notNull(f);
		//Assertion that the user modifying this finder has the correct privilege.
		Assert.isTrue(f.getId() == this.findPrincipalFinder().getId());//this.findPrincipalFinder().getId()
		//If all fields of the finder are null, the finder returns the entire listing of available tasks.
		f.setMoment(new Date(System.currentTimeMillis() - 1));
		final Finder saved = this.finderRepository.save(f);

		return saved;
	}

	public void delete(final Finder f) {
		Assert.notNull(f);

		//Assertion that the user deleting this finder has the correct privilege.
		Assert.isTrue(this.actorService.findByPrincipal().getId() == this.hackerService.hackerByFinder(f.getId()).getId());

		this.finderRepository.delete(f);
	}

	public Finder findPrincipalFinder() {
		final Actor a = this.actorService.findByPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.HACKER);
		Assert.isTrue(a.getUserAccount().getAuthorities().contains(auth));

		final Hacker h = (Hacker) this.actorService.findOne(a.getId());
		Finder fd = new Finder();
		if (h.getFinder() == null) {
			fd = this.create();
			final Finder saved = this.finderRepository.save(fd);
			h.setFinder(saved);
			this.hackerService.save(h);
			return saved;
		} else
			return h.getFinder();
	}

	public Collection<Position> find(final Finder finder) {
		Assert.notNull(finder);

		Collection<Position> results = new ArrayList<>();

		String keyWord = finder.getKeyWord();
		Date specificDeadline = finder.getSpecificDeadline(), maxDeadline = finder.getMaxDeadline();
		Double minimunSalary = finder.getMinimumSalary();

		if (finder.getKeyWord() == null)
			keyWord = "";
		if (specificDeadline == null)
			specificDeadline = new Date(631152000L);
		if (maxDeadline == null)
			maxDeadline = new Date(2524694400000L);
		if (minimunSalary == null)
			minimunSalary = 0.;

		final Collection<Position> positions = this.finderRepository.findPosition(keyWord, specificDeadline, maxDeadline, minimunSalary);

		results = this.limitResults(positions);
		return results;
	}

	public Collection<Position> limitResults(final Collection<Position> positions) {
		Collection<Position> results = new ArrayList<>();
		final int maxResults = this.configurationService.findAll().iterator().next().getMaxFinderResults();
		if (positions.size() > maxResults)
			results = new ArrayList<Position>(((ArrayList<Position>) positions).subList(0, maxResults));
		else
			results = positions;
		return results;
	}

	//
	//	//The minimum, the maximum, the average, and the standard deviation of the number of results in the finders.
	//	public Double[] minMaxAvgStddevResultsFinders() {
	//		return this.finderRepository.minMaxAvgAndStddevOfResultsByFinder();
	//	}
	//
	//	//  The ratio of empty versus non-empty finders
	//	public Double ratioEmptyVersusNonEmptyFinders() {
	//		Double res = this.finderRepository.ratioEmptyVsNonEmptyFinders();
	//		if (res == null)
	//			res = 0.0;
	//		return res;
	//	}

	//Returns a certain Hacker given his finder id
	public Hacker getHackerByFinder(final int id) {
		return this.finderRepository.getHackerByFinder(id);
	}
}
