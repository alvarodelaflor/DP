
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Actor;
import domain.Administrator;
import domain.Customer;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class ActorServiceTest extends AbstractTest {

	@Autowired
	private ActorService			actorService;
	@Autowired
	private CustomerService			customerService;
	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void testBanned() {
		final Administrator administrator = this.administratorService.createFirstAdmin();
		administrator.setName("Ana");
		administrator.setSurname("navarro");
		administrator.getUserAccount().setUsername("adminUser");
		administrator.getUserAccount().setPassword("12345678");
		final Administrator saveAdministrator = this.administratorService.save(administrator);
		Assert.isTrue(this.administratorService.findAll().contains(saveAdministrator));
		super.authenticate("adminUser");
		final Collection<Actor> banneds = this.actorService.findActorsBanned();
		final int sizeBannedsBefore = banneds.size();
		final Customer customer1 = this.customerService.create();
		customer1.setName("Alvaro");
		customer1.setSurname("viso");
		customer1.setIsBanned(true);
		final Customer saveCustomer1 = this.customerService.save(customer1);
		final Customer customer2 = this.customerService.create();
		customer2.setName("Alvaro");
		customer2.setSurname("viso");
		customer2.setIsBanned(false);
		final Customer saveCustomer2 = this.customerService.save(customer2);
		final Collection<Actor> banneds2 = this.actorService.findActorsBanned();
		final int sizeBannedsAfter = banneds2.size();
		Assert.isTrue(sizeBannedsBefore + 1 == sizeBannedsAfter);
	}

	@Test
	public void testSuspicious() {
		final Administrator administrator = this.administratorService.createFirstAdmin();
		administrator.setName("Ana");
		administrator.setSurname("navarro");
		administrator.getUserAccount().setUsername("adminUser");
		administrator.getUserAccount().setPassword("12345678");
		final Administrator saveAdministrator = this.administratorService.save(administrator);
		Assert.isTrue(this.administratorService.findAll().contains(saveAdministrator));
		super.authenticate("adminUser");
		final Collection<Actor> suspicious = this.actorService.findActorsSuspicious();
		final int sizeSuspiciousBefore = suspicious.size();
		final Customer customer1 = this.customerService.create();
		customer1.setName("Alvaro");
		customer1.setSurname("viso");
		customer1.setIsSuspicious(true);
		final Customer saveCustomer1 = this.customerService.save(customer1);
		final Customer customer2 = this.customerService.create();
		customer2.setName("Alvaro");
		customer2.setSurname("viso");
		customer2.setIsSuspicious(false);
		final Customer saveCustomer2 = this.customerService.save(customer2);
		final Collection<Actor> suspicious2 = this.actorService.findActorsSuspicious();
		final int sizeSuspiciousAfter = suspicious2.size();
		Assert.isTrue(sizeSuspiciousBefore + 1 == sizeSuspiciousAfter);
	}

	@Test
	public void testBan() {
		// Creo un customer
		final Customer customer1 = this.customerService.create();
		customer1.setName("Alvaro");
		customer1.setSurname("viso");
		customer1.setIsBanned(false);
		final Customer saveCustomer = this.customerService.save(customer1);
		// Logueo un administrador
		final Administrator administrator = this.administratorService.createFirstAdmin();
		administrator.setName("Ana");
		administrator.setSurname("navarro");
		administrator.getUserAccount().setUsername("adminUser");
		administrator.getUserAccount().setPassword("12345678");
		final Administrator saveAdministrator = this.administratorService.save(administrator);
		Assert.isTrue(this.administratorService.findAll().contains(saveAdministrator));
		super.authenticate("adminUser");
		final Boolean isBanBefore = saveCustomer.getIsBanned();
		this.actorService.banActor(saveCustomer);
		final Boolean isBanAfter = saveCustomer.getIsBanned();
		Assert.isTrue(isBanAfter != isBanBefore);
	}

	@Test
	public void testUnBan() {
		// Creo un customer
		final Customer customer1 = this.customerService.create();
		customer1.setName("Alvaro");
		customer1.setSurname("viso");
		customer1.setIsBanned(true);
		final Customer saveCustomer = this.customerService.save(customer1);
		// Logueo un administrador
		final Administrator administrator = this.administratorService.createFirstAdmin();
		administrator.setName("Ana");
		administrator.setSurname("navarro");
		administrator.getUserAccount().setUsername("adminUser");
		administrator.getUserAccount().setPassword("12345678");
		final Administrator saveAdministrator = this.administratorService.save(administrator);
		Assert.isTrue(this.administratorService.findAll().contains(saveAdministrator));
		super.authenticate("adminUser");
		final Boolean isBanBefore = saveCustomer.getIsBanned();
		this.actorService.unBanActor(saveCustomer);
		final Boolean isBanAfter = saveCustomer.getIsBanned();
		Assert.isTrue(isBanAfter != isBanBefore);
	}
}
