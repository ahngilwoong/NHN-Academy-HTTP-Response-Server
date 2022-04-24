package com.nhnacademy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.responsedata.ClassPacket;
import com.nhnacademy.responsedata.ResponseHeader;
import com.nhnacademy.responsedata.responsebodyproperty.Args;
import com.nhnacademy.responsedata.responsebodyproperty.Headers;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class GetController {
    private final Socket socket;
    private final PrintStream printStream;
    private final ClassPacket request;
    private final ResponseHeader responseHeader = new ResponseHeader();
    private final Args args = new Args();
    private final Headers headers = new Headers();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, Object> map = new LinkedHashMap<>();

    public GetController(Socket socket, PrintStream printStream,
                         ClassPacket request) {
        this.socket = socket;
        this.printStream = printStream;
        this.request = request;
    }

    public void parse() throws JsonProcessingException {
        map.put("args", args.createArgsMap(request.getUrlPathArgs()));
        map.put("headers", headers.createHeadersMap(request));
        map.put("origin", socket.getInetAddress().toString().replace("/", ""));
        map.put("url",
            request.getRequestHeader("Host") + request.getUrlPath());
        String responseJsonBody = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        responseHeader.print(printStream, responseJsonBody);
        printStream.println(responseJsonBody);
    }
}
