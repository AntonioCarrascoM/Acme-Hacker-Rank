
package converters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import repositories.PositionDataRepository;
import domain.PositionData;

@Component
@Transactional
public class StringToPositionDataConverter implements Converter<String, PositionData> {

	@Autowired
	PositionDataRepository	positionDataRepository;


	@Override
	public PositionData convert(final String s) {
		PositionData result;
		int id;

		try {
			if (StringUtils.isEmpty(s))
				result = null;
			else {
				id = Integer.valueOf(s);
				result = this.positionDataRepository.findOne(id);
			}
		} catch (final Throwable oops) {
			throw new IllegalArgumentException(oops);
		}

		return result;
	}
}
