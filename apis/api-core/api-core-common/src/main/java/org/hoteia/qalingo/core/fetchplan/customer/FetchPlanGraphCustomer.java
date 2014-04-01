package org.hoteia.qalingo.core.fetchplan.customer;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphCustomer {

    public static FetchPlan defaultCustomerFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();

        fetchplans.add(new SpecificFetchMode("credentials"));
        fetchplans.add(new SpecificFetchMode("addresses"));
        fetchplans.add(new SpecificFetchMode("connectionLogs"));

        fetchplans.add(new SpecificFetchMode("customerMarketAreas"));

        fetchplans.add(new SpecificFetchMode("optins", new SpecificAlias("customerMarketAreas.optins")));
        
        fetchplans.add(new SpecificFetchMode("wishlistProducts", new SpecificAlias("customerMarketAreas.wishlistProducts")));
        fetchplans.add(new SpecificFetchMode("productComments", new SpecificAlias("customerMarketAreas.productComments")));

        fetchplans.add(new SpecificFetchMode("attributes"));
        fetchplans.add(new SpecificFetchMode("groups"));
        fetchplans.add(new SpecificFetchMode("oauthAccesses"));
        fetchplans.add(new SpecificFetchMode("customerOrderAudit"));

        return new FetchPlan(fetchplans);
    }

    public static FetchPlan defaultCustomerGroupFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("roles"));
        return new FetchPlan(fetchplans);
    }
    
}