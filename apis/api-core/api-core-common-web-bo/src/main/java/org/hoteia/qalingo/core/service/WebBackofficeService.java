/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import javax.persistence.PersistenceException;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang.StringUtils;
import org.hoteia.qalingo.core.domain.AbstractPaymentGateway;
import org.hoteia.qalingo.core.domain.Asset;
import org.hoteia.qalingo.core.domain.AttributeDefinition;
import org.hoteia.qalingo.core.domain.CatalogCategoryMaster;
import org.hoteia.qalingo.core.domain.CatalogCategoryMasterAttribute;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtualAttribute;
import org.hoteia.qalingo.core.domain.Company;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.Email;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.EngineSettingValue;
import org.hoteia.qalingo.core.domain.Localization;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.PaymentGatewayOption;
import org.hoteia.qalingo.core.domain.ProductBrand;
import org.hoteia.qalingo.core.domain.ProductBrandAttribute;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.Retailer;
import org.hoteia.qalingo.core.domain.RetailerAddress;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Tax;
import org.hoteia.qalingo.core.domain.User;
import org.hoteia.qalingo.core.domain.UserCredential;
import org.hoteia.qalingo.core.domain.Warehouse;
import org.hoteia.qalingo.core.domain.WarehouseMarketAreaRel;
import org.hoteia.qalingo.core.domain.enumtype.BoUrls;
import org.hoteia.qalingo.core.email.bean.UserForgottenPasswordEmailBean;
import org.hoteia.qalingo.core.email.bean.UserNewAccountConfirmationEmailBean;
import org.hoteia.qalingo.core.email.bean.UserResetPasswordConfirmationEmailBean;
import org.hoteia.qalingo.core.exception.UniqueConstraintCodeCategoryException;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.security.helper.SecurityUtil;
import org.hoteia.qalingo.core.web.mvc.form.AssetForm;
import org.hoteia.qalingo.core.web.mvc.form.CatalogCategoryForm;
import org.hoteia.qalingo.core.web.mvc.form.CompanyForm;
import org.hoteia.qalingo.core.web.mvc.form.CustomerForm;
import org.hoteia.qalingo.core.web.mvc.form.DeliveryMethodForm;
import org.hoteia.qalingo.core.web.mvc.form.EngineSettingForm;
import org.hoteia.qalingo.core.web.mvc.form.EngineSettingValueForm;
import org.hoteia.qalingo.core.web.mvc.form.ForgottenPasswordForm;
import org.hoteia.qalingo.core.web.mvc.form.PaymentGatewayForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductBrandForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductMarketingForm;
import org.hoteia.qalingo.core.web.mvc.form.ProductSkuForm;
import org.hoteia.qalingo.core.web.mvc.form.ResetPasswordForm;
import org.hoteia.qalingo.core.web.mvc.form.RetailerForm;
import org.hoteia.qalingo.core.web.mvc.form.StoreForm;
import org.hoteia.qalingo.core.web.mvc.form.TaxForm;
import org.hoteia.qalingo.core.web.mvc.form.UserForm;
import org.hoteia.qalingo.core.web.mvc.form.WarehouseForm;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service("webBackofficeService")
@Transactional
public class WebBackofficeService {

    @Autowired
    protected UserService userService;

    @Autowired
    protected CustomerService customerService;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected RetailerService retailerService;

    @Autowired
    protected WarehouseService warehouseService;

    @Autowired
    protected DeliveryMethodService deliveryMethodService;

    @Autowired
    protected TaxService taxService;

    @Autowired
    protected AttributeService attributeService;

    @Autowired
    protected EngineSettingService engineSettingService;

    @Autowired
    protected PaymentGatewayService paymentGatewayService;

    @Autowired
    protected EmailService emailService;
    
    @Autowired
    protected BackofficeUrlService backofficeUrlService;
    
    @Autowired
    protected ReferentialDataService referentialDataService;
    
    @Autowired
    protected RequestUtil requestUtil;
    
    @Autowired
    protected SecurityUtil securityUtil;
    
    // USER
    
    public User createOrUpdatePersonalUser(User user, final UserForm userForm) {
        if (user == null) {
            user = new User();
        }
        user = handleUserForm(user, userForm);
        return userService.saveOrUpdateUser(user);
    }

    public User createOrUpdateUser(User user, final UserForm userForm) {
        if (user == null) {
            user = new User();
        }
        user = handleUserForm(user, userForm);
        return userService.saveOrUpdateUser(user);
    }

    /**
     * 
     */
    public User buildNewUser(final UserForm userForm) throws Exception {
        User user = new User();
        user = handleUserForm(user, userForm);
        return user;
    }

    /**
     * 
     */
    public User buildAndRegisterNewActiveUser(final RequestData requestData, final UserForm userForm) throws Exception {
        User user = buildNewUser(userForm);
        // FORCE TO ACTIVE A REGISTER USER
        user.setActive(true);
        return user;
    }
    
    protected User handleUserForm(User user, final UserForm userForm) {
        if (user == null) {
            user = new User();
        }
        user.setTitle(userForm.getTitle());
        user.setLastname(userForm.getLastname());
        user.setFirstname(userForm.getFirstname());
        user.setEmail(userForm.getEmail());

        if (StringUtils.isNotEmpty(userForm.getLogin())) {
            user.setLogin(userForm.getLogin());
        } else {
            String login = userForm.getFirstname().substring(0, 1) + userForm.getLastname();
            User checkUserLogin = userService.getUserByLoginOrEmail(login);
            int i = 1;
            while (checkUserLogin != null) {
                login = userForm.getFirstname().substring(0, 1) + userForm.getLastname() + i;
                checkUserLogin = userService.getUserByLoginOrEmail(login);
                i++;
            }
            user.setLogin(login.toLowerCase());
        }
        if (StringUtils.isNotEmpty(userForm.getPassword())) {
            // UPDATE PASSWORD
            user.setPassword(securityUtil.encodePassword(userForm.getPassword()));

        } else {
            if (StringUtils.isEmpty(user.getPassword())) {
                // SET A DEFAULT PASSWORD
                user.setPassword(securityUtil.generatePassword());
            }
        }

        user.setAddress1(userForm.getAddress1());
        user.setAddress2(userForm.getAddress2());
        user.setPostalCode(userForm.getPostalCode());
        user.setCity(userForm.getCity());
        user.setStateCode(userForm.getStateCode());
        user.setAreaCode(userForm.getAreaCode());
        user.setCountryCode(userForm.getCountryCode());

        user.setActive(userForm.isActive());

        return user;
    }
    
