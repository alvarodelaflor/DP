
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

@Entity
@Access(AccessType.PROPERTY)
public class Curriculum extends DomainEntity {

	private String							ticker;
	///////////////////////////
	private Collection<MiscellaneousRecord>	misrec;
	private Collection<EndorserRecord>		endrec;
	private PersonalRecord					perrec;
	private Collection<ProfessionalRecord>	prorec;
	private Collection<EducationalRecord>	edurec;


	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<MiscellaneousRecord> getMisrec() {
		return this.misrec;
	}

	public void setMisrec(final Collection<MiscellaneousRecord> misrec) {
		this.misrec = misrec;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EndorserRecord> getEndrec() {
		return this.endrec;
	}

	public void setEndrec(final Collection<EndorserRecord> endrec) {
		this.endrec = endrec;
	}

	@OneToOne(optional = true)
	public PersonalRecord getPerrec() {
		return this.perrec;
	}

	public void setPerrec(final PersonalRecord perrec) {
		this.perrec = perrec;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<ProfessionalRecord> getProrec() {
		return this.prorec;
	}

	public void setProrec(final Collection<ProfessionalRecord> prorec) {
		this.prorec = prorec;
	}

	@OneToMany(cascade = CascadeType.ALL)
	public Collection<EducationalRecord> getEdurec() {
		return this.edurec;
	}

	public void setEdurec(final Collection<EducationalRecord> edurec) {
		this.edurec = edurec;
	}
}
