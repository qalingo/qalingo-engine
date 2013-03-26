package fr.hoteia.qalingo.core.rest.util.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.ProductCategoryMaster;
import fr.hoteia.qalingo.core.domain.ProductCategoryVirtual;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.rest.pojo.CatalogJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.CustomerJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.ProductCategoryJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.ProductMarketingJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.ProductSkuJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.StoreJsonPojo;
import fr.hoteia.qalingo.core.rest.util.JsonFactory;

@Service("jsonFactory")
public class JsonFactoryImpl implements JsonFactory {
	
	public Customer buildCustomer(CustomerJsonPojo customerJsonPojo) {
		final Customer customer = new Customer();
	
		customer.setId(customerJsonPojo.getId());
		customer.setVersion(customerJsonPojo.getVersion());
		customer.setLogin(customerJsonPojo.getLogin());
	    customer.setTitle(customerJsonPojo.getTitle());
		customer.setFirstname(customerJsonPojo.getFirstname());
		customer.setLastname(customerJsonPojo.getLastname());
		customer.setEmail(customerJsonPojo.getEmail());
		customer.setPassword(customerJsonPojo.getPassword());
		customer.setDefaultLocale(customerJsonPojo.getDefaultLocale());
		customer.setActive(customer.isActive());
		
//		customer.setAddresses(customerJsonPojo.getAddresses()); 
//		customer.setConnectionLogs(customerJsonPojo.getConnectionLogs()); 
//		customer.setCustomerMarketAreas(customerJsonPojo.getCustomerMarketAreas()); 
//		customer.setCustomerAttributes(customerJsonPojo.getCustomerAttributes()); 
//		customer.setCustomerGroups(customerJsonPojo.getCustomerGroups()); 
		
		customer.setDateCreate(customerJsonPojo.getDateCreate());
		customer.setDateUpdate(customerJsonPojo.getDateUpdate());
	
		return customer;
	}
	
