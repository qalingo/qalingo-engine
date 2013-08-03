package fr.hoteia.qalingo.web.mvc.controller.oauth.json.bean.windowslive;

import java.util.ArrayList;
import java.util.List;

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
public class User extends AbstractJsonPojo {

	protected String id;
	
	protected String name;
	
	@JsonProperty("first_name")
	protected String firstName;
	
	@JsonProperty("last_name")
	protected String lastName;

	@JsonProperty("birth_day")
	protected String birthDay;

	@JsonProperty("birth_month")
	protected String birthMonth;

	@JsonProperty("birth_year")
	protected String birthYear;

	protected List<Work> work = new ArrayList<Work>();

	protected Emails emails = new Emails();

	protected String link;
	
	protected String gender;
	
	protected String locale;
	
	@JsonProperty("updated_time")
	protected String updatedTime;
	
	public String getId() {
    	return id;
    }

	public void setId(String id) {
    	this.id = id;
    }

	public String getName() {
    	return name;
    }

	public void setName(String name) {
    	this.name = name;
    }

	public String getFirstName() {
    	return firstName;
    }

	public void setFirstName(String firstName) {
    	this.firstName = firstName;
    }

	public String getLastName() {
    	return lastName;
    }

	public void setLastName(String lastName) {
    	this.lastName = lastName;
    }
	
	public String getBirthDay() {
    	return birthDay;
    }

	public void setBirthDay(String birthDay) {
    	this.birthDay = birthDay;
    }

	public String getBirthMonth() {
    	return birthMonth;
    }

	public void setBirthMonth(String birthMonth) {
    	this.birthMonth = birthMonth;
    }

	public String getBirthYear() {
    	return birthYear;
    }

	public void setBirthYear(String birthYear) {
    	this.birthYear = birthYear;
    }
	
	public List<Work> getWork() {
	    return work;
    }
	
	public void setWork(List<Work> work) {
	    this.work = work;
    }
	
	public Emails getEmails() {
	    return emails;
    }
	
	public void setEmails(Emails emails) {
	    this.emails = emails;
    }

	public String getLink() {
    	return link;
    }

	public void setLink(String link) {
    	this.link = link;
    }

	public String getGender() {
    	return gender;
    }

	public void setGender(String gender) {
    	this.gender = gender;
    }

	public String getLocale() {
    	return locale;
    }

	public void setLocale(String locale) {
    	this.locale = locale;
    }

	public String getUpdatedTime() {
    	return updatedTime;
    }

	public void setUpdatedTime(String updatedTime) {
    	this.updatedTime = updatedTime;
    }

	@JsonAnySetter
	@Override
	public void handleUnknown(String key, Object value) {
		super.handleUnknown(key, value);
	}
	
}