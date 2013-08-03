package fr.hoteia.qalingo.web.mvc.controller.oauth.json.bean.windowslive;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;

import fr.hoteia.qalingo.core.json.pojo.AbstractJsonPojo;

/**
*
* <p>
* <a href="CatalogJsonPojo.java.html"><i>View Source</i></a>
* </p>
*
* @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
* 
*/
@JsonIgnoreProperties(ignoreUnknown=true)
public class Emails extends AbstractJsonPojo {

	protected String preferred;
	
	protected String account;
	
	protected String personal;
	
	protected String business;

	public String getPreferred() {
		return preferred;
	}
	
	public void setPreferred(String preferred) {
		this.preferred = preferred;
	}

	public String getAccount() {
    	return account;
    }

	public void setAccount(String account) {
    	this.account = account;
    }

	public String getPersonal() {
    	return personal;
    }

	public void setPersonal(String personal) {
    	this.personal = personal;
    }

	public String getBusiness() {
	    return business;
    }
	
	public void setBusiness(String business) {
	    this.business = business;
    }

	@JsonAnySetter
	@Override
	public void handleUnknown(String key, Object value) {
		super.handleUnknown(key, value);
	}
	
}