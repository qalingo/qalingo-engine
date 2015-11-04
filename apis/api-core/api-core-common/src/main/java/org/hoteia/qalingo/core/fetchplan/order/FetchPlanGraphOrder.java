/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.fetchplan.order;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

import org.hoteia.qalingo.core.domain.OrderPurchase_;
import org.hoteia.qalingo.core.domain.OrderItem_;
import org.hoteia.qalingo.core.domain.OrderShipment_;
import org.hoteia.qalingo.core.domain.ProductSku_;

public class FetchPlanGraphOrder {

    public static FetchPlan defaultOrderPurchaseFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(OrderPurchase_.billingAddress.getName()));
        fetchplans.add(new SpecificFetchMode(OrderPurchase_.shippingAddress.getName()));
        fetchplans.add(new SpecificFetchMode(OrderPurchase_.payments.getName()));
        fetchplans.add(new SpecificFetchMode(OrderPurchase_.shipments.getName()));
        fetchplans.add(new SpecificFetchMode(OrderPurchase_.currency.getName()));

        fetchplans.add(new SpecificFetchMode(OrderPurchase_.shipments.getName() + "." + OrderShipment_.orderItems.getName()));
        
        fetchplans.add(new SpecificFetchMode(OrderPurchase_.shipments.getName() + "." + OrderShipment_.orderItems.getName() + "." + OrderItem_.productSku.getName()));
        fetchplans.add(new SpecificFetchMode(OrderPurchase_.shipments.getName() + "." + OrderShipment_.orderItems.getName() + "." + OrderItem_.productSku.getName() + "." + ProductSku_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(OrderPurchase_.shipments.getName() + "." + OrderShipment_.orderItems.getName() + "." + OrderItem_.productSku.getName() + "." + ProductSku_.assets.getName()));
        
        fetchplans.add(new SpecificFetchMode(OrderPurchase_.shipments.getName() + "." + OrderShipment_.orderItems.getName() + "." + OrderItem_.taxes.getName()));
        fetchplans.add(new SpecificFetchMode(OrderPurchase_.shipments.getName() + "." + OrderShipment_.orderItems.getName() + "." + OrderItem_.currency.getName()));

        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultOrderItemFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(OrderItem_.taxes.getName()));
        return new FetchPlan(fetchplans);
    }
    
}