
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CurriculumRepository;
import domain.Curriculum;

@Component
@Transactional
public class StringToCurriculumConverter implements Converter<String, Curriculum> {

	@Autowired
	CurriculumRepository	curriculumRepository;


	@Override
	public Curriculum convert(final String text) {
		Curriculum result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				System.out.println("Error en StringTocurriculumConverter IF: " + text);
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.curriculumRepository.findOne(id);
				System.out.println("Error en StringTocurriculumConverter ELSE: " + result);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringTocurriculumConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
