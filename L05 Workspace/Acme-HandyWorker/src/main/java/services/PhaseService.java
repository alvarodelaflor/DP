
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PhaseRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.FixUp;
import domain.Phase;

@Service
@Transactional
public class PhaseService {

	//Managed Repository -------------------	

	@Autowired
	private PhaseRepository			phaseRepository;

	//Managed Repository -------------------	

	@Autowired
	private AdministratorService	administratorService;


	//Supporting services ------------------

	public Phase create() {
		final Phase phase = new Phase();
		return phase;
	}

	public Phase save(final Phase phase) {
		Assert.notNull(phase);
		final UserAccount user = LoginService.getPrincipal();
		Assert.isTrue(user.getId() == phase.getFixUp().getHandyWorker().getUserAccount().getId());
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(authority));
		Assert.isTrue(phase.getFixUp() != null);
		return this.phaseRepository.save(phase);
	}

	public void delete(final Phase phase) {
		Assert.notNull(phase);
		Assert.notNull(phase.getFixUp());
		final UserAccount user = LoginService.getPrincipal();
		Assert.isTrue(user.getId() == phase.getFixUp().getHandyWorker().getUserAccount().getId());
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(authority));
		this.phaseRepository.delete(phase.getId());
	}

	public Collection<Phase> findAll() {
		return this.phaseRepository.findAll();
	}

	public Phase findOne(final int id) {
		final Phase result = this.phaseRepository.findOne(id);
		Assert.notNull(result);
		return result;
	}

	// Other M

	public Collection<Phase> getPhasesByFixUp(final FixUp fixUp) {
		return this.phaseRepository.getPhasesByFixUp(fixUp.getId());
	}

	public void deleteByFixUp(final Phase phase) {
		this.phaseRepository.delete(phase.getId());
	}
}
