
package services;

import javax.transaction.Transactional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Referee;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class RefereeServiceTest extends AbstractTest {

	//Services under Test

	@Autowired
	private RefereeService			refereeService;
	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void testSaveReferee() {
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
		final Referee saveReferee = this.refereeService.save(referee);
		Assert.isTrue(this.refereeService.findAll().contains(saveReferee));
	}

	@Test
	public void testUpdateReferee() {
		final Administrator administrator = this.administratorService.createFirstAdmin();
		administrator.setName("Ana");
		administrator.setSurname("navarro");
		administrator.getUserAccount().setUsername("adminUser");
		administrator.getUserAccount().setPassword("12345678");
		final Administrator saveAdministrator = this.administratorService.save(administrator);
		Assert.isTrue(this.administratorService.findAll().contains(saveAdministrator));
		super.authenticate("adminUser");
		final Referee referee = this.refereeService.create();
		//
		referee.setName("Ana");
		referee.setSurname("navarro");
		referee.getUserAccount().setUsername("anita");
		referee.getUserAccount().setPassword("123456");
		//

		final Referee referee2 = this.refereeService.save(referee);
		//

		super.authenticate("anita");
		//

		referee2.setName("Anasssss");
		referee2.setSurname("navarrosssss");
		final Referee saveReferee2 = this.refereeService.update(referee2);

		Assert.isTrue(this.refereeService.findAll().contains(saveReferee2));
	}

	@Test
	public void testRegisterReferee() {
		final Administrator administrator = this.administratorService.createFirstAdmin();
		administrator.setName("Ana");
		administrator.setSurname("navarro");
		administrator.getUserAccount().setUsername("adminUser");
		administrator.getUserAccount().setPassword("12345678");
		final Administrator saveAdministrator = this.administratorService.save(administrator);
		Assert.isTrue(this.administratorService.findAll().contains(saveAdministrator));
		super.authenticate("adminUser");
		final Referee ana = this.refereeService.create();
		ana.setName("Ana");
		ana.setSurname("navarro");
		final Referee saveAna = this.refereeService.isRegister(ana);
		Assert.isTrue(this.refereeService.findAll().contains(saveAna));
	}

}
