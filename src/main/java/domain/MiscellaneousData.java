
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
	private String		attachments;

	//Relationships
	private Curriculum	curriculum;


	//Getters
	@NotBlank
	public String getText() {
		return this.text;
	}

	public String getAttachments() {
		return this.attachments;
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

	public void setAttachments(final String attachments) {
		this.attachments = attachments;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}
}
