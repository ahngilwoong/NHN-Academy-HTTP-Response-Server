package com.nhnacademy.responsedata.responsebodyproperty;

import com.nhnacademy.responsedata.ClassPacket;
import java.util.HashMap;
import java.util.Map;

public class Headers {
    public Map<String, String> createHeadersMap(ClassPacket request) {
        Map<String, String> headersMap = new HashMap<>();
        headersMap.put("Host", request.getRequestHeader("Host"));
        headersMap.put("User-Agent", request.getRequestHeader("User-Agent"));
        headersMap.put("Accept", request.getRequestHeader("Accept"));
        headersMap.put("Host", request.getRequestHeader("Host"));
        return headersMap;
    }
}
