package fr.hoteia.qalingo.core.rest.util;

import fr.hoteia.qalingo.core.domain.*;
import fr.hoteia.qalingo.core.pojo.customer.CustomerPojo;
import fr.hoteia.qalingo.core.rest.pojo.*;

import java.util.List;

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
