package fr.hoteia.qalingo.core.rest.util.impl;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import fr.hoteia.qalingo.core.domain.CatalogCategoryMaster;
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
import fr.hoteia.qalingo.core.rest.util.JsonFactory;

@Service("jsonFactory")
public class JsonFactoryImpl implements JsonFactory {

    @Override
    public Customer buildCustomer(final CustomerPojo customerJsonPojo) {
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

        // customer.setAddresses(customerJsonPojo.getAddresses());
        // customer.setConnectionLogs(customerJsonPojo.getConnectionLogs());
        // customer.setCustomerMarketAreas(customerJsonPojo.getCustomerMarketAreas());
        // customer.setCustomerAttributes(customerJsonPojo.getCustomerAttributes());
        // customer.setCustomerGroups(customerJsonPojo.getCustomerGroups());

        customer.setDateCreate(customerJsonPojo.getDateCreate());
        customer.setDateUpdate(customerJsonPojo.getDateUpdate());

        return customer;
    }

    @Override
    public List<CustomerPojo> buildJsonCustomers(final List<Customer> customers) {
        final List<CustomerPojo> customerCustomerJsonPojos = new ArrayList<CustomerPojo>();
        if (customers != null) {
            for (Iterator<Customer> iterator = customers.iterator(); iterator.hasNext();) {
                Customer customer = iterator.next();
                CustomerPojo customerJsonPojo = buildJsonCustomer(customer);
                customerCustomerJsonPojos.add(customerJsonPojo);
            }
        }
        return customerCustomerJsonPojos;
    }

    @Override
    public CustomerPojo buildJsonCustomer(final Customer customer) {
        final CustomerPojo customerJsonPojo = new CustomerPojo();

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

        // customerJsonPojo.setAddresses(customer.getAddresses());
        // customerJsonPojo.setConnectionLogs(customer.getConnectionLogs());
        // customerJsonPojo.setCustomerMarketAreas(customer.getCustomerMarketAreas());
        // customerJsonPojo.setCustomerAttributes(customer.getCustomerAttributes());
        // customerJsonPojo.setCustomerGroups(customer.getCustomerGroups());

        customerJsonPojo.setDateCreate(customer.getDateCreate());
        customerJsonPojo.setDateUpdate(customer.getDateUpdate());

        return customerJsonPojo;
    }

    @Override
    public Store buildStore(final StoreJsonPojo storeJsonPojo) {
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
        store.setStateCode(storeJsonPojo.getStateCode());
        store.setCountryCode(storeJsonPojo.getCountryCode());

        // store.setStoreAttributes(storeJsonPojo.getStoreAttributes());

        store.setLongitude(storeJsonPojo.getLongitude());
        store.setLatitude(storeJsonPojo.getLatitude());

        store.setDateCreate(storeJsonPojo.getDateCreate());
        store.setDateUpdate(storeJsonPojo.getDateUpdate());

        return store;
    }

    @Override
    public List<StoreJsonPojo> buildJsonStores(final List<Store> stores) {
        final List<StoreJsonPojo> storeStoreJsonBeans = new ArrayList<StoreJsonPojo>();
        if (stores != null) {
            for (Iterator<Store> iterator = stores.iterator(); iterator.hasNext();) {
                Store store = iterator.next();
                StoreJsonPojo storeJsonBean = buildJsonStore(store);
                storeStoreJsonBeans.add(storeJsonBean);
            }
        }
        return storeStoreJsonBeans;
    }

    @Override
    public StoreJsonPojo buildJsonStore(final Store store) {
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
        storeJsonBean.setStateCode(store.getStateCode());
        storeJsonBean.setCountryCode(store.getCountryCode());

        // storeJsonBean.setStoreAttributes(store.getStoreAttributes());

        storeJsonBean.setLongitude(store.getLongitude());
        storeJsonBean.setLatitude(store.getLatitude());

        storeJsonBean.setDateCreate(store.getDateCreate());
        storeJsonBean.setDateUpdate(store.getDateUpdate());

        return storeJsonBean;
    }

