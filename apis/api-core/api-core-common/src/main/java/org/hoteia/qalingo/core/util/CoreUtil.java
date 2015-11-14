package org.hoteia.qalingo.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;

public class CoreUtil {

    public static boolean isLocalHostMode(final String address) throws Exception {
        if(StringUtils.isNotEmpty(address) 
                && (address.contains("localhost") 
                        || address.equalsIgnoreCase("127.0.0.1") 
                        || address.startsWith("192.168.") 
                        || address.startsWith("10."))){
            return true;
        }
        return false;
    }
   
    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
    
    public static String generateHashFolder(){
        return generateUUID();
    }
    
    public static String generateEntityCode(){
        // ONLY UNDERSCORE IN CODE
        return generateUUID().replace("-", "_");
    }
    
    public static String cleanEntityCode(String string) {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            stringToReturn = replaceCharactersNotLetterOrDigit(stringToReturn);
            stringToReturn = stringToReturn.replaceAll("-", "_").toUpperCase();
        }
        return stringToReturn;
    }
    
    public static String decodeParamCode(String string) {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            stringToReturn = stringToReturn.replaceAll("-", "_").toUpperCase();
        }
        return stringToReturn;
    }
    
    public static String handleFileName(String fileName){
        if(StringUtils.isNotEmpty(fileName)){
            String name = CoreUtil.replaceCharactersNotLetterOrDigit(fileName.substring(0, fileName.lastIndexOf(".")));
            String type = fileName.substring(fileName.lastIndexOf("."), fileName.length());
            String newFileName = name + "." + type;
            return newFileName.toLowerCase();
        }
        return fileName;
    }
    
    public static String handleTruncatedDescription(String string) {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            return StringUtils.substring(stringToReturn, 0, 150).replaceAll(" [^ ]+$", "") + "...";
        }
        return stringToReturn;
    }
    
    public static String handleSeoSpecificEscape(String string) throws UnsupportedEncodingException {
        return handleSeoSpecificEscape(string, false);
    }
    
    public static String handleSeoSpecificEscape(String string, boolean isEncoded) throws UnsupportedEncodingException {
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

            // REPLACE WITH DASH
            stringToReturn = stringToReturn.replaceAll("[&°?(){}<>'\";.,/#]", "-");

            stringToReturn = cleanDash(stringToReturn);

            if(isEncoded){
                return URLEncoder.encode(lowerCase(stringToReturn), "UTF-8");
            }
            return lowerCase(stringToReturn);
        }
        return stringToReturn;
    }
        
    public static String replaceCharactersNotLetterOrDigit(String string) {
        return replaceCharactersNotLetterOrDigit(string, "-");
    }
    
    public static String replaceCharactersNotLetterOrDigit(String string, String replacement) {
        String stringToReturn = string;
        if (StringUtils.isNotEmpty(stringToReturn)) {
            stringToReturn = stringToReturn.replaceAll("[^\\p{L}\\p{Nd}]+", replacement);
            stringToReturn = cleanDash(stringToReturn);
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