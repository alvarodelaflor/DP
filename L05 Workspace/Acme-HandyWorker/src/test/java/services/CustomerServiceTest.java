
package services;

import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Customer;
import domain.FixUp;
import domain.HandyWorker;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class CustomerServiceTest extends AbstractTest {

	//Services under Test

	@Autowired
	private CustomerService			customerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private FixUpService			fixUpService;

	@Autowired
	private HandyWorkerService		handyWorkerService;


	@Test
	public void testSaveCustomer() {
		final Customer customer = this.customerService.create();
		customer.setName("Alvaro");
		customer.setSurname("alvaro");
		final Customer saveCustomer = this.customerService.save(customer);
		Assert.isTrue(this.customerService.findAll().contains(saveCustomer));
	}

	@Test
	public void testCreateCustomer() {
		final Customer customer = this.customerService.create();
		Assert.notNull(customer);
	}

	@Test
	public void test1259() {
		final Administrator adminLogin = this.administratorService.createFirstAdmin();
		adminLogin.setName("Alvaro");
		adminLogin.setSurname("alvaro");
		adminLogin.getUserAccount().setUsername("dogran1");
		adminLogin.getUserAccount().setPassword("123456789");
		final Administrator saveAdministrator = this.administratorService.save(adminLogin);
		super.authenticate("dogran1");
		final Collection<Customer> customers = this.customerService.betterCustomer();
		final List<Customer> customersList = (List<Customer>) customers;
		Assert.isTrue(customersList.get(0).getId() == 455);
	}

	@Test
	public void testFindCustomerByFixUp() {
		//CREAR Y ATENTICAR CUSTOMER PARA CREAR FIXUP
		final Customer customer = this.customerService.create();
		customer.setName("Alvaro");
		customer.setSurname("alvaro");
		customer.getUserAccount().setUsername("dogran");
		customer.getUserAccount().setPassword("123456789");
		final Customer saveCustomer = this.customerService.save(customer);
		super.authenticate("dogran");
		final FixUp fixUp1 = this.fixUpService.create();
		final FixUp saveFixUp1 = this.fixUpService.save(fixUp1);
		//CREAR Y AUTENTICAR HW
		final HandyWorker handyWorker = this.handyWorkerService.create();
		handyWorker.setName("Ferrete");
		handyWorker.setSurname("Ferrete");
		handyWorker.getUserAccount().setUsername("dogran2");
		handyWorker.getUserAccount().setPassword("123456789");
		final HandyWorker saveHandyWorker = this.handyWorkerService.save(handyWorker);
		super.unauthenticate();
		super.authenticate("dogran2");
		final Customer owner = this.customerService.findOwner(saveFixUp1);
		//Comprobar que el customer obtenido es el mismo que cre� la FixUp
		Assert.isTrue(owner.getId() == saveCustomer.getId());

	}
	//----------------MARI-----------------
	@Test
	public void testUpdateCustomer() {
		final Customer cus = this.customerService.create();
		//
		cus.setName("Ana");
		cus.setSurname("navarro");
		cus.getUserAccount().setUsername("anita");
		cus.getUserAccount().setPassword("123456");
		//

		final Customer customer = this.customerService.save(cus);
		//

		super.authenticate("anita");
		//

		customer.setName("Anasssss");
		customer.setSurname("navarrosssss");
		final Customer saveCus2 = this.customerService.update(customer);

		Assert.isTrue(this.customerService.findAll().contains(saveCus2));
	}

	//
	@Test
	public void testRegisterCustomer() {
		final Customer ana = this.customerService.create();
		ana.setName("Ana");
		ana.setSurname("navarro");
		final Customer saveAna = this.customerService.isRegister(ana);
		Assert.isTrue(this.customerService.findAll().contains(saveAna));

	}

}
