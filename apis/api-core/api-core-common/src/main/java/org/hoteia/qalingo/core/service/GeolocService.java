/**
 * Most of the code in the Qalingo project is copyrighted Hoteia and licensed
 * under the Apache License Version 2.0 (release version 0.8.0)
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 *                   Copyright (c) Hoteia, 2012-2014
 * http://www.hoteia.com - http://twitter.com/hoteia - contact@hoteia.com
 *
 */
package org.hoteia.qalingo.core.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.AddressNotFoundException;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import com.maxmind.geoip2.record.City;
import com.maxmind.geoip2.record.Country;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.hibernate.internal.util.SerializationHelper;
import org.hoteia.qalingo.core.dao.GeolocDao;
import org.hoteia.qalingo.core.domain.EngineSetting;
import org.hoteia.qalingo.core.domain.GeolocAddress;
import org.hoteia.qalingo.core.domain.GeolocCity;
import org.hoteia.qalingo.core.domain.bean.GeolocData;
import org.hoteia.qalingo.core.domain.bean.GeolocDataCity;
import org.hoteia.qalingo.core.domain.bean.GeolocDataCountry;
import org.hoteia.qalingo.core.util.CoreUtil;
import org.hoteia.qalingo.core.web.bean.geoloc.json.GoogleGeoCode;
import org.hoteia.qalingo.core.web.bean.geoloc.json.GoogleGeoCodeResult;
import org.hoteia.qalingo.core.web.util.RequestUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.net.InetAddress;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service("geolocService")
@Transactional
public class GeolocService {
    
    protected final Logger logger = LoggerFactory.getLogger(getClass());

    protected final String GOOGLE_GEOCODING_GEO_CODE_OK               = "OK";
    protected final String GOOGLE_GEOCODING_GEO_CODE_ZERO_RESULTS     = "ZERO_RESULTS";
    protected final String GOOGLE_GEOCODING_GEO_CODE_OVER_QUERY_LIMIT = "OVER_QUERY_LIMIT";
    protected final String GOOGLE_GEOCODING_GEO_CODE_REQUEST_DENIED   = "REQUEST_DENIED";
    protected final String GOOGLE_GEOCODING_GEO_CODE_INVALID_REQUEST  = "INVALID_REQUEST";
    protected final String GOOGLE_GEOCODING_GEO_CODE_UNKNOWN_ERROR    = "UNKNOWN_ERROR";
    
    @Autowired
    protected EmailService emailService;
    
    @Autowired
    protected EngineSettingService engineSettingService;
    
    @Autowired
    protected GeolocDao geolocDao;

    @Autowired
    protected RequestUtil requestUtil;

    protected List<String> unknownValueList = new ArrayList<String>();
    
    public GeolocService() {
        unknownValueList.add("unknown"); // PRIVATE NAVIGATION VALUE : MOZILLA
    }
    
    // COMMON
    
    public GeolocCity geolocByCityAndCountry(final String city, final String country){
        GeolocCity geolocCity = null;
        String formatedAddress = encodeGoogleAddress(null, null, city, country);
        GoogleGeoCode geoCode = geolocGoogleWithAddress(formatedAddress);
        if(geoCode != null && GOOGLE_GEOCODING_GEO_CODE_OVER_QUERY_LIMIT.equals(geoCode.getStatus())){
            logger.error("API Geoloc returns message '" + geoCode.getStatus() + "': " + geoCode.getErrorMessage());
            logger.error("Address encoded: '" + formatedAddress + "'");
            engineSettingService.flagSettingGoogleGeolocationApiOverQuota();
            return geolocCity;
        }
        
        if(geoCode != null) {
            geolocCity = new GeolocCity();
            geolocCity.setCity(city);
            geolocCity.setCountry(country);
            geolocCity.setJson(SerializationHelper.serialize(geoCode));
            geolocCity.setLatitude(geoCode.getLatitude());
            geolocCity.setLongitude(geoCode.getLongitude());
            if(city == null){
                // SANITY CHECK : DON'T SAVE A CITY AS NULL TOO MANY TIME
                GeolocCity geolocCityCheck = geolocDao.getGeolocCityByCountryWithNullCity(country);
                if(geolocCityCheck == null){
                    try {
                        geolocCity = geolocDao.saveOrUpdateGeolocCity(geolocCity);
                    } catch (Exception e) {
                        logger.error("Can't save GeolocCity: City: '" + geolocCity.getCity() + "', Country: '" + geolocCity.getCountry() + "'", e);
                    }
                }
            } else {
                try {
                    geolocCity = geolocDao.saveOrUpdateGeolocCity(geolocCity);
                } catch (Exception e) {
                    logger.error("Can't save GeolocCity: City: '" + geolocCity.getCity() + "', Country: '" + geolocCity.getCountry() + "'", e);
                }
            }
        }
        return geolocCity;
    }
    
