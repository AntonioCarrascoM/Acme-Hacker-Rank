
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

	private String	title;
	private String	statement;
	private String	hint;
	private String	attachments;
	private Boolean	finalMode;

	//Relationships

	private Company	company;


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
	public String getAttachments() {
		return this.attachments;
	}

	@NotNull
	public Boolean getFinalMode() {
		return this.finalMode;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Company getCompany() {
		return this.company;
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

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}
}
