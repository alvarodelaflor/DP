
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.CustomerRepository;
import domain.Customer;

@Component
@Transactional
public class StringToWarrantyConverter implements Converter<String, Customer> {

	@Autowired
	CustomerRepository	customerRepository;


	@Override
	public Customer convert(final String text) {
		Customer result;
		int id;

		try {
			if (StringUtils.isEmpty(text)) {
				System.out.println("Error en StringToCustomerConverter IF: " + text);
				result = null;
			} else {
				id = Integer.valueOf(text);
				result = this.customerRepository.findOne(id);
				System.out.println("Error en StringToCustomerConverter ELSE: " + result);
			}
		} catch (final Throwable oops) {
			System.out.println("Error en StringToCustomerConverter CATCH: " + oops);
			throw new IllegalArgumentException(oops);
		}
		return result;
	}
}
