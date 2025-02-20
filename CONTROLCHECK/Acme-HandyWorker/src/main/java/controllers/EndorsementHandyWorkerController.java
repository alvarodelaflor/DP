/*
 * customerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import javax.validation.Valid;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.CustomerService;
import services.EndorsableService;
import services.EndorsementService;
import services.FixUpService;
import services.HandyWorkerService;
import services.WelcomeService;
import domain.Customer;
import domain.Endorsable;
import domain.Endorsement;
import domain.FixUp;
import domain.HandyWorker;

@Controller
@RequestMapping("/endorsement/handyWorker")
public class EndorsementHandyWorkerController extends AbstractController {

	@Autowired
	private EndorsementService	endorsementService;
	@Autowired
	private EndorsableService	endorsableService;
	@Autowired
	private CustomerService		customerService;
	@Autowired
	private HandyWorkerService	handyWorkerService;
	@Autowired
	private FixUpService		fixUpService;
	@Autowired
	private WelcomeService		welcomeService;


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public EndorsementHandyWorkerController() {
		super();
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create(@RequestParam(value = "receiverId", defaultValue = "-1") final int receiverId) {
		ModelAndView result;

		Endorsement endorsement;

		endorsement = this.endorsementService.create();

		final Integer userAccountId = LoginService.getPrincipal().getId();
		Assert.notNull(userAccountId);
		final HandyWorker handyWorker = this.handyWorkerService.getHandyWorkerByUserAccountId(userAccountId);
		final Customer customer = this.customerService.findOne(receiverId);
		final Collection<Customer> customers = this.customerService.getAllCustomersByHandyWorkers(handyWorker.getId());
		if (customer == null && handyWorker != null && customers.contains(customer)) {
			final Collection<FixUp> fixUps = this.fixUpService.findAll();
			final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
			final Collection<FixUp> myFixUps = this.fixUpService.findAllByHWLogger();

			result = new ModelAndView("fixUp/handyWorker/list");
			result.addObject("fixUps", fixUps);
			result.addObject("myFixUps", myFixUps);
			result.addObject("language", language);
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("requestURI", "fixUp/handyWorker/list.do");
		} else {
			Assert.isTrue(customer != null && handyWorker != null);
			endorsement.setendorsableReceiver(customer);
			endorsement.setEndorsableSender(handyWorker);
			endorsement.setMoment(LocalDate.now().toDate());

			result = new ModelAndView("endorsement/handyWorker/edit");
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);

			result.addObject("endorsement", endorsement);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "endorsementId", defaultValue = "-1") final int endorsementId) {
		ModelAndView result;

		final Endorsement endorsement;
		endorsement = this.endorsementService.findOne(endorsementId);
		if (endorsement == null || endorsement.getEndorsableSender().getUserAccount().getId() != LoginService.getPrincipal().getId())
			result = new ModelAndView("redirect:show.do");
		else {
			Assert.isTrue(this.endorsementService.findOne(endorsementId) != null);
			result = new ModelAndView("endorsement/handyWorker/edit");
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);

			result.addObject("endorsement", endorsement);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Endorsement endorsement, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("El error pasa por aqu� alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("endorsement/handyWorker/edit");
		} else
			try {
				Assert.isTrue(endorsement != null, "endorsement.null");
				final Endorsement saveEndorsement = this.endorsementService.save(endorsement);
				result = new ModelAndView("redirect:show.do");
				if (this.handyWorkerService.getHandyWorkerByUserAccountId(LoginService.getPrincipal().getId()) != null) {
					result.addObject("deleteURL", "endorsement/handyWorker/delete.do?endorsementId");
					result.addObject("requestURI", "endorsement/handyWorker/show.do");
				} else {
					result.addObject("deleteURL", "endorsement/handyWorker/delete.do?endorsementId");
					result.addObject("requestURI", "endorsement/handyWorker/show.do");
				}

			} catch (final Throwable oops) {
				System.out.println("El error es en endorsementController: ");
				System.out.println(oops);
				System.out.println(oops.getMessage());
				System.out.println(oops.getLocalizedMessage());
				System.out.println(binding);
				result = new ModelAndView("redirect:show.do");
			}
		return result;
	}
	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "endorsementId", defaultValue = "-1") final int endorsementId) {
		ModelAndView result;

		final Endorsement endorsement = this.endorsementService.findOne(endorsementId);
		System.out.println("endorsementId encontrado: " + endorsementId);
		if (endorsement == null || endorsement.getEndorsableSender().getUserAccount().getId() != LoginService.getPrincipal().getId())
			result = new ModelAndView("redirect:show.do");
		else
			try {
				Assert.notNull(endorsementId, "endorsement.null");
				endorsement.getEndorsableSender().getEndorsementSender().remove(endorsement);
				endorsement.getendorsableReceiver().getEndorsementReceiver().remove(endorsement);
				final Endorsable saveEndorsableSender = this.endorsableService.save(endorsement.getendorsableReceiver());
				final Endorsable savaEndrosableReceiver = this.endorsableService.save(endorsement.getendorsableReceiver());
				this.endorsementService.delete(endorsement);
				result = new ModelAndView("redirect:show.do");
				result.addObject("endorsable", saveEndorsableSender);
				result.addObject("requestURI", "endorsement/handyWorker/show");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar endorsement desde hw: ");
				System.out.println(oops);
				result = new ModelAndView("redirect:show.do");
			}
		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final int userLoggin = LoginService.getPrincipal().getId();
		final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
		final Customer customer = this.customerService.getCustomerByUserAccountId(userLoggin);
		if (handyWorker != null) {
			result = new ModelAndView("endorsement/handyWorker/show");
			final Collection<Endorsement> endorsementSend = this.endorsementService.getEndorsementSender(handyWorker.getId());
			final Collection<Endorsement> endorsementReceived = this.endorsementService.getEndorsementReceiver(handyWorker.getId());
			result.addObject("endorsementSend", endorsementSend);
			result.addObject("endorsementReceived", endorsementReceived);
			result.addObject("requestURI", "endorsement/handyWorker/show.do");
			result.addObject("deleteURL", "endorsement/handyWorker/delete.do?endorsementId");
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("editURL", "endorsement/handyWorker/edit.do?endorsementId");
		} else {
			Assert.notNull(customer, "customer.null");
			result = new ModelAndView("endorsement/customer/show");
			final Collection<Endorsement> endorsementSend = this.endorsementService.getEndorsementSender(customer.getId());
			final Collection<Endorsement> endorsementReceived = this.endorsableService.getendorsableByUserAccountId(userLoggin).getEndorsementReceiver();
			result.addObject("endorsementSend", endorsementSend);
			result.addObject("endorsementReceived", endorsementReceived);
			result.addObject("requestURI", "endorsement/customer/show.do");
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("deleteURL", "endorsement/customer/delete.do?endorsementId");
			result.addObject("editURL", "endorsement/customer/edit.do?endorsementId");
		}

		return result;
	}
}
