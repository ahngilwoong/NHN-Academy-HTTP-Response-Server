package com.nhnacademy.responsedata;

import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.sql.Time;

public class DataFlame {
    URL url;

    public DataFlame(URL url) {
        this.url = url;
    }

    public void responseHeader(OutputStream outputStream){
        PrintWriter printWriter = new PrintWriter(outputStream);
        printWriter.println("HTTP/1.1 200 OK");

    }

    public void responseBody(){
        //json 형식 구현
    }



}
