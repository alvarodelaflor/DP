
package domain;

import java.util.Collection;
import java.util.Date;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.Past;

@Entity
@Access(AccessType.PROPERTY)
public class FixUp extends DomainEntity {

	private String					ticker;
	private Date					moment;
	private String					description;
	private String					address;
	private Money					maxPrice;
	private Date					startDate;
	private Date					endDate;
	/////////////////////////////////////////////
	private Warranty				warranty;
	private Collection<Application>	applications;
	private Category				category;
	private Collection<Complaint>	complaints;
	private HandyWorker				handyWorker;
	private Customer				customer;


	@ManyToOne(optional = false)
	public Customer getCustomer() {
		return this.customer;
	}

	public void setCustomer(final Customer customer) {
		this.customer = customer;
	}

	@ManyToOne(optional = true)
	public HandyWorker getHandyWorker() {
		return this.handyWorker;
	}

	public void setHandyWorker(final HandyWorker handyWorker) {
		this.handyWorker = handyWorker;
	}

	@OneToMany(mappedBy = "fixUp")
	public Collection<Complaint> getComplaints() {
		return this.complaints;
	}

	public void setComplaints(final Collection<Complaint> complaints) {
		this.complaints = complaints;
	}
	@Column(unique = true)
	public String getTicker() {
		return this.ticker;
	}

	public void setTicker(final String ticker) {
		this.ticker = ticker;
	}
	@Past
	@Temporal(TemporalType.TIMESTAMP)
	public Date getMoment() {
		return this.moment;
	}

	@Temporal(TemporalType.TIMESTAMP)
	public void setMoment(final Date moment) {
		this.moment = moment;
	}

	public String getDescription() {
		return this.description;
	}

	public void setDescription(final String description) {
		this.description = description;
	}

	public String getAddress() {
		return this.address;
	}

	public void setAddress(final String address) {
		this.address = address;
	}

	public Money getMaxPrice() {
		return this.maxPrice;
	}

	public void setMaxPrice(final Money maxPrice) {
		this.maxPrice = maxPrice;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getStartDate() {
		return this.startDate;
	}

	public void setStartDate(final Date startDate) {
		this.startDate = startDate;
	}
	@Temporal(TemporalType.TIMESTAMP)
	public Date getEndDate() {
		return this.endDate;
	}

	public void setEndDate(final Date endDate) {
		this.endDate = endDate;
	}

	@ManyToOne(optional = true)
	public Warranty getWarranty() {
		return this.warranty;
	}

	public void setWarranty(final Warranty warranty) {
		this.warranty = warranty;
	}

	@OneToMany(mappedBy = "fixUp")
	@Valid
	public Collection<Application> getApplications() {
		return this.applications;
	}

	public void setApplications(final Collection<Application> applications) {
		this.applications = applications;
	}

	@ManyToOne(optional = true)
	public Category getCategory() {
		return this.category;
	}

	public void setCategory(final Category category) {
		this.category = category;
	}

}
