/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.fetchplan.user;

import java.util.ArrayList;
import java.util.List;

import org.hoteia.qalingo.core.fetchplan.FetchPlan;
import org.hoteia.qalingo.core.fetchplan.SpecificAlias;
import org.hoteia.qalingo.core.fetchplan.SpecificFetchMode;
import org.hoteia.qalingo.core.domain.UserGroup_;

public class FetchPlanGraphUser {

    public static FetchPlan defaultCompanyFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("localizations"));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultUserFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode("defaultLocalization"));
        fetchplans.add(new SpecificFetchMode("company"));
        fetchplans.add(new SpecificFetchMode("groups"));
        fetchplans.add(new SpecificFetchMode("credentials"));
        fetchplans.add(new SpecificFetchMode("roles", new SpecificAlias("groups.roles")));
        fetchplans.add(new SpecificFetchMode("permissions", new SpecificAlias("groups.roles.permissions")));
        fetchplans.add(new SpecificFetchMode("connectionLogs"));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultUserGroupFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(UserGroup_.roles.getName()));
        return new FetchPlan(fetchplans);
    }
    
}