
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;

@Entity
@Access(AccessType.PROPERTY)
public class Company extends Actor {

	//Attributes
	private String	commercialName;


	//Getters
	public String getCommercialName() {
		return this.commercialName;
	}

	//Setters
	public void setCommercialName(final String commercialName) {
		this.commercialName = commercialName;
	}

}
