package fr.hoteia.qalingo.core.pojo.util.mapper;

import fr.hoteia.qalingo.core.domain.CurrencyReferential;
import fr.hoteia.qalingo.core.pojo.CurrencyReferentialPojo;
import org.springframework.stereotype.Component;

@Component("currencyReferentialMapper")
public class CurrencyReferentialMapper extends AbstractPojoMapper<CurrencyReferential, CurrencyReferentialPojo> {
    @Override
    public Class<CurrencyReferential> getObjectType() {
        return CurrencyReferential.class;
    }

    @Override
    public Class<CurrencyReferentialPojo> getPojoType() {
        return CurrencyReferentialPojo.class;
    }
}
