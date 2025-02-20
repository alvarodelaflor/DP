
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
import domain.HandyWorker;
import domain.Tutorial;

@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@RunWith(SpringJUnit4ClassRunner.class)
@Transactional
public class HandyWorkerServiceTest extends AbstractTest {

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private TutorialService			tutorialService;


	@Test
	public void testCreateHandyWorker() {
		final HandyWorker handyWorker = this.handyWorkerService.create();
		Assert.notNull(handyWorker);
	}

	@Test
	public void test12510() {
		final Administrator adminBox = this.administratorService.createFirstAdmin();
		adminBox.getUserAccount().setUsername("AdminBox1");
		adminBox.getUserAccount().setPassword("AdminBoxPass");
		adminBox.setName("AdminBox");
		adminBox.setSurname("AdminBoxSur");
		final Administrator adminBoxSaved = this.administratorService.save(adminBox);
		super.authenticate("AdminBox1");
		final Collection<HandyWorker> handyWorkers = this.handyWorkerService.betterHandyWorker();
		final List<HandyWorker> handWorkerList = (List<HandyWorker>) handyWorkers;
		Assert.isTrue(handWorkerList.get(0).getId() == 450);
	}

	@Test
	public void testSaveHandyWorker() {
		final HandyWorker hw = this.handyWorkerService.create();
		hw.setName("Mari");
		hw.setSurname("pi");
		final HandyWorker saveHandyWorker = this.handyWorkerService.save(hw);
		Assert.isTrue(this.handyWorkerService.findAll().contains(saveHandyWorker));
	}

	@Test
	public void testUpdateHandyWorker() {
		final HandyWorker hw = this.handyWorkerService.create();
		//
		hw.setName("Ana");
		hw.setSurname("navarro");
		hw.getUserAccount().setUsername("anita");
		hw.getUserAccount().setPassword("123456");
		//

		final HandyWorker hw1 = this.handyWorkerService.save(hw);
		//

		super.authenticate("anita");
		//

		hw1.setName("Anasssss");
		hw1.setSurname("navarrosssss");
		final HandyWorker saveHw2 = this.handyWorkerService.update(hw1);

		Assert.isTrue(this.handyWorkerService.findAll().contains(saveHw2));
	}

	@Test
	public void testRegisterHandyWorker() {
		final HandyWorker ana = this.handyWorkerService.create();
		ana.setName("Ana");
		ana.setSurname("navarro");
		final HandyWorker saveAna = this.handyWorkerService.isRegister(ana);
		Assert.isTrue(this.handyWorkerService.findAll().contains(saveAna));

	}

	@Test
	public void testFindHWByTutorial() {
		//CREAR HW
		final HandyWorker handyWorker = this.handyWorkerService.create();
		handyWorker.setName("Ferrete");
		handyWorker.setSurname("Ferrete");
		handyWorker.getUserAccount().setUsername("dogran");
		handyWorker.getUserAccount().setPassword("123456789");
		final HandyWorker saveHandyWorker = this.handyWorkerService.save(handyWorker);
		super.authenticate("dogran");
		//CREAR tutorial
		final Tutorial tutorial1 = this.tutorialService.create();

		//ASIGNAR HW
		tutorial1.setHandyWorker(saveHandyWorker);

		//GUARDAR TUTORIALS
		final Tutorial savedTutorial = this.tutorialService.save(tutorial1);

		Assert.isTrue(this.handyWorkerService.findByTutorial(savedTutorial).getId() == saveHandyWorker.getId());

	}
}
