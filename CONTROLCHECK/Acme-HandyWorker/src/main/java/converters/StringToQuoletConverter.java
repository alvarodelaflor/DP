
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.QuoletRepository;
import domain.Quolet;

@Component
@Transactional
public class StringToQuoletConverter implements Converter<String, Quolet> {

	@Autowired
	QuoletRepository	quoletRepository;


	@Override
	public Quolet convert(final String text) {
		Quolet result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				System.out.println("Error en StringToQuoletConverter IF: " + text);
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.quoletRepository.findOne(id);
				System.out.println("Error en StringToQuoletConverter ELSE: " + result);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToQuoletConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
