package com.nhnacademy.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.responsedata.ClassPacket;
import com.nhnacademy.responsedata.ResponseHeader;
import com.nhnacademy.responsedata.responsebodyproperty.Args;
import com.nhnacademy.responsedata.responsebodyproperty.Data;
import com.nhnacademy.responsedata.responsebodyproperty.Files;
import com.nhnacademy.responsedata.responsebodyproperty.Form;
import com.nhnacademy.responsedata.responsebodyproperty.Headers;
import com.nhnacademy.responsedata.responsebodyproperty.Json;
import java.io.PrintStream;
import java.net.Socket;
import java.util.LinkedHashMap;
import java.util.Map;

public class PostController {
    private final Socket socket;
    private final PrintStream printStream;
    private final ClassPacket request;
    private final ResponseHeader responseHeader = new ResponseHeader();
    private final Args args = new Args();
    private final Data data = new Data();
    private final Files files = new Files();
    private final Form form = new Form();
    private final Headers headers = new Headers();
    private final Json json = new Json();
    private final ObjectMapper mapper = new ObjectMapper();
    private final Map<String, Object> map = new LinkedHashMap<>();
    private final String jsonStr;
    private final String message;

    public PostController(Socket socket, PrintStream printStream, ClassPacket request, String jsonStr, String message) {
        this.socket = socket;
        this.printStream = printStream;
        this.request = request;
        this.jsonStr = jsonStr;
        this.message = message;
    }

    public void parse() throws JsonProcessingException {
        map.put("args", args.createArgsMap(request.getUrlPathArgs()));
        map.put("data", data.createDataList(jsonStr));
        map.put("files", files.createFileMap());
        map.put("form", form.createFormMap());
        Map<String ,String> headersMap = headers.createHeadersMap(request);
        headersMap.put("Content-Length", message.length()+"");
        map.put("headers", headersMap);
        map.put("json", json.createJsonMap(jsonStr));
        map.put("origin", socket.getInetAddress().toString().replace("/", ""));
        map.put("url", request.getRequestHeader("Host") + request.getUrlPath());
        String responseJsonBody =
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        responseHeader.print(printStream, responseJsonBody);
        printStream.println(responseJsonBody);
    }
}
