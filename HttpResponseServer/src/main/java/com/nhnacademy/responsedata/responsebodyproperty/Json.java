package com.nhnacademy.responsedata.responsebodyproperty;

import java.util.LinkedHashMap;
import java.util.Map;

public class Json {
    public Map<String, String> createJsonMap (String jsonStr) {
        Map<String, String> returnJson = new LinkedHashMap<>();
        jsonStr = jsonStr.replace("\"", "").replace("{", "").replace("}", "");

        if (!jsonStr.contains(",")) {
            String[] temp = jsonStr.split(":");
            returnJson.put(temp[0].trim(), temp[1].trim());
        } else {
            String[] jsonString = jsonStr.split(",");
            for (String s : jsonString) {
                String[] temp = s.split(":");
                returnJson.put(temp[0].trim(), temp[1].trim());
            }
        }
        return returnJson;
    }
}
