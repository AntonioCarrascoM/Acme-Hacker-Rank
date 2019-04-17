
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.Table;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "commercialName")
})
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
