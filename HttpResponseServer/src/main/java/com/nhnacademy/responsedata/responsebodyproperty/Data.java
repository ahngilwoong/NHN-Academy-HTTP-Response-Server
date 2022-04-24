package com.nhnacademy.responsedata.responsebodyproperty;

import java.util.ArrayList;
import java.util.List;

public class Data {
    public List<String> createDataList(String jsonStr) {
        List<String> returnDataList = new ArrayList<>();
        returnDataList.add(jsonStr.trim());
        return returnDataList;
    }
}
