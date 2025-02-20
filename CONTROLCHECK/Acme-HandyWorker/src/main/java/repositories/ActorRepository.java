
package repositories;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import security.UserAccount;
import domain.Actor;

@Repository
public interface ActorRepository extends JpaRepository<Actor, Integer> {

	@Query("select a from Actor a where a.isBanned=true")
	Collection<Actor> findActorsBanned();

	@Query("select a from Actor a where a.isSuspicious=true")
	Collection<Actor> findActorsSuspicious();

	@Query("select a from Actor a join a.userAccount u where u.id = ?1")
	Actor getActorByUserId(Integer id);

	@Query("select u from Actor a join a.userAccount u where a.id = ?1")
	UserAccount getUserByUserActorId(Integer id);

	@Query("select a from Actor a join a.userAccount cua where cua.id=?1")
	Actor findByUserAccountId(int userAccountId);

	@Query("select a from Actor a where a.email = ?1")
	Actor getActorByEmail(String email);

	@Query("select a.email from Actor a where a.email!=null")
	Collection<String> getEmailofActors();

	@Query("select a from Actor a join a.mailBoxes m where m.id=?1")
	Actor getActorByMailBox(Integer id);
}
