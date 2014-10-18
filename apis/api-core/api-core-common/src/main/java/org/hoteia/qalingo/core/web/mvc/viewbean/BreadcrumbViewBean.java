package org.hoteia.qalingo.core.web.mvc.viewbean;

import java.util.ArrayList;
import java.util.List;

public class BreadcrumbViewBean extends AbstractViewBean {

    /**
     * Generated UID
     */
    private static final long serialVersionUID = 4594828019275652815L;
    
    protected String name;
    private List<MenuViewBean> menus = new ArrayList<MenuViewBean>();
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }
    
    public List<MenuViewBean> getMenus() {
        return menus;
    }
    
    public void setMenus(List<MenuViewBean> menus) {
        this.menus = menus;
    }
    
}
