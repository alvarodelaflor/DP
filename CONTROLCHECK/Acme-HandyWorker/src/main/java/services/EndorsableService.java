
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorsableRepository;
import domain.Endorsable;

@Service
@Transactional
public class EndorsableService {

	//Managed Repository -------------------	

	@Autowired
	private EndorsableRepository	endorsableRepository;

	//Supporting services ------------------

	@Autowired
	private FixUpService			fixUpService;

	@Autowired
	private MailBoxService			mailBoxService;

	@Autowired
	private AdministratorService	administratorService;


	//Simple CRUD Methods ------------------

	public Collection<Endorsable> findAll() {
		return this.endorsableRepository.findAll();

	}

	public Endorsable findOne(final int id) {

		final Endorsable result = this.endorsableRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	public Endorsable save(final Endorsable endorsable) {
		return this.endorsableRepository.save(endorsable);
	}

	public Endorsable getendorsableByUserAccountId(final int userAccountId) {
		Endorsable res;
		res = this.endorsableRepository.findByUserAccountId(userAccountId);
		return res;
	}
}
