/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.web.mvc.controller.oauth;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.CustomerAttribute;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Market;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.enumtype.CustomerNetworkOrigin;
import org.hoteia.qalingo.core.domain.enumtype.FoUrls;
import org.hoteia.qalingo.core.domain.enumtype.OAuthType;
import org.hoteia.qalingo.core.mapper.JsonMapper;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.security.helper.SecurityUtil;
import org.hoteia.qalingo.core.security.util.SecurityRequestUtil;
import org.hoteia.qalingo.core.service.AttributeService;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.YahooApi;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.model.Verifier;
import org.scribe.oauth.OAuthService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;

import org.hoteia.tools.scribe.mapping.oauth.yahoo.json.pojo.EmailPojo;
import org.hoteia.tools.scribe.mapping.oauth.yahoo.json.pojo.SocialPojo;
import org.hoteia.tools.scribe.mapping.oauth.yahoo.json.pojo.ProfilePojo;

/**
 * 
 */
@Controller("callBackOAuthYahooController")
public class CallBackOAuthYahooController extends AbstractOAuthFrontofficeController {

	protected final Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	protected AttributeService attributeService;
	
    @Autowired
    protected SecurityRequestUtil securityRequestUtil;
    
    @Autowired
    protected SecurityUtil securityUtil;

    @Autowired
    protected JsonMapper jsonMapper;
	
	@RequestMapping("/callback-oauth-yahoo.html*")
	public ModelAndView callBackYahoo(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		final RequestData requestData = requestUtil.getRequestData(request);
		
		// SANITY CHECK
		if(!requestUtil.hasKnownCustomerLogged(request)){
			try {

			    // CLIENT ID
			    EngineSetting clientIdEngineSetting = engineSettingService.getSettingOAuthAppKeyOrId();
			    EngineSettingValue clientIdEngineSettingValue = clientIdEngineSetting.getEngineSettingValue(OAuthType.YAHOO.name());
			    
			    // CLIENT SECRET
			    EngineSetting clientSecretEngineSetting = engineSettingService.getSettingOAuthAppSecret();
			    EngineSettingValue clientSecretEngineSettingValue = clientSecretEngineSetting.getEngineSettingValue(OAuthType.YAHOO.name());
			    
			    if(clientIdEngineSettingValue != null
			    		&& clientSecretEngineSetting != null){
					final String clientId = clientIdEngineSettingValue.getValue();
					final String clientSecret = clientSecretEngineSettingValue.getValue();

                    final String yahooCallBackURL = urlService.buildAbsoluteUrl(requestData, urlService.buildOAuthCallBackUrl(requestData, OAuthType.YAHOO.getPropertyKey().toLowerCase()));

                    OAuthService service = new ServiceBuilder()
                    .provider(YahooApi.class)
                    .apiKey(clientId)
                    .apiSecret(clientSecret)
                    .callback(yahooCallBackURL)
                    .build();

                    final String code = request.getParameter(REQUEST_PARAM_OAUTH_VERIFIER);
                    if(StringUtils.isNotEmpty(code)) {
                        Verifier verifier = new Verifier(code);
                        Token requestToken = (Token) request.getSession().getAttribute(YAHOO_OAUTH_REQUEST_TOKEN);
                        
                        Token accessToken = service.getAccessToken(requestToken, verifier);
                        String rowResponse = accessToken.getRawResponse();
                        String[] split = rowResponse.split("&");
                        String userGuid = null;
                        if(split.length > 0){
                            for(String value : split){
                            	if(value.contains("xoauth_yahoo_guid") && value.contains("=")){
                            		userGuid = value.split("=")[1];
                            	}
                            }
                        }
                        if(StringUtils.isNotEmpty(userGuid)){
                            OAuthRequest oauthRequest = new OAuthRequest(Verb.GET, "https://social.yahooapis.com/v1/user/" + userGuid + "/profile?format=json");
                            service.signRequest(accessToken, oauthRequest);
                            Response oauthResponse = oauthRequest.send();
                            int responseCode = oauthResponse.getCode();
                            String responseBody = oauthResponse.getBody();
                            
                            if(responseCode == 200){
                                handleAuthenticationData(request, response, requestData, OAuthType.YAHOO, responseBody);
                            } else {
                                logger.error("Callback With " + OAuthType.YAHOO.name() + " failed!");
                            }
                        }
                        
                    } else {
                        logger.error("Callback With " + OAuthType.YAHOO.name() + " failed!");
                    }
			    }
					
			} catch (Exception e) {
				logger.error("Callback With " + OAuthType.YAHOO.name() + " failed!");
			}
		}
		
		// DEFAULT FALLBACK VALUE
		if(!response.isCommitted()){
			response.sendRedirect(urlService.generateUrl(FoUrls.LOGIN, requestData));
		}

		return null;
	}
	
