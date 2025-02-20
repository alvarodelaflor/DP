
package services;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.WarrantyRepository;
import security.LoginService;
import domain.Administrator;
import domain.Warranty;

@Service
@Transactional
public class WarrantyService {

	//Managed Repository -------------------	

	//Managed Repository -------------------	

	@Autowired
	private WarrantyRepository		warrantyRepository;

	@Autowired
	private AdministratorService	administratorService;


	//Supporting services ------------------

	//Simple CRUD Methods ------------------

	public Warranty create() {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Warranty warranty = new Warranty();
		warranty.setIsFinal(false);

		return warranty;

	}

	public Collection<Warranty> findAll() {
		return this.warrantyRepository.findAll();
	}

	public Warranty findOne(final int id) {
		return this.warrantyRepository.findOne(id);
	}
	public Warranty save(final Warranty warranty) {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		return this.warrantyRepository.save(warranty);
	}

	public Warranty udpate(final Warranty warranty) {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		final Warranty oldWarranty = this.findOne(warranty.getId());
		System.out.println(oldWarranty.getIsFinal());
		System.out.println(oldWarranty.getTitle());
		Assert.isTrue(oldWarranty.getIsFinal() == false);
		return this.warrantyRepository.save(warranty);
	}

	public void delete(final Warranty warranty) {
		final Administrator a = this.administratorService.findByUserAccount(LoginService.getPrincipal().getId());
		Assert.notNull(a);
		Assert.notNull(this.warrantyRepository.findOne(warranty.getId()), "La Warranty no existe");
		Assert.isTrue(warranty.getIsFinal() == false);
		this.warrantyRepository.delete(warranty);
	}

}
