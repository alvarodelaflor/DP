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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ActorService;
import services.ApplicationService;
import services.CategoryService;
import services.ComplaintService;
import services.WarrantyService;
import domain.Actor;

@Controller
@RequestMapping("/actor/administrator")
public class ActorAdministratorController extends AbstractController {

	@Autowired
	private ActorService		actorService;
	@Autowired
	private ComplaintService	complaintService;
	@Autowired
	private ApplicationService	applicationService;
	@Autowired
	private CategoryService		categoryService;
	@Autowired
	private WarrantyService		warrantyService;


	// Constructors -----------------------------------------------------------

	public ActorAdministratorController() {
		super();
	}

	@RequestMapping(value = "/list")
	public ModelAndView list() {
		ModelAndView result;
		final Collection<Actor> actor = this.actorService.findActorsSuspicious();
		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		System.out.println("Carmen: Entro en el list");

		System.out.println(actor);

		result = new ModelAndView("actor/administrator/list");
		result.addObject("actor", actor);
		result.addObject("language", language);
		result.addObject("requestURI", "actor/administrator/list.do");

		return result;
	}

	@RequestMapping(value = "/edit", method = RequestMethod.GET)
	public ModelAndView edit(@RequestParam("actorId") final int actorId) {
		ModelAndView result;

		System.out.println("Carmen: Voy a unBanActor");

		final String language = LocaleContextHolder.getLocale().getDisplayLanguage();

		Actor actor;

		actor = this.actorService.findOne(actorId);
		Assert.notNull(actorId);

		System.out.println("Carmen: El actor es:" + actor + "se encunetra ban" + actor.getIsBanned());

		if (actor.getIsBanned() == false)
			actor = this.actorService.banActor(actor);
		else
			actor = this.actorService.unBanActor(actor);

		System.out.println("Carmen: El actor es:" + actor + "se encunetra ban" + actor.getIsBanned());

		this.actorService.save(actor);

		result = new ModelAndView("actor/administrator/list");
		result.addObject("language", language);
		result.addObject("actor", actor);

		return result;
	}

}