    // COMPANY
    
    public Company createOrUpdateCompany(Company company, final CompanyForm companyForm) {
        if (company == null) {
            company = new Company();
        }
        company = handleCompanyForm(company, companyForm);
        return userService.saveOrUpdateCompany(company);
    }

    protected Company handleCompanyForm(Company company, final CompanyForm companyForm) {
        if (company == null) {
            company = new Company();
        }
        company.setCode(companyForm.getCode());
        company.setName(companyForm.getName());
        company.setDescription(companyForm.getDescription());
        company.setActive(companyForm.isActive());
        company.setAddress1(companyForm.getAddress1());
        company.setAddress2(companyForm.getAddress2());
        company.setAddressAdditionalInformation(companyForm.getAddressAdditionalInformation());
        company.setPostalCode(companyForm.getPostalCode());
        company.setCity(companyForm.getCity());
        company.setStateCode(companyForm.getStateCode());
        company.setAreaCode(companyForm.getAreaCode());
        company.setCountryCode(companyForm.getCountryCode());

        return company;
    }
    
    // CUSTOMER
    
    public Customer createOrUpdateCustomer(Customer customer, final CustomerForm customerForm) throws Exception {
        if(customer == null){
            customer = new Customer();
        }
        customer.setLogin(customerForm.getLogin());
        customer.setLastname(customerForm.getLastname());
        customer.setFirstname(customerForm.getFirstname());
        customer.setEmail(customerForm.getEmail());
        customer.setActive(customerForm.isActive());
        return customerService.saveOrUpdateCustomer(customer);
    }
    
    // CATALOG
    
    public ProductBrand createOrUpdateProductBrand(final MarketArea marketArea, final Localization localization, ProductBrand brand, final ProductBrandForm brandForm) throws Exception {
        if(brand == null){
            brand = new ProductBrand();
        }
        
        if(StringUtils.isNotEmpty(brandForm.getCode())){
            brand.setCode(brandForm.getCode());
        }
        if(StringUtils.isNotEmpty(brandForm.getName())){
            brand.setName(brandForm.getName());
        }
        brand.setDescription(brandForm.getDescription());
        
        if(brandForm != null
                && brandForm.getMarketAreaAttributes() != null) {
            Map<String, String> attributes = brandForm.getMarketAreaAttributes();
            for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
                String attributeKey = (String) iterator.next();
                String value = attributes.get(attributeKey);
                if(StringUtils.isNotEmpty(value)) {
                    brand.getMarketAreaAttributes(marketArea.getId()).add(buildProductBrandAttribute(marketArea, localization, attributeKey, value, false));
                }
            }
        }
        
