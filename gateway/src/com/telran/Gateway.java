package com.telran;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;

import java.net.Socket;


public class Gateway implements Runnable {
    Socket gatewaySocket;
    UDPServerLoadBalancer udp;

    public Gateway(Socket socket, UDPServerLoadBalancer udp) {
        this.gatewaySocket = socket;
        this.udp = udp;
    }

    @Override
    public void run() {
        try {
            // Client calling
            PrintStream socketOutClient = new PrintStream(gatewaySocket.getOutputStream());
            BufferedReader socketInClient = new BufferedReader(
                    new InputStreamReader(gatewaySocket.getInputStream()));

            String line;
            while ((line = socketInClient.readLine()) != null) {
                System.out.println(String.format("The string %s was accepted from the client", line));

                //backend calling

                    String[] res = udp.sentence.split(":");
                    String host = res[0];
                    Integer port = Integer.parseInt(res[1]);
                    udp.sentence.split(":");

                    Socket backSocket = new Socket(host, port);

                    PrintStream socketOutBack = new PrintStream(backSocket.getOutputStream());
                    BufferedReader socketInputBack =
                            new BufferedReader(new InputStreamReader(backSocket.getInputStream()));

                    //responding to backend
                    socketOutBack.println(line);
                    String response = socketInputBack.readLine();
                    backSocket.close();

                    //responding to client
                    System.out.println(String.format(
                            "Response %s was received from the backend", response));
                    socketOutClient.println(response);
                }

            gatewaySocket.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}