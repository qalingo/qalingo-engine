package org.hoteia.qalingo.core.fetchplan.common;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;

public class FetchPlanGraphDeliveryMethod {

    public static FetchPlan defaultDeliveryMethodFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("countries"));
        fetchplans.add(new SpecificFetchMode("prices"));
        fetchplans.add(new SpecificFetchMode("currency", new SpecificAlias("prices.currency")));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan fullDeliveryMethodFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("countries"));
        fetchplans.add(new SpecificFetchMode("prices"));
        fetchplans.add(new SpecificFetchMode("currency", new SpecificAlias("prices.currency")));
        fetchplans.add(new SpecificFetchMode("warehouses"));
        return new FetchPlan(fetchplans);
    }
    
}