    public GeolocAddress geolocByAddress(final String address, final String postalCode, final String city, final String country){
        GeolocAddress geolocAddress = new GeolocAddress();
        geolocAddress.setAddress(address);
        geolocAddress.setPostalCode(postalCode);
        geolocAddress.setCity(city);
        geolocAddress.setCountry(country);
        String formatedAddress = encodeGoogleAddress(address, postalCode, city, country);
        GoogleGeoCode geoCode = geolocGoogleWithAddress(formatedAddress);
        
        // SANITY CHECK
        if(geoCode != null && GOOGLE_GEOCODING_GEO_CODE_OVER_QUERY_LIMIT.equals(geoCode.getStatus())){
            logger.error("API Geoloc returns message '" + geoCode.getStatus() + "': " + geoCode.getErrorMessage());
            logger.error("Address encoded: '" + formatedAddress + "'");
            engineSettingService.flagSettingGoogleGeolocationApiOverQuota();
            return geolocAddress;
        }
        
        if(geoCode != null){
            geolocAddress.setLatitude(geoCode.getLatitude());
            geolocAddress.setLongitude(geoCode.getLongitude());
            
         // SANITY CHECK
            if(!GOOGLE_GEOCODING_GEO_CODE_OK.equals(geoCode.getStatus())){
                logger.error("API Geoloc returns message '" + geoCode.getStatus() + "': " + geoCode.getErrorMessage());
                logger.error("Address encoded: '" + formatedAddress + "'");
                engineSettingService.flagSettingGoogleGeolocationApiOverQuota();
                return geolocAddress;
            }
            geolocAddress.setJson(SerializationHelper.serialize(geoCode));
            geolocAddress.setFormatedAddress(formatedAddress);
            geolocAddress = geolocDao.saveOrUpdateGeolocAddress(geolocAddress);
        }
        return geolocAddress;
    }
    
    public GeolocAddress geolocByLatitudeLongitude(final String latitude, final String longitude) {
        GeolocAddress geolocAddress = null;
        GoogleGeoCode geoCode = geolocGoogleWithLatitudeLongitude(latitude, longitude);
        if(geoCode != null && GOOGLE_GEOCODING_GEO_CODE_OVER_QUERY_LIMIT.equals(geoCode.getStatus())){
            logger.error("API Geoloc returns message '" + geoCode.getStatus() + "': " + geoCode.getErrorMessage());
            logger.error("latitude: '" + latitude + "', longitude: '" + longitude + "'");
            engineSettingService.flagSettingGoogleGeolocationApiOverQuota();
            return geolocAddress;
        }
        
        if(geoCode != null && geoCode.getResults().size() > 0) {
            GoogleGeoCodeResult googleGeoCodeResult = geoCode.getResults().get(0);
            String formatedAdress = googleGeoCodeResult.getFormattedAddress();
            formatedAdress = cleanGoogleAddress(formatedAdress);
            
            geolocAddress = new GeolocAddress();
            geolocAddress.setAddress(googleGeoCodeResult.getAddress());
            geolocAddress.setPostalCode(googleGeoCodeResult.getPostalCode());
            geolocAddress.setCity(googleGeoCodeResult.getCity());
            geolocAddress.setCountry(googleGeoCodeResult.getCountryCode());
            geolocAddress.setJson(SerializationHelper.serialize(geoCode));
            geolocAddress.setFormatedAddress(formatedAdress);
            geolocAddress.setLatitude(latitude);
            geolocAddress.setLongitude(longitude);
            
            // SANITY CHECK : DON'T SAVE AN ADDRESS WHICH ALREADY EXIST BUT WAS LOCATED WITH LAT/LONG DIFFERENT
            Long rowCount = geolocDao.countGeolocAddressByFormatedAddress(formatedAdress);
            if(rowCount != null && rowCount.intValue() == 0){
                geolocAddress = geolocDao.saveOrUpdateGeolocAddress(geolocAddress);
            }
            
        }
        return geolocAddress;
    }
    
