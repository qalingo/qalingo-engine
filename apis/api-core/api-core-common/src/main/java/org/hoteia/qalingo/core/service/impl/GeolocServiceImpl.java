/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.7.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2013
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service.impl;

import java.io.File;
import java.net.InetAddress;

import javax.servlet.http.HttpServletRequest;

import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.service.EmailService;
import org.hoteia.qalingo.core.service.EngineSettingService;
import org.hoteia.qalingo.core.service.GeolocService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.JsonNode;
import com.maxmind.db.Reader;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;

@Service("geolocService")
@Transactional
public class GeolocServiceImpl implements GeolocService {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());
    
    @Autowired
    protected EmailService emailService;
    
    @Autowired
    protected EngineSettingService engineSettingService;
    
    /**
     * 
     */
    public String geolocAndGetCountryIsoCode(final String customerRemoteAddr) throws Exception {
        final Country country = geolocAndGetCountry(customerRemoteAddr);
        return country.getIsoCode();
    }
    
    /**
     * 
     */
    public Country geolocAndGetCountry(final String customerRemoteAddr) throws Exception {
        try {
            final Reader reader = new Reader(getCountryDataBase());
            final InetAddress address = InetAddress.getByName(customerRemoteAddr);
            final JsonNode jsonNode = reader.get(address);
            if(jsonNode != null){
                System.out.println("jsonNode!!!!!!!!!!: " + jsonNode.toString());
            }
            reader.close();
            
            final DatabaseReader databaseReader = new DatabaseReader.Builder(getCountryDataBase()).build();
            final CountryResponse countryResponse = databaseReader.country(address);
            if(countryResponse != null){
                System.out.println("Country !!!! " + countryResponse.getCountry().getIsoCode());
                return countryResponse.getCountry();
            }
        } catch (AddressNotFoundException e) {
            logger.warn("Geoloc can't find this address:" + customerRemoteAddr);
        } catch (Exception e) {
            logger.error("Geoloc can't find this address:" + customerRemoteAddr);
        }
        return null;
    }
    
    /**
     * 
     */
    public String geolocAndGetCityName(final String customerRemoteAddr) throws Exception {
        final City city = geolocAndGetCity(customerRemoteAddr);
        return city.getName();
    }
    
    /**
     * 
     */
    public City geolocAndGetCity(final String customerRemoteAddr) throws Exception {
        try {
            final Reader reader = new Reader(getCityDataBase());
            final InetAddress address = InetAddress.getByName(customerRemoteAddr);
            final JsonNode jsonNode = reader.get(address);
            if(jsonNode != null){
                System.out.println("jsonNode!!!!!!!!!!: " + jsonNode.toString());
            }
            reader.close();
            
            final DatabaseReader databaseReader = new DatabaseReader.Builder(getCityDataBase()).build();

            final CityResponse cityResponse = databaseReader.city(address);
            if(cityResponse != null){
                System.out.println("Country !!!! " + cityResponse.getCountry().getIsoCode());
                return cityResponse.getCity();
                
            }
        } catch (AddressNotFoundException e) {
            logger.warn("Geoloc can't find this address:" + customerRemoteAddr);
        } catch (Exception e) {
            logger.error("Geoloc can't find this address:" + customerRemoteAddr);
        }
        return null;
    }
    
    /**
     * 
     */
    public String getRemoteAddr(final HttpServletRequest request){
        String customerRemoteAddr = request.getRemoteAddr();
        //request.getHeader("x-forwarded-for")
        return customerRemoteAddr;
    }
    
    protected File getCityDataBase(){
        EngineSetting engineSetting = engineSettingService.getGeolocCityFilePath();
        final File database = new File(engineSetting.getDefaultValue());
        return database;
    }
    
    protected File getCountryDataBase(){
        EngineSetting engineSetting = engineSettingService.getGeolocCountryFilePath();
        final File database = new File(engineSetting.getDefaultValue());
        return database;
    }
    
    
}