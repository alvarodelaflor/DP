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
import services.ProfessionalRecordService;
import services.WelcomeService;
import domain.Curriculum;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Controller
@RequestMapping("/professionalRecord/handyWorker")
public class ProfessionalRecordController extends AbstractController {

	@Autowired
	private HandyWorkerService			handyWorkerService;
	@Autowired
	private ProfessionalRecordService	professionalRecordService;
	@Autowired
	private WelcomeService				welcomeService;


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public ProfessionalRecordController() {
		super();
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		ProfessionalRecord professionalRecord;

		professionalRecord = this.professionalRecordService.create();

		result = new ModelAndView("professionalRecord/handyWorker/create");

		result.addObject("professionalRecord", professionalRecord);
		final String system = this.welcomeService.getSystem();
		result.addObject("system", system);
		final String logo = this.welcomeService.getLogo();
		result.addObject("logo", logo);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "professionalRecordId", defaultValue = "-1") final int professionalRecordId) {
		ModelAndView result;

		final ProfessionalRecord professionalRecord;
		final HandyWorker handyWorker2 = this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (this.professionalRecordService.findOne(professionalRecordId) == null || handyWorker2 == null || handyWorker2.getCurriculum() == null || handyWorker2.getCurriculum().getProrec() == null
			|| !this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getCurriculum().getProrec().contains(this.professionalRecordService.findOne(professionalRecordId))) {

			result = new ModelAndView("curriculum/handyWorker/show");
			result.addObject("handyWorker", handyWorker2);
			result.addObject("curriculum", handyWorker2.getCurriculum());
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("requestURI", "curriculum/handyWorker/show.do");
		} else {
			Assert.isTrue(this.professionalRecordService.findOne(professionalRecordId) != null);
			professionalRecord = this.professionalRecordService.findOne(professionalRecordId);

			result = new ModelAndView("professionalRecord/handyWorker/edit");

			result.addObject("professionalRecord", professionalRecord);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final ProfessionalRecord professionalRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("El error pasa por aqu� alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("professionalRecord/handyWorker/edit");
		} else
			try {
				Assert.isTrue(professionalRecord != null);
				final ProfessionalRecord savedprofessionalRecord = this.professionalRecordService.save(professionalRecord);
				final int userLoggin = LoginService.getPrincipal().getId();
				final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
				Assert.isTrue(handyWorker != null);
				if (handyWorker.getCurriculum().getProrec().contains(savedprofessionalRecord)) {
					handyWorker.getCurriculum().getProrec().remove(savedprofessionalRecord);
					handyWorker.getCurriculum().getProrec().add(savedprofessionalRecord);
				} else
					handyWorker.getCurriculum().getProrec().add(savedprofessionalRecord);
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
				System.out.println("El error es en professionalRecordController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("professionalRecord/handyWorker/edit");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "professionalRecordId", defaultValue = "-1") final int professionalRecordId) {
		ModelAndView result;
		final HandyWorker handyWorker2 = this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (this.professionalRecordService.findOne(professionalRecordId) == null || handyWorker2 == null || handyWorker2.getCurriculum() == null || handyWorker2.getCurriculum().getProrec() == null
			|| !this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getCurriculum().getProrec().contains(this.professionalRecordService.findOne(professionalRecordId))) {
			result = new ModelAndView("curriculum/handyWorker/show");
			result.addObject("handyWorker", handyWorker2);
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("curriculum", handyWorker2.getCurriculum());
			result.addObject("requestURI", "curriculum/handyWorker/show.do");
		} else {
			Assert.notNull(professionalRecordId, "professionalRecord.null");
			final ProfessionalRecord professionalRecord = this.professionalRecordService.findOne(professionalRecordId);
			System.out.println("professionalRecordId encontrado: " + professionalRecordId);

			try {
				final int userLoggin = LoginService.getPrincipal().getId();
				final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
				Assert.isTrue(handyWorker != null);
				final Curriculum curriculum = handyWorker.getCurriculum();
				Assert.notNull(curriculum, "curriculum.null");
				this.professionalRecordService.delete(professionalRecord);
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
				System.out.println("Error al borrar professionalRecord desde hw: ");
				System.out.println(oops);
				result = new ModelAndView("curriculum/handyWorker/show");
			}
		}

		return result;
	}
}