        if(brandForm != null
                && brandForm.getGlobalAttributes() != null) {
            Map<String, String> attributes = brandForm.getGlobalAttributes();
            for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
                String attributeKey = (String) iterator.next();
                String value = attributes.get(attributeKey);
                if(StringUtils.isNotEmpty(value)) {
                    brand.getGlobalAttributes().add(buildProductBrandAttribute(marketArea, localization, attributeKey, value, true));
                }
            }
        }
        
        return productService.saveOrUpdateProductBrand(brand);
    }
    
	public CatalogCategoryMaster createCatalogCategory(final RequestData requestData, final MarketArea marketArea, final Localization localization, final CatalogCategoryMaster parentCatalogCategory, 
	                                  final CatalogCategoryMaster catalogCategory, final CatalogCategoryForm catalogCategoryForm) throws UniqueConstraintCodeCategoryException {
		String catalogCategoryCode = catalogCategoryForm.getCode();
		
        if(StringUtils.isNotEmpty(catalogCategoryForm.getCode())){
            catalogCategory.setCode(catalogCategoryForm.getCode());
        }
        if(StringUtils.isNotEmpty(catalogCategoryForm.getName())){
            catalogCategory.setName(catalogCategoryForm.getName());
        }
        
		catalogCategory.setDescription(catalogCategoryForm.getDescription());
        if(StringUtils.isNotEmpty(catalogCategoryForm.getRanking())){
            try {
                  catalogCategory.setRanking(Integer.parseInt(catalogCategoryForm.getRanking()));
              } catch (Exception e) {
                  // NOTHING
              }
          } else {
              catalogCategory.setRanking(1);
          }
		catalogCategory.setParentCatalogCategory(parentCatalogCategory);
		catalogCategory.setCatalog(requestData.getMasterCatalog());
		
		if(catalogCategoryForm != null
				&& catalogCategoryForm.getMarketAreaAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getMarketAreaAttributes();
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				String value = attributes.get(attributeKey);
				if(StringUtils.isNotEmpty(value)) {
					catalogCategory.getMarketAreaAttributes(marketArea.getId()).add(buildCatalogCategoryMasterAttribute(marketArea, localization, attributeKey, value, false));
				}
			}
		}
		
		if(catalogCategoryForm != null
				&& catalogCategoryForm.getGlobalAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getGlobalAttributes();
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				String value = attributes.get(attributeKey);
				if(StringUtils.isNotEmpty(value)) {
					catalogCategory.getGlobalAttributes().add(buildCatalogCategoryMasterAttribute(marketArea, localization, attributeKey, value, true));
				}
			}
		}
		
		try {
		    CatalogCategoryMaster savedCatalogCategory = catalogCategoryService.saveOrUpdateCatalogCategory(catalogCategory);
			
			if(parentCatalogCategory != null) {
				if(!parentCatalogCategory.getCatalogCategories().contains(catalogCategory)) {
					// PARENT DOESN'T CONTAIN THE NEW CATEGORY - ADD IT IN THE MANY TO MANY
					CatalogCategoryMaster reloadedCatalogCategory = catalogCategoryService.getMasterCatalogCategoryByCode(catalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getMasterCatalogCode());
					parentCatalogCategory.getCatalogCategories().add(reloadedCatalogCategory);
					catalogCategoryService.saveOrUpdateCatalogCategory(parentCatalogCategory);
				}
			}
			
			return savedCatalogCategory;
			
		} catch (PersistenceException e) {
			if(e.getMessage().contains("ConstraintViolationException")) {
				throw new UniqueConstraintCodeCategoryException();
			} else {
				throw new UniqueConstraintCodeCategoryException();
			}
		}
		
	}
	
	public CatalogCategoryMaster updateCatalogCategory(final RequestData requestData, final MarketArea marketArea, final Retailer retailer, final Localization localization, 
	                                  final CatalogCategoryMaster catalogCategory, final CatalogCategoryForm catalogCategoryForm) {
        if(StringUtils.isNotEmpty(catalogCategoryForm.getCode())){
            catalogCategory.setCode(catalogCategoryForm.getCode());
        }
        if(StringUtils.isNotEmpty(catalogCategoryForm.getName())){
            catalogCategory.setName(catalogCategoryForm.getName());
        }
		catalogCategory.setDescription(catalogCategoryForm.getDescription());
	      if(StringUtils.isNotEmpty(catalogCategoryForm.getRanking())){
	          try {
	                catalogCategory.setRanking(Integer.parseInt(catalogCategoryForm.getRanking()));
                } catch (Exception e) {
                    // NOTHING
                }
	        } else {
	            catalogCategory.setRanking(1);
	        }
		if(StringUtils.isNotEmpty(catalogCategoryForm.getDefaultParentCategoryId())) {
			final CatalogCategoryMaster parentCatalogCategory = catalogCategoryService.getMasterCatalogCategoryById(catalogCategoryForm.getDefaultParentCategoryId());
			catalogCategory.setParentCatalogCategory(parentCatalogCategory);
		} else {
		    catalogCategory.setParentCatalogCategory(null);
		}

		if(catalogCategoryForm != null
				&& catalogCategoryForm.getGlobalAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getGlobalAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryMasterAttribute> iteratorCategoryGlobalAttributes = catalogCategory.getGlobalAttributes().iterator(); iteratorCategoryGlobalAttributes.hasNext();) {
					CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iteratorCategoryGlobalAttributes.next();
					if(catalogCategoryMasterAttribute.getAttributeDefinition().getCode().equals(attributeKey)) {
					    catalogCategoryMasterAttribute.setValue(catalogCategoryForm.getGlobalAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist) {
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)) {
						catalogCategory.getMarketAreaAttributes(marketArea.getId()).add(buildCatalogCategoryMasterAttribute(marketArea, localization, attributeKey, value, true));
					}
				}
			}
		}
		
		if(catalogCategoryForm != null
				&& catalogCategoryForm.getMarketAreaAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getMarketAreaAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryMasterAttribute> iteratorCategoryMarketAttributes = catalogCategory.getMarketAreaAttributes(marketArea.getId()).iterator(); iteratorCategoryMarketAttributes.hasNext();) {
					CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = (CatalogCategoryMasterAttribute) iteratorCategoryMarketAttributes.next();
					if(catalogCategoryMasterAttribute.getAttributeDefinition().getCode().equals(attributeKey)) {
					    catalogCategoryMasterAttribute.setValue(catalogCategoryForm.getMarketAreaAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist) {
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)) {
						catalogCategory.getMarketAreaAttributes(marketArea.getId()).add(buildCatalogCategoryMasterAttribute(marketArea, localization, attributeKey, value, false));
					}
				}
			}
		}
		return catalogCategoryService.saveOrUpdateCatalogCategory(catalogCategory);
	}
	
	public CatalogCategoryVirtual createCatalogCategory(final RequestData requestData, final MarketArea marketArea, final Localization localization, final CatalogCategoryVirtual parentCatalogCategory,
	                                  final CatalogCategoryVirtual catalogCategory, final CatalogCategoryForm catalogCategoryForm) throws UniqueConstraintCodeCategoryException {
        String catalogCategoryCode = catalogCategoryForm.getCode();
        if(StringUtils.isNotEmpty(catalogCategoryForm.getCode())){
            catalogCategory.setCode(catalogCategoryForm.getCode());
        }
        if(StringUtils.isNotEmpty(catalogCategoryForm.getName())){
            catalogCategory.setName(catalogCategoryForm.getName());
        }
        catalogCategory.setDescription(catalogCategoryForm.getDescription());
        if(StringUtils.isNotEmpty(catalogCategoryForm.getRanking())){
            try {
                  catalogCategory.setRanking(Integer.parseInt(catalogCategoryForm.getRanking()));
              } catch (Exception e) {
                  // NOTHING
              }
          } else {
              catalogCategory.setRanking(1);
          }
        catalogCategory.setParentCatalogCategory(parentCatalogCategory);
        catalogCategory.setCatalog(marketArea.getCatalog());
        
        if(catalogCategoryForm != null
                && catalogCategoryForm.getMarketAreaAttributes() != null) {
            Map<String, String> attributes = catalogCategoryForm.getMarketAreaAttributes();
            for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
                String attributeKey = (String) iterator.next();
                String value = attributes.get(attributeKey);
                if(StringUtils.isNotEmpty(value)) {
                    catalogCategory.getMarketAreaAttributes(marketArea.getId()).add(buildCatalogCategoryVirtualAttribute(marketArea, localization, attributeKey, value, false));
                }
            }
        }
        
        if(catalogCategoryForm != null
                && catalogCategoryForm.getGlobalAttributes() != null) {
            Map<String, String> attributes = catalogCategoryForm.getGlobalAttributes();
            for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
                String attributeKey = (String) iterator.next();
                String value = attributes.get(attributeKey);
                if(StringUtils.isNotEmpty(value)) {
                    catalogCategory.getGlobalAttributes().add(buildCatalogCategoryVirtualAttribute(marketArea, localization, attributeKey, value, true));
                }
            }
        }
        
        try {
            CatalogCategoryVirtual savedCatalogCategory = catalogCategoryService.saveOrUpdateCatalogCategory(catalogCategory);
            
            if(parentCatalogCategory != null) {
                if(!parentCatalogCategory.getCatalogCategories().contains(catalogCategory)) {
                    // PARENT DOESN'T CONTAIN THE NEW CATEGORY - ADD IT IN THE MANY TO MANY
                    CatalogCategoryVirtual reloadedCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode, requestData.getVirtualCatalogCode(), requestData.getVirtualCatalogCode());
                    parentCatalogCategory.getCatalogCategories().add(reloadedCatalogCategory);
                    catalogCategoryService.saveOrUpdateCatalogCategory(parentCatalogCategory);
                }
            }
            
            return savedCatalogCategory;
            
        } catch (PersistenceException e) {
            if(e.getMessage().contains("ConstraintViolationException")) {
                throw new UniqueConstraintCodeCategoryException();
            } else {
                throw new UniqueConstraintCodeCategoryException();
            }
        }
	}
	
	public CatalogCategoryVirtual updateCatalogCategory(final RequestData requestData, final MarketArea marketArea, final Retailer retailer, final Localization localization, 
	                                  final CatalogCategoryVirtual catalogCategory, final CatalogCategoryForm catalogCategoryForm) {
        if(StringUtils.isNotEmpty(catalogCategoryForm.getCode())){
            catalogCategory.setCode(catalogCategoryForm.getCode());
        }
        if(StringUtils.isNotEmpty(catalogCategoryForm.getName())){
            catalogCategory.setName(catalogCategoryForm.getName());
        }
		catalogCategory.setDescription(catalogCategoryForm.getDescription());
        if(StringUtils.isNotEmpty(catalogCategoryForm.getRanking())){
            try {
                  catalogCategory.setRanking(Integer.parseInt(catalogCategoryForm.getRanking()));
              } catch (Exception e) {
                  // NOTHING
              }
          } else {
              catalogCategory.setRanking(1);
          }
		if(StringUtils.isNotEmpty(catalogCategoryForm.getDefaultParentCategoryId())) {
			final CatalogCategoryVirtual parentCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryById(catalogCategoryForm.getDefaultParentCategoryId());
			catalogCategory.setParentCatalogCategory(parentCatalogCategory);
		} else {
		    catalogCategory.setParentCatalogCategory(null);
		}

        if (StringUtils.isNotEmpty(catalogCategoryForm.getMasterCategoryId())) {
            final CatalogCategoryMaster masterCatalogCategory = catalogCategoryService.getMasterCatalogCategoryById(catalogCategoryForm.getMasterCategoryId());
            catalogCategory.setCategoryMaster(masterCatalogCategory);
        } else {
            catalogCategory.setCategoryMaster(null);
        }

//		for (Iterator<String> iterator = catalogCategoryForm.getGlobalAttributes().keySet().iterator(); iterator.hasNext();) {
//			String key = (String) iterator.next();
//			for (Iterator<CatalogCategoryMasterAttribute> iteratorGlobalAttributes = catalogCategory.getCatalogCategoryGlobalAttributes().iterator(); iteratorGlobalAttributes.hasNext();) {
//				CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iteratorGlobalAttributes.next();
//				if(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(key)) {
//					updateCatalogCategoryVirtualAttribute(catalogCategoryVirtualAttribute, catalogCategoryForm.getGlobalAttributes().get(key));
//				}
//			}
//		}
//			
//		for (Iterator<String> iterator = catalogCategoryForm.getMarketAreaAttributes().keySet().iterator(); iterator.hasNext();) {
//			String key = (String) iterator.next();
//			for (Iterator<CatalogCategoryVirtualAttribute> iteratorMarketAreaAttribute = catalogCategory.getCatalogCategoryMarketAreaAttributes().iterator(); iteratorMarketAreaAttribute.hasNext();) {
//				CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iteratorMarketAreaAttribute.next();
//				if(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(key)) {
//					updateCatalogCategoryVirtualAttribute(catalogCategoryVirtualAttribute, catalogCategoryForm.getMarketAreaAttributes().get(key));
//				}
//			}
//		}
		
		if(catalogCategoryForm != null
				&& catalogCategoryForm.getGlobalAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getGlobalAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryVirtualAttribute> iteratorCategoryGlobalAttributes = catalogCategory.getGlobalAttributes().iterator(); iteratorCategoryGlobalAttributes.hasNext();) {
					CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iteratorCategoryGlobalAttributes.next();
					if(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(attributeKey)) {
					    catalogCategoryVirtualAttribute.setValue(catalogCategoryForm.getGlobalAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist) {
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)) {
						catalogCategory.getMarketAreaAttributes(marketArea.getId()).add(buildCatalogCategoryVirtualAttribute(marketArea, localization, attributeKey, value, true));
					}
				}
			}
		}
		
		if(catalogCategoryForm != null
				&& catalogCategoryForm.getMarketAreaAttributes() != null) {
			Map<String, String> attributes = catalogCategoryForm.getMarketAreaAttributes();
			boolean doesntExist = true;
			for (Iterator<String> iterator = attributes.keySet().iterator(); iterator.hasNext();) {
				String attributeKey = (String) iterator.next();
				for (Iterator<CatalogCategoryVirtualAttribute> iteratorCategoryMarketAttributes = catalogCategory.getMarketAreaAttributes(marketArea.getId()).iterator(); iteratorCategoryMarketAttributes.hasNext();) {
					CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = (CatalogCategoryVirtualAttribute) iteratorCategoryMarketAttributes.next();
					if(catalogCategoryVirtualAttribute.getAttributeDefinition().getCode().equals(attributeKey)) {
					    catalogCategoryVirtualAttribute.setValue(catalogCategoryForm.getMarketAreaAttributes().get(attributeKey));
						doesntExist = false;
					}
				}
				if(doesntExist) {
					String value = attributes.get(attributeKey);
					if(StringUtils.isNotEmpty(value)) {
						catalogCategory.getMarketAreaAttributes(marketArea.getId()).add(buildCatalogCategoryVirtualAttribute(marketArea, localization, attributeKey, value, false));
					}
				}
			}
		}
		
		return catalogCategoryService.saveOrUpdateCatalogCategory(catalogCategory);
	}

    private ProductBrandAttribute buildProductBrandAttribute(final MarketArea marketArea, final Localization localization, final String attributeKey, final String attributeValue, boolean isGlobal) {

        // TODO : denis : 20130125 : add cache
        AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);

        ProductBrandAttribute productBrandAttribute = new ProductBrandAttribute();
        productBrandAttribute.setAttributeDefinition(attributeDefinition);
        productBrandAttribute.setLocalizationCode(localization.getCode());
        productBrandAttribute.setMarketAreaId(marketArea.getId());
        productBrandAttribute.setStartDate(new Date());
        productBrandAttribute.setValue(attributeValue);
        return productBrandAttribute;
    }
	   
	private CatalogCategoryMasterAttribute buildCatalogCategoryMasterAttribute(final MarketArea marketArea, final Localization localization, final String attributeKey, final String attributeValue, boolean isGlobal) {
		
		//TODO : denis : 20130125 : add cache
		AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);

		CatalogCategoryMasterAttribute catalogCategoryMasterAttribute = new CatalogCategoryMasterAttribute();
		catalogCategoryMasterAttribute.setAttributeDefinition(attributeDefinition);
		catalogCategoryMasterAttribute.setLocalizationCode(localization.getCode());
		catalogCategoryMasterAttribute.setMarketAreaId(marketArea.getId());
		catalogCategoryMasterAttribute.setStartDate(new Date());
		catalogCategoryMasterAttribute.setValue(attributeValue);
		return catalogCategoryMasterAttribute;
	}
	
	private CatalogCategoryVirtualAttribute buildCatalogCategoryVirtualAttribute(final MarketArea marketArea, final Localization localization, final String attributeKey, final String attributeValue, boolean isGlobal) {
		
		//TODO : denis : 20130125 : add cache
		AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(attributeKey);

		CatalogCategoryVirtualAttribute catalogCategoryVirtualAttribute = new CatalogCategoryVirtualAttribute();
		catalogCategoryVirtualAttribute.setAttributeDefinition(attributeDefinition);
		catalogCategoryVirtualAttribute.setLocalizationCode(localization.getCode());
//		catalogCategoryVirtualAttribute.setMarketAreaId(marketArea.getId());
		catalogCategoryVirtualAttribute.setStartDate(new Date());
	    catalogCategoryVirtualAttribute.setValue(attributeValue);
	    
		return catalogCategoryVirtualAttribute;
	}
	
	public ProductMarketing createOrUpdateProductMarketing(ProductMarketing productMarketing, final ProductMarketingForm productMarketingForm) {
       if(productMarketing == null){
           productMarketing = new ProductMarketing();
        }
        if (StringUtils.isNotEmpty(productMarketingForm.getCode())) {
            productMarketing.setCode(productMarketingForm.getCode());
        }
        if (StringUtils.isNotEmpty(productMarketingForm.getName())) {
            productMarketing.setName(productMarketingForm.getName());
        }
		productMarketing.setDescription(productMarketingForm.getDescription());
		
		ProductMarketing savedProductMarketing = productService.saveOrUpdateProductMarketing(productMarketing);
		return savedProductMarketing;
	}
	
	public ProductSku createOrUpdateProductSku(ProductSku productSku, final ProductSkuForm productSkuForm) {
	    if(productSku == null){
	        productSku = new ProductSku();
	    }
        if (StringUtils.isNotEmpty(productSkuForm.getCode())) {
            productSku.setCode(productSkuForm.getCode());
        }
        if (StringUtils.isNotEmpty(productSkuForm.getName())) {
            productSku.setName(productSkuForm.getName());
        }
		productSku.setDescription(productSkuForm.getDescription());

		ProductSku savedProductSku = null;
        if (StringUtils.isNotEmpty(productSkuForm.getProductMarketingId())) {
            ProductMarketing productMarketing = productService.getProductMarketingById(productSkuForm.getProductMarketingId());
            productSku.setProductMarketing(productMarketing);
            savedProductSku = productService.saveOrUpdateProductSku(productSku);
        }
        return savedProductSku;
	}
	
	public Asset createOrUpdateProductMarketingAsset(final Asset asset, final AssetForm assetForm) {
        if (StringUtils.isNotEmpty(assetForm.getName())) {
            asset.setName(assetForm.getName());
        }
		asset.setDescription(assetForm.getDescription());

//		asset.setType(assetForm.getType);
//		private boolean isDefault;
//		private boolean isGlobal;
//		private Integer ordering;
//		private Long marketAreaId;
		
		return productService.saveOrUpdateProductMarketingAsset(asset);
	}
	
	public Retailer createOrUpdateRetailer(Retailer retailer, final RetailerForm retailerForm) throws Exception {
	    if (retailer == null) {
	        retailer = new Retailer();
	    }
        if (StringUtils.isNotEmpty(retailerForm.getCode())) {
            retailer.setCode(retailerForm.getCode());
        }
        if (StringUtils.isNotEmpty(retailerForm.getName())) {
            retailer.setName(retailerForm.getName());
        }
		retailer.setDescription(retailerForm.getDescription());
		
		RetailerAddress retailerAddress = retailer.getDefaultAddress();
        if (retailerAddress == null) {
            retailerAddress = new RetailerAddress();
            retailer.getAddresses().add(retailerAddress);
        }
		
		retailerAddress.setAddress1(retailerForm.getAddress1());
		retailerAddress.setAddress2(retailerForm.getAddress2());
		retailerAddress.setAddressAdditionalInformation(retailerForm.getAddressAdditionalInformation());
		retailerAddress.setPostalCode(retailerForm.getPostalCode());
		retailerAddress.setCity(retailerForm.getCity());
		retailerAddress.setStateCode(retailerForm.getStateCode());
		retailerAddress.setCountryCode(retailerForm.getCountryCode());
		
		retailerAddress.setPhone(retailerForm.getPhone());
		retailerAddress.setFax(retailerForm.getFax());
		retailerAddress.setMobile(retailerForm.getMobile());
		retailerAddress.setEmail(retailerForm.getEmail());
		retailerAddress.setWebsite(retailerForm.getWebsite());
		
		retailer.setBrand(retailerForm.isBrand());
		retailer.setCorner(retailerForm.isCorner());
		retailer.setEcommerce(retailerForm.isEcommerce());
		retailer.setOfficialRetailer(retailerForm.isOfficialRetailer());
		
//		if(StringUtils.isNotBlank(retailerForm.getWarehouseId())){
//			final Warehouse warehouse = warehouseService.getWarehouseById(retailerForm.getWarehouseId());
//			if(warehouse != null){
//				retailer.setWarehouse(warehouse);
//			}
//		} else {
//			retailer.setWarehouse(null);
//		}
		
        MultipartFile multipartFile = retailerForm.getFile();
        if (multipartFile != null
                && multipartFile.getSize() > 0) {
            UUID uuid = UUID.randomUUID();
            String pathRetailerLogoImage = new StringBuilder(uuid.toString()).append(System.getProperty ("file.separator")).append(FilenameUtils.getExtension(multipartFile.getOriginalFilename())).toString();

            String absoluteFilePath = retailerService.buildRetailerLogoFilePath(retailer, pathRetailerLogoImage);
            String absoluteFolderPath = absoluteFilePath.replace(pathRetailerLogoImage, "");
            
            InputStream inputStream = multipartFile.getInputStream();
            File fileLogo = null;
            File folderLogo = null;
            try {
                folderLogo = new File(absoluteFolderPath);
                folderLogo.mkdirs();
                fileLogo = new File(absoluteFilePath);
                
            } catch (Exception e) {
                //
            }
            if(fileLogo != null){
                OutputStream outputStream = new FileOutputStream(fileLogo);
                int readBytes = 0;
                byte[] buffer = new byte[8192];
                while ((readBytes = inputStream.read(buffer, 0, 8192)) != -1) {
                    outputStream.write(buffer, 0, readBytes);
                }
                outputStream.close();
                inputStream.close();
                retailer.setLogo(pathRetailerLogoImage);
            }
        }
		
        return retailerService.saveOrUpdateRetailer(retailer);
	}

    public Warehouse createOrUpdateWarehouse(final RequestData requestData, Warehouse warehouse, final WarehouseForm warehouseForm) {
        if (warehouse == null) {
            warehouse = new Warehouse();
            Set<WarehouseMarketAreaRel> warehouseMarketAreas = new HashSet<WarehouseMarketAreaRel>();
            WarehouseMarketAreaRel warehouseMarketArea = new WarehouseMarketAreaRel();
            warehouseMarketArea.setMarketArea(requestData.getMarketArea());
            warehouseMarketAreas.add(warehouseMarketArea);
            warehouse.setWarehouseMarketAreaRels(warehouseMarketAreas);
        }
        if (StringUtils.isNotEmpty(warehouseForm.getCode())) {
            warehouse.setCode(warehouseForm.getCode());
        }
        if (StringUtils.isNotEmpty(warehouseForm.getName())) {
            warehouse.setName(warehouseForm.getName());
        }
        warehouse.setDescription(warehouseForm.getDescription());

        warehouse.setAddress1(warehouseForm.getAddress1());
        warehouse.setAddress2(warehouseForm.getAddress2());
        warehouse.setAddressAdditionalInformation(warehouseForm.getAddressAdditionalInformation());
        warehouse.setPostalCode(warehouseForm.getPostalCode());
        warehouse.setCity(warehouseForm.getCity());
        warehouse.setStateCode(warehouseForm.getStateCode());
        warehouse.setCountryCode(warehouseForm.getCountryCode());

        warehouse.setLatitude(warehouseForm.getLatitude());
        warehouse.setLongitude(warehouseForm.getLongitude());
        
        Warehouse savedWarehouse = warehouseService.saveOrUpdateWarehouse(warehouse);
        return savedWarehouse;
    }

    public DeliveryMethod createOrUpdateDeliveryMethod(DeliveryMethod deliveryMethod, final DeliveryMethodForm deliveryMethodForm) {
        if (deliveryMethod == null) {
            deliveryMethod = new DeliveryMethod();
        }
        if (StringUtils.isNotEmpty(deliveryMethodForm.getCode())) {
            deliveryMethod.setCode(deliveryMethodForm.getCode());
        }
        if (StringUtils.isNotEmpty(deliveryMethodForm.getName())) {
            deliveryMethod.setName(deliveryMethodForm.getName());
        }
        deliveryMethod.setDescription(deliveryMethodForm.getDescription());

        return deliveryMethodService.saveOrUpdateDeliveryMethod(deliveryMethod);
    }
    
    public Tax createOrUpdateTax(Tax tax, final TaxForm taxForm) {
        if (tax == null) {
            tax = new Tax();
        }
        if (StringUtils.isNotEmpty(taxForm.getCode())) {
            tax.setCode(taxForm.getCode());
        }
        if (StringUtils.isNotEmpty(taxForm.getName())) {
            tax.setName(taxForm.getName());
        }
        tax.setDescription(taxForm.getDescription());
        tax.setPercent(new BigDecimal(taxForm.getPercent()));

        return taxService.saveOrUpdateTax(tax);
    }

    public EngineSettingValue updateEngineSettingValue(EngineSettingValue engineSettingValue, final EngineSettingValueForm engineSettingValueForm) {
        if (engineSettingValue == null) {
            engineSettingValue = new EngineSettingValue();
        }
        engineSettingValue.setValue(engineSettingValueForm.getValue());
        return engineSettingService.saveOrUpdateEngineSettingValue(engineSettingValue);
    }

    public AbstractPaymentGateway createOrUpdatePaymentGateway(final MarketArea marketArea, AbstractPaymentGateway paymentGateway, final PaymentGatewayForm paymentGatewayForm) {
        if (StringUtils.isNotEmpty(paymentGatewayForm.getCode())) {
            paymentGateway.setCode(paymentGatewayForm.getCode());
        }
        if (StringUtils.isNotEmpty(paymentGatewayForm.getName())) {
            paymentGateway.setName(paymentGatewayForm.getName());
        }
        paymentGateway.setDescription(paymentGatewayForm.getDescription());
        
        if (paymentGatewayForm.isActive()) {
            if (paymentGateway.getMarketAreas() == null || !paymentGateway.getMarketAreas().contains(marketArea)) {
                paymentGateway.addMarketArea(marketArea);
            }
        } else {
            if (paymentGateway.getMarketAreas() == null || paymentGateway.getMarketAreas().contains(marketArea)) {
                paymentGateway.getMarketAreas().remove(marketArea);
            }
        }

        for (Iterator<String> iterator = paymentGatewayForm.getGlobalAttributeMap().keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = paymentGatewayForm.getGlobalAttributeMap().get(key);
            boolean success = paymentGateway.updateAttribute(key, value);
            if(!success){
                // ATTRIBUTE DOESN'T EXIT - ADD
                AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(key);
                paymentGateway.addAttribute(null, attributeDefinition, value);
            }
        }
        
        for (Iterator<String> iterator = paymentGatewayForm.getMarketAreaAttributeMap().keySet().iterator(); iterator.hasNext();) {
            String key = (String) iterator.next();
            String value = paymentGatewayForm.getMarketAreaAttributeMap().get(key);
            boolean success = paymentGateway.updateAttribute(key, value);
            if(!success){
                // ATTRIBUTE DOESN'T EXIT - ADD
                AttributeDefinition attributeDefinition = attributeService.getAttributeDefinitionByCode(key);
                paymentGateway.addAttribute(marketArea, attributeDefinition, value);
            }
        }
        
        List<PaymentGatewayOption> availableOptions = paymentGatewayService.findPaymentGatewayOptions();
        Set<PaymentGatewayOption> options = new HashSet<PaymentGatewayOption>();
        for (Iterator<PaymentGatewayOption> iterator = availableOptions.iterator(); iterator.hasNext();) {
            PaymentGatewayOption paymentGatewayOption = (PaymentGatewayOption) iterator.next();
            if(paymentGatewayForm.getOptionMap().keySet().contains(paymentGatewayOption.getCode())){
                options.add(paymentGatewayOption);
            }
        }
        paymentGateway.setOptions(options);
        
        return paymentGatewayService.saveOrUpdatePaymentGateway(paymentGateway);
    }

    public EngineSetting createOrUpdateEngineSetting(EngineSetting engineSetting, final EngineSettingForm engineSettingForm) {
        if (engineSetting == null) {
            engineSetting = new EngineSetting();
        }
        engineSetting.setDefaultValue(engineSettingForm.getDefaultValue());
        return engineSettingService.saveOrUpdateEngineSetting(engineSetting);
    }

    public EngineSettingValue createOrUpdateEngineSettingValue(EngineSettingValue engineSettingValue, final EngineSettingValueForm engineSettingValueForm) {
        if (engineSettingValue == null) {
            engineSettingValue = new EngineSettingValue();
        }
        engineSettingValue.setContext(engineSettingValueForm.getContext());
        engineSettingValue.setValue(engineSettingValueForm.getValue());

        return engineSettingService.saveOrUpdateEngineSettingValue(engineSettingValue);
    }
    
    public Store createOrUpdateStore(Retailer retailer, Store store, StoreForm storeForm) throws Exception {
    	if (store == null) {
    		store = new Store();
	    }
        if (StringUtils.isNotEmpty(storeForm.getCode())) {
            store.setCode(storeForm.getCode());
        }
        if (StringUtils.isNotEmpty(storeForm.getName())) {
            store.setName(storeForm.getName());
        }
		
    	store.setAddress1(storeForm.getAddress1());
    	store.setAddress2(storeForm.getAddress2());
    	store.setAddressAdditionalInformation(storeForm.getAddressAdditionalInformation());
    	store.setPostalCode(storeForm.getPostalCode());
    	store.setCity(storeForm.getCity());
    	store.setStateCode(storeForm.getStateCode());
    	store.setCountryCode(storeForm.getCountryCode());

        store.setLatitude(storeForm.getLatitude());
        store.setLongitude(storeForm.getLongitude());

    	if(retailer != null){
            store.setRetailer(retailer);
    	}
		
    	return retailerService.saveOrUpdateStore(store);
    }
    
    /**
     * 
     */
    public void buildAndSaveUserNewAccountMail(final RequestData requestData, final User user) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final String contextNameValue = requestData.getContextNameValue();

        final UserNewAccountConfirmationEmailBean userNewAccountConfirmationEmailBean = new UserNewAccountConfirmationEmailBean();
        BeanUtils.copyProperties(user, userNewAccountConfirmationEmailBean);
        userNewAccountConfirmationEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION));
        userNewAccountConfirmationEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION));
        userNewAccountConfirmationEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_NEW_ACCOUNT_CONFIRMATION));
        userNewAccountConfirmationEmailBean.setToEmail(user.getEmail());

        userNewAccountConfirmationEmailBean.setTitle(user.getTitle());
        userNewAccountConfirmationEmailBean.setFirstname(user.getFirstname());
        userNewAccountConfirmationEmailBean.setLastname(user.getLastname());
        userNewAccountConfirmationEmailBean.setEmail(user.getEmail());
        
        userNewAccountConfirmationEmailBean.setUserDetailsUrl(backofficeUrlService.buildAbsoluteUrl(requestData, backofficeUrlService.generateUrl(BoUrls.PERSONAL_DETAILS, requestData)));
        
        buildAndSaveUserNewAccountMail(requestData, userNewAccountConfirmationEmailBean);
    }
    
    /**
     * 
     */
    public void buildAndSaveUserForgottenPasswordMail(final RequestData requestData, final User user, final UserCredential userCredential, final ForgottenPasswordForm forgottenPasswordForm) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Locale locale = requestData.getLocale();
        final String contextNameValue = requestData.getContextNameValue();

        final UserForgottenPasswordEmailBean userForgottenPasswordEmailBean = new UserForgottenPasswordEmailBean();
        userForgottenPasswordEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD));
        userForgottenPasswordEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD));
        userForgottenPasswordEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_FORGOTTEN_PASSWORD));
        userForgottenPasswordEmailBean.setToEmail(user.getEmail());
        userForgottenPasswordEmailBean.setToken(userCredential.getResetToken());
        
        userForgottenPasswordEmailBean.setTitle(referentialDataService.getTitleByLocale(user.getTitle(), locale));
        userForgottenPasswordEmailBean.setFirstname(user.getFirstname());
        userForgottenPasswordEmailBean.setLastname(user.getLastname());
        userForgottenPasswordEmailBean.setEmail(user.getEmail());
        
        buildAndSaveUserForgottenPasswordMail(requestData, user, userCredential, userForgottenPasswordEmailBean);
    }
    
    /**
     * 
     */
    public void resetUserCredential(final RequestData requestData, final User user, final ResetPasswordForm resetPasswordForm) throws Exception {
        // FLAG LAST CURRENT CREDENTIEL
        UserCredential userCredential = user.getCurrentCredential();
        if(userCredential != null){
            userCredential.setDateUpdate(new Date());
            userCredential.setResetProcessedDate(new Date());
        }
        
        // ADD A NEW ONE
        addNewUserCredential(requestData, user, resetPasswordForm.getNewPassword());
    }
    
    /**
     * 
     */
    public UserCredential flagUserCredentialWithToken(final RequestData requestData, final User user) throws Exception {
        if(user != null){
            String token = UUID.randomUUID().toString();
            UserCredential userCredential = user.getCurrentCredential();
            if(userCredential == null){
                userCredential = new UserCredential();
                userCredential.setDateCreate(new Date());
                user.getCredentials().add(userCredential);
            }
            userCredential.setDateUpdate(new Date());
            userCredential.setResetToken(token);
            userCredential.setTokenTimestamp(new Timestamp(new Date().getTime()));
            
            // UPDATE CUSTOMER
            userService.saveOrUpdateUser(user);
            
            return userCredential;
        }
        return null;
    }
    
    /**
     * 
     */
    public void cancelUserCredentialToken(final RequestData requestData, final User user) throws Exception {
        if(user != null){
            UserCredential userCredential = user.getCurrentCredential();
            if(userCredential != null){
                userCredential.setDateUpdate(new Date());
                userCredential.setResetToken("");
                userCredential.setTokenTimestamp(null);
                userService.saveOrUpdateUserCredential(userCredential);
            }
        }
    }
    
    /**
     * 
     */
    public void addNewUserCredential(final RequestData requestData, final User user, final String newPassword) throws Exception {
        if(user != null){
            String clearPassword = newPassword;
            String encodePassword = securityUtil.encodePassword(clearPassword);
            UserCredential userCredential = new UserCredential();
            userCredential.setPassword(encodePassword);
            userCredential.setDateCreate(new Date());
            userCredential.setDateUpdate(new Date());
            user.getCredentials().add(userCredential);
            
            user.setPassword(encodePassword);
            
            userService.saveOrUpdateUser(user);
        }
    }
    
    /**
     * 
     */
    public void buildAndSaveUserForgottenPasswordMail(final RequestData requestData, final User user, final UserCredential userCredential, final UserForgottenPasswordEmailBean userForgottenPasswordEmailBean) throws Exception {
        emailService.buildAndSaveUserForgottenPasswordMail(requestData, user, requestData.getVelocityEmailPrefix(), userForgottenPasswordEmailBean);
    }
    
    /**
     * 
     */
    public void buildAndSaveUserResetPasswordConfirmationMail(final RequestData requestData, final User user) throws Exception {
        final MarketArea marketArea = requestData.getMarketArea();
        final Locale locale = requestData.getLocale();
        final String contextNameValue = requestData.getContextNameValue();
        final String velocityPath = requestData.getVelocityEmailPrefix();

        final UserResetPasswordConfirmationEmailBean userResetPasswordConfirmationEmailBean = new UserResetPasswordConfirmationEmailBean();
        userResetPasswordConfirmationEmailBean.setFromAddress(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        userResetPasswordConfirmationEmailBean.setFromName(marketArea.getEmailFromName(contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        userResetPasswordConfirmationEmailBean.setReplyToEmail(getEmailFromAddress(requestData, marketArea, contextNameValue, Email.EMAIl_TYPE_RESET_PASSWORD_CONFIRMATION));
        userResetPasswordConfirmationEmailBean.setToEmail(user.getEmail());
        
        userResetPasswordConfirmationEmailBean.setTitle(referentialDataService.getTitleByLocale(user.getTitle(), locale));
        userResetPasswordConfirmationEmailBean.setFirstname(user.getFirstname());
        userResetPasswordConfirmationEmailBean.setLastname(user.getLastname());
        userResetPasswordConfirmationEmailBean.setEmail(user.getEmail());
        
        userResetPasswordConfirmationEmailBean.setUserDetailsUrl(backofficeUrlService.buildAbsoluteUrl(requestData, backofficeUrlService.generateUrl(BoUrls.PERSONAL_DETAILS, requestData)));
        
        emailService.buildAndSaveUserResetPasswordConfirmationMail(requestData, user, velocityPath, userResetPasswordConfirmationEmailBean);
    }
    
    /**
     * 
     */
    public void buildAndSaveUserNewAccountMail(final RequestData requestData, final UserNewAccountConfirmationEmailBean userNewAccountConfirmationEmailBean) throws Exception {
        emailService.buildAndSaveUserNewAccountMail(requestData, requestData.getVelocityEmailPrefix(), userNewAccountConfirmationEmailBean);
    }
    
    protected String getEmailFromAddress(final RequestData requestData, final MarketArea marketArea, final String contextNameValue, final String emailType) throws Exception{
        String emailFromAddress = marketArea.getEmailFromAddress(contextNameValue, emailType);
        if(StringUtils.isEmpty(emailFromAddress)){
            final HttpServletRequest request = requestData.getRequest();
            final String contextValue = requestUtil.getCurrentContextNameValue();
            emailFromAddress = engineSettingService.getDefaultEmailAddress(contextValue);
        }
        return emailFromAddress;
    }
}