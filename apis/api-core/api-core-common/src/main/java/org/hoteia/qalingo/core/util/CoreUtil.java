package org.hoteia.qalingo.core.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.UUID;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.i18n.phonenumbers.NumberParseException;
import com.google.i18n.phonenumbers.PhoneNumberUtil;
import com.google.i18n.phonenumbers.PhoneNumberUtil.PhoneNumberFormat;
import com.google.i18n.phonenumbers.Phonenumber.PhoneNumber;

public class CoreUtil {

    protected final static Logger logger = LoggerFactory.getLogger(CoreUtil.class);
    
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
    
    public static String cleanEntityCode(String stringInput) {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            stringOutput = replaceCharactersNotLetterOrDigit(stringOutput);
            stringOutput = stringOutput.replaceAll("-", "_").toUpperCase();
        }
        return stringOutput;
    }
    
    public static String decodeParamCode(String stringInput) {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            stringOutput = stringOutput.replaceAll("-", "_").toUpperCase();
        }
        return stringOutput;
    }
    
    public static String handleFileName(String fileName){
        if(StringUtils.isNotEmpty(fileName)){
            String name = fileName;
            String type = "";
            if(fileName.contains(".")){
                name = fileName.substring(0, fileName.lastIndexOf("."));
                type = fileName.substring(fileName.lastIndexOf(".") + 1, fileName.length());
            }
            String cleanedName = CoreUtil.replaceCharactersNotLetterOrDigit(name);
            String newFileName = cleanedName;
            if(StringUtils.isNotEmpty(type)){
                newFileName += "." + type;
            }
            return newFileName.toLowerCase();
        }
        return fileName;
    }
    
    public static String handleTruncatedString(String stringInput, int size) {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            return StringUtils.substring(stringOutput, 0, size).replaceAll(" [^ ]+$", "") + "...";
        }
        return stringOutput;
    }
    
    public static String handleSeoSpecificEscape(String stringInput) throws UnsupportedEncodingException {
        return handleSeoSpecificEscape(stringInput, false);
    }
    
    public static String handleSeoSpecificEscape(String stringInput, boolean isEncoded) throws UnsupportedEncodingException {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            stringOutput = replaceSpaceAndUnderscore(stringOutput);

            // REPLACE WITH DASH
            stringOutput = stringOutput.replaceAll("[&°?(){}<>'\"“”;.,/#]", "-");

            stringOutput = cleanDash(stringOutput);

            if(isEncoded){
                return URLEncoder.encode(lowerCase(stringOutput), "UTF-8");
            }
            return lowerCase(stringOutput);
        }
        return stringOutput;
    }

    public static String removeHtmlTag(final String stringInput) {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            stringOutput = stringOutput.replaceAll("\\<.*?>","");
            // HtmlUtils.htmlEscape() not good
        }
        return stringOutput;
    }
    
    public static String replaceSpecificAlphabet(final String stringInput) {
        String stringOutput = stringInput.toLowerCase();
        stringOutput = stringOutput.replaceAll("[àáâãäå]", "a");
        stringOutput = stringOutput.replaceAll("[ç]", "c");
        stringOutput = stringOutput.replaceAll("[èéêë]", "e");
        stringOutput = stringOutput.replaceAll("[ìíîï]", "i");
        stringOutput = stringOutput.replaceAll("[ðòóôõö]", "o");
        stringOutput = stringOutput.replaceAll("[ùúûü]", "u");
        stringOutput = stringOutput.replaceAll("[ýÿ]", "y");
        return stringOutput;
    }
    
    public static String replaceCharactersNotLetterOrDigit(String stringInput) {
        return replaceCharactersNotLetterOrDigit(stringInput, "-");
    }
    
    public static String replaceCharactersNotLetterOrDigit(String stringInput, String replacement) {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            stringOutput = stringOutput.replaceAll("[^\\p{L}\\p{Nd}]+", replacement);
            stringOutput = cleanDash(stringOutput);
        }
        return stringOutput;
    }
    
    public static String replaceSpaceAndUnderscore(String stringInput) {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            stringOutput = stringOutput.replaceAll(" ", "-");
            stringOutput = stringOutput.replaceAll("_", "-");

            stringOutput = cleanDash(stringOutput);

            return stringOutput;
        }
        return stringOutput;
    }
    
    public static String replaceCarriagReturn(String stringInput) {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            stringOutput = stringOutput.replaceAll("\r", " ").replaceAll("\n", "").replaceAll("  ", " ");

            return stringOutput;
        }
        return stringOutput;
    }
    
    public static String replaceSpaceAndDash(String stringInput) {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            stringOutput = stringOutput.replaceAll(" ", "_");
            stringOutput = stringOutput.replaceAll("-", "_");

            return stringOutput;
        }
        return stringOutput;
    }
    
    public static String cleanDash(String stringInput) {
        // SPECIFIC DASH
//        stringOutput = stringOutput.replaceAll("–", "-");// break build for non latin
        String stringOutput = stringInput;
        while (stringOutput.contains("--")) {
            stringOutput = stringOutput.replaceAll("--", "-");
        }
        if (stringOutput.startsWith("-")) {
            stringOutput = stringOutput.substring(1, stringOutput.length());
        }
        if (stringOutput.endsWith("-")) {
            stringOutput = stringOutput.substring(0, stringOutput.length() - 1);
        }
        return stringOutput;
    }

    public static String lowerCase(String stringInput) {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            return stringOutput.toLowerCase().trim();
        }
        return stringOutput;
    }
    
    public static String upperCase(String stringInput) {
        String stringOutput = stringInput;
        if (StringUtils.isNotEmpty(stringOutput)) {
            return stringOutput.toUpperCase().trim();
        }
        return stringOutput;
    }
    
    public static String encodePhone(String phone, String countryCode) {
        if(StringUtils.isNotEmpty(phone) 
                && StringUtils.isNotEmpty(countryCode)){
            try {
                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                PhoneNumber numberProto = phoneUtil.parse(phone, countryCode);
                boolean isValid = phoneUtil.isValidNumber(numberProto);
                if (isValid) {
                    String formatedPhone = phoneUtil.format(numberProto, PhoneNumberFormat.E164);
                    return formatedPhone;
                } else {
                    logger.debug("Phone can't be formated. It is not valid: ''" + phone + "'");
                }
            } catch (NumberParseException e) {
                logger.error("NumberParseException was thrown.", e);
            }
        } else {
            logger.debug("Phone can't be encoded. countryCode is empty, phone: ''" + phone + "'");
        }
        return phone;
    }
   
    public static String formatInternationalPhone(String phone, String countryCode) {
        return formatPhone(phone, countryCode, PhoneNumberFormat.INTERNATIONAL);
    }
    
    public static String formatNationalPhone(String phone, String countryCode) {
        return formatPhone(phone, countryCode, PhoneNumberFormat.NATIONAL);
    }
    
    public static String formatPhone(String phone, String countryCode, PhoneNumberFormat phoneNumberFormat) {
        if(StringUtils.isNotEmpty(phone) 
                && StringUtils.isNotEmpty(countryCode)){
            try {
                PhoneNumberUtil phoneUtil = PhoneNumberUtil.getInstance();
                PhoneNumber numberProto = phoneUtil.parse(phone, countryCode);
                boolean isValid = phoneUtil.isValidNumber(numberProto);
                if (isValid) {
                    String formatedPhone = phoneUtil.format(numberProto, phoneNumberFormat);
                    return formatedPhone;
                } else {
                    logger.debug("Phone can't be formated. It is not valid: ''" + phone + "'");
                }
            } catch (NumberParseException e) {
                logger.error("NumberParseException was thrown.", e);
            }
        } else {
            logger.debug("Phone can't be formated. countryCode is empty, phone: ''" + phone + "'");
        }
        return phone;
    }
}