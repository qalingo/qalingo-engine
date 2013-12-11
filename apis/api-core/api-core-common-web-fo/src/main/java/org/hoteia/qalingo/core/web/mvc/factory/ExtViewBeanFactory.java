package org.hoteia.qalingo.core.web.mvc.factory;

import java.util.List;

import org.hoteia.qalingo.core.domain.CatalogCategoryVirtual;
import org.hoteia.qalingo.core.pojo.RequestData;
import org.hoteia.qalingo.core.web.mvc.viewbean.CategoryViewBean;

public interface ExtViewBeanFactory extends ViewBeanFactory {
	
	List<CategoryViewBean> buildVirtualCategoryViewBeans(RequestData requestData, List<CatalogCategoryVirtual> categories, boolean fullPopulate) throws Exception;
	
	CategoryViewBean buildVirtualCategoryViewBean(RequestData requestData, CatalogCategoryVirtual categories, boolean fullPopulate) throws Exception;
}
