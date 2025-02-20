
package services;

// ----------------------------------MARI-------------------
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SponsorRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.MailBox;
import domain.Sponsor;

@Service
@Transactional
public class SponsorService {

	//Managed Repository -------------------	

	@Autowired
	private SponsorRepository	sponsorRepository;

	@Autowired
	private MailBoxService		mailBoxService;

	@Autowired
	private ActorService		actorService;


	//Supporting services ------------------

	//Simple CRUD Methods ------------------

	public Sponsor create() {
		final Sponsor sp = new Sponsor();
		final UserAccount cuenta = new UserAccount();
		final List<Authority> autoridades = new ArrayList<>();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.SPONSOR);
		autoridades.add(authority);
		cuenta.setAuthorities(autoridades);

		sp.setUserAccount(cuenta);

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

		sp.setMailBoxes(boxesDefault);

		return sp;
	}

	public Collection<Sponsor> findAll() {
		return this.sponsorRepository.findAll();
	}

	public Sponsor findOne(final int id) {
		final Sponsor result = this.sponsorRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Sponsor save(final Sponsor sp) {
		final UserAccount userA = this.actorService.getUserByActorId(sp.getId());
		Assert.isTrue(userA == null);
		return this.sponsorRepository.save(sp);
	}

	public Sponsor isRegister(final Sponsor sponsor) {
		final UserAccount a = sponsor.getUserAccount();
		Assert.isTrue(a.getUsername() == null);
		return this.sponsorRepository.save(sponsor);
	}

	//	public Sponsor getSponsorByUserAccountId(final int userAccountId) {
	//		Sponsor res;
	//		res = this.sponsorRepository.findByUserAccountId(userAccountId);
	//		return res;
	//	}

	//OTHERS

	//UN SPOSOR MODIFICA SOLO SUS DATOS
	public Sponsor update(final Sponsor sponsor) {
		Assert.isTrue(LoginService.getPrincipal().getId() == sponsor.getUserAccount().getId()); //UN ACTOR SOLO PUEDE MODIFICICAR SUS DATOS 9.2
		return this.sponsorRepository.save(sponsor);
	}

}
