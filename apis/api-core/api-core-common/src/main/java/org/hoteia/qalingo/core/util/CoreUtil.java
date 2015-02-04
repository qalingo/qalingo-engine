package org.hoteia.qalingo.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class CoreUtil {

    public static String generateEntityCode(){
        // ONLY UNDERSCORE IN CODE
        return UUID.randomUUID().toString().replace("-", "_");
    }
    
    public static String handleTruncatedDescription(String string) {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            return StringUtils.substring(stringToReturn, 0, 150).replaceAll(" [^ ]+$", "") + "...";
        }
        return stringToReturn;
    }
    
    public static String handleSeoSpecificEscape(String string) throws UnsupportedEncodingException {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            stringToReturn = replaceSpaceAndUnderscore(stringToReturn);
//            stringToReturn = stringToReturn.replaceAll("[àáâãäå]", "a");
//            stringToReturn = stringToReturn.replaceAll("[ç]", "c");
//            stringToReturn = stringToReturn.replaceAll("[èéêë]", "e");
//            stringToReturn = stringToReturn.replaceAll("[ìíîï]", "i");
//            stringToReturn = stringToReturn.replaceAll("[ðòóôõö]", "o");
//            stringToReturn = stringToReturn.replaceAll("[ùúûü]", "u");
//            stringToReturn = stringToReturn.replaceAll("[ýÿ]", "y");

            // REPLACE WITH NOTHING
            stringToReturn = stringToReturn.replaceAll("[°'\"?]", "");
            
            // REPLACE WITH DASH
            stringToReturn = stringToReturn.replaceAll("[(){}<>'\";.,/#]", "-");
            stringToReturn = stringToReturn.replaceAll("-&-", "-");

            stringToReturn = cleanDash(stringToReturn);

            return URLEncoder.encode(lowerCase(stringToReturn), "UTF-8");
        }
        return stringToReturn;
    }
    
    public static String replaceSpaceAndUnderscore(String string) {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            stringToReturn = stringToReturn.replaceAll(" ", "-");
            stringToReturn = stringToReturn.replaceAll("_", "-");

            stringToReturn = cleanDash(stringToReturn);

            return stringToReturn;
        }
        return stringToReturn;
    }
    
    public static String replaceCarriagReturn(String string) {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            stringToReturn = stringToReturn.replaceAll("\r", " ").replaceAll("\n", "").replaceAll("  ", " ");

            return stringToReturn;
        }
        return stringToReturn;
    }
    
    public static String replaceSpaceAndDash(String string) {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            stringToReturn = stringToReturn.replaceAll(" ", "_");
            stringToReturn = stringToReturn.replaceAll("-", "_");

            return stringToReturn;
        }
        return stringToReturn;
    }
    
    public static String cleanDash(String stringToReturn) {
        // SPECIFIC DASH
        stringToReturn = stringToReturn.replaceAll("–", "-");

        while (stringToReturn.contains("--")) {
            stringToReturn = stringToReturn.replaceAll("--", "-");
        }
        if (stringToReturn.startsWith("-")) {
            stringToReturn = stringToReturn.substring(1, stringToReturn.length());
        }
        if (stringToReturn.endsWith("-")) {
            stringToReturn = stringToReturn.substring(0, stringToReturn.length() - 1);
        }
        return stringToReturn;
    }

    public static String lowerCase(String string) {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            return stringToReturn.toLowerCase().trim();
        }
        return stringToReturn;
    }
    
    public static String upperCase(String string) {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            return stringToReturn.toUpperCase().trim();
        }
        return stringToReturn;
    }
    
}