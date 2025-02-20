
package services;

import java.util.Collection;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import utilities.AbstractTest;
import domain.Administrator;
import domain.Customer;
import domain.MailBox;
import domain.Message;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
	"classpath:spring/datasource.xml", "classpath:spring/config/packages.xml"
})
@Transactional
public class MailBoxServiceTest extends AbstractTest {

	@Autowired
	private MailBoxService			mailBoxService;

	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MessageService			messageService;

	@Autowired
	private CustomerService			customerService;


	//	@Autowired
	//	private UserAccountService		userAccountService;

	@Test
	public void testCreateMailBox() {
		final MailBox m = this.mailBoxService.create();
		Assert.notNull(m);
	}

	@Test
	public void delete() {
		final Customer customer = this.customerService.create();
		customer.setName("Alvaro");
		customer.setSurname("alvaro");
		customer.getUserAccount().setUsername("dogran");
		customer.getUserAccount().setPassword("123456789");
		final MailBox m = this.mailBoxService.create();
		customer.getMailBoxes().add(m);
		m.setName("boxTest");
		final MailBox msave = this.mailBoxService.save(m);
		final Customer saveCustomer = this.customerService.save(customer);
		super.authenticate("dogran");

		Assert.isTrue(this.mailBoxService.findAll().contains(msave));
		this.mailBoxService.delete(msave);
		Assert.isTrue(!this.mailBoxService.findAll().contains(msave));
	}
	@Test
	public void testSaveFindAll() {
		final MailBox m = this.mailBoxService.create();
		m.setName("boxTest");
		final MailBox saved = this.mailBoxService.save(m);
		final Collection<MailBox> boxes = this.mailBoxService.findAll();
		Assert.isTrue(boxes.contains(saved));
	}

	@Test
	public void testSaveFindAllDefault() {
		final MailBox m = this.mailBoxService.create();
		m.setName("testBox");
		final MailBox saved = this.mailBoxService.save(m);
		final Collection<MailBox> boxes = this.mailBoxService.findAll();
		Assert.isTrue(boxes.contains(saved));
	}

	@Test
	public void testSaveFindOneDefault() {
		final MailBox m = this.mailBoxService.create();
		m.setName("testBox");
		final MailBox saved = this.mailBoxService.save(m);
		final MailBox box = this.mailBoxService.findOneDefault(saved.getId());
		Assert.notNull(box);
	}

	@Test
	public void getInbox() {
		final MailBox inBox1 = this.mailBoxService.create();
		final MailBox inBox2 = this.mailBoxService.create();
		final MailBox outBox1 = this.mailBoxService.create();

		inBox1.setName("inBox");
		inBox2.setName("inBox");
		outBox1.setName("outBox");

		final MailBox savedInBox1 = this.mailBoxService.save(inBox1);
		final MailBox savedInBox2 = this.mailBoxService.save(inBox2);
		final MailBox savedOutBox1 = this.mailBoxService.save(outBox1);

		final Collection<MailBox> inBox = this.mailBoxService.getInbox();

		Assert.isTrue(inBox.contains(savedInBox1));
		Assert.isTrue(inBox.contains(savedInBox2));
		Assert.isTrue(!inBox.contains(savedOutBox1));

	}

	@Test
	public void testAdminBox() {
		final Administrator adminPrueba = this.administratorService.createFirstAdmin();

		adminPrueba.setName("AdminBox");
		adminPrueba.setSurname("AdminBox");

		adminPrueba.getUserAccount().setUsername("AdminBox");
		adminPrueba.getUserAccount().setPassword("AdminBox");

		final Administrator adminSaved = this.administratorService.save(adminPrueba);

		super.authenticate("AdminBox");

		final Collection<MailBox> inBoxADmin = this.mailBoxService.getAdminInBox();
		Assert.notNull(inBoxADmin);
	}
	@Test
	public void testAddMessageInBox() {
		final Administrator adminBox = this.administratorService.createFirstAdmin();
		adminBox.getUserAccount().setUsername("AdminBox2");
		adminBox.getUserAccount().setPassword("AdminBoxPass2");
		adminBox.setName("AdminBox2");
		adminBox.setSurname("AdminBoxSur2");
		final Administrator adminBoxSaved = this.administratorService.save(adminBox);
		super.authenticate("AdminBox2");

		final Message message = this.messageService.create();

		//	final Administrator adminSaved = this.administratorService.save(adminBox);

		//	super.authenticate("AdminBox");

		final Administrator adminBox1 = this.administratorService.create();
		adminBox1.getUserAccount().setUsername("AdminBox1");
		adminBox1.getUserAccount().setPassword("AdminBox1");
		adminBox1.setName("AdminBox1");
		adminBox1.setSurname("AdminBox1");
		final Administrator adminBoxSaved1 = this.administratorService.save(adminBox1);

		final Collection<MailBox> added = this.mailBoxService.addMessageCollectionInBpox(message);

		final List<MailBox> addedList = (List<MailBox>) added;

		final MailBox prueba1 = addedList.get(0);

		Assert.isTrue(prueba1.getMessages().contains(message));
		Assert.isTrue(added.size() == 9);

	}

}
