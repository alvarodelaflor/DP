/*
 * sponsorController.java
 * 
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the
 * TDG Licence, a copy of which you may download from
 * http://www.tdg-seville.info/License.html
 */

package controllers;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.encoding.Md5PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import security.LoginService;
import security.UserAccountRepository;
import services.ActorService;
import services.SponsorService;
import services.WelcomeService;
import domain.Sponsor;

@Controller
@RequestMapping("/sponsor")
public class SponsorController extends AbstractController {

	@Autowired
	private SponsorService			sponsorService;
	@Autowired
	private UserAccountRepository	userAccountService;
	@Autowired
	private WelcomeService			welcomeService;
	@Autowired
	private ActorService			actorService;


	// Constructors -----------------------------------------------------------

	public SponsorController() {
		super();
	}

	@RequestMapping(value = "/create", method = RequestMethod.GET)
	public ModelAndView create() {
		ModelAndView result;

		try {
			final Boolean res = LoginService.getPrincipal() != null;
			result = new ModelAndView("welcome/index");
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			SimpleDateFormat formatter;
			String moment;
			formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			moment = formatter.format(new Date());
			result.addObject("moment", moment);
		} catch (final Exception e) {
			Sponsor sponsor;

			sponsor = this.sponsorService.create();

			result = new ModelAndView("sponsor/create");

			result.addObject("sponsor", sponsor);
			final String phone = this.welcomeService.getPhone();
			result.addObject("phone", phone);
			final String system = this.welcomeService.getSystem();
			result.addObject("system", system);
			final String logo = this.welcomeService.getLogo();
			result.addObject("logo", logo);
			SimpleDateFormat formatter;
			String moment;
			formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
			moment = formatter.format(new Date());
			result.addObject("moment", moment);

		}

		return result;
	}

