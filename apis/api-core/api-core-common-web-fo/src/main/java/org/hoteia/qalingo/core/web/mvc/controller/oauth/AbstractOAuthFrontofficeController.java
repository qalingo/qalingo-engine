package org.hoteia.qalingo.core.web.mvc.controller.oauth;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.Constants;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerGroup;
import org.hoteia.qalingo.core.domain.enumtype.CustomerPlatformOrigin;
import org.hoteia.qalingo.core.security.util.SecurityUtil;
import org.hoteia.qalingo.core.service.AttributeService;
import org.hoteia.qalingo.core.service.GroupRoleService;
import org.hoteia.qalingo.core.service.CustomerService;
import org.hoteia.qalingo.core.service.openid.OpenIdException;
import org.hoteia.qalingo.core.web.mvc.controller.AbstractFrontofficeQalingoController;
import org.scribe.model.Token;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * 
 * <p>
 * <a href="AbstractOpenIdFrontofficeController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractOAuthFrontofficeController extends AbstractFrontofficeQalingoController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CustomerService customerService;
	
	@Autowired
	protected GroupRoleService customerGroupService;
	
	@Autowired
	protected AttributeService attributeService;
	
	@Autowired
    protected SecurityUtil securityUtil;
	
	protected static final Token EMPTY_TOKEN = null;

	// TODO : denis : 20130822 : move this in properties or database config
	protected static final String LIVE_ME_URL = "https://apis.live.net/v5.0/me";
	protected static final String FACEBOOK_ME_URL = "https://graph.facebook.com/me";
	protected static final String TWITTER_URL = "http://api.twitter.com/1.1/account/verify_credentials.json";

	protected static final String TWITTER_OAUTH_REQUEST_TOKEN = "TWITTER_OAUTH_REQUEST_TOKEN";
	
	protected static final String REQUEST_PARAM_OAUTH_VERIFIER = "oauth_verifier";
	
	protected void setCommonCustomerInformation(final HttpServletRequest request, final Customer customer) throws Exception {
		customer.setPlatformOrigin(CustomerPlatformOrigin.STANDARD);

		CustomerGroup customerGroup = customerGroupService.getCustomerGroupByCode(CustomerGroup.GROUP_FO_CUSTOMER);
		customer.getGroups().add(customerGroup);
	}
	   
    void checkNonce(String nonce) {
        // check response_nonce to prevent replay-attack:
        if (nonce==null || nonce.length()<20){
            throw new OpenIdException("Verify failed.");
        }
        // make sure the time of server is correct:
        long nonceTime = getNonceTime(nonce);
        long diff = Math.abs(System.currentTimeMillis() - nonceTime);
        if (diff > Constants.ONE_HOUR){
            throw new OpenIdException("Bad nonce time.");
        }
    }

    long getNonceTime(String nonce) {
        try {
            return new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")
                    .parse(nonce.substring(0, 19) + "+0000")
                    .getTime();
        }
        catch(ParseException e) {
            throw new OpenIdException("Bad nonce time.");
        }
    }

}