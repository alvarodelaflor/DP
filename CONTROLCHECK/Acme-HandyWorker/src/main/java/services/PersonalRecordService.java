
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.PersonalRecordRepository;
import security.LoginService;
import domain.Curriculum;
import domain.HandyWorker;
import domain.PersonalRecord;

@Service
@Transactional
public class PersonalRecordService {

	@Autowired
	private PersonalRecordRepository	personalRecordRepository;

	@Autowired
	private HandyWorkerService			handyWorkerService;


	public PersonalRecord create() {
		final PersonalRecord personalRecord = new PersonalRecord();
		return personalRecord;
	}

	public PersonalRecord save(final PersonalRecord personalRecord) {
		Assert.isTrue(personalRecord != null);
		final PersonalRecord personalRecordSaved = this.personalRecordRepository.save(personalRecord);
		return personalRecordSaved;
	}
	public Collection<PersonalRecord> findAll() {
		return this.personalRecordRepository.findAll();
	}

	public PersonalRecord findOne(final Integer id) {
		return this.personalRecordRepository.findOne(id);
	}

	public void delete(final PersonalRecord personalRecord) {
		final Integer idUserAccount = LoginService.getPrincipal().getId();
		Assert.notNull(idUserAccount);
		Assert.isTrue(this.handyWorkerService.findByUserAccountId(idUserAccount) != null);
		final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(idUserAccount);
		Assert.isTrue(handyWorker != null, "hw.null");
		final Curriculum curriculum = handyWorker.getCurriculum();
		Assert.notNull(curriculum, "curriculum.null");
		Assert.isTrue(curriculum.getPerrec().equals(personalRecord));
		curriculum.setPerrec(null);
		this.handyWorkerService.save(handyWorker);

		this.personalRecordRepository.delete(personalRecord);
	}

}
