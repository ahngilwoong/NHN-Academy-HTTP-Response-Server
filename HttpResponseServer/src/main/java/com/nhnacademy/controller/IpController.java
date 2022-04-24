package com.nhnacademy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.responsedata.ResponseHeader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class IpController {
    private final Socket socket;
    private final PrintStream printStream;
    private final ResponseHeader responseHeader = new ResponseHeader();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, Object> map = new LinkedHashMap<>();

    public IpController(Socket socket, PrintStream printStream) {
        this.socket = socket;
        this.printStream = printStream;
    }

    public void parse() throws JsonProcessingException {
        map.put("origin", socket.getInetAddress().toString().replace("/", ""));
        String json = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        System.out.println(json);
        responseHeader.print(printStream, json);
        printStream.println(json);
        printStream.flush();
    }

}
