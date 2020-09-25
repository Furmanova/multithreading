package com.telran;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.*;

public class UDPLoadBalancerClient implements Runnable {
    String host = "localhost";
    Integer port= 4000;

    String loadBalancerHost = "localhost";
    Integer loadBalancerPort = 3000;


    @Override
    public void run() {
        try {
            while (true) {
                //CPU utilisation
                com.sun.management.OperatingSystemMXBean osBean =
                        (com.sun.management.OperatingSystemMXBean) ManagementFactory
                                .getPlatformMXBean(OperatingSystemMXBean.class);
                Long aLong = Math.round(osBean.getSystemCpuLoad() * 100.0);
                Integer cpuLoad = aLong == null ? null : Math.toIntExact(aLong);
                String sentence = host+":"+port+"#"+cpuLoad;

               DatagramSocket clientSocket = new DatagramSocket();
              InetAddress IPAddress = InetAddress.getByName(loadBalancerHost);

                byte[] sendData = sentence.getBytes();
                DatagramPacket sendPacket =
                        new DatagramPacket(sendData, sendData.length, IPAddress, loadBalancerPort);
               clientSocket.send(sendPacket);
                sentence = new String(sendPacket.getData(), 0, sendPacket.getLength());

                System.out.println(sentence);


                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
