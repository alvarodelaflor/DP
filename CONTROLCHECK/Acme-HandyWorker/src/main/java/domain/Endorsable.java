
package domain;

import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.OneToMany;
import javax.validation.Valid;

@Entity
@Access(AccessType.PROPERTY)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class Endorsable extends Actor {

	private List<Endorsement>	endorsementReceiver, endorsementSender;
	private Double				calification;


	@OneToMany(mappedBy = "endorsableReceiver", cascade = CascadeType.ALL)
	@Valid
	public List<Endorsement> getEndorsementReceiver() {
		return this.endorsementReceiver;
	}

	public void setEndorsementReceiver(final List<Endorsement> endorsementReceiver) {
		this.endorsementReceiver = endorsementReceiver;
	}

	@OneToMany(mappedBy = "endorsableSender", cascade = CascadeType.ALL)
	@Valid
	public List<Endorsement> getEndorsementSender() {
		return this.endorsementSender;
	}

	public void setEndorsementSender(final List<Endorsement> endorsementSender) {
		this.endorsementSender = endorsementSender;
	}

	public Double getCalification() {
		return this.calification;
	}

	public void setCalification(final Double calification) {
		this.calification = calification;
	}

}