    @Override
    public CatalogJsonPojo buildJsonCatalog(final CatalogVirtual catalogVirtual, final Long marketAreaId) {
        final CatalogJsonPojo catalogJsonPojo = new CatalogJsonPojo();
        catalogJsonPojo.setId(catalogVirtual.getId());
        catalogJsonPojo.setVersion(catalogVirtual.getVersion());
        catalogJsonPojo.setBusinessName(catalogVirtual.getBusinessName());
        catalogJsonPojo.setDescription(catalogVirtual.getDescription());
        catalogJsonPojo.setDefault(catalogVirtual.isDefault());
        catalogJsonPojo.setCode(catalogVirtual.getCode());
        catalogJsonPojo.setDateCreate(catalogVirtual.getDateCreate());
        catalogJsonPojo.setDateUpdate(catalogVirtual.getDateUpdate());

        final List<CatalogCategoryVirtual> productCategories = catalogVirtual.getProductCategories(marketAreaId);
        if (productCategories != null) {
            for (Iterator<CatalogCategoryVirtual> iterator = productCategories.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtual productCategory = iterator.next();
                ProductCategoryJsonPojo productCategoryJsonPojo = buildJsonProductCategory(productCategory, marketAreaId);
                catalogJsonPojo.getProductCategories().add(productCategoryJsonPojo);
            }
        }
        return catalogJsonPojo;
    }

    @Override
    public CatalogJsonPojo buildJsonCatalog(final CatalogMaster catalogMaster) {
        final CatalogJsonPojo catalogJsonPojo = new CatalogJsonPojo();
        catalogJsonPojo.setId(catalogMaster.getId());
        catalogJsonPojo.setVersion(catalogMaster.getVersion());
        catalogJsonPojo.setBusinessName(catalogMaster.getBusinessName());
        catalogJsonPojo.setDescription(catalogMaster.getDescription());
        catalogJsonPojo.setDefault(catalogMaster.isDefault());
        catalogJsonPojo.setCode(catalogMaster.getCode());
        catalogJsonPojo.setDateCreate(catalogMaster.getDateCreate());
        catalogJsonPojo.setDateUpdate(catalogMaster.getDateUpdate());

        final Set<CatalogCategoryMaster> productCategories = catalogMaster.getProductCategories();
        if (productCategories != null) {
            for (Iterator<CatalogCategoryMaster> iterator = productCategories.iterator(); iterator.hasNext();) {
                CatalogCategoryMaster productCategory = iterator.next();
                ProductCategoryJsonPojo productCategoryJsonPojo = buildJsonProductCategory(productCategory);
                catalogJsonPojo.getProductCategories().add(productCategoryJsonPojo);
            }
        }
        return catalogJsonPojo;
    }

    public List<ProductCategoryJsonPojo> buildJsonProductCategories(final List<CatalogCategoryMaster> productCategories) {
        final List<ProductCategoryJsonPojo> productCategoryJsonPojos = new ArrayList<ProductCategoryJsonPojo>();
        if (productCategories != null) {
            for (Iterator<CatalogCategoryMaster> iterator = productCategories.iterator(); iterator.hasNext();) {
                CatalogCategoryMaster productCategory = iterator.next();
                ProductCategoryJsonPojo productCategoryJsonPojo = buildJsonProductCategory(productCategory);
                productCategoryJsonPojos.add(productCategoryJsonPojo);
            }
        }
        return productCategoryJsonPojos;
    }

