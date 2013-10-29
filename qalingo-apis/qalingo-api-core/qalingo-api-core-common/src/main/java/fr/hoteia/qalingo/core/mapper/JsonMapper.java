package fr.hoteia.qalingo.core.mapper;

import org.springframework.stereotype.Component;

@Component(value = "jsonMapper")
public class JsonMapper {

    public com.fasterxml.jackson.databind.ObjectMapper getJsonMapper(){
        com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
        
        return mapper;
    }
}
