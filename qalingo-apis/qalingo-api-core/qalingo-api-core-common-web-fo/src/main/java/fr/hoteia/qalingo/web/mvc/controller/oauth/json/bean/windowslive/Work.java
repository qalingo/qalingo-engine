package fr.hoteia.qalingo.web.mvc.controller.oauth.json.bean.windowslive;

import org.codehaus.jackson.annotate.JsonAnySetter;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.annotate.JsonProperty;

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
public class Work extends AbstractJsonPojo {

	protected Employer employer;
	
	protected Position position;
	
	public Employer getEmployer() {
	    return employer;
    }
	
	public void setEmployer(Employer employer) {
	    this.employer = employer;
    }
	
	public Position getPosition() {
	    return position;
    }
	
	public void setPosition(Position position) {
	    this.position = position;
    }
	
	@JsonAnySetter
	@Override
	public void handleUnknown(String key, Object value) {
		super.handleUnknown(key, value);
	}
	
}