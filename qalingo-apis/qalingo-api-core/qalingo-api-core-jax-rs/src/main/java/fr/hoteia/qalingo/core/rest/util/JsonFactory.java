package fr.hoteia.qalingo.core.rest.util;

import java.util.List;

import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Customer;
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

public interface JsonFactory {
	
	Customer buildCustomer(CustomerJsonPojo customerJsonPojo);

	List<CustomerJsonPojo> buildJsonCustomers(List<Customer> customers);

	CustomerJsonPojo buildJsonCustomer(Customer customer);
	
	Store buildStore(StoreJsonPojo storeJsonPojo);
	
	List<StoreJsonPojo> buildJsonStores(List<Store> stores);
	
	StoreJsonPojo buildJsonStore(Store store);
	
	CatalogJsonPojo buildJsonCatalog(CatalogMaster catalogMaster);

	CatalogJsonPojo buildJsonCatalog(CatalogVirtual catalogVirtual, Long marketAreaId);
	
	List<ProductCategoryJsonPojo> buildJsonProductCategories(List<ProductCategoryVirtual> productCategories, Long marketAreaId);
	
	ProductCategoryJsonPojo buildJsonProductCategory(ProductCategoryVirtual productCategory, Long marketAreaId);
	
	List<ProductMarketingJsonPojo> buildJsonProductMarketings(List<ProductMarketing> productMarketings);
	
	ProductMarketingJsonPojo buildJsonProductMarketing(ProductMarketing productMarketing);
	
	List<ProductSkuJsonPojo> buildJsonProductSkus(List<ProductSku> productSkus);
	
	ProductSkuJsonPojo buildJsonProductSku(ProductSku productSku);
	
}
