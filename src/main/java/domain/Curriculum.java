
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	//Attributes
	private Hacker	hacker;


	//Getters
	public Hacker getHacker() {
		return this.hacker;
	}

	//Setters
	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;
	}
}
