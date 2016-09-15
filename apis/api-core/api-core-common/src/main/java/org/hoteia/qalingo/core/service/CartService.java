/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p/>
 * Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 */
package org.hoteia.qalingo.core.service;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.Transient;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.dao.CartDao;
import org.hoteia.qalingo.core.dao.ProductDao;
import org.hoteia.qalingo.core.domain.Cart;
import org.hoteia.qalingo.core.domain.CartItem;
import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.domain.CurrencyReferential;
import org.hoteia.qalingo.core.domain.Customer;
import org.hoteia.qalingo.core.domain.DeliveryMethod;
import org.hoteia.qalingo.core.domain.MarketArea;
import org.hoteia.qalingo.core.domain.ProductMarketing;
import org.hoteia.qalingo.core.domain.ProductSku;
import org.hoteia.qalingo.core.domain.ProductSkuStorePrice;
import org.hoteia.qalingo.core.domain.Store;
import org.hoteia.qalingo.core.domain.Tax;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service("cartService")
@Transactional
public class CartService {

    @Autowired
    protected CartDao cartDao;

    @Autowired
    protected ProductDao productDao;

    @Autowired
    protected CatalogCategoryService catalogCategoryService;

    @Autowired
    protected ProductService productService;

    @Autowired
    protected DeliveryMethodService deliveryMethodService;

    @Autowired
    protected RetailerService retailerService;

    @Autowired
    protected TaxService taxService;

    public void addProductSkuToCart(Cart cart, final String virtualCatalogCode, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        addProductSkuToCart(cart, null, virtualCatalogCode, catalogCategoryCode, productSkuCode, quantity);
    }

    public Cart addProductSkuToCart(Cart cart, Store store, final String virtualCatalogCode, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        int finalQuantity = quantity;
        if (cart != null) {
            Set<CartItem> cartItems = cart.getCartItems();
            for (CartItem cartItem : cartItems) {
                if (cartItem.getProductSku().getCode().equalsIgnoreCase(productSkuCode) && cartItem.getStoreId().equals(store.getId())) {
                    finalQuantity = finalQuantity + cartItem.getQuantity();
                }
            }
        }
        cart = updateCartItem(cart, store, virtualCatalogCode, catalogCategoryCode, productSkuCode, finalQuantity);
        return cart;
    }

    public Cart updateCartItem(Cart cart, final String productSkuCode, final int quantity) throws Exception {
        return updateCartItem(cart, null, null, null, productSkuCode, quantity);
    }

    public Cart updateCartItem(Cart cart, Store store, final String virtualCatalogCode, final String catalogCategoryCode, final String productSkuCode, final int quantity) throws Exception {
        Set<CartItem> cartItems = cart.getCartItems();
        boolean productSkuIsNew = true;
        for (CartItem cartItem : cartItems) {
            if (cartItem.getProductSku().getCode().equalsIgnoreCase(productSkuCode) && Long.compare(store.getId(), cartItem.getStoreId()) == 0) {
                cartItem.setQuantity(quantity);
                productSkuIsNew = false;
            }
        }
        if (productSkuIsNew) {
            final ProductSku productSku = productService.getProductSkuByCode(productSkuCode);
            if (productSku != null) {
                CartItem cartItem = new CartItem();
                cartItem.setProductSku(productSku);

                final ProductMarketing reloadedProductMarketing = productService.getProductMarketingByCode(productSku.getProductMarketing().getCode());
                cartItem.setProductMarketing(reloadedProductMarketing);
                cartItem.setQuantity(quantity);
                if (store != null) {
                    cartItem.setStoreId(store.getId());
                }

                if (StringUtils.isNotEmpty(catalogCategoryCode)) {
                    final CatalogCategoryVirtual defaultVirtualCatalogCategory = catalogCategoryService.getVirtualCatalogCategoryByCode(catalogCategoryCode, virtualCatalogCode);
                    cartItem.setCatalogCategory(defaultVirtualCatalogCategory);
                } else {
                    final List<CatalogCategoryVirtual> catalogCategories = catalogCategoryService.findVirtualCategoriesByProductSkuId(productSku.getId());
                    final CatalogCategoryVirtual defaultVirtualCatalogCategory = productService.getDefaultVirtualCatalogCategory(reloadedProductMarketing, catalogCategories, true);
                    cartItem.setCatalogCategory(defaultVirtualCatalogCategory);
                }
                cart.getCartItems().add(cartItem);
            } else {
                // TODO : throw ??
            }
        }
        return saveOrUpdateCart(cart);
    }

