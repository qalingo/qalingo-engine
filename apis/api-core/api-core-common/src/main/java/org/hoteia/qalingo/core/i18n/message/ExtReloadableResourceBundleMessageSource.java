/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.i18n.message;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.util.Assert;

public class ExtReloadableResourceBundleMessageSource extends ReloadableResourceBundleMessageSource {

	private String[] fileBasenames = new String[0];
	
	public List<String> getFileBasenames() {
		List<String> basenameList = new ArrayList<String>();
		// REVERSE ARRAY TO KEEP THE FILEPATH ORDER LIKE SPRING
		for (int i = 0; i < fileBasenames.length; i++) {
			String fileBasename = fileBasenames[i];
			if(fileBasename.contains("classpath")){
				fileBasename = fileBasename.replace("classpath:*", "");
				fileBasename = fileBasename.replace("classpath:", "");
			}
			basenameList.add(fileBasename);
        }
		Collections.reverse(basenameList);
		return basenameList;
	}
	
	public Map<String, String> getWordingProperties(String fileName, Locale locale) {
		final Map<String, String> wording = new HashMap<String, String>();
		final PropertiesHolder propertiesHolder = getSpecificProperties(fileName, locale);
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
		String fileName = "classpath:" + fileNamePattern + "_" + locale.toString();
		PropertiesHolder propertiesHolder =  getProperties(fileName);
		if(propertiesHolder == null){
			fileName = "classpath:" + fileNamePattern;
			propertiesHolder =  getProperties(fileName);
		}
		return propertiesHolder;
	}
	
	@Override
	public void setBasenames(String... basenames) {
		if (basenames != null) {
			this.fileBasenames = new String[basenames.length];
			for (int i = 0; i < basenames.length; i++) {
				String basename = basenames[i];
				Assert.hasText(basename, "Basename must not be empty");
				this.fileBasenames[i] = basename.trim();
			}
		}
		super.setBasenames(basenames);
	}
}