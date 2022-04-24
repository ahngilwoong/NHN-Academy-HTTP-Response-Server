package com.nhnacademy.responsedata.responsebodyproperty;

import java.util.LinkedHashMap;
import java.util.Map;
import org.apache.commons.lang3.StringUtils;

public class Args {

    public Map<String, String> createArgsMap(String urlPathArgs) {
        Map<String, String> returnMap = new LinkedHashMap<>();
        if (StringUtils.isEmpty(urlPathArgs)) {
            return returnMap;
        }
        if (urlPathArgs.contains("&")) {
            String[] splitAndStr = urlPathArgs.split("&");
            for (int i = 0; i < splitAndStr.length; i++) {
                String[] splitEqualStr = splitAndStr[i].split("=");
                returnMap.put(splitEqualStr[0], splitEqualStr[1]);
            }
        } else {
            String[] args = urlPathArgs.split("=");
            returnMap.put(args[0], args[1]);
        }
        return returnMap;
    }

}
