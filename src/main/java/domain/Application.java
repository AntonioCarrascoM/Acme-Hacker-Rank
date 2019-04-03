
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.hibernate.validator.constraints.URL;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Application extends DomainEntity {

	//Attributes

	private Date		moment;
	private Problem		problem;
	private String		answerDescription;
	private String		answerLink;
	private Date		answerMoment;
	private Status		status;

	//Relationships

	private Position	position;
	private Hacker		hacker;


	//Getters

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@NotNull
	@Valid
	@ManyToOne(optional = false)
	public Problem getProblem() {
		return this.problem;
	}

	public String getAnswerDescription() {
		return this.answerDescription;
	}

	@URL
	public String getAnswerLink() {
		return this.answerLink;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getAnswerMoment() {
		return this.answerMoment;
	}

	@NotNull
	@Valid
	public Status getStatus() {
		return this.status;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Position getPosition() {
		return this.position;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Hacker getHacker() {
		return this.hacker;
	}

	//Setters
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setProblem(final Problem problem) {
		this.problem = problem;
	}

	public void setAnswerDescription(final String answerDescription) {
		this.answerDescription = answerDescription;
	}

	public void setAnswerLink(final String answerLink) {
		this.answerLink = answerLink;
	}

	public void setAnswerMoment(final Date answerMoment) {
		this.answerMoment = answerMoment;
	}

	public void setStatus(final Status status) {
		this.status = status;
	}

	public void setPosition(final Position position) {
		this.position = position;
	}

	public void setHacker(final Hacker hacker) {
		this.hacker = hacker;
	}
}
