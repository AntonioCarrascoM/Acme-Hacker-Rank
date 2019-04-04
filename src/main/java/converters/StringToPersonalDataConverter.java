
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PersonalDataRepository;
import domain.PersonalData;

@Component
@Transactional
public class StringToPersonalDataConverter implements Converter<String, PersonalData> {

	@Autowired
	PersonalDataRepository	personalDataRepository;


	@Override
	public PersonalData convert(final String s) {
		PersonalData result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.personalDataRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
