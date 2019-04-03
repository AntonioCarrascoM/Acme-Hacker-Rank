
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	//Attributes
	private Hacker	hacker;


	//Getters
	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return this.hacker;
	}

	//Setters
	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;
	}
}
