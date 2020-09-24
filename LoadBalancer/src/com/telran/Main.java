package com.telran;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    static final int SERVER_POT_BACKEND = 4002;
    public static void main(String[] args) throws IOException {

        ExecutorService pool = Executors.newFixedThreadPool(10);
        ServerSocket serverSocket = new ServerSocket(SERVER_POT_BACKEND);

        UDPServerBackend udpThread = new UDPServerBackend();
        pool.execute(udpThread);

        while (true) {
            // сервер принимает соединение
            Socket socket = serverSocket.accept();
            Runnable gatewayThread = new UDPClientGateway(socket, udpThread);
            pool.execute(gatewayThread);
        }
    }
}
