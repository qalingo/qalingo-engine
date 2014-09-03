package org.hoteia.qalingo.core.pojo;

public class ReferentialDataPojo {

    private String code;
    private String value;
    private String locale;
    
    public ReferentialDataPojo(String code, String locale, String value) {
        this.code = code;
        this.value = value;
        this.locale = locale;
    }
    
    public String getCode() {
        return code;
    }
    
    public void setCode(String code) {
        this.code = code;
    }
    
    public String getValue() {
        return value;
    }
    
    public void setValue(String value) {
        this.value = value;
    }
    
    public String getLocale() {
        return locale;
    }
    
    public void setLocale(String locale) {
        this.locale = locale;
    }
    
}
