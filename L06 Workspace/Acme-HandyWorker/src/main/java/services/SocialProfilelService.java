
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.SocialProfileRepository;
import security.LoginService;
import domain.Customer;
import domain.HandyWorker;
import domain.SocialProfile;

@Service
@Transactional
public class SocialProfilelService {

	@Autowired
	private SocialProfileRepository	socialProfileRepository;

	@Autowired
	private ActorService			actorService;

	@Autowired
	private HandyWorkerService		handyWorkerService;

	@Autowired
	private CustomerService			customerService;


	public SocialProfile create() {
		final SocialProfile socialProfile = new SocialProfile();
		return socialProfile;
	}

	public SocialProfile save(final SocialProfile socialProfile) {
		Assert.isTrue(socialProfile != null);
		final SocialProfile socialProfileSaved = this.socialProfileRepository.save(socialProfile);
		return socialProfileSaved;
	}
	public Collection<SocialProfile> findAll() {
		return this.socialProfileRepository.findAll();
	}

	public SocialProfile findOne(final Integer id) {
		return this.socialProfileRepository.findOne(id);
	}

	public void delete(final SocialProfile socialProfile) {
		final Integer idUserAccount = LoginService.getPrincipal().getId();
		Assert.notNull(idUserAccount);
		if (this.handyWorkerService.findByUserAccountId(idUserAccount) != null) {
			final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(idUserAccount);
			Assert.isTrue(handyWorker != null);
			Assert.isTrue(handyWorker.getSocialProfiles().contains(socialProfile));
			handyWorker.getSocialProfiles().remove(socialProfile);
			this.handyWorkerService.save(handyWorker);
		} else if (this.customerService.getCustomerByUserAccountId(idUserAccount) != null) {
			final Customer customer = this.customerService.getCustomerByUserAccountId(idUserAccount);
			Assert.isTrue(customer != null);
			Assert.isTrue(customer.getSocialProfiles().contains(socialProfile));
			customer.getSocialProfiles().remove(socialProfile);
			this.customerService.save(customer);
		}
		this.socialProfileRepository.delete(socialProfile);
	}

}
