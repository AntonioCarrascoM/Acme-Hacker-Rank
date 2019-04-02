
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class MiscellaneousData extends DomainEntity {

	//Attributes
	private String		text;
	private String		attatchments;

	//Relationships
	private Curriculum	curriculum;


	//Getters
	@NotBlank
	public String getText() {
		return this.text;
	}

	public String getAttatchments() {
		return this.attatchments;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	//Setters
	public void setText(final String text) {
		this.text = text;
	}

	public void setAttatchments(final String attatchments) {
		this.attatchments = attatchments;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}
}
