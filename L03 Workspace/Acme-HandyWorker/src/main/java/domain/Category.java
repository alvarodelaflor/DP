
package domain;

import java.util.Collection;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;

@Entity
@Access(AccessType.PROPERTY)
public class Category extends DomainEntity {

	private String					nameES;
	private String					nameEN;
	private Collection<Category>	subCategories;


	public String getNameES() {
		return this.nameES;
	}

	public void setNameES(final String nameES) {
		this.nameES = nameES;
	}

	public String getNameEN() {
		return this.nameEN;
	}

	public void setNameEN(final String nameEN) {
		this.nameEN = nameEN;
	}

	@ManyToMany(cascade = {
		CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH
	})
	public Collection<Category> getSubCategories() {
		return this.subCategories;
	}

	public void setSubCategories(final Collection<Category> subCategories) {
		this.subCategories = subCategories;
	}

}
