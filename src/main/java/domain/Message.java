
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
@Table(indexes = {
	@Index(columnList = "spam")
})
public class Message extends DomainEntity {

	//Attributes

	private Date	sent;
	private String	subject;
	private String	body;
	private Boolean	spam;
	private String	tags;

	//Relationship

	private Actor	sender;
	private Actor	recipient;


	//Getters

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSent() {
		return this.sent;
	}

	@NotBlank
	public String getSubject() {
		return this.subject;
	}

	@NotBlank
	public String getBody() {
		return this.body;
	}

	public Boolean getSpam() {
		return this.spam;
	}

	@NotBlank
	public String getTags() {
		return this.tags;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getSender() {
		return this.sender;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Actor getRecipient() {
		return this.recipient;
	}
	//Setters

	public void setSent(final Date sent) {
		this.sent = sent;
	}

	public void setSubject(final String subject) {
		this.subject = subject;
	}

	public void setBody(final String body) {
		this.body = body;
	}

	public void setSpam(final Boolean spam) {
		this.spam = spam;
	}

	public void setTags(final String tags) {
		this.tags = tags;
	}

	public void setSender(final Actor sender) {
		this.sender = sender;
	}

	public void setRecipient(final Actor recipient) {
		this.recipient = recipient;
	}

}
