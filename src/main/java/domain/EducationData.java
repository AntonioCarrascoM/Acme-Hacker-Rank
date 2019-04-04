
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

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class EducationData extends DomainEntity {

	//Attributes
	private String		degree;
	private String		institution;
	private String		mark;
	private Date		startDate;
	private Date		endDate;

	//Relationships
	private Curriculum	curriculum;


	//Getters

	@NotBlank
	public String getDegree() {
		return this.degree;
	}

	@NotBlank
	public String getInstitution() {
		return this.institution;
	}

	@NotBlank
	public String getMark() {
		return this.mark;
	}

	@NotNull
	@Past
	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getStartDate() {
		return this.startDate;
	}

	@Temporal(TemporalType.DATE)
	@DateTimeFormat(pattern = "dd/MM/yyyy")
	public Date getEndDate() {
		return this.endDate;
	}

	@Valid
	@NotNull
	@ManyToOne(optional = false)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	//Setters
	public void setDegree(final String degree) {
		this.degree = degree;
	}

	public void setInstitution(final String institution) {
		this.institution = institution;
	}

	public void setMark(final String mark) {
		this.mark = mark;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}
}
