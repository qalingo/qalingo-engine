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
import java.math.RoundingMode;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.hibernate.Hibernate;
import org.hoteia.qalingo.core.dao.CartDao;
import org.hoteia.qalingo.core.dao.ProductDao;
import org.hoteia.qalingo.core.domain.*;
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


    public String getCartTotalWithStandardCurrencySign(final Cart cart) {
        return cart.getCurrency().formatPriceWithStandardCurrencySign(getCartTotal(cart));
    }

    public BigDecimal getCartTotal(final Cart cart) {
        BigDecimal total = new BigDecimal(0);
        Long marketAreaId = cart.getMarketAreaId();
        for (CartItem cartItem : cart.getCartItems()) {
            total = total.add(getCartItemPriceWithTaxes(cartItem, marketAreaId));
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

    public BigDecimal getTaxTotal(final Cart cart) {
        BigDecimal total = new BigDecimal(0);
        Long marketAreaId = cart.getMarketAreaId();
        Tax tax = getTax(marketAreaId);
        if(tax == null) {
            return total;
        }
        for (final CartItem cartItem : cart.getCartItems()) {
            List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
            for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
                if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                    if (productSkuStorePrice.isVATIncluded()) {
                        BigDecimal itemTax = productSkuStorePrice.getSalePrice().multiply(tax.getPercent().divide(tax.getPercent().add(new BigDecimal(100)), 5, BigDecimal.ROUND_HALF_EVEN)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        total = total.add(itemTax).multiply(new BigDecimal(cartItem.getQuantity()));
                    } else {
                        BigDecimal itemTax = productSkuStorePrice.getSalePrice().multiply(tax.getPercent()).divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                        total = total.add(itemTax).multiply(new BigDecimal(cartItem.getQuantity()));;
                    }

                    break;
                }
            }
        }
        return total;
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
                cartItemsTotal = cartItemsTotal.add(getCartItemPrice(cartItem, marketAreaId));
            }
        }
        return cartItemsTotal;
    }

    public String getCartItemTotalWithTaxesWithStandardCurrencySign(final Cart cart) {
        return cart.getCurrency().formatPriceWithStandardCurrencySign(getCartItemTotalWithTaxes(cart));
    }

    public BigDecimal getCartItemTotalWithTaxes(final Cart cart) {
        BigDecimal cartItemsTotal = new BigDecimal("0");
        Long marketAreaId = cart.getMarketAreaId();
        Set<CartItem> cartItems = cart.getCartItems();
        if (cartItems != null && Hibernate.isInitialized(cartItems)) {
            for (final CartItem cartItem : cartItems) {
                cartItemsTotal = cartItemsTotal.add(getCartItemPriceWithTaxes(cartItem, marketAreaId));
            }
        }
        return cartItemsTotal;
    }

    public String getCartItemPriceWithStandardCurrencySign(final CartItem cartItem, final Long marketAreaId) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return productSkuStorePrice.getCurrency().formatPriceWithStandardCurrencySign(getCartItemPrice(productSkuStorePrice, cartItem, marketAreaId));
            }
        }
        return null;
    }

    public BigDecimal getCartItemPrice(final CartItem cartItem, final Long marketAreaId) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return getCartItemPrice(productSkuStorePrice, cartItem, marketAreaId);
            }
        }
        return null;
    }

    public BigDecimal getCartItemPrice(final ProductSkuStorePrice productSkuStorePrice, final CartItem cartItem, final Long marketAreaId) {
        BigDecimal totalAmount = productSkuStorePrice.getSalePrice();
        totalAmount = totalAmount.multiply(new BigDecimal(cartItem.getQuantity()));
        if (productSkuStorePrice.isVATIncluded()) {
            Tax taxes = getTax(marketAreaId);
            if (taxes != null) {
                BigDecimal taxAmount = taxes.getPercent().divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN).add(new BigDecimal(1)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
                totalAmount = totalAmount.divide(taxAmount, BigDecimal.ROUND_CEILING);
            }
        }
        return totalAmount;
    }

    public String getCartItemPriceWithTaxesWithStandardCurrencySign(final CartItem cartItem, final Long marketAreaId) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return productSkuStorePrice.getCurrency().formatPriceWithStandardCurrencySign(getCartItemPriceWithTaxes(productSkuStorePrice, cartItem, marketAreaId));
            }
        }
        return null;
    }

    public BigDecimal getCartItemPriceWithTaxes(final CartItem cartItem, final Long marketAreaId) {
        List<ProductSkuStorePrice> productSkuStorePrices = productDao.findProductSkuStorePrices(cartItem.getStoreId(), cartItem.getProductSku().getId());
        for (ProductSkuStorePrice productSkuStorePrice : productSkuStorePrices) {
            if (productSkuStorePrice.getMarketAreaId().equals(marketAreaId)) {
                return getCartItemPriceWithTaxes(productSkuStorePrice, cartItem, marketAreaId);
            }
        }
        return null;
    }

    public BigDecimal getCartItemPriceWithTaxes(final ProductSkuStorePrice productSkuStorePrice, final CartItem cartItem, final Long marketAreaId) {
        BigDecimal totalAmount = productSkuStorePrice.getSalePrice();
        totalAmount = totalAmount.multiply(new BigDecimal(cartItem.getQuantity()));
        if (!productSkuStorePrice.isVATIncluded()) {
            Tax taxes = getTax(marketAreaId);
            if (taxes != null) {
                BigDecimal taxAmount = totalAmount.multiply(taxes.getPercent());
                totalAmount = totalAmount.add(taxAmount.divide(new BigDecimal(100), 5, BigDecimal.ROUND_HALF_EVEN)).setScale(2, BigDecimal.ROUND_HALF_EVEN);
            }
        }
        return totalAmount;
    }

    public List<ProductSkuStorePrice> findProductSkuStorePrices(final Long storeId, final Long productSkuCode) {
        return productDao.findProductSkuStorePrices(storeId, productSkuCode);
    }

    public Tax getTax(Long marketAreaId) {
        List<Tax> taxes = taxService.findTaxesByMarketAreaId(marketAreaId);
        for (Tax tax : taxes) {
            TaxType taxType = tax.getTaxType();
            if (taxType != null && "OPTIC".equals(taxType.getCode())) {
                return tax;
            }
        }
        return null;
    }

}