
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Finder;

@Component
@Transactional
public class FinderToStringConverter implements Converter<Finder, String> {

	@Override
	public String convert(final Finder finder) {
		String result;

		if (finder == null)
			result = null;
		else
			result = String.valueOf(finder.getId());
		System.out.println("CONVERTIDOR FinderToStringConverter.java " + result);
		return result;
	}
}
