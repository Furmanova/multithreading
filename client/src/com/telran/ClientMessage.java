package com.telran;

import java.io.*;
import java.net.Socket;

public class ClientMessage {

         static final String SERVER_HOST = "localhost";
        static final int GATEWAY_PORT = 5000;
        public static void main(String[] args) throws IOException {
            Socket socket =new Socket(SERVER_HOST, GATEWAY_PORT);

            BufferedReader consoledReader =
                    new BufferedReader(new InputStreamReader(System.in));
            PrintStream socketOut = new PrintStream(socket.getOutputStream());
            BufferedReader socketInput =
                    new BufferedReader(new InputStreamReader(socket.getInputStream()));

            String line;
            while ((line = consoledReader.readLine())!=null && !line.equals("exit")){
                socketOut.println(line);
                System.out.println("from the gateway: " + socketInput.readLine());
            }
            socket.close();
        }

    }

