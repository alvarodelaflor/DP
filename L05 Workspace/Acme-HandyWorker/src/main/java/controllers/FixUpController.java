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
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ApplicationService;
import services.CategoryService;
import services.ComplaintService;
import services.FixUpService;
import domain.Application;
import domain.Category;
import domain.Complaint;
import domain.FixUp;

@Controller
@RequestMapping("/fixUp")
public class FixUpController extends AbstractController {

	@Autowired
	private FixUpService		fixUpService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private CategoryService		categoryService;


	// Constructors -----------------------------------------------------------

	public FixUpController() {
		super();
	}

	@RequestMapping("customer/listingFixUpTasks")
	public ModelAndView action1() {
		ModelAndView result;
		final Collection<FixUp> fixUps = this.fixUpService.listing();
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		result = new ModelAndView("fixUp/customer/listingFixUpTasks");
		result.addObject("fixUps", fixUps);
		result.addObject("language", language);

		return result;
	}

	@RequestMapping("customer/showFixUp")
	public ModelAndView action2(@RequestParam("fixUpId") final int fixUpId) {
		ModelAndView result;

		//		final FixUp fixUp = this.fixUpService.findOne(463);
		final FixUp fixUp = this.fixUpService.findOne(fixUpId);
		final Category category = fixUp.getCategory();
		final Collection<Application> applications = this.applicationService.findAllByFixUp(fixUp);
		final Collection<Complaint> complaints = this.complaintService.getComplaintByFixUp(fixUp);
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		result = new ModelAndView("fixUp/customer/showFixUp");
		result.addObject("fixUp", fixUp);
		result.addObject("category", category);
		result.addObject("language", language);
		result.addObject("applications", applications);
		result.addObject("complaints", complaints);

		return result;
	}

	@RequestMapping(value = "customer/editFixUpTask", method = RequestMethod.GET)
	public ModelAndView editFixUpTask(@RequestParam("id") final int fixUpId) {
		ModelAndView result;

		//		final FixUp fixUp = this.fixUpService.findOne(463);
		final FixUp fixUp = this.fixUpService.findOne(fixUpId);
		System.out.println("Customer del FixUp: " + fixUp.getCustomer() + " " + fixUp);
		final Category category = fixUp.getCategory();
		final Collection<Application> applications = this.applicationService.findAllByFixUp(fixUp);
		final Collection<Complaint> complaints = this.complaintService.getComplaintByFixUp(fixUp);
		final Collection<Category> categories = this.categoryService.findAll();
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		result = new ModelAndView("fixUp/customer/editFixUpTask");
		result.addObject("fixUp", fixUp);
		result.addObject("category", category);
		result.addObject("language", language);
		result.addObject("applications", applications);
		result.addObject("complaints", complaints);
		result.addObject("categories", categories);

		return result;
	}

	@RequestMapping(value = "customer/editFixUpTask", method = RequestMethod.POST)
	public ModelAndView editFixUpTasksave(@Valid final FixUp fixUp, final BindingResult binding) {
		ModelAndView result;

		System.out.println(binding.getFieldErrors());
		System.out.println(fixUp + " " + fixUp.getCustomer());
		this.fixUpService.save(fixUp);

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		result = new ModelAndView("fixUp/customer/listingFixUpTasks");
		System.out.println(binding.getFieldErrors());
		result.addObject("language", language);

		return result;
	}

	@RequestMapping("customer/createFixUpTask")
	public ModelAndView createFixUpTask(@RequestParam("create") final Boolean create) {
		ModelAndView result;
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		if (create.equals(false)) {
			result = new ModelAndView("fixUp/customer/createFixUpTask");
			result.addObject("language", language);
		} else {
			System.out.println("He creado un FixUpTask");

			final Collection<FixUp> fixUps = this.fixUpService.listing();

			result = new ModelAndView("customer/listingFixUpTasks");
			result.addObject("fixUps", fixUps);
			result.addObject("language", language);
		}

		return result;
	}

	@RequestMapping("/customer/deleteFixUpTask")
	public ModelAndView delete(@RequestParam("id") final int fixUpId, @RequestParam("delete") final String delete) {
		ModelAndView result;
		if (delete != null && delete.equals("y")) {
			final FixUp fixUp = this.fixUpService.findOne(fixUpId);
			this.fixUpService.delete(fixUp);
		}
		final Collection<FixUp> fixUps = this.fixUpService.listing();

		result = new ModelAndView("fixUp/customer/listingFixUpTasks");
		result.addObject("fixUps", fixUps);

		return result;
	}

}
