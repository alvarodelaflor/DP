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
import services.EndorserRecordService;
import services.HandyWorkerService;
import services.WelcomeService;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.HandyWorker;

@Controller
@RequestMapping("/endorserRecord/handyWorker")
public class EndorserRecordController extends AbstractController {

	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private EndorserRecordService	endorserRecordService;
	@Autowired
	private WelcomeService			welcomeService;


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public EndorserRecordController() {
		super();
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		EndorserRecord endorserRecord;

		endorserRecord = this.endorserRecordService.create();

		result = new ModelAndView("endorserRecord/handyWorker/create");
		final String system = this.welcomeService.getSystem();
		result.addObject("system", system);
		final String logo = this.welcomeService.getLogo();
		result.addObject("logo", logo);

		result.addObject("endorserRecord", endorserRecord);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "endorserRecordId", defaultValue = "-1") final int endorserRecordId) {
		ModelAndView result;

		final EndorserRecord endorserRecord;
		final HandyWorker handyWorker2 = this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (this.endorserRecordService.findOne(endorserRecordId) == null || handyWorker2 == null || handyWorker2.getCurriculum() == null || handyWorker2.getCurriculum().getEndrec() == null
			|| !this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getCurriculum().getEndrec().contains(this.endorserRecordService.findOne(endorserRecordId))) {
			result = new ModelAndView("curriculum/handyWorker/show");
			result.addObject("handyWorker", handyWorker2);
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("curriculum", handyWorker2.getCurriculum());
			result.addObject("requestURI", "curriculum/handyWorker/show.do");
		} else {
			Assert.isTrue(this.endorserRecordService.findOne(endorserRecordId) != null);
			endorserRecord = this.endorserRecordService.findOne(endorserRecordId);

			result = new ModelAndView("endorserRecord/handyWorker/edit");

			result.addObject("endorserRecord", endorserRecord);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final EndorserRecord endorserRecord, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("El error pasa por aqu� alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("endorserRecord/handyWorker/edit");
		} else
			try {
				Assert.isTrue(endorserRecord != null);
				final EndorserRecord savedendorserRecord = this.endorserRecordService.save(endorserRecord);
				final int userLoggin = LoginService.getPrincipal().getId();
				final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
				Assert.isTrue(handyWorker != null);
				if (handyWorker.getCurriculum().getEndrec().contains(savedendorserRecord)) {
					handyWorker.getCurriculum().getEndrec().remove(savedendorserRecord);
					handyWorker.getCurriculum().getEndrec().add(savedendorserRecord);
				} else
					handyWorker.getCurriculum().getEndrec().add(savedendorserRecord);
				final HandyWorker savedHandyWorker = this.handyWorkerService.save(handyWorker);

				result = new ModelAndView("curriculum/handyWorker/show");
				result.addObject("handyWorker", savedHandyWorker);
				result.addObject("curriculum", savedHandyWorker.getCurriculum());
				result.addObject("requestURI", "curriculum/handyWorker/show.do");
			} catch (final Throwable oops) {
				System.out.println("El error es en endorserRecordController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("endorserRecord/handyWorker/edit");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "endorserRecordId", defaultValue = "-1") final int endorserRecordId) {
		ModelAndView result;
		final HandyWorker handyWorker2 = this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId());
		if (this.endorserRecordService.findOne(endorserRecordId) == null || handyWorker2 == null || handyWorker2.getCurriculum() == null || handyWorker2.getCurriculum().getEndrec() == null
			|| !this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getCurriculum().getEndrec().contains(this.endorserRecordService.findOne(endorserRecordId))) {
			result = new ModelAndView("curriculum/handyWorker/show");
			result.addObject("handyWorker", handyWorker2);
			result.addObject("curriculum", handyWorker2.getCurriculum());
			result.addObject("requestURI", "curriculum/handyWorker/show.do");
		} else {
			Assert.notNull(endorserRecordId, "endorserRecord.null");
			final EndorserRecord endorserRecord = this.endorserRecordService.findOne(endorserRecordId);
			System.out.println("endorserRecordId encontrado: " + endorserRecordId);

			try {
				final int userLoggin = LoginService.getPrincipal().getId();
				final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
				Assert.isTrue(handyWorker != null);
				final Curriculum curriculum = handyWorker.getCurriculum();
				Assert.notNull(curriculum, "curriculum.null");
				this.endorserRecordService.delete(endorserRecord);
				final HandyWorker savedHandyWorker = this.handyWorkerService.save(handyWorker);
				result = new ModelAndView("curriculum/handyWorker/show");
				result.addObject("handyWorker", savedHandyWorker);
				result.addObject("curriculum", savedHandyWorker.getCurriculum());
				result.addObject("requestURI", "curriculum/handyWorker/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar endorserRecord desde hw: ");
				System.out.println(oops);
				result = new ModelAndView("curriculum/handyWorker/show");
			}
		}
		return result;
	}
}