    public Cart deleteCartItem(Cart cart, Store store, final String productSkuCode) throws Exception {
        if (cart != null && store != null && productSkuCode != null) {
            Set<CartItem> cartItems = new HashSet<CartItem>(cart.getCartItems());
            for (CartItem cartItem : cart.getCartItems()) {
                if (cartItem.getProductSku().getCode().equalsIgnoreCase(productSkuCode) && cartItem.getStoreId().equals(store.getId())) {
                    cartItems.remove(cartItem);
                }
            }
            cart.setCartItems(cartItems);
            return saveOrUpdateCart(cart);
        }
        return cart;
    }

    public Cart setShippingAddress(Cart cart, Customer customer, Long customerAddressId) throws Exception {
        if (customer.getAddress(customerAddressId) != null) {
            cart.setShippingAddressId(customerAddressId);
        }
        return saveOrUpdateCart(cart);
    }

    public Cart setBillingAddress(Cart cart, Customer customer, Long customerAddressId) throws Exception {
        if (customer.getAddress(customerAddressId) != null) {
            cart.setShippingAddressId(customerAddressId);
        }
        return saveOrUpdateCart(cart);
    }

    public Cart setDeliveryMethod(Cart cart, String deliveryMethodCode) throws Exception {
        if (!cart.getDeliveryMethods().isEmpty()) {
            cart.getDeliveryMethods().clear();
        }
        cart.getDeliveryMethods().add(deliveryMethodService.getDeliveryMethodByCode(deliveryMethodCode));
        
        // NO SAVE : Cart DeliveryMethods is @Transient
        return cart;
    }

    public Cart addDeliveryMethod(Cart cart, String deliveryMethodCode) throws Exception {
        cart.getDeliveryMethods().add(deliveryMethodService.getDeliveryMethodByCode(deliveryMethodCode));
        return saveOrUpdateCart(cart);
    }
    
    public Cart newCustomerCart(final MarketArea marketArea, Customer customer) {
        Cart cart = new Cart();
        cart.setMarketAreaId(marketArea.getId());
        cart.setCustomerId(customer.getId());
        saveOrUpdateCart(cart);
        return cart;
    }

    public Cart newGuestCart(final MarketArea marketArea) {
        Cart cart = new Cart();
        cart.setMarketAreaId(marketArea.getId());
        saveOrUpdateCart(cart);
        return cart;
    }

    public Cart getCartById(final Long cartId, Object... params) {
        return cartDao.getCartById(cartId, params);
    }

    public Cart getCartByMarketAreaIdAndCustomerId(final Long marketAreaId, final Long customerId, Object... params) {
        return cartDao.getCartByMarketAreaIdAndCustomerId(marketAreaId, customerId, params);
    }

    public Cart getCartById(final String rawCartId, Object... params) {
        long cartId = -1;
        try {
            cartId = Long.parseLong(rawCartId);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(e);
        }
        return getCartById(cartId, params);
    }

    public Cart saveOrUpdateCart(Cart cart) {
        return cartDao.saveOrUpdateCart(cart);
    }

    public void deleteCart(Cart cart) {
        cartDao.deleteCart(cart);
    }

    public int deleteCart(final Timestamp before) {
        return cartDao.deleteCart(before);
    }

    public String getCartTotalWithStandardCurrencySign(final Cart cart) {
        return cart.getCurrency().formatPriceWithStandardCurrencySign(getCartTotal(cart));
    }

    public BigDecimal getCartTotal(final Cart cart) {
        BigDecimal total = new BigDecimal(0);
        Long marketAreaId = cart.getMarketAreaId();
        for (CartItem cartItem : cart.getCartItems()) {
            total = total.add(getCartItemTotalPriceWithTaxes(cartItem, marketAreaId, cart.getTaxes()));
        }
        return total;
    }

    public String getDeliveryMethodTotalWithStandardCurrencySign(final Cart cart) {
        return cart.getCurrency().formatPriceWithStandardCurrencySign(getDeliveryMethodTotal(cart));
    }

