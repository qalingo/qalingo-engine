package org.hoteia.qalingo.core.pojo;

public class ValuePojo {

    private String code;
    private String value;
    
    public ValuePojo() {
    }
    
    public ValuePojo(String code, String value) {
        this.code = code;
        this.value = value;
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
    
}
