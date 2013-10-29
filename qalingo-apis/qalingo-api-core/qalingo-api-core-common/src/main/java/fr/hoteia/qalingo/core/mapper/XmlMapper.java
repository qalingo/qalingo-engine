package fr.hoteia.qalingo.core.mapper;

import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule;

@Component(value = "xmlMapper")
public class XmlMapper {

    public com.fasterxml.jackson.dataformat.xml.XmlMapper getXmlMapper(){
        JacksonXmlModule module = new JacksonXmlModule();
        module.setDefaultUseWrapper(true);
        
        com.fasterxml.jackson.dataformat.xml.XmlMapper mapper = new com.fasterxml.jackson.dataformat.xml.XmlMapper(module);
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        
        return mapper;
    }
}
