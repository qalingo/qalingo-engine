package fr.hoteia.qalingo.core.rest.util;

import java.util.List;

import fr.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import fr.hoteia.qalingo.core.domain.CatalogMaster;
import fr.hoteia.qalingo.core.domain.CatalogVirtual;
import fr.hoteia.qalingo.core.domain.Customer;
import fr.hoteia.qalingo.core.domain.ProductMarketing;
import fr.hoteia.qalingo.core.domain.ProductSku;
import fr.hoteia.qalingo.core.domain.Store;
import fr.hoteia.qalingo.core.pojo.CustomerPojo;
import fr.hoteia.qalingo.core.rest.pojo.CatalogJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.ProductCategoryJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.ProductMarketingJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.ProductSkuJsonPojo;
import fr.hoteia.qalingo.core.rest.pojo.StoreJsonPojo;

public interface JsonFactory {

    Customer buildCustomer(final CustomerPojo customerJsonPojo);

    List<CustomerPojo> buildJsonCustomers(final List<Customer> customers);

    CustomerPojo buildJsonCustomer(final Customer customer);

    Store buildStore(final StoreJsonPojo storeJsonPojo);

    List<StoreJsonPojo> buildJsonStores(final List<Store> stores);

    StoreJsonPojo buildJsonStore(final Store store);

    CatalogJsonPojo buildJsonCatalog(final CatalogMaster catalogMaster);

    CatalogJsonPojo buildJsonCatalog(final CatalogVirtual catalogVirtual, final Long marketAreaId);

    List<ProductCategoryJsonPojo> buildJsonProductCategories(final List<CatalogCategoryVirtual> productCategories, final Long marketAreaId);

    ProductCategoryJsonPojo buildJsonProductCategory(final CatalogCategoryVirtual productCategory, final Long marketAreaId);

    List<ProductMarketingJsonPojo> buildJsonProductMarketings(final List<ProductMarketing> productMarketings);

    ProductMarketingJsonPojo buildJsonProductMarketing(final ProductMarketing productMarketing);

    List<ProductSkuJsonPojo> buildJsonProductSkus(final List<ProductSku> productSkus);

    ProductSkuJsonPojo buildJsonProductSku(final ProductSku productSku);

}
