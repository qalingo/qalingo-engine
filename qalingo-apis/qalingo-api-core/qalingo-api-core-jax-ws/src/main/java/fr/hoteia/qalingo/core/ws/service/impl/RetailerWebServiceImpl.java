package fr.hoteia.qalingo.core.ws.service.impl;

import javax.jws.WebService;
import java.util.List;

import fr.hoteia.qalingo.core.pojo.catalog.CatalogMasterPojo;
import fr.hoteia.qalingo.core.pojo.retailer.RetailerPojo;
import fr.hoteia.qalingo.core.service.pojo.RetailerPojoService;
import fr.hoteia.qalingo.core.ws.service.RetailerWebService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("retailerWebService")
@WebService(endpointInterface="fr.hoteia.qalingo.core.ws.service.RetailerWebService")
public class RetailerWebServiceImpl implements RetailerWebService {

    @Autowired private RetailerPojoService retailerService;

    @Override
    public List<RetailerPojo> findAllRetailers() {
        return retailerService.findAllRetailers();
    }

    @Override
    public RetailerPojo getRetailerById(String retailerId) {
        return retailerService.getRetailerById(retailerId);
    }
}
