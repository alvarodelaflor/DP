
package services;

import java.util.Date;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Complaint;
import domain.Customer;
import domain.FixUp;
import domain.Referee;
import domain.Report;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ReportServiceTest extends AbstractTest {

	@Autowired
	private ReportService			reportService;
	@Autowired
	private AdministratorService	administratorService;
	@Autowired
	private RefereeService			refereeService;
	@Autowired
	private ComplaintService		complaintService;
	@Autowired
	private FixUpService			fixUpService;
	@Autowired
	private CustomerService			customerService;


	//CARMEN
	@Test
	public void testCreate() {

		final Customer customer = this.customerService.create();
		customer.setName("Alvaro");
		customer.setSurname("alvaro");
		customer.getUserAccount().setUsername("dogran");
		customer.getUserAccount().setPassword("123456789");
		final Customer saveCustomer = this.customerService.save(customer);
		super.authenticate("dogran");

		final FixUp fixUp = this.fixUpService.create();
		@SuppressWarnings("deprecation")
		final Date startDate = new Date(2019, 11, 11);
		@SuppressWarnings("deprecation")
		final Date endDate = new Date(2019, 12, 11);
		fixUp.setStartDate(startDate);
		fixUp.setEndDate(endDate);
		fixUp.setAddress("AddressTest");
		fixUp.setDescription("DescriptionTest");
		final FixUp saveFixUp = this.fixUpService.save(fixUp);

		final Complaint c1 = this.complaintService.create();
		c1.setFixUp(saveFixUp);
		c1.setDescription("DescriptionTest");
		final Complaint saveComplaint1 = this.complaintService.save(c1);
		super.unauthenticate();

		final Administrator administrator = this.administratorService.createFirstAdmin();
		administrator.setName("Ana");
		administrator.setSurname("navarro");
		administrator.getUserAccount().setUsername("adminUser");
		administrator.getUserAccount().setPassword("12345678");
		final Administrator saveAdministrator = this.administratorService.save(administrator);
		Assert.isTrue(this.administratorService.findAll().contains(saveAdministrator));
		super.authenticate("adminUser");
		final Referee referee = this.refereeService.create();
		referee.setName("Alvaro");
		referee.setSurname("alvaro");
		referee.getUserAccount().setUsername("refereeUser");
		referee.getUserAccount().setPassword("12345678");
		final Referee saveReferee = this.refereeService.save(referee);
		super.authenticate("refereeUser");

		final Boolean refereeBefore = saveComplaint1.getReferee() != null;
		this.complaintService.setReefereeToAComplaint(saveComplaint1);
		final Boolean refereeAfter = saveComplaint1.getReferee() != null;
		Assert.isTrue(refereeAfter != refereeBefore);

		final Report re = this.reportService.create();
		re.setComplaint(saveComplaint1);
		re.setDescription("test");
		final Report save = this.reportService.save(re);

		Assert.isTrue(this.reportService.findAll().contains(save));
	}

	@Test
	public void testUpdate() {

		final Customer customer = this.customerService.create();
		customer.setName("Alvaro");
		customer.setSurname("alvaro");
		customer.getUserAccount().setUsername("dogran");
		customer.getUserAccount().setPassword("123456789");
		final Customer saveCustomer = this.customerService.save(customer);
		super.authenticate("dogran");

		final FixUp fixUp = this.fixUpService.create();
		@SuppressWarnings("deprecation")
		final Date startDate = new Date(2019, 11, 11);
		@SuppressWarnings("deprecation")
		final Date endDate = new Date(2019, 12, 11);
		fixUp.setStartDate(startDate);
		fixUp.setEndDate(endDate);
		fixUp.setAddress("AddressTest");
		fixUp.setDescription("DescriptionTest");
		final FixUp saveFixUp = this.fixUpService.save(fixUp);

		final Complaint c1 = this.complaintService.create();
		c1.setFixUp(saveFixUp);
		c1.setDescription("descriptionTes");
		final Complaint saveComplaint1 = this.complaintService.save(c1);
		super.unauthenticate();

		final Administrator administrator = this.administratorService.createFirstAdmin();
		administrator.setName("Ana");
		administrator.setSurname("navarro");
		administrator.getUserAccount().setUsername("adminUser");
		administrator.getUserAccount().setPassword("12345678");
		final Administrator saveAdministrator = this.administratorService.save(administrator);
		Assert.isTrue(this.administratorService.findAll().contains(saveAdministrator));
		super.authenticate("adminUser");
		final Referee referee = this.refereeService.create();
		referee.setName("Alvaro");
		referee.setSurname("alvaro");
		referee.getUserAccount().setUsername("refereeUser");
		referee.getUserAccount().setPassword("12345678");
		final Referee saveReferee = this.refereeService.save(referee);
		super.authenticate("refereeUser");

		final Boolean refereeBefore = saveComplaint1.getReferee() != null;
		this.complaintService.setReefereeToAComplaint(saveComplaint1);
		final Boolean refereeAfter = saveComplaint1.getReferee() != null;
		Assert.isTrue(refereeAfter != refereeBefore);

		final Report re = this.reportService.create();
		re.setComplaint(saveComplaint1);
		final Report save = this.reportService.save(re);
		save.setDescription("Descripción editada");
		final Report saveReport = this.reportService.update(save);
		Assert.isTrue(this.reportService.findAll().contains(saveReport));
	}
}
