
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.EndorserRecordRepository;
import security.LoginService;
import domain.Curriculum;
import domain.EndorserRecord;
import domain.HandyWorker;

@Service
@Transactional
public class EndorserRecordService {

	@Autowired
	private EndorserRecordRepository	endorserRecordRepository;

	@Autowired
	private HandyWorkerService			handyWorkerService;


	public EndorserRecord create() {
		final EndorserRecord endorserRecord = new EndorserRecord();
		return endorserRecord;
	}

	public EndorserRecord save(final EndorserRecord endorserRecord) {
		Assert.isTrue(endorserRecord != null);
		final EndorserRecord endorserRecordSaved = this.endorserRecordRepository.save(endorserRecord);
		return endorserRecordSaved;
	}
	public Collection<EndorserRecord> findAll() {
		return this.endorserRecordRepository.findAll();
	}

	public EndorserRecord findOne(final Integer id) {
		return this.endorserRecordRepository.findOne(id);
	}

	public void delete(final EndorserRecord endorserRecord) {
		final Integer idUserAccount = LoginService.getPrincipal().getId();
		Assert.notNull(idUserAccount);
		Assert.isTrue(this.handyWorkerService.findByUserAccountId(idUserAccount) != null);
		final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(idUserAccount);
		Assert.isTrue(handyWorker != null, "hw.null");
		final Curriculum curriculum = handyWorker.getCurriculum();
		Assert.notNull(curriculum, "curriculum.null");
		Assert.isTrue(curriculum.getEndrec().contains(endorserRecord));
		curriculum.getEndrec().remove(endorserRecord);
		this.handyWorkerService.save(handyWorker);

		this.endorserRecordRepository.delete(endorserRecord);
	}

}
