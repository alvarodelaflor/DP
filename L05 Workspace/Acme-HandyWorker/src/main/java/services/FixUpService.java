
package services;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.FixUpRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Administrator;
import domain.Category;
import domain.Customer;
import domain.Finder;
import domain.FixUp;
import domain.Phase;
import domain.Warranty;

@Service
@Transactional
public class FixUpService {

	//Managed Repository -------------------	

	@Autowired
	private FixUpRepository		fixUpRepository;

	//Supporting services ------------------
	@Autowired
	private CustomerService		customerService;
	@Autowired
	CategoryService				categoryService;
	@Autowired
	WarrantyService				warrantyService;
	@Autowired
	AdministratorService		administratorService;
	@Autowired
	FinderService				finderService;
	@Autowired
	PhaseService				phaseService;

	private final List<String>	spamWords	= Arrays.asList("sex", "viagra", "cialis", "ferrete", "one million", "you've been selected", "Nigeria", "queryfonsiponsypaferrete", "sexo", "un millón", "ha sido seleccionado");


	//Simple CRUD Methods ------------------

	public FixUp create() {

		final FixUp fixUp = new FixUp();
		fixUp.setTicker(this.randomTicker());
		final UserAccount login = LoginService.getPrincipal();
		final Customer customer = this.customerService.getCustomerByUserAccountId(login.getId());
		fixUp.setCustomer(customer);
		//		final Category category = this.categoryService.create();
		//		category.setNameEN("DefaultCategory");
		//		category.setNameES("CategoríaEjemplo");
		//		final Category saveCategory = this.categoryService.save(category);
		//		fixUp.setCategory(saveCategory);
		//		final Warranty warranty = this.warrantyService.create();
		//		warranty.setTitle("DefaultWarranty");
		//		final Warranty saveWaranty = this.warrantyService.save(warranty);
		//		fixUp.setWarranty(saveWaranty);
		fixUp.setDescription("");
		return fixUp;

	}

	public String randomTicker() {
		final String characterSet = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		final StringBuilder sb = new StringBuilder(); //consider using StringBuffer if needed
		for (int i = 0; i < 6; i++) {
			final int randomInt = new SecureRandom().nextInt(characterSet.length());
			sb.append(characterSet.substring(randomInt, randomInt + 1));
		}
		return LocalDate.now().toString().replaceAll("-", "") + sb.toString();
	}

	public Collection<FixUp> findAll() {
		return this.fixUpRepository.findAll();
	}

