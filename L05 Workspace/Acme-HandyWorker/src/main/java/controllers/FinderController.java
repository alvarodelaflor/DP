/*
 * handyWorkerController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.FinderService;
import services.FixUpService;
import domain.Category;
import domain.Finder;
import domain.FixUp;
import domain.Warranty;

@Controller
@RequestMapping("/finder")
public class FinderController extends AbstractController {

	@Autowired
	private FinderService	finderService;

	@Autowired
	private FixUpService	fixUpService;


	// Constructors -----------------------------------------------------------

	public FinderController() {
		super();
	}

	// Action-1 ---------------------------------------------------------------		

	@RequestMapping(value = "/handyWorker/yourFinder")
	public ModelAndView action1() {
		ModelAndView result;
		final Finder finder = this.finderService.yourFinder();
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		result = new ModelAndView("finder/handyWorker/yourFinder");
		result.addObject("finder", finder);
		result.addObject("language", language);

		return result;
	}

	// Action-2 ---------------------------------------------------------------		

	@RequestMapping("/handyWorker/editYourFinder")
	public ModelAndView action2(@RequestParam("finderId") final int finderId) {
		ModelAndView result;

		final Finder finder = this.finderService.findOne(finderId);
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();
		final Collection<FixUp> fixUp = this.fixUpService.showAllFixUpbyFinder(finderId);
		final Category category = finder.getCategory();
		final Warranty warranty = finder.getWarranty();

		result = new ModelAndView("finder/handyWorker/editYourFinder");
		result.addObject("finder", finder);
		result.addObject("fixUp", fixUp);
		result.addObject("language", language);
		result.addObject("category", category);
		result.addObject("warranty", warranty);

		return result;
	}

}
