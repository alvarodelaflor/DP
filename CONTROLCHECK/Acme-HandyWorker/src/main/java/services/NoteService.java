
package services;

import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.joda.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.NoteRepository;
import security.Authority;
import security.LoginService;
import security.UserAccount;
import domain.Note;
import domain.Report;

@Service
@Transactional
public class NoteService {

	@Autowired
	private NoteRepository		noteRepository;

	@Autowired
	private RefereeService		refereeService;

	@Autowired
	private CustomerService		customerService;

	@Autowired
	private HandyWorkerService	handyWorkerService;

	@Autowired
	private ReportService		reportService;


	//36.4 (CARMEN) --> Write a note regarding any of the reports that he or shes written (as long as it was
	//saved in final mode).
	//35.2 (FRAN)
	public Note create() {
		final Note res = new Note();
		//		final UserAccount login = LoginService.getPrincipal();
		//		final Authority hw = new Authority();
		//		hw.setAuthority(Authority.HANDYWORKER);
		//		final Authority c = new Authority();
		//		hw.setAuthority(Authority.CUSTOMER);
		//		final Authority r = new Authority();
		//		hw.setAuthority(Authority.REFEREE);
		//
		//		if (login.getAuthorities().contains(hw))
		//			res.setCommentHandyWorker("");
		//		else if (login.getAuthorities().contains(c))
		//			res.setCustomer(this.customerService.getCustomerByUserAccountId(login.getId()));
		//
		//		//El report esta vac�o porque se pondr� el valor por defecto en otro create
		//		//        if (login.getAuthorities().contains(r)) {    
		//		//            res.setReport(this.refereeService.findRefereeByReport(res.getReport()));
		//		//        }

		final Report report = new Report();

		final Date moment = LocalDate.now().toDate();

		res.setMoment(moment);
		res.setReport(report);

		return res;
	}
	//CARMEN
	//FRAN

	//CARMEN
	public Note save(final Note note) {
		//Assert.isTrue(note.getReport() != null);
		return this.noteRepository.save(note);
	}
	//CARMEN

	//CARMEN
	public Collection<Note> findAll() {
		return this.noteRepository.findAll();
	}
	//CARMEN

	//CARMEN
	public Note findOne(final Integer note) {
		return this.noteRepository.findOne(note);
	}
	//CARMEN

	//35.3 (FRAN)
	//36.5 (CARMEN)
	//37.5 (CARMEN)
	public Note update(final Note note) {

		final UserAccount login = LoginService.getPrincipal();
		final Authority hw = new Authority();
		hw.setAuthority(Authority.HANDYWORKER);
		final Authority c = new Authority();
		hw.setAuthority(Authority.CUSTOMER);
		final Authority r = new Authority();
		hw.setAuthority(Authority.REFEREE);

		if (this.handyWorkerService.getHandyWorkerByUserAccountId(login.getId()) != null) {
			Assert.isTrue(note.getHandyWorker().getUserAccount().getId() == login.getId());
			return this.noteRepository.save(note);

		}

		if (this.customerService.getCustomerByUserAccountId(login.getId()) != null) {
			Assert.isTrue(note.getCustomer().getUserAccount().getId() == login.getId());
			return this.noteRepository.save(note);
		}
		return this.noteRepository.save(note);

		//El report esta vac�o porque se pondr� el valor por defecto en otro create
		//        if (login.getAuthorities().contains(r)) {    
		//            res.setReport(this.refereeService.findRefereeByReport(res.getReport()));
		//        }

	}
	//FRAN
	//CARMEN

	//A�ADIDO
	public Map<String, Double> computeStatistics2() {
		Map<String, Double> result;
		double minNoteFx, maxNoteFx, avNoteFx, sdNoteFx;

		result = new HashMap<String, Double>();

		if (this.noteRepository.getMinNotesPerFixUp() == null)
			result.put("min.note.fx", 0.0);
		else {
			minNoteFx = this.noteRepository.getMinNotesPerFixUp();
			result.put("min.note.fx", minNoteFx);
		}

		if (this.noteRepository.getMaxNotesPerFixUp() == null)
			result.put("max.note.fx", 0.0);
		else {
			maxNoteFx = this.noteRepository.getMaxNotesPerFixUp();
			result.put("max.note.fx", maxNoteFx);
		}

		if (this.noteRepository.getAvgNotesPerFixUp() == null)
			result.put("av.note.fx", 0.0);
		else {
			avNoteFx = this.noteRepository.getAvgNotesPerFixUp();
			result.put("av.note.fx", avNoteFx);
		}

		if (this.noteRepository.getStandardDeviationNotesPerFixUp() == null)
			result.put("sd.note.fx", 0.0);
		else {
			sdNoteFx = this.noteRepository.getStandardDeviationNotesPerFixUp();
			result.put("sd.note.fx", sdNoteFx);
		}

		return result;
	}

}