    public ProductCategoryJsonPojo buildJsonProductCategory(final CatalogCategoryMaster productCategory) {
        final ProductCategoryJsonPojo productCategoryJsonPojo = new ProductCategoryJsonPojo();

        productCategoryJsonPojo.setId(productCategory.getId());
        productCategoryJsonPojo.setVersion(productCategory.getVersion());
        productCategoryJsonPojo.setBusinessName(productCategory.getBusinessName());
        productCategoryJsonPojo.setDescription(productCategory.getDescription());
        productCategoryJsonPojo.setCode(productCategory.getCode());
        productCategoryJsonPojo.setDefault(productCategory.isDefault());
        productCategoryJsonPojo.setRoot(productCategory.isRoot());

        // productCategoryJsonPojo.setProductCategoryVirtualAttribute(productCategoryVirtualAttribute);
        // productCategoryJsonPojo.setProductCategoryMarketAreaAttributes(productCategoryMarketAreaAttributes);

        Set<CatalogCategoryMaster> productCategories = productCategory.getCatalogCategories();
        productCategoryJsonPojo.setProductCategories(buildJsonProductCategories(new ArrayList<CatalogCategoryMaster>(productCategories)));

        List<ProductMarketing> productMarketings = new ArrayList<ProductMarketing>(productCategory.getProductMarketings());
        productCategoryJsonPojo.setProductMarketings(buildJsonProductMarketings(productMarketings));

        // productCategoryJsonPojo.setProductMarketings(productMarketings);

        // productCategoryJsonPojo.setImages(images);

        productCategoryJsonPojo.setDateCreate(productCategory.getDateCreate());
        productCategoryJsonPojo.setDateUpdate(productCategory.getDateUpdate());

        return productCategoryJsonPojo;
    }

    @Override
    public List<ProductCategoryJsonPojo> buildJsonProductCategories(final List<CatalogCategoryVirtual> productCategories,
            final Long marketAreaId) {
        final List<ProductCategoryJsonPojo> productCategoryJsonPojos = new ArrayList<ProductCategoryJsonPojo>();
        if (productCategories != null) {
            for (Iterator<CatalogCategoryVirtual> iterator = productCategories.iterator(); iterator.hasNext();) {
                CatalogCategoryVirtual productCategory = iterator.next();
                ProductCategoryJsonPojo productCategoryJsonPojo = buildJsonProductCategory(productCategory, marketAreaId);
                productCategoryJsonPojos.add(productCategoryJsonPojo);
            }
        }
        return productCategoryJsonPojos;
    }

    @Override
    public ProductCategoryJsonPojo buildJsonProductCategory(final CatalogCategoryVirtual productCategory, final Long marketAreaId) {
        final ProductCategoryJsonPojo productCategoryJsonPojo = new ProductCategoryJsonPojo();

        productCategoryJsonPojo.setId(productCategory.getId());
        productCategoryJsonPojo.setVersion(productCategory.getVersion());
        productCategoryJsonPojo.setBusinessName(productCategory.getBusinessName());
        productCategoryJsonPojo.setDescription(productCategory.getDescription());
        productCategoryJsonPojo.setCode(productCategory.getCode());
        productCategoryJsonPojo.setDefault(productCategory.isDefault());
        productCategoryJsonPojo.setRoot(productCategory.isRoot());

        // productCategoryJsonPojo.setProductCategoryVirtualAttribute(productCategoryVirtualAttribute);
        // productCategoryJsonPojo.setProductCategoryMarketAreaAttributes(productCategoryMarketAreaAttributes);

        List<CatalogCategoryVirtual> productCategories = productCategory.getCatalogCategories(marketAreaId);
        productCategoryJsonPojo.setProductCategories(buildJsonProductCategories(productCategories, marketAreaId));

        List<ProductMarketing> productMarketings = new ArrayList<ProductMarketing>(productCategory.getProductMarketings());
        productCategoryJsonPojo.setProductMarketings(buildJsonProductMarketings(productMarketings));

        // productCategoryJsonPojo.setProductMarketings(productMarketings);

        // productCategoryJsonPojo.setImages(images);

        productCategoryJsonPojo.setDateCreate(productCategory.getDateCreate());
        productCategoryJsonPojo.setDateUpdate(productCategory.getDateUpdate());

        return productCategoryJsonPojo;
    }

