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
import services.RefereeService;
import services.SocialProfilelService;
import services.WelcomeService;
import domain.Referee;
import domain.SocialProfile;

@Controller
@RequestMapping("/socialProfile/referee")
public class SocialProfileRefereeController extends AbstractController {

	@Autowired
	private SocialProfilelService	socialProfileService;
	@Autowired
	private RefereeService			refereeService;
	@Autowired
	private WelcomeService			welcomeService;


	//	@Autowired
	//	private UserAccount			userAccountService;

	// Constructors -----------------------------------------------------------

	public SocialProfileRefereeController() {
		super();
	}
	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		SocialProfile socialProfile;

		socialProfile = this.socialProfileService.create();

		result = new ModelAndView("socialProfile/referee/create");
		final String system = this.welcomeService.getSystem();
		result.addObject("system", system);
		final String logo = this.welcomeService.getLogo();
		result.addObject("logo", logo);

		result.addObject("socialProfile", socialProfile);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam(value = "socialProfileId", defaultValue = "-1") final int socialProfileId) {
		ModelAndView result;
		if (this.socialProfileService.findOne(socialProfileId) == null || !this.refereeService.findByUserAccountId(LoginService.getPrincipal().getId()).getSocialProfiles().contains(this.socialProfileService.findOne(socialProfileId)))
			result = new ModelAndView("welcome/index");
		else {
			final SocialProfile socialProfile;
			Assert.isTrue(this.socialProfileService.findOne(socialProfileId) != null);
			socialProfile = this.socialProfileService.findOne(socialProfileId);

			result = new ModelAndView("socialProfile/referee/edit");
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
			result = new ModelAndView("socialProfile/referee/edit");
		} else
			try {
				Assert.isTrue(socialProfile != null);
				final SocialProfile savedSocialProfile = this.socialProfileService.save(socialProfile);
				final int userLoggin = LoginService.getPrincipal().getId();
				final Referee referee = this.refereeService.findByUserAccountId(userLoggin);
				Assert.isTrue(referee != null);
				if (referee.getSocialProfiles().contains(savedSocialProfile)) {
					referee.getSocialProfiles().remove(savedSocialProfile);
					referee.getSocialProfiles().add(savedSocialProfile);
				} else
					referee.getSocialProfiles().add(socialProfile);
				final Referee savedreferee = this.refereeService.save(referee);

				result = new ModelAndView("referee/show");
				final String system = this.welcomeService.getSystem();
				result.addObject("system", system);
				final String logo = this.welcomeService.getLogo();
				result.addObject("logo", logo);
				result.addObject("referee", savedreferee);
				result.addObject("socialProfiles", savedreferee.getSocialProfiles());
				result.addObject("requestURI", "referee/show.do");
			} catch (final Throwable oops) {
				System.out.println("El error es en SocialProfileController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("socialProfile/referee/edit");
			}
		return result;
	}

	@RequestMapping(value = "/delete", method = RequestMethod.GET)
	public ModelAndView delete(@RequestParam(value = "socialProfileId", defaultValue = "-1") final int socialProfileId) {
		ModelAndView result;
		if (this.socialProfileService.findOne(socialProfileId) == null || !this.refereeService.findByUserAccountId(LoginService.getPrincipal().getId()).getSocialProfiles().contains(this.socialProfileService.findOne(socialProfileId))) {
			System.out.println("socialProfileId encontrado: " + socialProfileId);
			final int userLoggin = LoginService.getPrincipal().getId();
			final Referee referee = this.refereeService.findByUserAccountId(userLoggin);
			Assert.isTrue(referee != null);
			result = new ModelAndView("referee/show");
			result.addObject("referee", referee);
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			result.addObject("socialProfiles", referee.getSocialProfiles());
			result.addObject("requestURI", "referee/show.do");
		} else {
			final SocialProfile socialProfile = this.socialProfileService.findOne(socialProfileId);
			System.out.println("socialProfileId encontrado: " + socialProfileId);
			Assert.notNull(socialProfileId, "socialProfile.null");

			try {
				final int userLoggin = LoginService.getPrincipal().getId();
				final Referee referee = this.refereeService.findByUserAccountId(userLoggin);
				Assert.isTrue(referee != null);
				referee.getSocialProfiles().remove(socialProfile);
				this.refereeService.save(referee);
				this.socialProfileService.delete(socialProfile);
				result = new ModelAndView("referee/show");
				result.addObject("referee", referee);
				final String system = this.welcomeService.getSystem();
				result.addObject("system", system);
				final String logo = this.welcomeService.getLogo();
				result.addObject("logo", logo);
				result.addObject("socialProfiles", referee.getSocialProfiles());
				result.addObject("requestURI", "referee/show.do");
			} catch (final Throwable oops) {
				System.out.println("Error al borrar socialProfile desde hw: ");
				System.out.println(oops);
				result = new ModelAndView("referee/show");
			}
		}
		return result;
	}
}
