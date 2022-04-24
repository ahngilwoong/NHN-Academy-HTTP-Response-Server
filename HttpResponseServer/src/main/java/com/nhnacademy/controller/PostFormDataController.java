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
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public class PostFormDataController {
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

    public PostFormDataController(Socket socket, PrintStream printStream, ClassPacket request, String jsonStr, String message) {
        this.socket = socket;
        this.printStream = printStream;
        this.request = request;
        this.jsonStr = jsonStr;
        this.message = message;
    }

    public void parse() throws JsonProcessingException {
        map.put("args", args.createArgsMap(request.getUrlPathArgs()));
        map.put("data", "");
        map.put("files", createFormDataFileMap(request));
        map.put("form", form.createFormMap());
        map.put("headers", headers.createPostHeadersMap(request, message));
        map.put("json", "");
        map.put("origin", socket.getInetAddress().toString().replace("/", ""));
        map.put("url", request.getRequestHeader("Host") + request.getUrlPath());
        String responseJsonBody =
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(map);
        responseHeader.print(printStream, responseJsonBody);
        printStream.println(responseJsonBody);
    }

    private Map<String, Object> wrapperMapObjectToJson(String name, Object obj){
        Map<String,Object> wrapperMap = new HashMap<>();
        wrapperMap.put(name,obj);
        return wrapperMap;
    }

    private Map<String, String> createFormDataFileMap(ClassPacket request)
        throws JsonProcessingException {
        Map<String, String> returnFileMap = new LinkedHashMap<>();
        byte[] bytes = request.getRequestBody().getBytes(StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        boolean start = false;
        for (int i = 0; i < bytes.length; i++) {
            String charInFile = Character.toString(bytes[i]);
            if ("{".equals(charInFile)) {
                start = true;
            }
            if (start) {
                sb.append(charInFile);
            }
            if ("}".equals(charInFile)) {
                start = false;
                break;
            }
        }
        String fileContent = sb.toString();
        System.out.println("data:" + sb.toString());
        returnFileMap.put("upload", sb.toString());

        //파일로 저장
        File file = new File("./src/main/resources/" + "msg.json");
        byte[] byteArr = new byte[4096];
        byteArr = fileContent.getBytes(StandardCharsets.UTF_8);

        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            fileOutputStream.write(byteArr);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return returnFileMap;
    }
}
