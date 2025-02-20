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
import services.SocialProfilelService;
import services.WelcomeService;
import domain.HandyWorker;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile/handyWorker")
public class SocialProfileaHandyWorkerController extends AbstractController {

	@Autowired
	private SocialProfilelService	socialProfileService;
	@Autowired
	private HandyWorkerService		handyWorkerService;
	@Autowired
	private WelcomeService			welcomeService;


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public SocialProfileaHandyWorkerController() {
		super();
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();

		result = new ModelAndView("socialProfile/handyWorker/create");

		result.addObject("socialProfile", socialProfile);
		final String system = this.welcomeService.getSystem();
		result.addObject("system", system);
		final String logo = this.welcomeService.getLogo();
		result.addObject("logo", logo);
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "socialProfileId", defaultValue = "-1") final int socialProfileId) {
		ModelAndView result;

		if (this.socialProfileService.findOne(socialProfileId) == null || !this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getSocialProfiles().contains(this.socialProfileService.findOne(socialProfileId)))
			result = new ModelAndView("welcome/index");
		else {
			final SocialProfile socialProfile;
			Assert.isTrue(this.socialProfileService.findOne(socialProfileId) != null);
			socialProfile = this.socialProfileService.findOne(socialProfileId);

			result = new ModelAndView("socialProfile/handyWorker/edit");
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("socialProfile", socialProfile);
		}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final SocialProfile socialProfile, final BindingResult binding) {
		ModelAndView result;

		if (binding.hasErrors()) {
			System.out.println("El error pasa por aqu� alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("socialProfile/handyWorker/edit");
		} else
			try {
				Assert.isTrue(socialProfile != null);
				final SocialProfile savedSocialProfile = this.socialProfileService.save(socialProfile);
				final int userLoggin = LoginService.getPrincipal().getId();
				final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
				Assert.isTrue(handyWorker != null);
				if (handyWorker.getSocialProfiles().contains(savedSocialProfile)) {
					handyWorker.getSocialProfiles().remove(savedSocialProfile);
					handyWorker.getSocialProfiles().add(savedSocialProfile);
				} else
					handyWorker.getSocialProfiles().add(socialProfile);
				final HandyWorker savedHandyWorker = this.handyWorkerService.save(handyWorker);

				result = new ModelAndView("handyWorker/show");
				final String system = this.welcomeService.getSystem();
				result.addObject("system", system);
				final String logo = this.welcomeService.getLogo();
				result.addObject("logo", logo);
				result.addObject("handyWorker", savedHandyWorker);
				result.addObject("socialProfiles", savedHandyWorker.getSocialProfiles());
				result.addObject("requestURI", "handyWorker/show.do");
			} catch (final Throwable oops) {
				System.out.println("El error es en SocialProfileController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("socialProfile/handyWorker/edit");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "socialProfileId", defaultValue = "-1") final int socialProfileId) {
		ModelAndView result;

		final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
		System.out.println("socialProfileId encontrado: " + socialProfileId);
		Assert.notNull(socialProfileId, "socialProfile.null");
		if (this.socialProfileService.findOne(socialProfileId) == null || !this.handyWorkerService.findByUserAccountId(LoginService.getPrincipal().getId()).getSocialProfiles().contains(this.socialProfileService.findOne(socialProfileId))) {
			final int userLoggin = LoginService.getPrincipal().getId();
			final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
			Assert.isTrue(handyWorker != null);
			result = new ModelAndView("handyWorker/show");
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("handyWorker", handyWorker);
			result.addObject("socialProfiles", handyWorker.getSocialProfiles());
			result.addObject("requestURI", "handyWorker/show.do");
		} else
			try {
				this.socialProfileService.delete(socialProfile);

				final int userLoggin = LoginService.getPrincipal().getId();
				final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(userLoggin);
				Assert.isTrue(handyWorker != null);
				result = new ModelAndView("handyWorker/show");
				result.addObject("handyWorker", handyWorker);
				final String system = this.welcomeService.getSystem();
				result.addObject("system", system);
				final String logo = this.welcomeService.getLogo();
				result.addObject("logo", logo);
				result.addObject("socialProfiles", handyWorker.getSocialProfiles());
				result.addObject("requestURI", "handyWorker/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar socialProfile desde hw: ");
				System.out.println(oops);
				result = new ModelAndView("handyWorker/show");
			}
		return result;
	}
}
