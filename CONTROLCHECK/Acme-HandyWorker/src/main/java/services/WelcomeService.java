
package services;

import org.hibernate.validator.constraints.URL;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class WelcomeService {

	String	e		= "Welcome to Acme Handy Worker! Price, quality, and trust in a single place";

	String	s		= "�Bienvenidos a Acme Handy Worker! Precio, calidad y confianza en el mismo sitio!";

	String	system	= "Acme Handy Worker";

	String	phone	= "34";

	String	country	= "Espa�a/Spain";

	@URL
	String	logo	= "https://tinyurl.com/acme-handy-worker-logo";


	public String getLogo() {
		return this.logo;
	}

	public String newLogo(final String newLogo) {
		//		Assert.isTrue(!this.checkUrl(newLogo), "logo.bad");
		this.logo = newLogo;
		return this.logo;
	}

	private Boolean checkUrl(final String url) {
		Boolean res = true;
		final String[] elementos = url.split("://");
		final String elemento1 = elementos[0];
		final String elemento2 = elementos[1];
		if (elemento1 == "https" || elemento1 == "http")
			res = false;
		return res;
	}

	public String newE(final String newE) {
		this.e = newE;
		return this.e;
	}

	public String newS(final String newS) {
		this.s = newS;
		return this.s;
	}

	public String getE() {
		return this.e;
	}

	public String getS() {
		return this.s;
	}

	public String getSystem() {
		return this.system;
	}

	public String newSystem(final String newSystem) {
		this.system = newSystem;
		return this.system;
	}

	public String getPhone() {
		return this.phone;
	}

	public String newPhone(final String phoneNew) {
		this.phone = phoneNew;
		return this.phone;
	}

	public String getCountry() {
		return this.country;
	}

	public void newCountry(final String country) {
		this.country = country;
	}
}