	public List<CustomerJsonPojo> buildJsonCustomers(List<Customer> customers) {
		final List<CustomerJsonPojo> customerCustomerJsonPojos = new ArrayList<CustomerJsonPojo>();
		if(customers != null) {
			for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
				Customer customer = (Customer) iterator.next();
				CustomerJsonPojo customerJsonPojo = buildJsonCustomer(customer);
				customerCustomerJsonPojos.add(customerJsonPojo);
			}
		}
		return customerCustomerJsonPojos;
	}
	
	public CustomerJsonPojo buildJsonCustomer(Customer customer) {
		final CustomerJsonPojo customerJsonPojo = new CustomerJsonPojo();
		
		customerJsonPojo.setId(customer.getId());
		customerJsonPojo.setVersion(customer.getVersion());
		customerJsonPojo.setLogin(customer.getLogin());
	    customerJsonPojo.setTitle(customer.getTitle());
		customerJsonPojo.setFirstname(customer.getFirstname());
		customerJsonPojo.setLastname(customer.getLastname());
		customerJsonPojo.setEmail(customer.getEmail());
		customerJsonPojo.setPassword(customer.getPassword());
		customerJsonPojo.setDefaultLocale(customer.getDefaultLocale());
		customerJsonPojo.setActive(customer.isActive());
		
//		customerJsonPojo.setAddresses(customer.getAddresses()); 
//		customerJsonPojo.setConnectionLogs(customer.getConnectionLogs()); 
//		customerJsonPojo.setCustomerMarketAreas(customer.getCustomerMarketAreas()); 
//		customerJsonPojo.setCustomerAttributes(customer.getCustomerAttributes()); 
//		customerJsonPojo.setCustomerGroups(customer.getCustomerGroups()); 
		
		customerJsonPojo.setDateCreate(customer.getDateCreate());
		customerJsonPojo.setDateUpdate(customer.getDateUpdate());

		return customerJsonPojo;
	}
	
	public Store buildStore(StoreJsonPojo storeJsonPojo) {
		final Store store = new Store();
		
		store.setId(storeJsonPojo.getId());
		store.setVersion(storeJsonPojo.getVersion());
		store.setCode(storeJsonPojo.getCode());
		store.setBusinessName(storeJsonPojo.getBusinessName());
		store.setAddress1(storeJsonPojo.getAddress1());
		store.setAddress2(storeJsonPojo.getAddress2());
		store.setAddressAdditionalInformation(storeJsonPojo.getAddressAdditionalInformation());
		store.setPostalCode(storeJsonPojo.getPostalCode());
		store.setCity(storeJsonPojo.getCity());
		store.setCountyCode(storeJsonPojo.getCountyCode());
		store.setCountryCode(storeJsonPojo.getCountryCode());
		
//		store.setStoreAttributes(storeJsonPojo.getStoreAttributes()); 
		
		store.setLongitude(storeJsonPojo.getLongitude());
		store.setLatitude(storeJsonPojo.getLatitude());

		store.setDateCreate(storeJsonPojo.getDateCreate());
		store.setDateUpdate(storeJsonPojo.getDateUpdate());
	
		return store;
	}
	
	public List<StoreJsonPojo> buildJsonStores(List<Store> stores) {
		final List<StoreJsonPojo> storeStoreJsonBeans = new ArrayList<StoreJsonPojo>();
		if(stores != null) {
			for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
				Store store = (Store) iterator.next();
				StoreJsonPojo storeJsonBean = buildJsonStore(store);
				storeStoreJsonBeans.add(storeJsonBean);
			}
		}
		return storeStoreJsonBeans;
	}
	
	public StoreJsonPojo buildJsonStore(Store store) {
		final StoreJsonPojo storeJsonBean = new StoreJsonPojo();

		storeJsonBean.setId(store.getId());
		storeJsonBean.setVersion(store.getVersion());
		storeJsonBean.setCode(store.getCode());
		storeJsonBean.setBusinessName(store.getBusinessName());
		storeJsonBean.setAddress1(store.getAddress1());
		storeJsonBean.setAddress2(store.getAddress2());
		storeJsonBean.setAddressAdditionalInformation(store.getAddressAdditionalInformation());
		storeJsonBean.setPostalCode(store.getPostalCode());
		storeJsonBean.setCity(store.getCity());
		storeJsonBean.setCountyCode(store.getCountyCode());
		storeJsonBean.setCountryCode(store.getCountryCode());
		
//		storeJsonBean.setStoreAttributes(store.getStoreAttributes()); 
		
		storeJsonBean.setLongitude(store.getLongitude());
		storeJsonBean.setLatitude(store.getLatitude());

		storeJsonBean.setDateCreate(store.getDateCreate());
		storeJsonBean.setDateUpdate(store.getDateUpdate());
		
		return storeJsonBean;
	}
	
	public CatalogJsonPojo buildJsonCatalog(CatalogVirtual catalogVirtual, Long marketAreaId) {
		final CatalogJsonPojo catalogJsonPojo = new CatalogJsonPojo();
		catalogJsonPojo.setId(catalogVirtual.getId());
		catalogJsonPojo.setVersion(catalogVirtual.getVersion());
		catalogJsonPojo.setBusinessName(catalogVirtual.getBusinessName());
		catalogJsonPojo.setDescription(catalogVirtual.getDescription());
		catalogJsonPojo.setDefault(catalogVirtual.isDefault());
		catalogJsonPojo.setCode(catalogVirtual.getCode());
		catalogJsonPojo.setDateCreate(catalogVirtual.getDateCreate());
		catalogJsonPojo.setDateUpdate(catalogVirtual.getDateUpdate());
		
		final List<ProductCategoryVirtual> productCategories = catalogVirtual.getProductCategories(marketAreaId);
		if(productCategories != null) {
			for (Iterator<ProductCategoryVirtual> iterator = productCategories.iterator(); iterator.hasNext();) {
				ProductCategoryVirtual productCategory = (ProductCategoryVirtual) iterator.next();
				ProductCategoryJsonPojo productCategoryJsonPojo = buildJsonProductCategory(productCategory, marketAreaId);
				catalogJsonPojo.getProductCategories().add(productCategoryJsonPojo);
			}
		}
		return catalogJsonPojo;
	}
	
	public CatalogJsonPojo buildJsonCatalog(CatalogMaster catalogMaster) {
		final CatalogJsonPojo catalogJsonPojo = new CatalogJsonPojo();
		catalogJsonPojo.setId(catalogMaster.getId());
		catalogJsonPojo.setVersion(catalogMaster.getVersion());
		catalogJsonPojo.setBusinessName(catalogMaster.getBusinessName());
		catalogJsonPojo.setDescription(catalogMaster.getDescription());
		catalogJsonPojo.setDefault(catalogMaster.isDefault());
		catalogJsonPojo.setCode(catalogMaster.getCode());
		catalogJsonPojo.setDateCreate(catalogMaster.getDateCreate());
		catalogJsonPojo.setDateUpdate(catalogMaster.getDateUpdate());
		
		final Set<ProductCategoryMaster> productCategories = catalogMaster.getProductCategories();
		if(productCategories != null) {
			for (Iterator<ProductCategoryMaster> iterator = productCategories.iterator(); iterator.hasNext();) {
				ProductCategoryMaster productCategory = (ProductCategoryMaster) iterator.next();
				ProductCategoryJsonPojo productCategoryJsonPojo = buildJsonProductCategory(productCategory);
				catalogJsonPojo.getProductCategories().add(productCategoryJsonPojo);
			}
		}
		return catalogJsonPojo;
	}
	
	public List<ProductCategoryJsonPojo> buildJsonProductCategories(List<ProductCategoryMaster> productCategories) {
		final List<ProductCategoryJsonPojo> productCategoryJsonPojos = new ArrayList<ProductCategoryJsonPojo>();
		if(productCategories != null) {
			for (Iterator<ProductCategoryMaster> iterator = productCategories.iterator(); iterator.hasNext();) {
				ProductCategoryMaster productCategory = (ProductCategoryMaster) iterator.next();
				ProductCategoryJsonPojo productCategoryJsonPojo = buildJsonProductCategory(productCategory);
				productCategoryJsonPojos.add(productCategoryJsonPojo);
			}
		}
		return productCategoryJsonPojos;
	}
	
	public ProductCategoryJsonPojo buildJsonProductCategory(ProductCategoryMaster productCategory) {
		final ProductCategoryJsonPojo productCategoryJsonPojo = new ProductCategoryJsonPojo();
		
		productCategoryJsonPojo.setId(productCategory.getId());
		productCategoryJsonPojo.setVersion(productCategory.getVersion());
		productCategoryJsonPojo.setBusinessName(productCategory.getBusinessName());
		productCategoryJsonPojo.setDescription(productCategory.getDescription());
		productCategoryJsonPojo.setCode(productCategory.getCode());
		productCategoryJsonPojo.setDefault(productCategory.isDefault());
		productCategoryJsonPojo.setRoot(productCategory.isRoot());
		
//		productCategoryJsonPojo.setProductCategoryVirtualAttribute(productCategoryVirtualAttribute);
//		productCategoryJsonPojo.setProductCategoryMarketAreaAttributes(productCategoryMarketAreaAttributes);

		Set<ProductCategoryMaster> productCategories = productCategory.getProductCategories();
		productCategoryJsonPojo.setProductCategories(buildJsonProductCategories(new ArrayList<ProductCategoryMaster>(productCategories)));

		List<ProductMarketing> productMarketings = new ArrayList<ProductMarketing>(productCategory.getProductMarketings());
		productCategoryJsonPojo.setProductMarketings(buildJsonProductMarketings(productMarketings));

//		productCategoryJsonPojo.setProductMarketings(productMarketings);
		
//		productCategoryJsonPojo.setImages(images);
		
		productCategoryJsonPojo.setDateCreate(productCategory.getDateCreate());
		productCategoryJsonPojo.setDateUpdate(productCategory.getDateUpdate());
		
		return productCategoryJsonPojo;
	}
	
	public List<ProductCategoryJsonPojo> buildJsonProductCategories(List<ProductCategoryVirtual> productCategories, Long marketAreaId) {
		final List<ProductCategoryJsonPojo> productCategoryJsonPojos = new ArrayList<ProductCategoryJsonPojo>();
		if(productCategories != null) {
			for (Iterator<ProductCategoryVirtual> iterator = productCategories.iterator(); iterator.hasNext();) {
				ProductCategoryVirtual productCategory = (ProductCategoryVirtual) iterator.next();
				ProductCategoryJsonPojo productCategoryJsonPojo = buildJsonProductCategory(productCategory, marketAreaId);
				productCategoryJsonPojos.add(productCategoryJsonPojo);
			}
		}
		return productCategoryJsonPojos;
	}
	
	public ProductCategoryJsonPojo buildJsonProductCategory(ProductCategoryVirtual productCategory, Long marketAreaId) {
		final ProductCategoryJsonPojo productCategoryJsonPojo = new ProductCategoryJsonPojo();
		
		productCategoryJsonPojo.setId(productCategory.getId());
		productCategoryJsonPojo.setVersion(productCategory.getVersion());
		productCategoryJsonPojo.setBusinessName(productCategory.getBusinessName());
		productCategoryJsonPojo.setDescription(productCategory.getDescription());
		productCategoryJsonPojo.setCode(productCategory.getCode());
		productCategoryJsonPojo.setDefault(productCategory.isDefault());
		productCategoryJsonPojo.setRoot(productCategory.isRoot());
		
//		productCategoryJsonPojo.setProductCategoryVirtualAttribute(productCategoryVirtualAttribute);
//		productCategoryJsonPojo.setProductCategoryMarketAreaAttributes(productCategoryMarketAreaAttributes);

		List<ProductCategoryVirtual> productCategories = productCategory.getProductCategories(marketAreaId);
		productCategoryJsonPojo.setProductCategories(buildJsonProductCategories(productCategories, marketAreaId));

		List<ProductMarketing> productMarketings = new ArrayList<ProductMarketing>(productCategory.getProductMarketings());
		productCategoryJsonPojo.setProductMarketings(buildJsonProductMarketings(productMarketings));

//		productCategoryJsonPojo.setProductMarketings(productMarketings);
		
//		productCategoryJsonPojo.setImages(images);
		
		productCategoryJsonPojo.setDateCreate(productCategory.getDateCreate());
		productCategoryJsonPojo.setDateUpdate(productCategory.getDateUpdate());
		
		return productCategoryJsonPojo;
	}
	
	public List<ProductMarketingJsonPojo> buildJsonProductMarketings(List<ProductMarketing> productMarketings) {
		final List<ProductMarketingJsonPojo> productMarketingJsonPojos = new ArrayList<ProductMarketingJsonPojo>();
		if(productMarketings != null) {
			for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
				ProductMarketing productMarketing = (ProductMarketing) iterator.next();
				ProductMarketingJsonPojo productMarketingJsonPojo = buildJsonProductMarketing(productMarketing);
				productMarketingJsonPojos.add(productMarketingJsonPojo);
			}
		}
		return productMarketingJsonPojos;
	}
	
	public ProductMarketingJsonPojo buildJsonProductMarketing(ProductMarketing productMarketing) {
		final ProductMarketingJsonPojo productMarketingJsonPojo = new ProductMarketingJsonPojo();

		productMarketingJsonPojo.setId(productMarketing.getId());
		productMarketingJsonPojo.setVersion(productMarketing.getVersion());
		productMarketingJsonPojo.setBusinessName(productMarketing.getBusinessName());
		productMarketingJsonPojo.setDescription(productMarketing.getDescription());
		productMarketingJsonPojo.setDefault(productMarketing.isDefault());
		productMarketingJsonPojo.setCode(productMarketing.getCode());
		
//		productMarketingJsonPojo.setProductBrand(productBrand);
//		productMarketingJsonPojo.setProductMarketingGlobalAttributes(productMarketingGlobalAttributes); 
//		productMarketingJsonPojo.setProductMarketingMarketAreaAttributes(productMarketingMarketAreaAttributes); 

		List<ProductSku> productSkus = new ArrayList<ProductSku>(productMarketing.getProductSkus());
		productMarketingJsonPojo.setProductSkus(buildJsonProductSkus(productSkus));
		
//		productMarketingJsonPojo.setProductCrossLinks(productCrossLinks);
//		productMarketingJsonPojo.setImages(images);

		productMarketingJsonPojo.setDateCreate(productMarketing.getDateCreate());
		productMarketingJsonPojo.setDateUpdate(productMarketing.getDateUpdate());
		
		return productMarketingJsonPojo;
	}
	
	public List<ProductSkuJsonPojo> buildJsonProductSkus(List<ProductSku> productSkus) {
		final List<ProductSkuJsonPojo> productSkuJsonPojos = new ArrayList<ProductSkuJsonPojo>();
		if(productSkus != null) {
			for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
				ProductSku productSku = (ProductSku) iterator.next();
				ProductSkuJsonPojo productSkuJsonPojo = buildJsonProductSku(productSku);
				productSkuJsonPojos.add(productSkuJsonPojo);
			}
		}
		return productSkuJsonPojos;
	}
	
	public ProductSkuJsonPojo buildJsonProductSku(ProductSku productSku) {
		final ProductSkuJsonPojo productSkuJsonPojo = new ProductSkuJsonPojo();
		
		productSkuJsonPojo.setId(productSku.getId());
		productSkuJsonPojo.setVersion(productSku.getVersion());
		productSkuJsonPojo.setBusinessName(productSku.getBusinessName());
		productSkuJsonPojo.setDescription(productSku.getDescription());
		productSkuJsonPojo.setDefault(productSku.isDefault());
		productSkuJsonPojo.setCode(productSku.getCode());
		
//		productSkuJsonPojo.setProductSkuGlobalAttributes(productSkuGlobalAttributes); 
//		productSkuJsonPojo.setProductSkuMarketAreaAttributes(productSkuMarketAreaAttributes); 
//		productSkuJsonPojo.setProductMarketing(productMarketing);
//		productSkuJsonPojo.setImages(images); 
//		productSkuJsonPojo.setPrices(prices); 
//		productSkuJsonPojo.setStocks(stocks); 
//		productSkuJsonPojo.setRetailers(retailers);
		
		productSkuJsonPojo.setDateCreate(productSku.getDateCreate());
		productSkuJsonPojo.setDateUpdate(productSku.getDateUpdate());
		
		return null;
	}
}