	@RequestMapping(value = "/create", method = RequestMethod.POST, params = "save")
	public ModelAndView save(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;
		if (this.actorService.getActorByEmail(sponsor.getEmail()) != null || !(sponsor.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}") || sponsor.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}(>)"))) {
			final ObjectError error = new ObjectError("actor.email", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("email", "error.actor.email.exits");
		}
		if (sponsor.getUserAccount().getPassword().length() < 5 || sponsor.getUserAccount().getPassword().length() > 32) {
			final ObjectError error = new ObjectError("userAccount.password", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("userAccount.password", "error.userAccount.password");
		}

		if (sponsor.getUserAccount().getUsername().length() < 5 || sponsor.getUserAccount().getUsername().length() > 32) {
			final ObjectError error = new ObjectError("userAccount.password", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("userAccount.username", "error.userAccount.username");
		}
		System.out.println(sponsor);
		System.out.println(sponsor.getUserAccount());
		System.out.println(sponsor.getUserAccount().getUsername());
		if (this.userAccountService.findByUsername(sponsor.getUserAccount().getUsername()) != null) {
			final ObjectError error = new ObjectError("userAccount.username", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("userAccount.username", "error.userAccount.username.exits");
		}

		if (binding.hasErrors()) {
			System.out.println("El error pasa por aqu� alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("sponsor/create");
		} else
			try {
				System.out.println("El error pasa por aqu� alvaro (TRY de save())");
				System.out.println(binding);
				if (sponsor.getPhone().matches("^([0-9]{4,})$"))
					sponsor.setPhone("+" + this.welcomeService.getPhone() + " " + sponsor.getPhone());
				final String password = sponsor.getUserAccount().getPassword();
				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				final String hashPassword = encoder.encodePassword(password, null);
				sponsor.getUserAccount().setPassword(hashPassword);
				this.sponsorService.save(sponsor);
				result = new ModelAndView("welcome/index");
				final String system = this.welcomeService.getSystem();
				result.addObject("system", system);
				final String logo = this.welcomeService.getLogo();
				result.addObject("logo", logo);
				SimpleDateFormat formatter;
				String moment;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());
				result.addObject("moment", moment);
			} catch (final Throwable oops) {
				System.out.println("El error: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("sponsor/create");
				final String system = this.welcomeService.getSystem();
				result.addObject("system", system);
				final String logo = this.welcomeService.getLogo();
				result.addObject("logo", logo);
				SimpleDateFormat formatter;
				String moment;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());
				result.addObject("moment", moment);
			}
		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit() {
		ModelAndView result;

		Sponsor sponsor;

		final int idUserAccount = LoginService.getPrincipal().getId();

		sponsor = this.sponsorService.findByUserAccountId(idUserAccount);

		result = new ModelAndView("sponsor/edit");

		result.addObject("sponsor", sponsor);
		final String phone = this.welcomeService.getPhone();
		result.addObject("phone", phone);
		final String system = this.welcomeService.getSystem();
		result.addObject("system", system);
		final String logo = this.welcomeService.getLogo();
		result.addObject("logo", logo);

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.POST, params = "save")
	public ModelAndView saveEdit(@Valid final Sponsor sponsor, final BindingResult binding) {
		ModelAndView result;
		if (sponsor.getUserAccount().getPassword().length() < 5 || sponsor.getUserAccount().getPassword().length() > 32) {
			final ObjectError error = new ObjectError("userAccount.password", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("userAccount.password", "error.userAccount.password");
		}

		if (sponsor.getUserAccount().getUsername().length() < 5 || sponsor.getUserAccount().getUsername().length() > 32) {
			final ObjectError error = new ObjectError("userAccount.password", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("userAccount.username", "error.userAccount.username");
		}

		if (!this.userAccountService.findByUsername(sponsor.getUserAccount().getUsername()).equals(sponsor.getUserAccount())) {
			final ObjectError error = new ObjectError("userAccount.username", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("userAccount.username", "error.userAccount.username.exits");
		}
		if (sponsor.getEmail() != null && this.actorService.getActorByEmail(sponsor.getEmail()) != null
			&& this.sponsorService.findByUserAccountId(LoginService.getPrincipal().getId()).getId() != this.actorService.getActorByEmail(sponsor.getEmail()).getId()
			|| !(sponsor.getEmail().matches("[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}") || sponsor.getEmail().matches("[\\w\\s\\w]{1,}(<)[\\w\\.\\w]{1,}(@)[\\w]{1,}\\.[\\w]{1,}(>)"))) {
			final ObjectError error = new ObjectError("actor.email", "An account already exists for this email.");
			binding.addError(error);
			binding.rejectValue("email", "error.actor.email.exits");
		}
		if (binding.hasErrors()) {
			System.out.println("El error pasa por aqu� alvaro (IF de save())");
			System.out.println(binding);
			result = new ModelAndView("sponsor/edit");
		} else
			try {
				Assert.notNull(sponsor, "sponsor.null");
				//				final String password = sponsor.getUserAccount().getPassword();
				//				final Md5PasswordEncoder encoder = new Md5PasswordEncoder();
				//				final String hashPassword = encoder.encodePassword(password, null);
				//				sponsor.getUserAccount().setPassword(hashPassword);
				if (sponsor.getPhone().matches("^([0-9]{4,})$"))
					sponsor.setPhone("+" + this.welcomeService.getPhone() + " " + sponsor.getPhone());
				this.sponsorService.save(sponsor);
				result = new ModelAndView("actor/show");
				result.addObject("actor", sponsor);
				final String system = this.welcomeService.getSystem();
				result.addObject("system", system);
				final String logo = this.welcomeService.getLogo();
				result.addObject("logo", logo);
			} catch (final Throwable oops) {
				System.out.println("El error es en sponsorController: ");
				System.out.println(oops);
				System.out.println(binding);
				result = new ModelAndView("sponsor/edit");
				final String system = this.welcomeService.getSystem();
				result.addObject("system", system);
				final String logo = this.welcomeService.getLogo();
				result.addObject("logo", logo);
				SimpleDateFormat formatter;
				String moment;
				formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
				moment = formatter.format(new Date());
				result.addObject("moment", moment);
			}
		return result;
	}
}
