
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.NotBlank;

@Entity
@Access(AccessType.PROPERTY)
public class Problem extends DomainEntity {

	//Attributes

	private String		title;
	private String		statement;
	private String		hint;
	private String		attatchments;
	private Boolean		finalMode;

	//Relationships

	private Position	position;


	//Getters
	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	public String getHint() {
		return this.hint;
	}

	@NotBlank
	public String getAttatchments() {
		return this.attatchments;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	//Setters
	public void setTitle(final String title) {
		this.title = title;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	public void setHint(final String hint) {
		this.hint = hint;
	}

	public void setAttatchments(final String attatchments) {
		this.attatchments = attatchments;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

}
