package com.atipera.api.git_data.services.helper_classes;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.net.URL;

public class DataConverter {

    public static JsonNode convertToObjectTree(String data, ObjectMapper mapper) {
        try {
            return mapper.readTree(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
