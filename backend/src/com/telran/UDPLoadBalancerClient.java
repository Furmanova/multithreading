package com.telran;

import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.net.*;

public class UDPLoadBalancerClient implements Runnable {
    String host = "localhost";
    Integer port= 4000;


    @Override
    public void run() {
        try {
            while (true) {
                //CPU utilisation
                com.sun.management.OperatingSystemMXBean osBean =
                        (com.sun.management.OperatingSystemMXBean) ManagementFactory
                                .getPlatformMXBean(OperatingSystemMXBean.class);
                Long cpuLoad = Math.round(osBean.getSystemCpuLoad() * 100.0);
                String sentence = host+":"+port+"#"+cpuLoad;

                byte[] sendData = sentence.getBytes();
                DatagramPacket SendPacket =
                        new DatagramPacket(sendData, sendData.length);
                sentence = new String(SendPacket.getData(), 0, SendPacket.getLength());
                System.out.println(sentence);

                Thread.sleep(5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
