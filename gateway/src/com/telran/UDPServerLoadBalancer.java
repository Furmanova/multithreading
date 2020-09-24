package com.telran;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;

public class UDPServerLoadBalancer implements Runnable {
    public String sentence = "localhost:4000";

    @Override
    public void run() {
        try {
            DatagramSocket serverSocket = new DatagramSocket(5001);

            while (true) {
                byte[] receiveData = new byte[1024];
                DatagramPacket receivePacket =
                        new DatagramPacket(receiveData, receiveData.length);
                serverSocket.receive(receivePacket);
                sentence = new String(receivePacket.getData(), 0, receivePacket.getLength());
                System.out.println("RECEIVED: " + sentence);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}