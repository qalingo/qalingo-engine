package fr.hoteia.qalingo.web.mvc.controller.openid;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAttribute;
import fr.hoteia.qalingo.core.domain.CustomerGroup;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.AttributeService;
import fr.hoteia.qalingo.core.service.CustomerGroupService;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.service.openid.OpenIdAuthentication;
import fr.hoteia.qalingo.core.service.openid.OpenIdException;
import fr.hoteia.qalingo.core.service.openid.OpenIdService;
import fr.hoteia.qalingo.core.web.mvc.controller.AbstractQalingoController;

/**
 * 
 * <p>
 * <a href="AbstractOpenIdFrontofficeController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractOpenIdFrontofficeController extends AbstractQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected OpenIdService openIdService;

	@Autowired
	protected CustomerService customerService;
	
	@Autowired
	protected CustomerGroupService customerGroupService;
	
	@Autowired
	protected AttributeService attributeService;
	
	@Autowired
    protected SecurityUtil securityUtil;
	
	void handleAuthenticationData(final HttpServletRequest request, final OpenIdAuthentication auth) throws Exception {
		final String email = auth.getEmail();
		Customer customer = customerService.getCustomerByLoginOrEmail(email);
		
		if(customer == null){
			// CREATE A NEW CUSTOMER
			customer = new Customer();
			customer.setLogin(email);
			customer.setPassword(securityUtil.generateAndEncodePassword());
			customer.setEmail(email);
			customer.setFirstname(auth.getFirstname());
			customer.setLastname(auth.getLastname());
			if(StringUtils.isNotEmpty(auth.getGender())){
				customer.setGender(auth.getGender());
			}
			
			CustomerAttribute attribute = new CustomerAttribute();
			AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(CustomerAttribute.CUSTOMER_ATTRIBUTE_SCREENAME);
			attribute.setAttributeDefinition(attributeDefinition);
			String screenName = "";
			if(StringUtils.isNotEmpty(auth.getLastname())){
				if(StringUtils.isNotEmpty(auth.getLastname())){
					screenName = auth.getLastname();
					if(screenName.length() > 1){
						screenName = screenName.substring(0, 1);
					}
					if(!screenName.endsWith(".")){
						screenName = screenName + ". ";
					}
				}
			}
			screenName = screenName + auth.getFirstname();
			attribute.setStringValue(screenName);
			customer.getCustomerAttributes().add(attribute);
			
			CustomerGroup customerGroup = customerGroupService.getCustomerGroupByCode(CustomerGroup.GROUP_FO_CUSTOMER);
			customer.getCustomerGroups().add(customerGroup);
			
			if(StringUtils.isNotEmpty(auth.getLanguage())){
				customer.setDefaultLocale(auth.getLanguage());
			}
			
			customerService.saveOrUpdateCustomer(customer);
		}
		
		// Login the new customer
		securityUtil.authenticationCustomer(request, customer);
		
		// Update the customer session
		requestUtil.updateCurrentCustomer(request, customer);
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