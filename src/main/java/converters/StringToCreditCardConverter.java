
package converters;

import java.net.URLDecoder;

import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import domain.CreditCard;

@Component
@Transactional
public class StringToCreditCardConverter implements Converter<String, CreditCard> {

	@Override
	public CreditCard convert(final String s) {
		CreditCard result;
		String parts[];

		if (s == null || s == "")
			result = null;
		else
			try {
				parts = s.split("\\|");
				result = new CreditCard();
				result.setHolder(URLDecoder.decode(parts[0], "UTF-8"));
				result.setMake(URLDecoder.decode(parts[1], "UTF-8"));
				result.setNumber(URLDecoder.decode(parts[2], "UTF-8"));
				result.setExpMonth(Integer.valueOf(URLDecoder.decode(parts[3], "UTF-8")));
				result.setExpYear(Integer.valueOf(URLDecoder.decode(parts[4], "UTF-8")));
				result.setCvv(Integer.valueOf(URLDecoder.decode(parts[5], "UTF-8")));
			} catch (final Throwable oops) {
				throw new RuntimeException(oops);
			}

		return result;
	}
}