    public BigDecimal getDeliveryMethodTotal(final Cart cart) {
        final Set<DeliveryMethod> deliveryMethods = cart.getDeliveryMethods();
        BigDecimal cartDeliveryMethodTotal = new BigDecimal("0");
        CurrencyReferential currency = cart.getCurrency();
        if (deliveryMethods != null && Hibernate.isInitialized(deliveryMethods)) {
            for (final DeliveryMethod deliveryMethod : deliveryMethods) {
                if (deliveryMethod != null) {
                    BigDecimal price = deliveryMethod.getPrice(currency.getId());
                    if (price != null) {
                        cartDeliveryMethodTotal = cartDeliveryMethodTotal.add(price);
                    }
                }
            }
        }
        return cartDeliveryMethodTotal;
    }

    public String getTaxTotalWithStandardCurrencySign(final Cart cart) {
        return cart.getCurrency().formatPriceWithStandardCurrencySign(getTaxTotal(cart));
    }

    public String getCartItemTotalWithStandardCurrencySign(final Cart cart) {
        return cart.getCurrency().formatPriceWithStandardCurrencySign(getCartItemTotal(cart));
    }

    public BigDecimal getCartItemTotal(final Cart cart) {
        BigDecimal cartItemsTotal = new BigDecimal(0);
        Long marketAreaId = cart.getMarketAreaId();
        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems != null && Hibernate.isInitialized(cartItems)) {
            for (final CartItem cartItem : cartItems) {
                cartItemsTotal = cartItemsTotal.add(getCartItemTotalPrice(cartItem, marketAreaId, cart.getTaxes()));
            }
        }
        return cartItemsTotal;
    }

    public String getCartItemTotalWithTaxesWithStandardCurrencySign(final Cart cart) {
        return cart.getCurrency().formatPriceWithStandardCurrencySign(getCartItemTotalWithTaxes(cart));
    }

    public BigDecimal getCartItemTotalWithTaxes(final Cart cart) {
        BigDecimal cartItemsTotal = new BigDecimal(0);
        Long marketAreaId = cart.getMarketAreaId();
        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems != null && Hibernate.isInitialized(cartItems)) {
            for (final CartItem cartItem : cartItems) {
                cartItemsTotal = cartItemsTotal.add(getCartItemTotalPriceWithTaxes(cartItem, marketAreaId, cart.getTaxes()));
            }
        }
        return cartItemsTotal;
    }

    public String getCartItemPriceWithStandardCurrencySign(final CartItem cartItem, final Long marketAreaId, final Set<Tax> taxes) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return productSkuStorePrice.getCurrency().formatPriceWithStandardCurrencySign(getCartItemPrice(productSkuStorePrice, taxes));
            }
        }
        return null;
    }

    public BigDecimal getCartItemPrice(final CartItem cartItem, final Long marketAreaId, final Set<Tax> taxes) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return getCartItemPrice(productSkuStorePrice, taxes);
            }
        }
        return null;
    }

    public BigDecimal getCartItemPrice(final ProductSkuStorePrice productSkuStorePrice, final Set<Tax> taxes) {
        BigDecimal totalAmount = productSkuStorePrice.getSalePrice();
        if (productSkuStorePrice.isVATIncluded()) {
            if (taxes != null && taxes.size() > 0) {
                for (Tax tax : taxes) {
                    BigDecimal taxAmount = tax.getPercent().divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                    totalAmount = totalAmount.divide(taxAmount, 5, BigDecimal.ROUND_CEILING).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                }
            }
        }
        return totalAmount;
    }

    public String getCartItemPriceWithTaxesWithStandardCurrencySign(final CartItem cartItem, final Long marketAreaId, final Set<Tax> taxes) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return productSkuStorePrice.getCurrency().formatPriceWithStandardCurrencySign(getCartItemPriceWithTaxes(productSkuStorePrice, taxes));
            }
        }
        return null;
    }

    public BigDecimal getCartItemPriceWithTaxes(final CartItem cartItem, final Long marketAreaId, final Set<Tax> taxes) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return getCartItemPriceWithTaxes(productSkuStorePrice, taxes);
            }
        }
        return null;
    }

    public BigDecimal getCartItemPriceWithTaxes(final ProductSkuStorePrice productSkuStorePrice, final Set<Tax> taxes) {
        BigDecimal totalAmount = productSkuStorePrice.getSalePrice();
        if (!productSkuStorePrice.isVATIncluded()) {
            if (taxes != null && taxes.size() > 0) {
                for (Tax tax : taxes) {
                    BigDecimal taxAmount = totalAmount.multiply(tax.getPercent());
                    totalAmount = totalAmount.add(taxAmount.divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                }
            }
        }
        return totalAmount;
    }


    public String getCartItemTotalPriceWithStandardCurrencySign(final CartItem cartItem, final Long marketAreaId, final Set<Tax> taxes) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                BigDecimal result = getCartItemPrice(productSkuStorePrice, taxes).multiply(new BigDecimal(cartItem.getQuantity()));
                return productSkuStorePrice.getCurrency().formatPriceWithStandardCurrencySign(result);
            }
        }
        return null;
    }

    public BigDecimal getCartItemTotalPrice(final CartItem cartItem, final Long marketAreaId, final Set<Tax> taxes) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return getCartItemPrice(productSkuStorePrice, taxes).multiply(new BigDecimal(cartItem.getQuantity()));
            }
        }
        return null;
    }

    public String getCartItemTotalPriceWithTaxesWithStandardCurrencySign(final CartItem cartItem, final Long marketAreaId, final Set<Tax> taxes) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                BigDecimal result = getCartItemPriceWithTaxes(productSkuStorePrice, taxes).multiply(new BigDecimal(cartItem.getQuantity()));
                return productSkuStorePrice.getCurrency().formatPriceWithStandardCurrencySign(result);
            }
        }
        return null;
    }

    public BigDecimal getCartItemTotalPriceWithTaxes(final CartItem cartItem, final Long marketAreaId, final Set<Tax> taxes) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return getCartItemPriceWithTaxes(productSkuStorePrice, taxes).multiply(new BigDecimal(cartItem.getQuantity()));
            }
        }
        return null;
    }

    public Boolean isCartItemVATIncluded(final CartItem cartItem, final Long marketAreaId) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return productSkuStorePrice.isVATIncluded();
            }
        }
        return null;
    }

    public BigDecimal getCartItemTaxesAmount(final CartItem cartItem, final Long marketAreaId, final Set<Tax> taxes) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return getCartItemTaxesAmount(productSkuStorePrice, cartItem.getQuantity(), taxes);
            }
        }
        return null;
    }

    public BigDecimal getTaxTotal(final Cart cart) {
        BigDecimal total = new BigDecimal(0);
        Long marketAreaId = cart.getMarketAreaId();
        Set<Tax> taxes = cart.getTaxes();
        if (taxes == null || taxes.size() == 0) {
            return total;
        }
        for (final CartItem cartItem : cart.getCartItems()) {
            List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
            for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
                if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                    BigDecimal cartItemTaxesAmount = getCartItemTaxesAmount(productSkuStorePrice, cartItem.getQuantity(), taxes);
                    total = total.add(cartItemTaxesAmount);
                    break;
                }
            }
        }
        return total;
    }

    public static BigDecimal getCartItemTaxesAmount(final ProductSkuStorePrice productSkuStorePrice, int quantity, final Set<Tax> taxes) {
        BigDecimal salePrice = productSkuStorePrice.getSalePrice();
        BigDecimal totalAmount = new BigDecimal(0);
        if (taxes == null || taxes.size() == 0) {
            return totalAmount;
        }
        boolean vatIncluded = productSkuStorePrice.isVATIncluded();
        for (Tax tax : taxes) {
            if (vatIncluded) {
                BigDecimal taxAmount = tax.getPercent().divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                taxAmount = salePrice.subtract(salePrice.divide(taxAmount, 5, BigDecimal.ROUND_CEILING)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                totalAmount = totalAmount.add(taxAmount.multiply(new BigDecimal(quantity)));
            } else {
                BigDecimal taxAmount = salePrice.multiply(tax.getPercent());
                taxAmount = salePrice.add(taxAmount.divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                totalAmount = totalAmount.add(taxAmount.multiply(new BigDecimal(quantity)));
            }
        }
        return totalAmount;
    }
}