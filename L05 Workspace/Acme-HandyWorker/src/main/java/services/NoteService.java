
package services;

import java.util.Collection;
import java.util.Date;

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
		final UserAccount login = LoginService.getPrincipal();
		final Authority hw = new Authority();
		hw.setAuthority(Authority.HANDYWORKER);
		final Authority c = new Authority();
		hw.setAuthority(Authority.CUSTOMER);
		final Authority r = new Authority();
		hw.setAuthority(Authority.REFEREE);

		if (login.getAuthorities().contains(hw))
			res.setHandyWorker(this.handyWorkerService.getHandyWorkerByUserAccountId(login.getId()));
		else if (login.getAuthorities().contains(c))
			res.setCustomer(this.customerService.getCustomerByUserAccountId(login.getId()));

		//El report esta vac�o porque se pondr� el valor por defecto en otro create
		//        if (login.getAuthorities().contains(r)) {    
		//            res.setReport(this.refereeService.findRefereeByReport(res.getReport()));
		//        }

		final Date moment = LocalDate.now().toDate();
		res.setMoment(moment);

		return res;
	}
	//CARMEN
	//FRAN

	//CARMEN
	public Note save(final Note note) {
		Assert.isTrue(note.getReport() != null);
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
}
