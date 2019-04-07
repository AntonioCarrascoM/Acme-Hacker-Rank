
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
public class Hacker extends Actor {

	//Attributes
	private Finder	finder;


	//Getters
	@Valid
	@OneToOne(optional = true)
	public Finder getFinder() {
		return this.finder;
	}

	//Setters
	public void setFinder(final Finder finder) {
		this.finder = finder;
	}

}
