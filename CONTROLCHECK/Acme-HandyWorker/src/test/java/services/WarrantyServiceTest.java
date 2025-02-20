
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
import domain.Warranty;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class WarrantyServiceTest extends AbstractTest {

	@Autowired
	WarrantyService					warrantyService;

	@Autowired
	private AdministratorService	administratorService;


	@Test
	public void testCreate() {
		final Administrator adminTest = this.administratorService.createFirstAdmin();
		adminTest.setName("Alvaro");
		adminTest.setSurname("alvaro");
		adminTest.getUserAccount().setUsername("dogran");
		adminTest.getUserAccount().setPassword("123456789");
		final Administrator adminTestSave = this.administratorService.save(adminTest);
		super.authenticate("dogran");
		final Warranty warrantyTest = this.warrantyService.create();
		Assert.notNull(warrantyTest);
	}
	@Test
	public void testFindOne() {
		Assert.isTrue(this.warrantyService.findOne(870) != null);
	}

	@Test
	public void testSave() {
		final Administrator adminTest = this.administratorService.createFirstAdmin();
		adminTest.setName("Alvaro");
		adminTest.setSurname("alvaro");
		adminTest.getUserAccount().setUsername("dogran");
		adminTest.getUserAccount().setPassword("123456789");
		final Administrator adminTestSave = this.administratorService.save(adminTest);
		super.authenticate("dogran");
		final Warranty warranty = this.warrantyService.create();
		warranty.setTitle("TittleTest");
		final Warranty saved = this.warrantyService.save(warranty);
		Assert.isTrue(this.warrantyService.findAll().contains(saved));
	}

	@Test
	public void testUpdate() {
		final Administrator adminTest = this.administratorService.createFirstAdmin();
		adminTest.setName("Alvaro");
		adminTest.setSurname("alvaro");
		adminTest.getUserAccount().setUsername("dogran");
		adminTest.getUserAccount().setPassword("123456789");
		final Administrator adminTestSave = this.administratorService.save(adminTest);
		super.authenticate("dogran");
		final Warranty warranty = this.warrantyService.create();
		warranty.setTitle("TittleTest");
		final Warranty saved = this.warrantyService.save(warranty);
		Assert.isTrue(this.warrantyService.findAll().contains(saved));
		final Warranty savedCopy = this.warrantyService.create();
		savedCopy.setLaws(saved.getLaws());
		savedCopy.setTerms(saved.getTerms());
		savedCopy.setId(saved.getId());
		savedCopy.setVersion(saved.getVersion());
		savedCopy.setTitle(saved.getTitle());
		savedCopy.setIsFinal(saved.getIsFinal());

		savedCopy.setTitle("hola");
		savedCopy.setIsFinal(true);

		final Warranty saveUpdate = this.warrantyService.udpate(savedCopy);
		Assert.isTrue(saveUpdate.getTitle() == "hola");
		Assert.isTrue(saveUpdate.getIsFinal() == true);
		System.out.println(saved.getVersion());
		System.out.println(saveUpdate.getVersion());
	}

	@Test
	public void testDelete() {
		final Administrator adminTest = this.administratorService.createFirstAdmin();
		adminTest.setName("Alvaro");
		adminTest.setSurname("alvaro");
		adminTest.getUserAccount().setUsername("dogran");
		adminTest.getUserAccount().setPassword("123456789");
		final Administrator adminTestSave = this.administratorService.save(adminTest);
		super.authenticate("dogran");
		final Warranty warranty = this.warrantyService.create();
		warranty.setTitle("TittleTest");
		final Warranty saved = this.warrantyService.save(warranty);
		Assert.isTrue(this.warrantyService.findAll().contains(saved));
		this.warrantyService.delete(saved);
		Assert.isTrue(!this.warrantyService.findAll().contains(saved));
	}

}
