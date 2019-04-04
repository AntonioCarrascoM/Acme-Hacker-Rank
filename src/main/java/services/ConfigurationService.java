
package services;

import java.util.Collection;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import repositories.ConfigurationRepository;
import security.Authority;
import domain.Actor;
import domain.Configuration;

@Service
@Transactional
public class ConfigurationService {

	//Managed repository

	@Autowired
	private ConfigurationRepository	configurationRepository;

	@Autowired
	private ActorService			actorService;


	//Supporting services --------------------------------

	//Simple CRUD methods

	public Collection<Configuration> findAll() {
		return this.configurationRepository.findAll();
	}

	public Configuration findOne(final int id) {
		Assert.notNull(id);

		return this.configurationRepository.findOne(id);
	}

	public Configuration save(final Configuration configuration) {
		Assert.notNull(configuration);
		final Actor a = this.actorService.findByPrincipal();
		final Authority auth = new Authority();
		auth.setAuthority(Authority.ADMIN);

		//Assertion that the user modifying this configuration has the correct privilege.
		Assert.isTrue(a.getUserAccount().getAuthorities().contains(auth));

		//Assertion to make sure that the country code is valid.
		Assert.isTrue(this.checkCountryCode(configuration));

		final Configuration saved = this.configurationRepository.save(configuration);

		return saved;
	}

	//Assertion to make sure that the country code is between 1 and 999.
	private Boolean checkCountryCode(final Configuration configuration) {
		Boolean res = true;

		final String cc = configuration.getCountryCode();
		String number = cc.substring(1);
		if (StringUtils.isNumeric(number)) {

			final Integer numero = Integer.parseInt(number);

			if (numero < 1 || numero > 999)
				res = false;
			else
				configuration.setCountryCode("+" + number + " ");
		} else if (StringUtils.isNumericSpace(number)) {
			final int largo = number.length();
			number = number.substring(0, largo - 1);
			final Integer numero = Integer.parseInt(number);
			if (numero < 1 || numero > 999)
				res = false;
			else
				configuration.setCountryCode("+" + number + " ");
		}
		return res;
	}
}
