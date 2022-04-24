package com.nhnacademy.httpserver;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ResponseServer {
    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(80);
            System.out.println("log");
            while(true){
                Socket clientSocket = serverSocket.accept(); // 대기
                System.out.println(clientSocket.getInetAddress()+"주소로 연결되었습니다.");
                ResponseThread responseThread = new ResponseThread(clientSocket);
                responseThread.run();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