    @Override
    public List<ProductMarketingJsonPojo> buildJsonProductMarketings(final List<ProductMarketing> productMarketings) {
        final List<ProductMarketingJsonPojo> productMarketingJsonPojos = new ArrayList<ProductMarketingJsonPojo>();
        if (productMarketings != null) {
            for (Iterator<ProductMarketing> iterator = productMarketings.iterator(); iterator.hasNext();) {
                ProductMarketing productMarketing = iterator.next();
                ProductMarketingJsonPojo productMarketingJsonPojo = buildJsonProductMarketing(productMarketing);
                productMarketingJsonPojos.add(productMarketingJsonPojo);
            }
        }
        return productMarketingJsonPojos;
    }

    @Override
    public ProductMarketingJsonPojo buildJsonProductMarketing(final ProductMarketing productMarketing) {
        final ProductMarketingJsonPojo productMarketingJsonPojo = new ProductMarketingJsonPojo();

        productMarketingJsonPojo.setId(productMarketing.getId());
        productMarketingJsonPojo.setVersion(productMarketing.getVersion());
        productMarketingJsonPojo.setBusinessName(productMarketing.getBusinessName());
        productMarketingJsonPojo.setDescription(productMarketing.getDescription());
        productMarketingJsonPojo.setDefault(productMarketing.isDefault());
        productMarketingJsonPojo.setCode(productMarketing.getCode());

        // productMarketingJsonPojo.setProductBrand(productBrand);
        // productMarketingJsonPojo.setProductMarketingGlobalAttributes(productMarketingGlobalAttributes);
        // productMarketingJsonPojo.setProductMarketingMarketAreaAttributes(productMarketingMarketAreaAttributes);

        List<ProductSku> productSkus = new ArrayList<ProductSku>(productMarketing.getProductSkus());
        productMarketingJsonPojo.setProductSkus(buildJsonProductSkus(productSkus));

        // productMarketingJsonPojo.setProductCrossLinks(productCrossLinks);
        // productMarketingJsonPojo.setImages(images);

        productMarketingJsonPojo.setDateCreate(productMarketing.getDateCreate());
        productMarketingJsonPojo.setDateUpdate(productMarketing.getDateUpdate());

        return productMarketingJsonPojo;
    }

    @Override
    public List<ProductSkuJsonPojo> buildJsonProductSkus(final List<ProductSku> productSkus) {
        final List<ProductSkuJsonPojo> productSkuJsonPojos = new ArrayList<ProductSkuJsonPojo>();
        if (productSkus != null) {
            for (Iterator<ProductSku> iterator = productSkus.iterator(); iterator.hasNext();) {
                ProductSku productSku = iterator.next();
                ProductSkuJsonPojo productSkuJsonPojo = buildJsonProductSku(productSku);
                productSkuJsonPojos.add(productSkuJsonPojo);
            }
        }
        return productSkuJsonPojos;
    }

    @Override
    public ProductSkuJsonPojo buildJsonProductSku(final ProductSku productSku) {
        final ProductSkuJsonPojo productSkuJsonPojo = new ProductSkuJsonPojo();

        productSkuJsonPojo.setId(productSku.getId());
        productSkuJsonPojo.setVersion(productSku.getVersion());
        productSkuJsonPojo.setBusinessName(productSku.getBusinessName());
        productSkuJsonPojo.setDescription(productSku.getDescription());
        productSkuJsonPojo.setDefault(productSku.isDefault());
        productSkuJsonPojo.setCode(productSku.getCode());

        // productSkuJsonPojo.setProductSkuGlobalAttributes(productSkuGlobalAttributes);
        // productSkuJsonPojo.setProductSkuMarketAreaAttributes(productSkuMarketAreaAttributes);
        // productSkuJsonPojo.setProductMarketing(productMarketing);
        // productSkuJsonPojo.setImages(images);
        // productSkuJsonPojo.setPrices(prices);
        // productSkuJsonPojo.setStocks(stocks);
        // productSkuJsonPojo.setRetailers(retailers);

        productSkuJsonPojo.setDateCreate(productSku.getDateCreate());
        productSkuJsonPojo.setDateUpdate(productSku.getDateUpdate());

        return null;
    }
}
