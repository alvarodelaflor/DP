
package converters;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.Report;

@Component
@Transactional
public class ReportToStringConverter implements Converter<Report, String> {

	@Override
	public String convert(final Report Report) {

		String res;

		if (Report == null)
			res = null;
		else
			res = String.valueOf(Report.getId());

		return res;
	}

}
