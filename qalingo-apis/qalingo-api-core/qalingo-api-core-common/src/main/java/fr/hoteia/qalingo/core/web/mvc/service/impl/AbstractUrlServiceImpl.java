package fr.hoteia.qalingo.core.web.mvc.service.impl;

import java.net.URLEncoder;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.Localization;
import fr.hoteia.qalingo.core.i18n.enumtype.I18nKeyValueUniverse;
import fr.hoteia.qalingo.core.i18n.enumtype.ScopeWebMessage;
import fr.hoteia.qalingo.core.i18n.message.CoreMessageSource;
import fr.hoteia.qalingo.core.service.EngineSettingService;
import fr.hoteia.qalingo.core.service.pojo.RequestData;

public abstract class AbstractUrlServiceImpl {

	@Autowired
	public CoreMessageSource coreMessageSource;

	@Autowired
	public EngineSettingService engineSettingService;
	
    protected String handleUrlParameters(String url, Map<String, String> urlParams, Map<String, String> getParams) {
    	if(StringUtils.isNotEmpty(url)){
            if (urlParams != null) {
                for (Entry<String, String> entry : urlParams.entrySet()) {
                    String key = String.format("\\{%s(:[^\\}]+)?\\}", entry.getKey());
                    if (StringUtils.equals(entry.getKey(), "slug")){
                    	key = "\\*\\*";
                    }
                    if (entry.getValue() != null){
                    	url = url.replaceAll(key, entry.getValue());
                    }
                }
            }

            String queryString = "";
            if (getParams != null) {
                for (Entry<String, String> entry : getParams.entrySet()) {
                    queryString += "&" + entry.getKey() + "=" + entry.getValue();
                }
            }
            return url + queryString.replaceFirst("&", "?");
    	}
        return url;
    }
    
	protected String encodeString(String url) throws Exception {
		if(StringUtils.isNotEmpty(url)){
			return URLEncoder.encode(url, Constants.UTF8);
		}
		return url;
	}

	protected String handleParamValue(String string) throws Exception {
		if(StringUtils.isNotEmpty(string)) {
			string = string.toLowerCase();
		}
		return string;
	}

	protected String handleString(String string) throws Exception {
		if(StringUtils.isNotEmpty(string)) {
			string = string.replaceAll(" ", "-");
			string = string.replaceAll("_", "-");
			String escapeAccent = engineSettingService.withEscapeAccent().getDefaultValue();
			if(BooleanUtils.toBoolean(escapeAccent)){
				string = string.replaceAll("[àáâãäå]", "a");
				string = string.replaceAll("[ç]", "c");
				string = string.replaceAll("[èéêë]", "e");
				string = string.replaceAll("[ìíîï]", "i");
				string = string.replaceAll("[ðòóôõö]", "o");
				string = string.replaceAll("[ùúûü]", "u");
				string = string.replaceAll("[ýÿ]", "y");
			}
			string = encodeString(string).toLowerCase().trim();
		 }
		return string;
	}
	
	protected String buildDefaultPrefix(final RequestData requestData){
		return buildContextPath(requestData) + Constants.SPRING_URL_PATH;
	}
	
	protected String buildContextPath(final RequestData requestData){
		return requestData.getContextPath();
	}
	
	protected String getMessage(final Localization localization, final String key) throws Exception {
		final Locale locale = localization.getLocale();
		return handleString(coreMessageSource.getSpecificMessage(I18nKeyValueUniverse.FO, ScopeWebMessage.SEO, key, locale));
	}

	protected MessageSource getMessageSource() throws Exception {
		return coreMessageSource;
	}
	
}