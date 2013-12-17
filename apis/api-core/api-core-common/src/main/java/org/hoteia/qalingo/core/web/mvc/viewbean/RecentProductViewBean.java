package org.hoteia.qalingo.core.web.mvc.viewbean;
import java.io.Serializable;
public class RecentProductViewBean extends AbstractViewBean implements Serializable{

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 5117261702675284863L;
	
    protected Long id;
    protected String code;
    
	protected String i18nName;
	protected String detailsUrl;

	public String getI18nName() {
		return i18nName;
	}

	public void setI18nName(String i18nName) {
		this.i18nName = i18nName;
	}

	public String getDetailsUrl() {
        return detailsUrl;
    }

    public void setDetailsUrl(String detailsUrl) {
        this.detailsUrl = detailsUrl;
    }
    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}
}
