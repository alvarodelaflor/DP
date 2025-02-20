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
import services.EducationalRecordService;
import services.HandyWorkerService;
import services.WelcomeService;
import domain.Curriculum;
import domain.EducationalRecord;
import domain.HandyWorker;

@Controller
@RequestMapping("/educationalRecord/handyWorker")
public class EducationalRecordController extends AbstractController {

	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private EducationalRecordService	educationalRecordService;
	@Autowired
	private WelcomeService				welcomeService;


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public EducationalRecordController() {
		super();
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		EducationalRecord educationalRecord;

		educationalRecord = this.educationalRecordService.create();

		result = new ModelAndView("educationalRecord/handyWorker/create");
		final String system = this.welcomeService.getSystem();
		result.addObject("system", system);
		final String logo = this.welcomeService.getLogo();
		result.addObject("logo", logo);

		result.addObject("educationalRecord", educationalRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "educationalRecordId", defaultValue = "-1") final int educationalRecordId) {
		ModelAndView result;
		final EducationalRecord educationalRecord;
		final HandyWorker handyWorker2 = this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (this.educationalRecordService.findOne(educationalRecordId) == null || handyWorker2 == null || handyWorker2.getCurriculum() == null || handyWorker2.getCurriculum().getEdurec() == null
			|| !this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getCurriculum().getEdurec().contains(this.educationalRecordService.findOne(educationalRecordId))) {
			result = new ModelAndView("curriculum/handyWorker/show");
			result.addObject("handyWorker", handyWorker2);
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("curriculum", handyWorker2.getCurriculum());
			result.addObject("requestURI", "curriculum/handyWorker/show.do");
		} else {
			Assert.isTrue(this.educationalRecordService.findOne(educationalRecordId) != null);
			educationalRecord = this.educationalRecordService.findOne(educationalRecordId);

			result = new ModelAndView("educationalRecord/handyWorker/edit");

			result.addObject("educationalRecord", educationalRecord);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final EducationalRecord educationalRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("El error pasa por aqu� alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("educationalRecord/handyWorker/edit");
		} else
			try {
				Assert.isTrue(educationalRecord != null);
				final EducationalRecord savededucationalRecord = this.educationalRecordService.save(educationalRecord);
				final int userLoggin = LoginService.getPrincipal().getId();
				final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
				Assert.isTrue(handyWorker != null);
				if (handyWorker.getCurriculum().getEdurec().contains(savededucationalRecord)) {
					handyWorker.getCurriculum().getEdurec().remove(savededucationalRecord);
					handyWorker.getCurriculum().getEdurec().add(savededucationalRecord);
				} else
					handyWorker.getCurriculum().getEdurec().add(savededucationalRecord);
				final HandyWorker savedHandyWorker = this.handyWorkerService.save(handyWorker);

				result = new ModelAndView("curriculum/handyWorker/show");
				result.addObject("handyWorker", savedHandyWorker);
				result.addObject("curriculum", savedHandyWorker.getCurriculum());
				result.addObject("requestURI", "curriculum/handyWorker/show.do");
			} catch (final Throwable oops) {
				System.out.println("El error es en educationalRecordController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("educationalRecord/handyWorker/edit");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "educationalRecordId", defaultValue = "-1") final int educationalRecordId) {
		ModelAndView result;
		final HandyWorker handyWorker2 = this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (this.educationalRecordService.findOne(educationalRecordId) == null || handyWorker2 == null || handyWorker2.getCurriculum() == null || handyWorker2.getCurriculum().getEdurec() == null
			|| !this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getCurriculum().getEdurec().contains(this.educationalRecordService.findOne(educationalRecordId))) {
			result = new ModelAndView("curriculum/handyWorker/show");
			result.addObject("handyWorker", handyWorker2);
			result.addObject("curriculum", handyWorker2.getCurriculum());
			result.addObject("requestURI", "curriculum/handyWorker/show.do");
		} else {
			Assert.notNull(educationalRecordId, "educationalRecord.null");
			final EducationalRecord educationalRecord = this.educationalRecordService.findOne(educationalRecordId);
			System.out.println("educationalRecordId encontrado: " + educationalRecordId);

			try {
				final int userLoggin = LoginService.getPrincipal().getId();
				final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
				Assert.isTrue(handyWorker != null);
				final Curriculum curriculum = handyWorker.getCurriculum();
				Assert.notNull(curriculum, "curriculum.null");
				this.educationalRecordService.delete(educationalRecord);
				final HandyWorker savedHandyWorker = this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("curriculum/handyWorker/show");
				result.addObject("handyWorker", savedHandyWorker);
				result.addObject("curriculum", savedHandyWorker.getCurriculum());
				result.addObject("requestURI", "curriculum/handyWorker/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar educationalRecord desde hw: ");
				System.out.println(oops);
				result = new ModelAndView("curriculum/handyWorker/show");
			}
		}
		return result;
	}
}
