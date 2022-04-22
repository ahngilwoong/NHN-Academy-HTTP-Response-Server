package com.nhnacademy.responsedata;

import com.fasterxml.jackson.databind.util.JSONPObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ClassPacket {
    Map<String, String> requestHeaderMap = new HashMap<>();
    String xMethod;
    String urlPath;
    String urlPathArgs;
    String requestBody;

    public ClassPacket(List<String> packetSaveList, String requestBody) {
        this.requestBody = requestBody;
        headerListToMap(packetSaveList);

        String[] splitArr = packetSaveList.get(0).split(" ");
        this.xMethod = splitArr[0];
        if(splitArr[1].contains("?")){
            urlPath = splitArr[1];
            urlPathArgs = splitArr[1].split("\\?")[1]; // url에 파라미터가 있는 경우 ? 뒤 값 저장.
        }else{
            this.urlPath = splitArr[1];
            this.urlPathArgs = "";
        }
    }

    public void headerListToMap(List<String> list){
        for (int i = 1; i < list.size(); i++) { // 0번쨰 줄은 메소드와 path이므로 위에서 미리 처리함.
            String[] temp = list.get(i).split(":");
            requestHeaderMap.put(temp[0].trim(),temp[1].trim());
        }
    }

    public String getRequestHeader(String target){
        return requestHeaderMap.get(target);
    }

    public Map.Entry<String, String> getAllRequestHeader(){
        return (Map.Entry<String, String>) requestHeaderMap.entrySet();
    }



    public String getxMethod() {
        return xMethod;
    }

    public String getUrlPath() {
        return urlPath;
    }

    public String getUrlPathArgs() {
        return urlPathArgs;
    }

    public String getRequestBody() {
        return requestBody;
    }

}
