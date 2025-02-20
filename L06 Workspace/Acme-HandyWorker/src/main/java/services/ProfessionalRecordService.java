
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.ProfessionalRecordRepository;
import security.LoginService;
import domain.Curriculum;
import domain.HandyWorker;
import domain.ProfessionalRecord;

@Service
@Transactional
public class ProfessionalRecordService {

	@Autowired
	private ProfessionalRecordRepository	professionalRecordRepository;

	@Autowired
	private HandyWorkerService				handyWorkerService;


	public ProfessionalRecord create() {
		final ProfessionalRecord professionalRecord = new ProfessionalRecord();
		return professionalRecord;
	}

	public ProfessionalRecord save(final ProfessionalRecord professionalRecord) {
		Assert.isTrue(professionalRecord != null);
		final ProfessionalRecord professionalRecordSaved = this.professionalRecordRepository.save(professionalRecord);
		return professionalRecordSaved;
	}
	public Collection<ProfessionalRecord> findAll() {
		return this.professionalRecordRepository.findAll();
	}

	public ProfessionalRecord findOne(final Integer id) {
		return this.professionalRecordRepository.findOne(id);
	}

	public void delete(final ProfessionalRecord professionalRecord) {
		final Integer idUserAccount = LoginService.getPrincipal().getId();
		Assert.notNull(idUserAccount);
		Assert.isTrue(this.handyWorkerService.findByUserAccountId(idUserAccount) != null);
		final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(idUserAccount);
		Assert.isTrue(handyWorker != null, "hw.null");
		final Curriculum curriculum = handyWorker.getCurriculum();
		Assert.notNull(curriculum, "curriculum.null");
		Assert.isTrue(curriculum.getProrec().contains(professionalRecord));
		curriculum.getProrec().remove(professionalRecord);
		this.handyWorkerService.save(handyWorker);

		this.professionalRecordRepository.delete(professionalRecord);
	}

}
