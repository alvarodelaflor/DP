
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.HandyWorkerRepository;
import domain.HandyWorker;

@Component
@Transactional
public class StringToHandyWorkerConverter implements Converter<String, HandyWorker> {

	@Autowired
	HandyWorkerRepository	handyWorkerRepository;


	@Override
	public HandyWorker convert(final String text) {
		HandyWorker result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				System.out.println("Error en StringToHandyWorkerConverter IF: " + text);
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.handyWorkerRepository.findOne(id);
				System.out.println("Error en StringToHandyWorkerConverter ELSE: " + result);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToHandyWorkerConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
