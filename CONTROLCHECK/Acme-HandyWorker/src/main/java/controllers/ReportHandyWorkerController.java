
package controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import services.ReportService;
import services.WelcomeService;
import domain.Report;

@Controller
@RequestMapping("/report/handyWorker")
public class ReportHandyWorkerController extends AbstractController {

	@Autowired
	private ReportService	reportService;

	@Autowired
	private WelcomeService	welcomeService;


	// ==============================================================

	@RequestMapping(value = "/show", method = RequestMethod.GET)
	public ModelAndView show(@RequestParam("reportId") final int reportId) {

		final ModelAndView res;
		res = new ModelAndView("report/handyWorker/show");

		final Report report;
		report = this.reportService.findOne(reportId);

		res.addObject("report", report);
		final String system = this.welcomeService.getSystem();
		res.addObject("system", system);
		final String logo = this.welcomeService.getLogo();
		res.addObject("logo", logo);
		res.addObject("requestURI", "report/handyWorker/show.do");

		return res;
	}
}
