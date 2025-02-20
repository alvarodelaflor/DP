/*
 * refereeController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import services.HandyWorkerService;
import services.PersonalRecordService;
import services.WelcomeService;
import domain.Curriculum;
import domain.HandyWorker;
import domain.PersonalRecord;

@Controller
@RequestMapping("/personalRecord/handyWorker")
public class PersonalRecordController extends AbstractController {

	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private PersonalRecordService	personalRecordService;
	@Autowired
	private WelcomeService			welcomeService;


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public PersonalRecordController() {
		super();
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		PersonalRecord personalRecord;

		personalRecord = this.personalRecordService.create();

		result = new ModelAndView("personalRecord/handyWorker/create");

		result.addObject("personalRecord", personalRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "personalRecordId", defaultValue = "-1") final int personalRecordId) {
		ModelAndView result;

		final PersonalRecord personalRecord;
		final HandyWorker handyWorker2 = this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (this.personalRecordService.findOne(personalRecordId) == null || handyWorker2 == null || handyWorker2.getCurriculum() == null || handyWorker2.getCurriculum().getPerrec() == null
			|| !this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getCurriculum().getPerrec().equals(this.personalRecordService.findOne(personalRecordId))) {
			result = new ModelAndView("curriculum/handyWorker/show");
			result.addObject("handyWorker", handyWorker2);
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("curriculum", handyWorker2.getCurriculum());
			result.addObject("requestURI", "curriculum/handyWorker/show.do");
		} else {
			Assert.isTrue(this.personalRecordService.findOne(personalRecordId) != null);
			personalRecord = this.personalRecordService.findOne(personalRecordId);

			result = new ModelAndView("personalRecord/handyWorker/edit");
			result.addObject("personalRecord", personalRecord);
		}

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final PersonalRecord personalRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("El error pasa por aqu� alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("personalRecord/handyWorker/edit");
		} else
			try {
				Assert.isTrue(personalRecord != null);
				final PersonalRecord savedpersonalRecord = this.personalRecordService.save(personalRecord);
				final int userLoggin = LoginService.getPrincipal().getId();
				final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
				Assert.isTrue(handyWorker != null);
				handyWorker.getCurriculum().setPerrec(savedpersonalRecord);
				final HandyWorker savedHandyWorker = this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("curriculum/handyWorker/show");
				result.addObject("handyWorker", savedHandyWorker);
				final String system = this.welcomeService.getSystem();
				result.addObject("system", system);
				final String logo = this.welcomeService.getLogo();
				result.addObject("logo", logo);
				result.addObject("curriculum", savedHandyWorker.getCurriculum());
				result.addObject("requestURI", "curriculum/handyWorker/show.do");
			} catch (final Throwable oops) {
				System.out.println("El error es en personalRecordController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("personalRecord/handyWorker/edit");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "personalRecordId", defaultValue = "-1") final int personalRecordId) {
		ModelAndView result;

		final PersonalRecord personalRecord;
		final HandyWorker handyWorker2 = this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (this.personalRecordService.findOne(personalRecordId) == null || handyWorker2 == null || handyWorker2.getCurriculum() == null || handyWorker2.getCurriculum().getPerrec() == null
			|| !this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getCurriculum().getPerrec().equals(this.personalRecordService.findOne(personalRecordId))) {
			result = new ModelAndView("curriculum/handyWorker/show");
			result.addObject("handyWorker", handyWorker2);
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("curriculum", handyWorker2.getCurriculum());
			result.addObject("requestURI", "curriculum/handyWorker/show.do");
		} else {
			Assert.notNull(personalRecordId, "personalRecord.null");
			personalRecord = this.personalRecordService.findOne(personalRecordId);
			System.out.println("personalRecordId encontrado: " + personalRecordId);

			try {
				final int userLoggin = LoginService.getPrincipal().getId();
				final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
				Assert.isTrue(handyWorker != null);
				final Curriculum curriculum = handyWorker.getCurriculum();
				Assert.notNull(curriculum, "curriculum.null");
				this.personalRecordService.delete(personalRecord);
				final HandyWorker savedHandyWorker = this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("curriculum/handyWorker/show");
				result.addObject("handyWorker", savedHandyWorker);
				result.addObject("curriculum", savedHandyWorker.getCurriculum());
				result.addObject("requestURI", "curriculum/handyWorker/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar personalRecord desde hw: ");
				System.out.println(oops);
				result = new ModelAndView("curriculum/handyWorker/show");
			}

		}
		return result;
	}
}
