
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
public class PersonalData extends DomainEntity {

	//Attributes
	private String		fullName;
	private String		statement;
	private String		phoneNumber;
	private String		gitHubProfile;
	private String		linkedInProfile;

	//Relationships
	private Curriculum	curriculum;


	//Getters
	@NotBlank
	public String getFullName() {
		return this.fullName;
	}

	@NotBlank
	public String getStatement() {
		return this.statement;
	}

	@NotBlank
	public String getPhoneNumber() {
		return this.phoneNumber;
	}

	@NotBlank
	public String getGitHubProfile() {
		return this.gitHubProfile;
	}

	@NotBlank
	public String getLinkedInProfile() {
		return this.linkedInProfile;
	}

	@Valid
	@NotNull
	@OneToOne(optional = false)
	public Curriculum getCurriculum() {
		return this.curriculum;
	}

	//Setters
	public void setFullName(final String fullName) {
		this.fullName = fullName;
	}

	public void setStatement(final String statement) {
		this.statement = statement;
	}

	public void setPhoneNumber(final String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public void setGitHubProfile(final String gitHubProfile) {
		this.gitHubProfile = gitHubProfile;
	}

	public void setLinkedInProfile(final String linkedInProfile) {
		this.linkedInProfile = linkedInProfile;
	}

	public void setCurriculum(final Curriculum curriculum) {
		this.curriculum = curriculum;
	}
}
