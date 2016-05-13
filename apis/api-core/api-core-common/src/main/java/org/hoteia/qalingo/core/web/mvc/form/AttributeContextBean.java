package org.hoteia.qalingo.core.web.mvc.form;

public class AttributeContextBean {

	protected String code;
	protected String marketAreaCode;
    protected String localizationCode;
    protected boolean global;
    
	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

    public String getMarketAreaCode() {
        return marketAreaCode;
    }

    public void setMarketAreaCode(String marketAreaCode) {
        this.marketAreaCode = marketAreaCode;
    }

    public String getLocalizationCode() {
        return localizationCode;
    }

    public void setLocalizationCode(String localizationCode) {
        this.localizationCode = localizationCode;
    }

    public boolean isGlobal() {
        return global;
    }

    public void setGlobal(boolean global) {
        this.global = global;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((code == null) ? 0 : code.hashCode());
        result = prime * result + (global ? 1231 : 1237);
        result = prime * result + ((localizationCode == null) ? 0 : localizationCode.hashCode());
        result = prime * result + ((marketAreaCode == null) ? 0 : marketAreaCode.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        AttributeContextBean other = (AttributeContextBean) obj;
        if (code == null) {
            if (other.code != null)
                return false;
        } else if (!code.equals(other.code))
            return false;
        if (global != other.global)
            return false;
        if (localizationCode == null) {
            if (other.localizationCode != null)
                return false;
        } else if (!localizationCode.equals(other.localizationCode))
            return false;
        if (marketAreaCode == null) {
            if (other.marketAreaCode != null)
                return false;
        } else if (!marketAreaCode.equals(other.marketAreaCode))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return "AttributeContextBean [code=" + code + ", marketAreaCode=" + marketAreaCode + ", localizationCode=" + localizationCode + ", global=" + global + "]";
    }

}