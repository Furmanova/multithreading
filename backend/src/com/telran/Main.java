package com.telran;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    static final int SERVER_PORT_GATEWAY = 4000;
    public static void main(String[] args) throws IOException {

        ExecutorService pool = Executors.newFixedThreadPool(10);

        // объект сервера, который резервирует определенный порт,
        // а затем прослушивает новые соединения (сокеты)
        ServerSocket serverSocket = new ServerSocket(SERVER_PORT_GATEWAY);

        UDPLoadBalancerClient udpThread = new UDPLoadBalancerClient();
        pool.execute(udpThread);


        while (true) {
            Socket socket = serverSocket.accept();
            Runnable serverTaskThread = new ServerTaskGateway(socket);
            pool.execute(serverTaskThread);
        }
    }
}