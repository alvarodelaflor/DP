
package repositories;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import domain.FixUp;

@Repository
public interface FixUpRepository extends JpaRepository<FixUp, Integer> {

	@Query("select f from FixUp f join f.customer fc where fc.id=?1")
	Collection<FixUp> findFixUpsByCustomer(Integer id);

	@Query("select f from FixUp f where f.handyWorker.id=?1")
	Collection<FixUp> findFixUpsByHandyWoker(Integer id);

	//	@Query("select f.fixUp from HandyWorker h join h.finder f where h.id=?1")
	//	Collection<FixUp> findFixUpsOfFinderByHandyWorker(Integer id);

	@Query("select avg(h.fixUps.size) from HandyWorker h")
	Double averagePerHandyWorker();

	@Query("select sqrt(sum(h.fixUps.size*h.fixUps.size)/count(h)-(avg(h.fixUps.size)*avg(h.fixUps.size))) from HandyWorker h")
	Double desviationPerHandyWorker();

	@Query("select min(b.quantity) from FixUp a join a.maxPrice b")
	Double minPriceFixUp();

	@Query("select max(b.quantity) from FixUp a join a.maxPrice b")
	Double maxPriceFixUp();

	@Query("select avg(b.quantity) from FixUp a join a.maxPrice b")
	Double averagePriceFixUp();

	@Query("select sqrt(sum(b.quantity*b.quantity)/count(b.quantity)-(avg(b.quantity)*avg(b.quantity))) from FixUp f join f.maxPrice b")
	Double desviationPriceFixUp();

	@Query("select min(h.fixUps.size) from HandyWorker h")
	Integer minFixUpHandyWorker();

	@Query("select max(h.fixUps.size) from HandyWorker h")
	Integer maxFixUpHandyWorker();

	@Query("select f from FixUp f where f.ticker like ?1 or f.description like ?1 or f.address like ?1")
	Collection<FixUp> filterFixUpByString(String cadena);

	@Query("select f from FixUp f where f.warranty.id=?1")
	Collection<FixUp> filterFixUpByWarranty(int warrantyId);

	@Query("select f from FixUp f where f.category.id=?1")
	Collection<FixUp> filterFixUpByCategory(int categoryId);

	@Query("select f from FixUp f where f.startDate>?1 and f.endDate<?2")
	Collection<FixUp> filterFixUpByDate(Date startDate, Date endDate);

	@Query("select f from FixUp f where f.maxPrice.quantity between ?1 and ?2")
	Collection<FixUp> filterFixUpByPrice(Double minMoney, Double maxMoney);

	//73.2 (CARMEN) --> Display the fix-up tasks in his or her finder.
	@Query("select f.fixUps from HandyWorker h join h.finder f where f.id=?1")
	Collection<FixUp> findFixUpsOfFinderByHandyWorker(Integer Fid);

	//A�ADIDO
	@Query("select ((select count(f) from FixUp f where f.complaints.size > 0)/count(f))*1.0 from FixUp f")
	Double getRatioFixUpComplaint();
}
