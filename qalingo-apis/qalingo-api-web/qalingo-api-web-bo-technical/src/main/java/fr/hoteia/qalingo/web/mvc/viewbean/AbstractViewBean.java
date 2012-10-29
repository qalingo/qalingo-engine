package fr.hoteia.qalingo.web.mvc.viewbean;

import java.io.Serializable;

import org.drools.core.util.StringUtils;

public abstract class AbstractViewBean implements Serializable {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5246629091678484667L;

	protected String handleString(String string){
		if(StringUtils.isEmpty(string)){
			return "";
		}
		return string;
	}
}
