package fr.hoteia.qalingo.core.i18n.message;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import fr.hoteia.qalingo.core.i18n.enumtype.I18nBasename;

public class ExtReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

	public Map<String, String> getWordingProperties(I18nBasename i18nBasename, Locale locale) {
		final Map<String, String> wording = new HashMap<String, String>();
		final PropertiesHolder propertiesHolder = getSpecificProperties(i18nBasename.getPropertyKey(), locale);
		final Properties properties = propertiesHolder.getProperties();
		if(properties != null){
			final Set<Object> keys = properties.keySet();
			for (Iterator<Object> iterator = keys.iterator(); iterator.hasNext();) {
				String key = (String) iterator.next();
				if(StringUtils.isNotEmpty(key)){
					wording.put(key, properties.getProperty(key));
				}
			}
		}
		return wording;
	}
	
	protected PropertiesHolder getSpecificProperties(String fileNamePattern, Locale locale) {
		String fileName = "classpath:" + fileNamePattern + "_" + locale.getLanguage();
		PropertiesHolder propertiesHolder =  getProperties(fileName);
		if(propertiesHolder == null){
			fileName = "classpath:" + fileNamePattern;
			propertiesHolder =  getProperties(fileName);
		}
		return propertiesHolder;
	}
}
