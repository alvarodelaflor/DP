
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.FixUp;

@Component
@Transactional
public class FixUpToStringConverter implements Converter<FixUp, String> {

	@Override
	public String convert(final FixUp fixUp) {
		String result;

		if (fixUp == null)
			result = null;
		else
			result = String.valueOf(fixUp.getId());
		System.out.println("CONVERTIDOR FixUpToStringConverter.java " + result);
		return result;
	}
}
