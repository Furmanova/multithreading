package com.telran;


import com.telran.backend.IBackendServerStorage;
import com.telran.backend.SetBackendStorage;
import com.telran.task.GatewayUdpSender;
import com.telran.task.BackendUdpListener;

public class Main {
    // the port which listens to Backends
   static final int UDP_BACKEND_LISTENER_PORT = 3000;
   static final String GATEWAY_HOST = "localhost";
    // coordinates of Gateway
   static final int UDP_GATEWAY_PORT = 5001;
   static final int SENDING_PERIOD_MILLIS = 100;

    public static void main(String[] args) {
            IBackendServerStorage storage = new SetBackendStorage();

            // start listening load data from the backends
            BackendUdpListener backendUdpListener = new BackendUdpListener(storage, UDP_BACKEND_LISTENER_PORT);
            Thread listenerThread = new Thread(backendUdpListener);
            listenerThread.start();

            // start periodically send optimal com.tel-ran.backend data to the Gateway
            GatewayUdpSender gatewaySender = new GatewayUdpSender(GATEWAY_HOST, UDP_GATEWAY_PORT, storage, SENDING_PERIOD_MILLIS);
            Thread senderThread = new Thread(gatewaySender);
            senderThread.start();
        }
}
