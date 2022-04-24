package com.nhnacademy.responsedata.responsebodyproperty;

import com.nhnacademy.responsedata.ClassPacket;
import java.util.LinkedHashMap;
import java.util.Map;

public class Headers {
    public Map<String, String> createHeadersMap(ClassPacket request) {
        Map<String, String> headersMap = new LinkedHashMap<>();
        headersMap.put("Accept", request.getRequestHeader("Accept"));
        headersMap.put("Host", request.getRequestHeader("Host"));
        headersMap.put("User-Agent", request.getRequestHeader("User-Agent"));
        return headersMap;
    }
    public Map<String, String> createPostHeadersMap(ClassPacket request, String message) {
        Map<String, String> headersMap = new LinkedHashMap<>();
        headersMap.put("Accept", request.getRequestHeader("Accept"));
        headersMap.put("Content-Length", message.length()+"");
        headersMap.put("Content-Type", request.getRequestHeader("Content-Type"));
        headersMap.put("Host", request.getRequestHeader("Host"));
        headersMap.put("User-Agent", request.getRequestHeader("User-Agent"));
        return headersMap;
    }
}
