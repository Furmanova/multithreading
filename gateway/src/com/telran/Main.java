package com.telran;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static final int CLIENT_MESSAGE_PORT = 5000;

    public static void main(String[] args) throws IOException {
        ExecutorService pool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(CLIENT_MESSAGE_PORT);

        UDPServerLoadBalancer udpThread = new UDPServerLoadBalancer();
        pool.execute(udpThread);
        
        while (true) {
            // сервер принимает соединение
            Socket socket = serverSocket.accept();
            Runnable gatewayThread = new Gateway(socket, udpThread);
            pool.execute(gatewayThread);
        }
    }
}