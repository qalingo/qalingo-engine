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
import org.hoteia.qalingo.core.domain.User_;
import org.hoteia.qalingo.core.domain.UserGroup_;
import org.hoteia.qalingo.core.domain.UserRole_;
import org.hoteia.qalingo.core.domain.Company_;

public class FetchPlanGraphUser {

    public static FetchPlan defaultCompanyFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(Company_.localizations.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultUserFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(User_.defaultLocalization.getName()));
        fetchplans.add(new SpecificFetchMode(User_.company.getName()));
        fetchplans.add(new SpecificFetchMode(User_.groups.getName()));
        fetchplans.add(new SpecificFetchMode(User_.credentials.getName()));
        fetchplans.add(new SpecificFetchMode(UserGroup_.roles.getName(), new SpecificAlias(User_.groups + "." + UserGroup_.roles.getName())));
        fetchplans.add(new SpecificFetchMode(UserRole_.permissions.getName(), new SpecificAlias(User_.groups + "." + UserGroup_.roles + "." + UserRole_.permissions)));
        fetchplans.add(new SpecificFetchMode(User_.connectionLogs.getName()));
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultUserGroupFetchPlan() {
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        fetchplans.add(new SpecificFetchMode(UserGroup_.roles.getName()));
        return new FetchPlan(fetchplans);
    }
    
}