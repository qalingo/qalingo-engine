package fr.hoteia.qalingo.web.mvc.controller.oauth;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fr.hoteia.qalingo.core.Constants;
import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAttribute;
import fr.hoteia.qalingo.core.domain.CustomerGroup;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.CustomerNetworkOrigin;
import fr.hoteia.qalingo.core.domain.enumtype.CustomerPlatformOrigin;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.AttributeService;
import fr.hoteia.qalingo.core.service.CustomerGroupService;
import fr.hoteia.qalingo.core.service.CustomerService;
import fr.hoteia.qalingo.core.service.openid.OpenIdException;
import fr.hoteia.qalingo.core.web.mvc.controller.AbstractQalingoController;
import fr.hoteia.qalingo.core.web.util.RequestUtil;
import fr.hoteia.qalingo.web.mvc.controller.oauth.json.bean.windowslive.User;

/**
 * 
 * <p>
 * <a href="AbstractOpenIdFrontofficeController.java.html"><i>View Source</i></a>
 * </p>
 * 
 * @author Denis Gosset <a href="http://www.hoteia.com"><i>Hoteia.com</i></a>
 * 
 */
public abstract class AbstractOAuthFrontofficeController extends AbstractQalingoController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());

	@Autowired
	protected CustomerService customerService;
	
	@Autowired
	protected CustomerGroupService customerGroupService;
	
	@Autowired
	protected AttributeService attributeService;
	
	@Autowired
    protected RequestUtil requestUtil;
	
	@Autowired
    protected SecurityUtil securityUtil;
	
	void handleAuthenticationData(final HttpServletRequest request, final HttpServletResponse response, final MarketArea currentMarketArea, final OAuthType type, final String jsonData) throws Exception {
		ObjectMapper mapper = new ObjectMapper();
		User user = null;
		try {
			user = mapper.readValue(jsonData, User.class);
		} catch (JsonGenerationException e) {
			LOG.error(e.getMessage());
		} catch (JsonMappingException e) {
			LOG.error(e.getMessage());
		}
		if(user != null){
			final String email = user.getEmails().getPreferred();
			final String firstName = user.getFirstName();
			final String lastName = user.getLastName();
			final String gender = user.getGender();
			final String locale = user.getLocale();
			Customer customer = customerService.getCustomerByLoginOrEmail(email);
			
			if(customer == null){
				// CREATE A NEW CUSTOMER
				customer = new Customer();
				customer.setLogin(email);
				customer.setPassword(securityUtil.generateAndEncodePassword());
				customer.setEmail(email);
				customer.setFirstname(firstName);
				customer.setLastname(lastName);
				if(StringUtils.isNotEmpty(gender)){
					customer.setGender(gender);
				}
				
				customer.setPlatformOrigin(CustomerPlatformOrigin.STANDARD);
				
				switch (type) {
					case FACEBOOK:
						customer.setNetworkOrigin(CustomerNetworkOrigin.FACEBOOK);
						break;
					case TWITTER:
						customer.setNetworkOrigin(CustomerNetworkOrigin.TWITTER);
						break;
						
					case WINDOWS_LIVE:
						customer.setNetworkOrigin(CustomerNetworkOrigin.WINDOWS_LIVE);
						break;
					
					case YAHOO:
						customer.setNetworkOrigin(CustomerNetworkOrigin.YAHOO);
						break;
						
					case GOOGLE_CONTACT:
						customer.setNetworkOrigin(CustomerNetworkOrigin.GOOGLE_ACCOUNT);
						break;
	
					default:
						customer.setNetworkOrigin(CustomerNetworkOrigin.STANDARD);
						break;
				}
				
				CustomerAttribute attribute = new CustomerAttribute();
				AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(CustomerAttribute.CUSTOMER_ATTRIBUTE_SCREENAME);
				attribute.setAttributeDefinition(attributeDefinition);
				String screenName = "";
				if(StringUtils.isNotEmpty(lastName)){
					if(StringUtils.isNotEmpty(lastName)){
						screenName = lastName;
						if(screenName.length() > 1){
							screenName = screenName.substring(0, 1);
						}
						if(!screenName.endsWith(".")){
							screenName = screenName + ". ";
						}
					}
				}
				screenName = screenName + firstName;
				attribute.setStringValue(screenName);
				customer.getCustomerAttributes().add(attribute);
				
				CustomerGroup customerGroup = customerGroupService.getCustomerGroupByCode(CustomerGroup.GROUP_FO_CUSTOMER);
				customer.getCustomerGroups().add(customerGroup);
				
				if(StringUtils.isNotEmpty(locale)){
					customer.setDefaultLocale(locale);
				}
				
				customerService.saveOrUpdateCustomer(customer);
			}

			// Redirect to the details page
			if(StringUtils.isNotEmpty(customer.getEmail())){
				
				// Login the new customer
				securityUtil.authenticationCustomer(request, customer);
				
				// Update the customer session
				requestUtil.updateCurrentCustomer(request, customer);

				response.sendRedirect(urlService.buildCustomerDetailsUrl( currentMarketArea));
			}
			
		}
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