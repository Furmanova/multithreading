package com.telran;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;

public class ServerTaskGateway implements Runnable {
    Socket socket;


    public ServerTaskGateway(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            PrintStream socketOut = new PrintStream(socket.getOutputStream());
            // объект получает данные от клиента
            BufferedReader socketIn = new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = socketIn.readLine()) != null) {
                String response =
                        String.format("The string %s was accepted from the client and handled", line);
                System.out.println(response);
                socketOut.println(response);

                }
                socket.close();


            } catch(Exception e){
                e.printStackTrace();
        }
    }
}