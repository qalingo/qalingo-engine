package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class StoreLocatorFilterBean extends AbstractViewBean implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5515180433042585447L;
	
	private final Logger logger = LoggerFactory.getLogger(getClass());
	
	private List<StoreLocatorCountryFilterBean> countries;

	public List<StoreLocatorCountryFilterBean> getCountries() {
		return countries;
	}

	public void addCountry(StoreLocatorCountryFilterBean country) {
		if(this.countries == null){
			this.countries = new ArrayList<StoreLocatorCountryFilterBean>();
		}
		this.countries.add(country);
	}	
	
	public String toJSON(){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(countries);
		} catch (JsonProcessingException e) {
			logger.warn(e.getMessage(), e);
		}
		return null;
	}
}
