package fr.hoteia.qalingo.core.service.pojo;

import java.util.List;

import fr.hoteia.qalingo.core.domain.Retailer;
import fr.hoteia.qalingo.core.pojo.retailer.RetailerPojo;

public interface RetailerPojoService {

    List<RetailerPojo> findAllRetailers();

    RetailerPojo getRetailerById(String retailerId);
}
