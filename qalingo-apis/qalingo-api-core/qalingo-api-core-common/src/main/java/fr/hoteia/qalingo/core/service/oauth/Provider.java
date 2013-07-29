package fr.hoteia.qalingo.core.service.oauth;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 */
public class Provider {

    public static final String OPEN_ID_PROVIDERS_FILE = "openid-providers.properties";
    public static final String OPEN_ID_PROVIDER_ALIAS_SUFIX = ".alias";

//    public static final String REQUEST_PARAM_OPEN_ID_RESPONSE_NONCE		= "openid.response_nonce";
//    public static final String REQUEST_PARAM_OPEN_ID_IDENTITY			= "openid.identity";
//    public static final String REQUEST_PARAM_OPEN_ID_INVALIDATE_HANDLE	= "openid.invalidate_handle";
//    public static final String REQUEST_PARAM_OPEN_ID_SIG				= "openid.sig";
//    public static final String REQUEST_PARAM_OPEN_ID_SIGNED				= "openid.signed";
//    public static final String REQUEST_PARAM_OPEN_ID_RETURN_TO			= "openid.return_to";
//    public static final String REQUEST_PARAM_OPEN_ID_ASSOC_HANDLE		= "openid.assoc_handle";
//    public static final String REQUEST_PARAM_OPEN_ID_REALM				= "openid.realm";
//    
//    public static final String REQUEST_PARAM_OPEN_ID_NS					= "openid.ns";
//    public static final String REQUEST_PARAM_OPEN_ID_CLAIMED_ID			= "openid.claimed_id";
//    public static final String REQUEST_PARAM_OPEN_ID_MODE				= "openid.mode";
//    public static final String REQUEST_PARAM_OPEN_ID_SESSION_TYPE		= "openid.session_type";
//    public static final String REQUEST_PARAM_OPEN_ID_ASSOC_TYPE			= "openid.assoc_type";
    
    public static final String ATTR_MAC = "openid_mac";
    public static final String ATTR_ALIAS = "openid_alias";
    
	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
    private Map<String, String> urlMap = new HashMap<String, String>();
    private Map<String, String> aliasMap = new HashMap<String, String>();

    /**
     * Load providers from "openid-providers.properties" under class path.
     * @throws IOException 
     */
    public Provider() {
        InputStream input = null;
        try {
            input = getClass().getClassLoader().getResourceAsStream(OPEN_ID_PROVIDERS_FILE);
            Properties props = new Properties();
            props.load(input);
            for (Object k : props.keySet()) {
                String key = (String) k;
                String value = props.getProperty(key);
                if (key.endsWith(OPEN_ID_PROVIDER_ALIAS_SUFIX)) {
                    aliasMap.put(key.substring(0, key.length()-6), value);
                }
                else {
                    urlMap.put(key, value);
                }
            }
        }
        catch (IOException e) {
        	LOG.error("Loading providers failed!", e);
        }
        finally {
            if (input != null) {
            	try {
            		input.close();
                } catch (Exception e) {
                	LOG.debug("Provider : close properties file failed!");
                }
            }
        }
    }

    String lookupUrlByName(String name) {
        return urlMap.get(name);
    }

    String lookupAliasByName(String name) {
        String alias = aliasMap.get(name);
        return alias==null ? Endpoint.DEFAULT_ALIAS : alias;
    }

}