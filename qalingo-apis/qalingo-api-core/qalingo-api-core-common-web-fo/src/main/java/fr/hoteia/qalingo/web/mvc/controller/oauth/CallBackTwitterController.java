package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.enumtype.FoUrls;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.AttributeService;

/**
 * 
 */
@Controller("callBackTwitterController")
public class CallBackTwitterController extends AbstractOAuthFrontofficeController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected AttributeService attributeService;
	
	@Autowired
    protected SecurityUtil securityUtil;
	
	@RequestMapping("/callback-oauth-twitter.html*")
	public ModelAndView callBackTwitter(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {

			    // CLIENT ID
			    EngineSetting clientIdEngineSetting = engineSettingService.geOAuthAppKeyOrId();
			    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.TWITTER.name());
			    
			    // CLIENT SECRET
			    EngineSetting clientSecretEngineSetting = engineSettingService.geOAuthAppSecret();
			    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.TWITTER.name());
			    
			    // CLIENT PERMISSIONS
			    EngineSetting permissionsEngineSetting = engineSettingService.geOAuthAppKeyOrId();
			    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.TWITTER.name());
			    
			    if(clientIdEngineSettingValue != null
			    		&& clientSecretEngineSetting != null
			    		&& permissionsEngineSettingValue != null){
					
//					final String clientId = clientIdEngineSettingValue.getValue();
//					final String clientSecret = clientSecretEngineSettingValue.getValue();
//					
//					Twitter twitter = TwitterFactory.getSingleton();
//					twitter.setOAuthConsumer(clientId, clientSecret);
//					
//					User user = twitter.verifyCredentials();
//					String email = user.getScreenName();
//					
//					Customer customer = customerService.getCustomerByLoginOrEmail(email);
//					if(customer == null){
//						String lastName = user.getName();
//						String firstName = "";
//						
//						// CREATE AN NEW CUSTOMER
//						customer = new Customer();
//						customer.setEmail(email);
//						customer.setFirstname(firstName);
//						customer.setLastname(lastName);
//						
//						CustomerAttribute attribute = new CustomerAttribute();
//						AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(CustomerAttribute.CUSTOMER_ATTRIBUTE_SCREENAME);
//						attribute.setAttributeDefinition(attributeDefinition);
//						String screenName = user.getScreenName();
//						if(StringUtils.isEmpty(screenName)){
//							if(StringUtils.isNotEmpty(screenName)){
//								if(StringUtils.isNotEmpty(lastName)){
//									screenName = lastName;
//									if(screenName.length() > 1){
//										screenName = screenName.substring(0, 1);
//									}
//									if(!screenName.endsWith(".")){
//										screenName = screenName + ".";
//									}
//								}
//							}
//						}
//						screenName = screenName + firstName;
//						attribute.setStringValue(screenName);
//						customer.getCustomerAttributes().add(attribute);
//						
//						customer.setDefaultLocale(user.getLang());
//						customerService.saveOrUpdateCustomer(customer);
//						
//					} else {
//						// CHECK AND UPDATE THE CUSTOMER/OAUTH
//						
//					}
//					
//					// Login the new customer
//					securityUtil.authenticationCustomer(request, customer);
//					
//					// Update the customer session
//					requestUtil.updateCurrentCustomer(request, customer);
//
//					// Redirect to the details page
//					if(StringUtils.isNotEmpty(email)){
//						response.sendRedirect(urlService.buildCustomerDetailsUrl( currentMarketArea));
//					}

			    }
					
			} catch (Exception e) {
				LOG.error("Callback With " + OAuthType.TWITTER.name() + " failed!");
			}
		}
		
		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.generateUrl(FoUrls.LOGIN, requestUtil.getRequestData(request)));
		}

		return null;
	}

}