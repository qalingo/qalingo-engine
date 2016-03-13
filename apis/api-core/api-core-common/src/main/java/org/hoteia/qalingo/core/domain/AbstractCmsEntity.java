package org.hoteia.qalingo.core.domain;

public abstract class AbstractCmsEntity<E, A extends AbstractAttribute<A>> extends AbstractExtendEntity<E, A> {

	/**
	 * Generated UID
	 */
	private static final long serialVersionUID = 2371513657810234869L;
	
    public static final String APP_NAME_FRONTOFFICE_B2C      = "FRONTOFFICE_B2C";
    public static final String APP_NAME_BACKOFFICE_TECHNIQUE = "BACKOFFICE_B2C";
    public static final String APP_NAME_BACKOFFICE_BUSINESS  = "BACKOFFICE_B2C";
    public static final String APP_NAME_BACKOFFICE_REPORTING = "BACKOFFICE_B2C";
    public static final String APP_NAME_BACKOFFICE_B2B       = "BACKOFFICE_B2B";
    
    abstract public String getCode();
    
    abstract public MarketArea getMarketArea();

    abstract public String getType();

}
