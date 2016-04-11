/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.fetchplan;

import java.util.ArrayList;
import java.util.List;
import org.hoteia.qalingo.core.domain.MarketArea_;
import org.hoteia.qalingo.core.domain.CmsMenu_;
import org.hoteia.qalingo.core.domain.CmsContent_;
import org.hoteia.qalingo.core.domain.CmsContentBlock_;
import org.hoteia.qalingo.core.domain.CmsContentAsset_;

public class FetchPlanGraphCmsContent {

    public static FetchPlan defaultCmsContentFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(CmsContent_.assets.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.attributes.getName()));

        fetchplans.add(new SpecificFetchMode(CmsContent_.user.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.marketArea.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.marketArea.getName() + "." + MarketArea_.market.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.masterCmsContent.getName()));

        fetchplans.add(new SpecificFetchMode(CmsContent_.blocks.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.blocks.getName() + "." + CmsContentBlock_.link.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.blocks.getName() + "." + CmsContentBlock_.assets.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.blocks.getName() + "." + CmsContentBlock_.attributes.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.blocks.getName() + "." + CmsContentBlock_.blocks.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.blocks.getName() + "." + CmsContentBlock_.blocks.getName() + "." + CmsContentBlock_.link.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.blocks.getName() + "." + CmsContentBlock_.blocks.getName() + "." + CmsContentBlock_.assets.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContent_.blocks.getName() + "." + CmsContentBlock_.blocks.getName() + "." + CmsContentBlock_.attributes.getName()));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultCmsContentBlockFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(CmsContentBlock_.link.getName()));
        fetchplans.add(new SpecificFetchMode(CmsContentBlock_.assets.getName()));
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultCmsContentAssetFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        
        return new FetchPlan(fetchplans);
    }
    
    public static FetchPlan defaultCmsMenuFetchPlan(){
        List<SpecificFetchMode> fetchplans = new ArrayList<SpecificFetchMode>();
        
        fetchplans.add(new SpecificFetchMode(CmsMenu_.marketArea.getName()));
        fetchplans.add(new SpecificFetchMode(CmsMenu_.menu.getName()));
        fetchplans.add(new SpecificFetchMode(CmsMenu_.subMenus.getName()));
        fetchplans.add(new SpecificFetchMode(CmsMenu_.link.getName()));
        fetchplans.add(new SpecificFetchMode(CmsMenu_.blocks.getName()));

        fetchplans.add(new SpecificFetchMode(CmsMenu_.blocks.getName() + "." + CmsContentBlock_.assets.getName()));

        return new FetchPlan(fetchplans);
    }
    
}