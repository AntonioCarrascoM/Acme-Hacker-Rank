
package domain;

import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.NotBlank;
import org.springframework.format.annotation.DateTimeFormat;

@Entity
@Access(AccessType.PROPERTY)
public class Position extends DomainEntity {

	//Attributes

	private String	ticker;
	private String	title;
	private String	description;
	private Date	deadline;
	private String	requiredProfile;
	private String	requiredSkills;
	private String	requiredTech;
	private Double	offeredSalary;
	private Boolean	finalMode;

	//Relationships

	private Company	company;


	//Getters
	@Column(unique = true)
	@NotBlank
	@Pattern(regexp = "\\w{4}-\\d{4}")
	public String getTicker() {
		return this.ticker;
	}

	@NotBlank
	public String getTitle() {
		return this.title;
	}

	@NotBlank
	public String getDescription() {
		return this.description;
	}

	@NotNull
	@Temporal(TemporalType.TIMESTAMP)
	@DateTimeFormat(pattern = "dd/MM/yyyy HH:mm")
	public Date getDeadLine() {
		return this.deadline;
	}

	@NotBlank
	public String getRequiredProfile() {
		return this.requiredProfile;
	}

	@NotBlank
	public String getRequiredSkills() {
		return this.requiredSkills;
	}

	@NotBlank
	public String getRequiredTech() {
		return this.requiredTech;
	}

	@Min(0)
	public Double getOfferedSalary() {
		return this.offeredSalary;
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
	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	public void setTitle(final String title) {
		this.title = title;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public void setDeadLine(final Date deadline) {
		this.deadline = deadline;
	}

	public void setRequiredProfile(final String requiredProfile) {
		this.requiredProfile = requiredProfile;
	}

	public void setRequiredSkills(final String requiredSkills) {
		this.requiredSkills = requiredSkills;
	}

	public void setRequiredTech(final String requiredTech) {
		this.requiredTech = requiredTech;
	}

	public void setOfferedSalary(final Double offeredSalary) {
		this.offeredSalary = offeredSalary;
	}

	public void setFinalMode(final Boolean finalMode) {
		this.finalMode = finalMode;
	}

	public void setCompany(final Company company) {
		this.company = company;
	}
}