	public FixUp findOne(final int id) {
		return this.fixUpRepository.findOne(id);
	}
	public FixUp save(final FixUp fixUp) {
		this.checkSuspicious(fixUp);
		return this.fixUpRepository.save(fixUp);
	}
	public void delete(final FixUp fixUp) {
		Assert.notNull(this.fixUpRepository.findOne(fixUp.getId()), "La fixUp no existe");
		final Collection<Finder> finders = this.finderService.findFinderOfFixUp(fixUp);
		if (finders != null)
			for (final Finder finder : finders)
				finder.getFixUps().remove(fixUp);
		final Collection<Phase> phases = this.phaseService.getPhasesByFixUp(fixUp);
		if (phases != null)
			for (final Phase phase : phases) {
				System.out.println(phase);
				this.phaseService.deleteByFixUp(phase);
			}
		this.fixUpRepository.delete(fixUp);
	}
	public FixUp update(final FixUp fixUp) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.customerService.getCustomerByUserAccountId(login.getId()) != null);
		final Customer customer = this.customerService.getCustomerByUserAccountId(login.getId());
		Assert.isTrue(fixUp.getCustomer().equals(customer));
		Assert.isTrue(this.findOne(fixUp.getId()) != null);
		if (fixUp.getWarranty() != null)
			Assert.isTrue(fixUp.getWarranty().getIsFinal() == true);
		final FixUp saveFixUp = this.fixUpRepository.save(fixUp);
		return saveFixUp;
	}

	//Other Methods

	public Collection<FixUp> getFixUpByCustomerId(final int customerId) {
		return this.fixUpRepository.findFixUpsByCustomer(customerId);
	}

	public Collection<FixUp> listing() {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.customerService.getCustomerByUserAccountId(login.getId()) != null);
		final Customer customer = this.customerService.getCustomerByUserAccountId(login.getId());
		return this.fixUpRepository.findFixUpsByCustomer(customer.getId());
	}

	public FixUp showing(final int fixUpId) {
		final UserAccount login = LoginService.getPrincipal();
		Assert.isTrue(login != null);
		Assert.isTrue(this.customerService.getCustomerByUserAccountId(login.getId()) != null);
		final Customer customerLogin = this.customerService.getCustomerByUserAccountId(login.getId());
		Assert.isTrue(this.findOne(fixUpId) != null);
		final Customer customerFixUp = this.findOne(fixUpId).getCustomer();
		Assert.isTrue(customerLogin.equals(customerFixUp));
		return this.findOne(fixUpId);
	}

	//1251
	public Integer minFixUpHandyWorker() {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Integer result = this.fixUpRepository.minFixUpHandyWorker();
		return result;
	}

	//1251
	public Integer maxFixUpHandyWorker() {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Integer result = this.fixUpRepository.maxFixUpHandyWorker();
		return result;
	}

	//1251
	public Double avgFixUpPerHandyWorker() {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Double result = this.fixUpRepository.averagePerHandyWorker();
		return result;
	}

	//1251
	public Double desviationFixUpPerHandyWorker() {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Double result = this.fixUpRepository.desviationPerHandyWorker();
		return result;
	}

	//1253
	public Double minPriceFixUp() {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Double result = this.fixUpRepository.minPriceFixUp();
		return result;
	}

	//1253
	public Double maxPriceFixUp() {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Double result = this.fixUpRepository.maxPriceFixUp();
		return result;
	}

	//1253
	public Double avgPriceFixUp() {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Double result = this.fixUpRepository.averagePriceFixUp();
		return result;
	}

	//1253
	public Double desviationPriceFixUp() {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Double result = this.fixUpRepository.desviationPriceFixUp();
		return result;
	}

	//FERRETE
	public Collection<FixUp> filterFixUps(String cadena, final Warranty warranty, final Category category, final Date startDate, final Date endDate, final Double minMoney, final Double maxMoney) {

		final UserAccount user = LoginService.getPrincipal();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(authority));

		final Collection<FixUp> results = this.findAll();

		if (cadena != null)
			cadena = "%" + cadena + "%";
		results.retainAll(this.fixUpRepository.filterFixUpByString(cadena));
		if (warranty != null)
			results.retainAll(this.fixUpRepository.filterFixUpByWarranty(warranty.getId()));
		if (category != null)
			results.retainAll(this.fixUpRepository.filterFixUpByCategory(category.getId()));
		if (startDate != null && endDate != null)
			results.retainAll(this.fixUpRepository.filterFixUpByDate(startDate, endDate));
		if (minMoney != null && maxMoney != null)
			results.retainAll(this.fixUpRepository.filterFixUpByPrice(minMoney, maxMoney));
		return results;
	}

	//FERRETE
	private void checkSuspicious(final FixUp fixUp) {
		for (final String word : this.spamWords)
			if (fixUp.getDescription().contains(word))
				fixUp.getCustomer().setIsSuspicious(true);
	}
	//FERRETE
	public Collection<FixUp> findAllByHW() {

		final UserAccount user = LoginService.getPrincipal();
		final Authority authority = new Authority();
		authority.setAuthority(Authority.HANDYWORKER);
		Assert.isTrue(user.getAuthorities().contains(authority));
		return this.fixUpRepository.findAll();
	}

	//73.2 (CARMEN) --> Display the fix-up tasks in his or her finder.
	public Collection<FixUp> showAllFixUpbyFinder(final int finderId) {
		return this.fixUpRepository.findFixUpsOfFinderByHandyWorker(finderId);
	}
	//(CARMEN)

}
