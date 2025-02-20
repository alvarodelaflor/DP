
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.TutorialRepository;
import domain.Tutorial;

@Component
@Transactional
public class StringToTutorialConverter implements Converter<String, Tutorial> {

	@Autowired
	TutorialRepository	tutorialRepository;


	@Override
	public Tutorial convert(final String text) {
		Tutorial result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				System.out.println("Error en TutorialToTutorialConverter IF: " + text);
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.tutorialRepository.findOne(id);
				System.out.println("Error en StringToTutorialConverter ELSE: " + result);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToTutorialConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
