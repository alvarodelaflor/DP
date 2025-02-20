
package services;

import java.util.Collection;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import repositories.MiscellaneousRecordRepository;
import security.LoginService;
import domain.Curriculum;
import domain.HandyWorker;
import domain.MiscellaneousRecord;

@Service
@Transactional
public class MiscellaneousRecordService {

	@Autowired
	private MiscellaneousRecordRepository	miscellaneousRecordRepository;

	@Autowired
	private ActorService					actorService;

	@Autowired
	private HandyWorkerService				handyWorkerService;

	@Autowired
	private CustomerService					customerService;


	public MiscellaneousRecord create() {
		final MiscellaneousRecord miscellaneousRecord = new MiscellaneousRecord();
		return miscellaneousRecord;
	}

	public MiscellaneousRecord save(final MiscellaneousRecord miscellaneousRecord) {
		Assert.isTrue(miscellaneousRecord != null);
		final MiscellaneousRecord miscellaneousRecordSaved = this.miscellaneousRecordRepository.save(miscellaneousRecord);
		return miscellaneousRecordSaved;
	}
	public Collection<MiscellaneousRecord> findAll() {
		return this.miscellaneousRecordRepository.findAll();
	}

	public MiscellaneousRecord findOne(final Integer id) {
		return this.miscellaneousRecordRepository.findOne(id);
	}

	public void delete(final MiscellaneousRecord miscellaneousRecord) {
		final Integer idUserAccount = LoginService.getPrincipal().getId();
		Assert.notNull(idUserAccount);
		Assert.isTrue(this.handyWorkerService.findByUserAccountId(idUserAccount) != null);
		final HandyWorker handyWorker = this.handyWorkerService.findByUserAccountId(idUserAccount);
		Assert.isTrue(handyWorker != null, "hw.null");
		final Curriculum curriculum = handyWorker.getCurriculum();
		Assert.notNull(curriculum, "curriculum.null");
		Assert.isTrue(curriculum.getMisrec().contains(miscellaneousRecord));
		curriculum.getMisrec().remove(miscellaneousRecord);
		this.handyWorkerService.save(handyWorker);

		this.miscellaneousRecordRepository.delete(miscellaneousRecord);
	}

}
