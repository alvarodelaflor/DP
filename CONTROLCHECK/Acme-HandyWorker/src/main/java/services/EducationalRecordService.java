
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EducationalRecordRepository;
import security.LoginService;
import domain.Curriculum;
import domain.EducationalRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EducationalRecordService {

	@Autowired
	private EducationalRecordRepository	educationalRecordRepository;

	@Autowired
	private HandyWorkerService			handyWorkerService;


	public EducationalRecord create() {
		final EducationalRecord educationalRecord = new EducationalRecord();
		return educationalRecord;
	}

	public EducationalRecord save(final EducationalRecord educationalRecord) {
		Assert.isTrue(educationalRecord != null);
		final EducationalRecord educationalRecordSaved = this.educationalRecordRepository.save(educationalRecord);
		return educationalRecordSaved;
	}
	public Collection<EducationalRecord> findAll() {
		return this.educationalRecordRepository.findAll();
	}

	public EducationalRecord findOne(final Integer id) {
		return this.educationalRecordRepository.findOne(id);
	}

	public void delete(final EducationalRecord educationalRecord) {
		final Integer idUserAccount = LoginService.getPrincipal().getId();
		Assert.notNull(idUserAccount);
		Assert.isTrue(this.handyWorkerService.findByUserAccountId(idUserAccount) != null);
		final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(idUserAccount);
		Assert.isTrue(handyWorker != null, "hw.null");
		final Curriculum curriculum = handyWorker.getCurriculum();
		Assert.notNull(curriculum, "curriculum.null");
		Assert.isTrue(curriculum.getEdurec().contains(educationalRecord));
		curriculum.getEdurec().remove(educationalRecord);
		this.handyWorkerService.save(handyWorker);

		this.educationalRecordRepository.delete(educationalRecord);
	}

}
