
package services;

import java.security.SecureRandom;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
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
import domain.HandyWorker;
import domain.Phase;
import domain.Warranty;

@Service
@Transactional
public class FixUpService {

	//Managed Repository -------------------	

	@Autowired
	private FixUpRepository		fixUpRepository;
	private Integer				iva	= 21;

	//Supporting services ------------------
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private HandyWorkerService	handyWorkerService;
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
	@Autowired
	ActorService				actorService;


	//CARMEN: M�todo para modificar el IVA
	public Integer newIva(final Integer newIva) {
		Assert.isTrue(!this.checkIva(newIva), "iva.bad");
		this.iva = newIva;

		return this.iva;
	}

	public Integer getIva() {
		return this.iva;
	}

	private Boolean checkIva(final Integer iva) {
		Boolean res = true;
		if (iva != null && iva >= 0)
			res = false;
		return res;
	}

	public Double iva(final FixUp fixUp) {
		System.out.println(this.getIva());
		System.out.println(fixUp.getMaxPrice().getQuantity());
		final Double res = (double) (this.getIva() / 100.0 * fixUp.getMaxPrice().getQuantity());

		return res;
	}


	public HashSet<String>	spamWords	= new HashSet<>();


	//Arrays.asList("sex", "viagra", "cialis", "ferrete", "one million", "you've been selected", "Nigeria", "queryfonsiponsypaferrete", "sexo", "un mill�n", "ha sido seleccionado");

	//Carmen: M�todo para a�adir spam words (adm)
	public HashSet<String> newSpamWords(final String newWord) {
		this.listSpamWords().add(newWord);
		return this.listSpamWords();
	}

	//Carmen: M�todo para mostrar las spam words
	public HashSet<String> listSpamWords() {

		this.spamWords.add("sex");
		this.spamWords.add("viagra");
		this.spamWords.add("cialis");
		this.spamWords.add("one millon");
		this.spamWords.add("you've been selected");
		this.spamWords.add("Nigeria");
		this.spamWords.add("sexo");
		this.spamWords.add("un mill�n");
		this.spamWords.add("ha sido seleccionado");

		return this.spamWords;
	}
	//private final List<String>	spamWords	= Arrays.asList("sex", "viagra", "cialis", "ferrete", "one million", "you've been selected", "Nigeria", "queryfonsiponsypaferrete", "sexo", "un mill�n", "ha sido seleccionado");

	//Simple CRUD Methods ------------------

	public FixUp create() {

		final FixUp fixUp = new FixUp();
		fixUp.setTicker(this.randomTicker());
		final UserAccount login = LoginService.getPrincipal();
		final Customer customer = this.customerService.getCustomerByUserAccountId(login.getId());
		fixUp.setCustomer(customer);
		//		final Category category = this.categoryService.create();
		//		category.setNameEN("DefaultCategory");
		//		category.setNameES("Categor�aEjemplo");
		//		final Category saveCategory = this.categoryService.save(category);
		//		fixUp.setCategory(saveCategory);
		//		final Warranty warranty = this.warrantyService.create();
		//		warranty.setTitle("DefaultWarranty");
		//		final Warranty saveWaranty = this.warrantyService.save(warranty);
		//		fixUp.setWarranty(saveWaranty);
		fixUp.setDescription("");
		fixUp.setMoment(LocalDateTime.now().toDate());
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
		this.checkSuspiciousWithBoolean(fixUp);
		Assert.isTrue(!this.checkStartDateEndDate(fixUp.getStartDate(), fixUp.getEndDate()), "fixUp.wrongDate");
		Assert.isTrue(!this.checkMomentDate(fixUp.getStartDate(), fixUp.getMoment()), "fixUp.wrongMomentDate");
		return this.fixUpRepository.save(fixUp);
	}

	private Boolean checkStartDateEndDate(final Date startDate, final Date endDate) {
		Boolean res = true;
		if (startDate != null && endDate != null && startDate.before(endDate))
			res = false;
		return res;
	}

	private Boolean checkMomentDate(final Date startDate, final Date moment) {
		Boolean res = true;
		if (startDate != null && moment != null && moment.before(startDate))
			res = false;
		return res;
	}
	public void delete(final FixUp fixUp) {
		Assert.notNull(this.fixUpRepository.findOne(fixUp.getId()), "La fixUp no existe");
		Assert.isTrue(LoginService.getPrincipal().getId() == fixUp.getCustomer().getUserAccount().getId());
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

	public Collection<FixUp> findAllByHWLogger() {

		final UserAccount user = LoginService.getPrincipal();
		final HandyWorker hw = this.handyWorkerService.findByUserAccountId(user.getId());
		return this.fixUpRepository.findFixUpsByHandyWoker(hw.getId());
	}
	//73.2 (CARMEN) --> Display the fix-up tasks in his or her finder.
	public Collection<FixUp> showAllFixUpbyFinder(final int finderId) {
		return this.fixUpRepository.findFixUpsOfFinderByHandyWorker(finderId);
	}
	//(CARMEN)
	//A�ADIDO
	public Map<String, Double> computeStatistics() {
		Map<String, Double> result;
		double ratioFx;

		result = new HashMap<String, Double>();

		if (this.fixUpRepository.getRatioFixUpComplaint() == null)
			result.put("ratio.fx", 0.0);
		else {
			ratioFx = this.fixUpRepository.getRatioFixUpComplaint();
			result.put("ratio.fx", ratioFx);
		}

		return result;
	}

	private Boolean checkSuspiciousWithBoolean(final FixUp fixUp) {
		Boolean res = false;
		System.out.println(this.spamWords);
		for (final String word : this.listSpamWords())
			if (fixUp.getDescription().contains(word)) {
				res = true;
				this.actorService.getActorByUserId(LoginService.getPrincipal().getId()).setIsSuspicious(true);
			}
		return res;
	}
}
