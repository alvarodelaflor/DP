
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.HandyWorker;

@Repository
public interface HandyWorkerRepository extends JpaRepository<HandyWorker, Integer> {

	@Query("select hw from HandyWorker hw join hw.userAccount cua where cua.id=?1")
	HandyWorker findByUserAccountId(int userAccountId);

	@Query("select cfh from Customer c join c.fixUps cf join cf.handyWorker cfh where c.id=?1")
	Collection<HandyWorker> getAllHandyWorkersByCustomer(int customerId);

	@Query("select h from HandyWorker h where((select count(a) from HandyWorker hw join hw.applications a where(a.state=1 and hw.id=h.id)) >=((select count(a.state)/2.0 from HandyWorker h join h.applications a where (a.state=1)) * 1.1))")
	Collection<HandyWorker> betterHandyWorker();

	@Query("select distinct hw from HandyWorker hw join hw.fixUps f order by f.complaints.size desc")
	Collection<HandyWorker> getTopThreeHandyWorker();
}
