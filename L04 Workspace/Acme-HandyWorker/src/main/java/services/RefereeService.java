
package services;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.RefereeRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.MailBox;
import domain.Referee;

@Service
@Transactional
public class RefereeService {

	//Managed Repository -------------------	

	@Autowired
	private RefereeRepository		refereeRepository;

	//Supporting services ------------------
	@Autowired
	private AdministratorService	administratorService;

	@Autowired
	private MailBoxService			mailBoxService;


	//Simple CRUD Methods ------------------

	public Referee create() {

		final UserAccount userAccount = LoginService.getPrincipal();
		final int idAdmin = userAccount.getId();
		Assert.isTrue(this.administratorService.getAdministratorByUserAccountId(idAdmin) != null);
		final Referee referee = new Referee();
		final UserAccount cuenta = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.REFEREE);
		autoridades.add(authority);
		cuenta.setAuthorities(autoridades);

		referee.setUserAccount(cuenta);

		final Collection<MailBox> boxesDefault = new ArrayList<>();

		final MailBox inBox = this.mailBoxService.create();
		inBox.setName("inBox");
		inBox.setIsDefault(true);
		final MailBox outBox = this.mailBoxService.create();
		outBox.setName("outBox");
		outBox.setIsDefault(true);
		final MailBox spamBox = this.mailBoxService.create();
		spamBox.setName("spamBox");
		spamBox.setIsDefault(true);
		final MailBox trashBox = this.mailBoxService.create();
		trashBox.setName("trashBox");
		trashBox.setIsDefault(true);

		final MailBox inBoxSave = this.mailBoxService.save(inBox);
		final MailBox outBoxSave = this.mailBoxService.save(outBox);
		final MailBox spamBoxSave = this.mailBoxService.save(spamBox);
		final MailBox trashBoxSave = this.mailBoxService.save(trashBox);

		boxesDefault.add(inBoxSave);
		boxesDefault.add(outBoxSave);
		boxesDefault.add(spamBoxSave);
		boxesDefault.add(trashBoxSave);

		referee.setMailBoxes(boxesDefault);

		return referee;
	}

	public Collection<Referee> findAll() {
		return this.refereeRepository.findAll();

	}

	public Referee findOne(final int id) {

		final Referee result = this.refereeRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Referee save(final Referee referee) {
		return this.refereeRepository.save(referee);
	}

	public Referee isRegister(final Referee rf) {
		final UserAccount a = rf.getUserAccount();
		Assert.isTrue(a.getUsername() == null);
		return this.refereeRepository.save(rf);
	}
	public Referee update(final Referee referee) {
		Assert.isTrue(LoginService.getPrincipal().getId() == referee.getUserAccount().getId()); //UN ACTOR SOLO PUEDE MODIFICICAR SUS DATOS 9.2
		return this.refereeRepository.save(referee);
	}

	public Referee findByUserAccountId(final int userAccountId) {
		return this.refereeRepository.findByUserAccountId(userAccountId);
	}

	//Other Methods

}
