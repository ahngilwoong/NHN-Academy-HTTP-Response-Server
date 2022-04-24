package com.nhnacademy.responsedata;

import java.io.PrintStream;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

public class ResponseHeader {

    public void print(PrintStream printStream, String json) {
        OffsetDateTime currentTime = OffsetDateTime.now(ZoneId.of("GMT"));
        printStream.println("HTTP/1.1 200 OK");
        printStream.println("Date: " +
            currentTime.format(DateTimeFormatter.RFC_1123_DATE_TIME));
        printStream.println("Content-Type: application/json");
        printStream.println("Content-Length: " + json.length());
        printStream.println("Connection: keep-alive");
        printStream.println("Server: gunicorn/19.9.0");
        printStream.println("Access-Control-Allow-Origin: *");
        printStream.println("Access-Control-Allow-Credentials: true");
        printStream.println();
    }
}
