package fr.hoteia.qalingo.web.mvc.controller.oauth;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import facebook4j.Facebook;
import facebook4j.FacebookFactory;
import facebook4j.auth.AccessToken;
import fr.hoteia.qalingo.core.domain.AttributeDefinition;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.CustomerAttribute;
import fr.hoteia.qalingo.core.domain.CustomerOAuth;
import fr.hoteia.qalingo.core.domain.EngineSetting;
import fr.hoteia.qalingo.core.domain.EngineSettingValue;
import fr.hoteia.qalingo.core.domain.MarketArea;
import fr.hoteia.qalingo.core.domain.enumtype.OAuthType;
import fr.hoteia.qalingo.core.security.util.SecurityUtil;
import fr.hoteia.qalingo.core.service.AttributeService;

/**
 * 
 */
@Controller("callBackFacebookController")
public class CallBackFacebookController extends AbstractOAuthFrontofficeController {

	protected final Logger LOG = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected AttributeService attributeService;
	
	@Autowired
    protected SecurityUtil securityUtil;
	
	@RequestMapping("/callback-oauth-facebook.html*")
	public ModelAndView callBackFacebook(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final MarketArea currentMarketArea = requestUtil.getCurrentMarketArea(request);
		
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {
				String error = request.getParameter("error");
				if(StringUtils.isNotEmpty(error)){
					String errorCode = request.getParameter("error_code");
					String errorDescription = request.getParameter("error_description");
					String errorReason = request.getParameter("error_reason");
					LOG.warn("Facebook connect fail! errorCode: " + errorCode + ", errorReason: " + errorReason+ ", errorDescription: " + errorDescription);
					
				} else {
				    // CLIENT ID
				    EngineSetting clientIdEngineSetting = engineSettingService.geOAuthAppKeyOrId();
				    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.name());
				    
				    // CLIENT SECRET
				    EngineSetting clientSecretEngineSetting = engineSettingService.geOAuthAppSecret();
				    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.name());
				    
				    // CLIENT PERMISSIONS
				    EngineSetting permissionsEngineSetting = engineSettingService.geOAuthAppPermissions();
				    EngineSettingValue permissionsEngineSettingValue = permissionsEngineSetting.getEngineSettingValue(OAuthType.FACEBOOK.name());
				    
				    if(clientIdEngineSettingValue != null
				    		&& clientSecretEngineSetting != null
				    		&& permissionsEngineSettingValue != null){
				    	
						final String clientId = clientIdEngineSettingValue.getValue();
						final String clientSecret = clientSecretEngineSettingValue.getValue();
						final String permissions = permissionsEngineSettingValue.getValue();

						Facebook facebook = new FacebookFactory().getInstance();
						facebook.setOAuthAppId(clientId, clientSecret);
						facebook.setOAuthPermissions(permissions);
						
						// CALLBACK STEP 1 : GET THE CODE AND ASK A TOKEN
						String code = request.getParameter("code");
						AccessToken accessToken = null;
					    String expires = null;
					    
						if(StringUtils.isNotEmpty(code)){
							accessToken = facebook.getOAuthAccessToken(code);
						}

						// CALLBACK STEP 2 : Use the accessToken to ask user information
						String email = null;
						if(accessToken != null
								&& StringUtils.isNotEmpty(accessToken.getToken())){
							
							email = facebook.getEmail();
							
							Customer customer = customerService.getCustomerByLoginOrEmail(email);
							if(customer == null){
								String lastName = facebook.getMe().getLastName();
								String firstName = facebook.getMe().getFirstName();
								
								// CREATE AN NEW CUSTOMER
								customer = new Customer();
								customer.setEmail(email);
								customer.setFirstname(firstName);
								customer.setLastname(lastName);
								
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
											screenName = screenName + ".";
										}
									}
								}
								screenName = screenName + firstName;
								attribute.setStringValue(screenName);
								customer.getCustomerAttributes().add(attribute);
								
								CustomerOAuth customerOAuth = new CustomerOAuth();
								customerOAuth.setOauthToken(accessToken.getToken());
								customerOAuth.setType(OAuthType.FACEBOOK);
								customerOAuth.setUserId(facebook.getMe().getId());
								customer.getOauthAccesses().add(customerOAuth);
								
								if(facebook.getMe().getLocale() != null){
									customer.setDefaultLocale(facebook.getMe().getLocale().toString());
								}

								customerService.saveOrUpdateCustomer(customer);
								
							} else {
								// CHECK AND UPDATE THE CUSTOMER/OAUTH
								
							}
							
							// Login the new customer
							securityUtil.authenticationCustomer(request, customer);
							
							// Update the customer session
							requestUtil.updateCurrentCustomer(request, customer);

							// Redirect to the details page
							if(StringUtils.isNotEmpty(email)){
								response.sendRedirect(urlService.buildCustomerDetailsUrl(request, currentMarketArea));
							}

						}

				    }
				}
				
			} catch (Exception e) {
				LOG.error("Callback With " + OAuthType.FACEBOOK.name() + " failed!");
			}
		}
		
		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.buildLoginUrl(request, currentMarketArea));
		}

		return null;
	}

}