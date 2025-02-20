/*
 * CustomerController.java
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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.ApplicationService;
import services.CategoryService;
import services.ComplaintService;
import services.FixUpService;
import services.WarrantyService;
import services.WelcomeService;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.FixUp;
import domain.Money;
import domain.Warranty;

@Controller
@RequestMapping("/fixUp/customer")
public class FixUpCustomerController extends AbstractController {

	@Autowired
	private FixUpService		fixUpService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private WarrantyService		warrantyService;
	@Autowired
	private WelcomeService		welcomeService;


	// Constructors -----------------------------------------------------------

	public FixUpCustomerController() {
		super();
	}

	@RequestMapping(value = "/list", method = RequestMethod.GET)
	public ModelAndView list() {
		ModelAndView result;
		final Collection<FixUp> fixUps = this.fixUpService.listing();
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		result = new ModelAndView("fixUp/customer/list");
		result.addObject("fixUps", fixUps);
		result.addObject("language", language);
		final String system = this.welcomeService.getSystem();
		result.addObject("system", system);
		final String logo = this.welcomeService.getLogo();
		result.addObject("logo", logo);
		result.addObject("requestURI", "fixUp/customer/list.do");

		return result;
	}

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam(value = "fixUpId", defaultValue = "-1") final int fixUpId) {
		ModelAndView result;
		final FixUp fixUp = this.fixUpService.findOne(fixUpId);
		//		final FixUp fixUp = this.fixUpService.findOne(463);

		if (this.fixUpService.findOne(fixUpId) == null || LoginService.getPrincipal().getId() != fixUp.getCustomer().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(fixUp, "fixUp.nul");
			final Category category = fixUp.getCategory();
			final Collection<Application> applications = this.applicationService.findAllByFixUp(fixUp);
			final Collection<Complaint> complaints = this.complaintService.getComplaintByFixUp(fixUp);
			final Double iva = this.fixUpService.iva(fixUp);

			result = new ModelAndView("fixUp/customer/show");
			result.addObject("fixUp", fixUp);
			result.addObject("category", category);
			result.addObject("applications", applications);
			result.addObject("complaints", complaints);
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("requestURI", "fixUp/customer/show.do");
			result.addObject("iva", iva);
		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		FixUp fixUp;

		fixUp = this.fixUpService.create();
		final Money money = new Money();
		money.setCurrency("EUR");
		money.setQuantity(0.);
		fixUp.setMaxPrice(money);

		result = this.createEditModelAndView(fixUp);
		result.addObject("language", language);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "id", defaultValue = "-1") final int fixUpId) {
		ModelAndView result;

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		FixUp fixUp;

		fixUp = this.fixUpService.findOne(fixUpId);
		if (this.fixUpService.findOne(fixUpId) == null || LoginService.getPrincipal().getId() != fixUp.getCustomer().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(fixUp);
			result = this.createEditModelAndView(fixUp);
			result.addObject("language", language);
		}

		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "id", defaultValue = "-1") final int fixUpId) {
		ModelAndView result;

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
		final FixUp fixUp = this.fixUpService.findOne(fixUpId);
		System.out.println("FixUp encontrado: " + fixUp);
		if (this.fixUpService.findOne(fixUpId) == null || LoginService.getPrincipal().getId() != fixUp.getCustomer().getUserAccount().getId())
			result = new ModelAndView("redirect:list.do");
		else {
			Assert.notNull(fixUp, "fixUp.null");

			try {
				this.fixUpService.delete(fixUp);
				result = new ModelAndView("redirect:list.do");
			} catch (final Exception e) {
				result = this.createEditModelAndView(fixUp, "fixUp.commit.error");
			}

			result.addObject("language", language);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final FixUp fixUp, final BindingResult binding) {
		ModelAndView result;
		if (fixUp.getMaxPrice().getQuantity() < 0) {
			final ObjectError error = new ObjectError("maxPrice.quantity", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("maxPrice.quantity", "error.maxPrice.quantity");
		}
		if (binding.hasErrors()) {
			System.out.println("El error pasa por aqu� alvaro (IF de save())");
			System.out.println(binding);
			result = this.createEditModelAndView(fixUp);
		} else
			try {
				System.out.println("El error pasa por aqu� alvaro (TRY de save())");
				System.out.println(binding);
				this.fixUpService.save(fixUp);
				result = new ModelAndView("redirect:list.do");
			} catch (final Throwable oops) {
				System.out.println("El error: ");
				System.out.println(oops);
				System.out.println(binding);
				if (oops.getMessage().equals("fixUp.wrongDate"))
					result = this.createEditModelAndView(fixUp, "fixUp.wrongDate");
				else if (oops.getMessage().equals("fixUp.wrongMomentDate"))
					result = this.createEditModelAndView(fixUp, "fixUp.wrongMomentDate");
				else
					result = this.createEditModelAndView(fixUp, "fixUp.commit.error");
			}
		return result;
	}
	private ModelAndView createEditModelAndView(final FixUp fixUp) {
		ModelAndView result;
		final Collection<Warranty> warranties = this.warrantyService.getFinalWarranty();
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("fixUp/customer/edit");

		result.addObject("fixUp", fixUp);
		final String system = this.welcomeService.getSystem();
		result.addObject("system", system);
		final String logo = this.welcomeService.getLogo();
		result.addObject("logo", logo);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);

		return result;
	}

	private ModelAndView createEditModelAndView(final FixUp fixUp, final String messageCode) {
		ModelAndView result;
		final Collection<Warranty> warranties = this.warrantyService.findAll();
		final Collection<Category> categories = this.categoryService.findAll();

		result = new ModelAndView("fixUp/customer/edit");

		result.addObject("fixUp", fixUp);
		final String system = this.welcomeService.getSystem();
		result.addObject("system", system);
		final String logo = this.welcomeService.getLogo();
		result.addObject("logo", logo);
		result.addObject("warranties", warranties);
		result.addObject("categories", categories);
		result.addObject("message", messageCode);

		return result;
	}

	//	@RequestMapping(value = "customer/editFixUpTask", method = RequestMethod.GET)
	//	public ModelAndView editFixUpTask(@RequestParam("id") final int fixUpId) {
	//		ModelAndView result;
	//
	//		//		final FixUp fixUp = this.fixUpService.findOne(463);
	//		final FixUp fixUp = this.fixUpService.findOne(fixUpId);
	//		System.out.println("Customer del FixUp: " + fixUp.getCustomer() + " " + fixUp);
	//		final Category category = fixUp.getCategory();
	//		final Collection<Application> applications = this.applicationService.findAllByFixUp(fixUp);
	//		final Collection<Complaint> complaints = this.complaintService.getComplaintByFixUp(fixUp);
	//		final Collection<Category> categories = this.categoryService.findAll();
	//		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
	//
	//		result = new ModelAndView("fixUp/customer/editFixUpTask");
	//		result.addObject("fixUp", fixUp);
	//		result.addObject("category", category);
	//		result.addObject("language", language);
	//		result.addObject("applications", applications);
	//		result.addObject("complaints", complaints);
	//		result.addObject("categories", categories);
	//
	//		return result;
	//	}
	//
	//	@RequestMapping(value = "customer/editFixUpTask", method = RequestMethod.POST)
	//	public ModelAndView editFixUpTasksave(@Valid final FixUp fixUp, final BindingResult binding) {
	//		ModelAndView result;
	//
	//		System.out.println(binding.getFieldErrors());
	//		System.out.println(fixUp + " " + fixUp.getCustomer());
	//		this.fixUpService.save(fixUp);
	//
	//		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
	//
	//		result = new ModelAndView("fixUp/customer/listingFixUpTasks");
	//		System.out.println(binding.getFieldErrors());
	//		result.addObject("language", language);
	//
	//		return result;
	//	}
	//
	//	@RequestMapping("/customer/deleteFixUpTask")
	//	public ModelAndView delete(@RequestParam("id") final int fixUpId, @RequestParam("delete") final String delete) {
	//		ModelAndView result;
	//		if (delete != null && delete.equals("y")) {
	//			final FixUp fixUp = this.fixUpService.findOne(fixUpId);
	//			this.fixUpService.delete(fixUp);
	//		}
	//		final Collection<FixUp> fixUps = this.fixUpService.listing();
	//
	//		result = new ModelAndView("fixUp/customer/listingFixUpTasks");
	//		result.addObject("fixUps", fixUps);
	//
	//		return result;
	//	}

}
