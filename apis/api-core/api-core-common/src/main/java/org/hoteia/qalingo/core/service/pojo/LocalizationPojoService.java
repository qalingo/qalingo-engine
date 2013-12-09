package org.hoteia.qalingo.core.service.pojo;

import java.util.List;

import org.hoteia.qalingo.core.pojo.LocalizationPojo;

public interface LocalizationPojoService {

    LocalizationPojo getLocalizationById(String localizationId);

    LocalizationPojo getLocalizationByCode(String localizationCode);

    List<LocalizationPojo> findAllLocalizations();
    
    List<LocalizationPojo> findLocalizationsByMarketAreaCode(String marketAreaCode);

}