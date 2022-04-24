package com.nhnacademy.httpserver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nhnacademy.controller.GetController;
import com.nhnacademy.controller.IpController;
import com.nhnacademy.controller.PostController;
import com.nhnacademy.controller.PostFormDataController;
import com.nhnacademy.exception.StatusCodeFoundException;
import com.nhnacademy.responsedata.ClassPacket;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class ResponseThread implements Runnable {
    Socket socket;
    DataInputStream in;
    List<String> packetSave = new ArrayList<>();

    ResponseThread(Socket socket) {
        this.socket = socket;
        try {
            in = new DataInputStream(socket.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void run() {
        try {
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(in));
            String line;
            boolean isChecked = false;

            String jsonStr = "";
            byte[] byteArr = new byte[4096];
            int readByteCount = in.read(byteArr);
            String message = new String(byteArr, 0, readByteCount, "UTF-8");
            String[] temp = message.split("\n");
            for (int i = 0; i < temp.length; i++) {
                if (temp[i].equals("\r")) {
                    isChecked = true; //requestHeader body 구분
                }
                if (!isChecked) {
                    packetSave.add(temp[i]);
                } else {
                    jsonStr += temp[i]; //requestBody
                }
            }
            //-----------------Requset 패킷 받아온 값들 .

            ClassPacket request = new ClassPacket(packetSave, jsonStr.trim());
            PrintStream printStream = new PrintStream(socket.getOutputStream());

            ObjectMapper mapper = new ObjectMapper();
            Map<String, Object> map = new LinkedHashMap<>();

            if (request.getUrlPath().contains("/ip")) {
                IpController ipController = new IpController(socket, printStream);
                ipController.parse();
            } else if (request.getUrlPath().contains("/get")) {
                GetController getController = new GetController(socket, printStream, request);
                getController.parse();
            } else if (request.getUrlPath().contains("/post")) {
                if (!request.getRequestHeader("Content-Type").contains("multipart/form-data")) {
                    PostController
                        postController =
                        new PostController(socket, printStream, request, jsonStr, message);
                    postController.parse();
                } else {
                    PostFormDataController
                        postFormDataController =
                        new PostFormDataController(socket, printStream, request, jsonStr, message);
                    postFormDataController.parse();
                }
            } else {
                new StatusCodeFoundException("404 ERROR");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