    public GoogleGeoCode geolocGoogleWithAddress(final String formatedAddress){
        GoogleGeoCode geoCode = null;
        boolean googleGelocIsOverQuota;
        try {
            googleGelocIsOverQuota = engineSettingService.isGoogleGeolocationApiStillOverQuotas(new Date());
            if (googleGelocIsOverQuota == false) {
                String key = null;
                try {
                    key = engineSettingService.getGoogleGeolocationApiKey();
                } catch (Exception e) {
                    logger.error("Google Geolocation API Key is mandatory!", e);
                }
                if (key != null && StringUtils.isNotEmpty(key)) {
                    HttpPost httpPost = new HttpPost("https://maps.googleapis.com/maps/api/geocode/json?address=" + formatedAddress + "&key=" + key);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse httpResponse = httpClient.execute(httpPost);

                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(httpResponse.getEntity().getContent()));
                    StringBuilder responseStrBuilder = new StringBuilder();

                    String inputStr;
                    while ((inputStr = streamReader.readLine()) != null) {
                        responseStrBuilder.append(inputStr);
                    }
                    String json = responseStrBuilder.toString();

                    ObjectMapper mapper = new ObjectMapper();
                    geoCode = mapper.readValue(json, GoogleGeoCode.class);
                }
            } else {
                logger.warn("Google Geolocation API still over Quota! We can't use geolocation for this address: " + formatedAddress);
            }
        } catch (ClientProtocolException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        } catch (IllegalStateException e) {
            logger.error("", e);
        } catch (ParseException e) {
            logger.error("", e);
        }
        return geoCode;
    }
    
    public GoogleGeoCode geolocGoogleWithLatitudeLongitude(final String latitude, final String longitude){
        GoogleGeoCode geoCode = null;
        boolean googleGelocIsOverQuota;
        try {
            googleGelocIsOverQuota = engineSettingService.isGoogleGeolocationApiStillOverQuotas(new Date());
            String paramLatLong = latitude.trim() + "," + longitude.trim();
            if (!googleGelocIsOverQuota) {
                String key = null;
                try {
                    key = engineSettingService.getGoogleGeolocationApiKey();
                } catch (Exception e) {
                    logger.error("Google Geolocation API Key is mandatory!", e);
                }
                if (key != null && StringUtils.isNotEmpty(key)) {
                    HttpPost request = new HttpPost("https://maps.googleapis.com/maps/api/geocode/json?latlng=" + paramLatLong + "&key=" + key);
                    HttpClient httpClient = new DefaultHttpClient();
                    HttpResponse response = httpClient.execute(request);

                    BufferedReader streamReader = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
                    StringBuilder responseStrBuilder = new StringBuilder();

                    String inputStr;
                    while ((inputStr = streamReader.readLine()) != null) {
                        responseStrBuilder.append(inputStr);
                    }
                    String json = responseStrBuilder.toString();

                    ObjectMapper mapper = new ObjectMapper();
                    geoCode = mapper.readValue(json, GoogleGeoCode.class);
                }
            } else {
                logger.warn("Google Geolocation API still over Quota! We can't use geolocation for this lat/long: " + paramLatLong);
            }
        } catch (ClientProtocolException e) {
            logger.error("", e);
        } catch (IOException e) {
            logger.error("", e);
        } catch (IllegalStateException e) {
            logger.error("", e);
        } catch (ParseException e) {
            logger.error("", e);
        }
        return geoCode;
    }
    
    public String encodeGoogleAddress(final String address, final String postalCode, final String city, final String country) {
        StringBuffer encode  = new StringBuffer();
        if(StringUtils.isNotEmpty(address)){
            encode.append(cleanGoogleAddress(address.trim()));
            encode.append(",");
        }
        if(StringUtils.isNotEmpty(city)){
            encode.append(cleanGoogleAddress(city.trim()));
            encode.append(",");
        }
        if(StringUtils.isNotEmpty(postalCode)){
            encode.append(cleanGoogleAddress(postalCode.trim()));
            encode.append(",");
        }
        if(StringUtils.isNotEmpty(country)){
            encode.append(cleanGoogleAddress(country.trim()));
            encode.append(",");
        }
        return encode.toString();
    }
    
    protected String cleanGoogleAddress(String value){
        if(StringUtils.isNotEmpty(value)){
            value = CoreUtil.replaceCharactersNotLetterOrDigit(value, "+");
        }
        return value;
    }
    
    // GEOLOC CITY
    
    public GeolocCity getGeolocCityByCityAndCountry(final String city, final String country, Object... params) {
        return geolocDao.getGeolocCityByCityAndCountry(city, country, params);
    }
    
    public GeolocCity getGeolocCityByCountryWithNullCity(final String country, Object... params) {
        return geolocDao.getGeolocCityByCountryWithNullCity(country, params);
    }
    
    public GeolocCity saveOrUpdateGeolocCity(final GeolocCity geolocCity) {
        return geolocDao.saveOrUpdateGeolocCity(geolocCity);
    }
    
    public void deleteGeolocCity(final GeolocCity geolocCity) {
        geolocDao.deleteGeolocCity(geolocCity);
    }
    
    // GEOLOC ADDRESS
    
    public GeolocAddress getGeolocAddressByFormatedAddress(final String formatedAddress, Object... params) {
        return geolocDao.getGeolocAddressByFormatedAddress(formatedAddress, params);
    }

    public GeolocAddress getGeolocAddressByLatitudeAndLongitude(final String latitude, final String longitude, Object... params) {
        return geolocDao.getGeolocAddressByLatitudeAndLongitude(latitude, longitude, params);
    }

    public Long countGeolocAddressByFormatedAddress(final String formatedAddress) {
        return geolocDao.countGeolocAddressByFormatedAddress(formatedAddress);
    }
    
    public GeolocAddress saveOrUpdateGeolocAddress(final GeolocAddress geolocCity) {
        return geolocDao.saveOrUpdateGeolocAddress(geolocCity);
    }
    
    public void deleteGeolocAddress(final GeolocAddress geolocCity) {
        geolocDao.deleteGeolocAddress(geolocCity);
    }
    
    /**
     * 
     */
    public GeolocData getGeolocData(final String remoteAddress) throws Exception {
        GeolocData geolocData = null;
        if(!requestUtil.isLocalHostMode(remoteAddress)){
            geolocData = new GeolocData();
            final Country country = geolocAndGetCountry(remoteAddress);
            geolocData.setRemoteAddress(remoteAddress);
            if(country != null 
                    && StringUtils.isNotEmpty(country.getIsoCode())){
                GeolocDataCountry geolocDataCountry = new GeolocDataCountry();
                geolocDataCountry.setIsoCode(country.getIsoCode());
                geolocDataCountry.setName(country.getName());
                geolocData.setCountry(geolocDataCountry);
                final City city = geolocAndGetCity(remoteAddress);
                GeolocDataCity geolocDataCity = new GeolocDataCity();
                geolocDataCity.setGeoNameId(city.getGeoNameId());
                geolocDataCity.setName(city.getName());
                geolocData.setCity(geolocDataCity);
            }
        }
        return geolocData;
    }
    
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
        if(StringUtils.isNotEmpty(customerRemoteAddr)){
            if(!unknownValueList.contains(customerRemoteAddr)){
                try {
                    final InetAddress address = InetAddress.getByName(customerRemoteAddr);
                    
                    final DatabaseReader databaseReader = new DatabaseReader.Builder(getCountryDataBase()).build();
                    final CountryResponse countryResponse = databaseReader.country(address);
                    if(countryResponse != null){
                        return countryResponse.getCountry();
                    }
                } catch (AddressNotFoundException e) {
                    logger.warn("Geoloc country, can't find this address: '" + customerRemoteAddr + "'");
                } catch (FileNotFoundException e) {
                    logger.error("Geoloc country, can't find database MaxMind", e);
                } catch (Exception e) {
                    logger.error("Geoloc country, exception to find country with this address: '" + customerRemoteAddr + "'", e);
                }
            } else {
                logger.debug("Geoloc country, can't find address (private navigation): '" + customerRemoteAddr + "'");
            }
        } else {
            logger.debug("Geoloc country, can't find address, value is empty.");
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
        if(StringUtils.isNotEmpty(customerRemoteAddr)){
            if(!unknownValueList.contains(customerRemoteAddr)){
                try {
                    final InetAddress address = InetAddress.getByName(customerRemoteAddr);
                    
                    final DatabaseReader databaseReader = new DatabaseReader.Builder(getCityDataBase()).build();

                    final CityResponse cityResponse = databaseReader.city(address);
                    if(cityResponse != null){
                        return cityResponse.getCity();
                    }
                } catch (AddressNotFoundException e) {
                    logger.warn("Geoloc city, can't find this address:" + customerRemoteAddr);
                } catch (FileNotFoundException e) {
                    logger.error("Geoloc city, can't find database MaxMind", e);
                } catch (Exception e) {
                    logger.error("Geoloc city, can't find this city with this address:" + customerRemoteAddr, e);
                }
            } else {
                logger.debug("Geoloc city, can't find address (private navigation): '" + customerRemoteAddr + "'");
            }
        } else {
            logger.debug("Geoloc city, can't find address, value is empty.");
        }
        return null;
    }
    
    protected File getCityDataBase(){
        EngineSetting engineSetting = engineSettingService.getSettingGeolocCityFilePath();
        if(engineSetting != null){
            final File database = new File(engineSetting.getDefaultValue());
            return database;
        }
        return null;
    }
    
    protected File getCountryDataBase(){
        EngineSetting engineSetting = engineSettingService.getSettingGeolocCountryFilePath();
        if(engineSetting != null){
            final File database = new File(engineSetting.getDefaultValue());
            return database;
        }
        return null;
    }
    
}