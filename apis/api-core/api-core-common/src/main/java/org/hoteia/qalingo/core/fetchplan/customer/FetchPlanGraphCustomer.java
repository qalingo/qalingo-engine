/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.fetchplan.customer;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.domain.CustomerGroup_;
import org.hoteia.qalingo.core.domain.CustomerMarketArea_;
import org.hoteia.qalingo.core.domain.CustomerRole_;
import org.hoteia.qalingo.core.domain.Customer_;
import org.hoteia.qalingo.core.domain.UserGroup_;
import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphCustomer {

    public static FetchPlan fullCustomerFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();

        fetchplans.add(new SpecificFetchMode(Customer_.credentials.getName()));
        fetchplans.add(new SpecificFetchMode(Customer_.addresses.getName()));
        fetchplans.add(new SpecificFetchMode(Customer_.connectionLogs.getName()));

        fetchplans.add(new SpecificFetchMode(Customer_.customerMarketAreas.getName()));

        fetchplans.add(new SpecificFetchMode(Customer_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(Customer_.oauthAccesses.getName()));
        fetchplans.add(new SpecificFetchMode(Customer_.customerOrderAudit.getName()));
        fetchplans.add(new SpecificFetchMode(Customer_.paymentInformations.getName()));

        fetchplans.add(new SpecificFetchMode(CustomerMarketArea_.productComments.getName(), new SpecificAlias(Customer_.customerMarketAreas.getName() + "." + CustomerMarketArea_.productComments.getName())));
        fetchplans.add(new SpecificFetchMode(CustomerMarketArea_.wishlistProducts.getName(), new SpecificAlias(Customer_.customerMarketAreas.getName() + "." + CustomerMarketArea_.wishlistProducts.getName())));
        fetchplans.add(new SpecificFetchMode(CustomerMarketArea_.optins.getName(), new SpecificAlias(Customer_.customerMarketAreas.getName() + "." + CustomerMarketArea_.optins.getName())));

        fetchplans.add(new SpecificFetchMode(Customer_.groups.getName()));
        fetchplans.add(new SpecificFetchMode(CustomerGroup_.roles.getName(), new SpecificAlias(Customer_.groups.getName() + "." + CustomerGroup_.roles.getName())));
        fetchplans.add(new SpecificFetchMode(CustomerRole_.permissions.getName(), new SpecificAlias(CustomerGroup_.roles.getName() + "." + CustomerRole_.permissions.getName())));

        return new FetchPlan(fetchplans);
    }

    public static FetchPlan defaultCustomerGroupFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(CustomerGroup_.roles.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultUserGroupFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(UserGroup_.roles.getName()));
        return new FetchPlan(fetchplans);
    }
    
}