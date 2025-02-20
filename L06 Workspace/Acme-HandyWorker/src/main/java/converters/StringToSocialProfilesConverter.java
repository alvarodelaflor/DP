
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.SocialProfileRepository;
import domain.SocialProfile;

@Component
@Transactional
public class StringToSocialProfilesConverter implements Converter<String, SocialProfile> {

	@Autowired
	SocialProfileRepository	socialProfileRepository;


	@Override
	public SocialProfile convert(final String text) {
		SocialProfile result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				System.out.println("Error en StringToApplicationConverter IF: " + text);
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.socialProfileRepository.findOne(id);
				System.out.println("Error en StringToSocialProfileConverter ELSE: " + result);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToSocialProfileConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
