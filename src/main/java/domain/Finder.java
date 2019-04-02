
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Finder extends DomainEntity {

	//Attributes

	private String					keyWord;
	private Date					specificDeadline;
	private Double					minimumSalary;
	private Date					maxDeadline;
	private Date					moment;

	//Relationships

	private Collection<Position>	positions;


	//Getters

	public String getKeyWord() {
		return this.keyWord;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getSpecificDeadline() {
		return this.specificDeadline;
	}

	@Min(0)
	public Double getMinimumSalary() {
		return this.minimumSalary;
	}

	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMaxDeadline() {
		return this.maxDeadline;
	}

	@Past
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getMoment() {
		return this.moment;
	}

	@Valid
	@NotNull
	@ManyToMany
	public Collection<Position> getPositions() {
		return this.positions;
	}

	//Setters

	public void setKeyWord(final String keyWord) {
		this.keyWord = keyWord;
	}

	public void setSpecificDeadline(final Date specificDeadline) {
		this.specificDeadline = specificDeadline;
	}

	public void setMinimumSalary(final Double minimumSalary) {
		this.minimumSalary = minimumSalary;
	}

	public void setMaxDeadline(final Date maxDeadline) {
		this.maxDeadline = maxDeadline;
	}

	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public void setPositions(final Collection<Position> positions) {
		this.positions = positions;
	}
}