    protected void handleAuthenticationData(HttpServletRequest request, HttpServletResponse response, RequestData requestData, OAuthType type, String jsonData) throws Exception {
        SocialPojo socialPojo = null;
        try {
            socialPojo = jsonMapper.getJsonMapper().readValue(jsonData, SocialPojo.class);
        } catch (JsonGenerationException e) {
            logger.error(e.getMessage());
        } catch (JsonMappingException e) {
            logger.error(e.getMessage());
        }
        if (socialPojo != null) {
            final ProfilePojo profile = socialPojo.getProfile();
            String email = null;
            List<EmailPojo> emails = profile.getEmails();
            for(EmailPojo emailPojo : emails){
               if(emailPojo.isPrimary()){
                   email = emailPojo.getHandle();
               }
            }
            final String firstName = profile.getGivenName();
            final String lastName = profile.getFamilyName();
            final String gender = profile.getGender();
            final String username = profile.getNickname();
            
            if(StringUtils.isNotEmpty(email)){
                Customer customer = customerService.getCustomerByLoginOrEmail(email);

                if(customer == null){
                    final Market currentMarket = requestData.getMarket();
                    final MarketArea currentMarketArea = requestData.getMarketArea();
                    
                    // CREATE A NEW CUSTOMER
                    customer = new Customer();
//                    customer = setCommonCustomerInformation(request, customer);

                    customer.setLogin(email);
                    customer.setPassword(securityUtil.generatePassword());
                    customer.setEmail(email);
                    customer.setFirstname(firstName);
                    customer.setLastname(lastName);
                    if (StringUtils.isNotEmpty(gender)) {
                        customer.setGender(gender);
                        if ("M".equals(gender)) {
                            customer.setTitle("MR");
                        } else if ("F".equals(gender)) {
                            customer.setTitle("MME");
                        }
                    }

                    customer.setNetworkOrigin(CustomerNetworkOrigin.YAHOO.getPropertyKey());

                    CustomerAttribute attribute = new CustomerAttribute();
                    AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(CustomerAttribute.CUSTOMER_ATTRIBUTE_SCREENAME);
                    attribute.setAttributeDefinition(attributeDefinition);
                    String screenName = username;
                    if (StringUtils.isEmpty(screenName)) {
                        if (StringUtils.isNotEmpty(lastName)) {
                            if (StringUtils.isNotEmpty(lastName)) {
                                screenName = lastName;
                                if (screenName.length() > 1) {
                                    screenName = screenName.substring(0, 1);
                                }
                                if (!screenName.endsWith(".")) {
                                    screenName = screenName + ". ";
                                }
                            }
                        }
                        screenName = screenName + firstName;
                    }
                    attribute.setShortStringValue(screenName);
                    customer.getAttributes().add(attribute);

                    // Save the new customer
                    customer = webManagementService.buildAndSaveNewCustomer(requestData, currentMarket, currentMarketArea, customer);
                    
                    // Save the email confirmation
                    webManagementService.buildAndSaveCustomerNewAccountMail(requestData, customer);
                }

                // Redirect to the edit page
                if (StringUtils.isNotEmpty(customer.getEmail())) {

                    // Login the new customer
                    securityRequestUtil.authenticationCustomer(request, customer);

                    // Update the customer session
                    requestUtil.updateCurrentCustomer(request, customer);

                    response.sendRedirect(urlService.generateUrl(FoUrls.PERSONAL_EDIT, requestData));
                }
            }
        }
    }

}