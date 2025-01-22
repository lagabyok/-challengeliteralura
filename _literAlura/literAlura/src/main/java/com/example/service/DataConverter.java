package com.example.service;
import com.example.model.AutorData;
import com.example.model.LibroData;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.model.AutorData;
import com.example.model.LibroData;
import com.example.service.IDataConverter;


public class DataConverter implements IDataConverter {
    private ObjectMapper mapper = new ObjectMapper();


    @Override
    public <T> T getData(String json, Class<T> classe) {
        T resultado = null;
        try {
            if (classe == LibroData.class) {
                JsonNode node = mapper.readTree(json);
                var s = node.get("results").get(0);
                resultado = mapper.treeToValue(s, classe);
            }else if (classe == AutorData.class) {
                JsonNode node = mapper.readTree(json);
                var s = node.get("results").get(0).get("autores").get(0);
                resultado = mapper.treeToValue(s, classe);
            }else {
                resultado = mapper.readValue(json, classe);
            }
        }catch (JsonProcessingException e){
            e.getStackTrace();
            throw new RuntimeException(e);
        }
        return resultado;
    }
}