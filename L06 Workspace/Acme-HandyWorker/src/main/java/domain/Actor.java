
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.validation.Valid;

import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.URL;

import security.UserAccount;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Actor extends DomainEntity {

	private String						name;
	private String						address;
	private String						surname;
	private String						middleName;
	private Collection<SocialProfile>	socialProfiles;
	private String						email;
	private String						photo;
	private Collection<MailBox>			mailBoxes;
	private UserAccount					userAccount;
	private Boolean						isBanned;
	private Boolean						isSuspicious;

	//carmen
	private String						phone;


	@ManyToOne(cascade = CascadeType.ALL)
	public UserAccount getUserAccount() {
		return this.userAccount;
	}

	public void setUserAccount(final UserAccount userAccount) {
		this.userAccount = userAccount;
	}

	@NotBlank
	public String getName() {
		return this.name;
	}

	public void setName(final String name) {
		this.name = name;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}
	@NotBlank
	public String getSurname() {
		return this.surname;
	}

	public void setSurname(final String surname) {
		this.surname = surname;
	}

	public String getMiddleName() {
		return this.middleName;
	}

	public void setMiddleName(final String middleName) {
		this.middleName = middleName;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<SocialProfile> getSocialProfiles() {
		return this.socialProfiles;
	}

	public void setSocialProfiles(final Collection<SocialProfile> socialProfiles) {
		this.socialProfiles = socialProfiles;
	}
	@Column(unique = true)
	public String getEmail() {
		return this.email;
	}

	public void setEmail(final String email) {
		this.email = email;
	}

	@URL
	public String getPhoto() {
		return this.photo;
	}

	public void setPhoto(final String photo) {
		this.photo = photo;
	}

	@Valid
	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MailBox> getMailBoxes() {
		return this.mailBoxes;
	}

	public void setMailBoxes(final Collection<MailBox> mailBoxes) {
		this.mailBoxes = mailBoxes;
	}

	public Boolean getIsBanned() {
		return this.isBanned;
	}

	public void setIsBanned(final Boolean isBanned) {
		this.isBanned = isBanned;
	}

	public Boolean getIsSuspicious() {
		return this.isSuspicious;
	}

	public void setIsSuspicious(final Boolean isSuspicious) {
		this.isSuspicious = isSuspicious;
	}

	public String getPhone() {
		return this.phone;
	}

	public void setPhone(final String phone) {
		this.phone = phone;
	}

}
