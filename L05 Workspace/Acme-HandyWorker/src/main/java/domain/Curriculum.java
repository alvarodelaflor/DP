
package domain;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	private String				ticker;
	///////////////////////////
	private MiscellaneousRecord	misrec;
	private EndorserRecord		endrec;
	private PersonalRecord		perrec;
	private ProfessionalRecord	prorec;
	private EducationalRecord	edurec;
	private HandyWorker			owner;


	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@OneToOne(optional = false)
	public MiscellaneousRecord getMisrec() {
		return this.misrec;
	}

	public void setMisrec(final MiscellaneousRecord misrec) {
		this.misrec = misrec;
	}

	@OneToOne(optional = false)
	public EndorserRecord getEndrec() {
		return this.endrec;
	}

	public void setEndrec(final EndorserRecord endrec) {
		this.endrec = endrec;
	}

	@OneToOne(optional = false)
	public PersonalRecord getPerrec() {
		return this.perrec;
	}

	public void setPerrec(final PersonalRecord perrec) {
		this.perrec = perrec;
	}

	@OneToOne(optional = false)
	public ProfessionalRecord getProrec() {
		return this.prorec;
	}

	public void setProrec(final ProfessionalRecord prorec) {
		this.prorec = prorec;
	}

	@OneToOne(optional = false)
	public EducationalRecord getEdurec() {
		return this.edurec;
	}

	public void setEdurec(final EducationalRecord edurec) {
		this.edurec = edurec;
	}

	@OneToOne(optional = false)
	public HandyWorker getOwner() {
		return this.owner;
	}

	@OneToOne(optional = false)
	public void setOwner(final HandyWorker owner) {
		this.owner = owner;
	}
}
