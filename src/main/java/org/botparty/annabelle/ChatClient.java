package org.botparty.annabelle;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.botparty.annabelle.domain.CommunicationData;
import org.java_websocket.WebSocket;
import org.java_websocket.client.WebSocketClient;
import org.java_websocket.handshake.ServerHandshake;

import java.net.URI;
import java.net.URISyntaxException;

public class ChatClient {

    // singleton pattern
    // todo - dependency injection
    public static ChatClient getInstance() {
        if(instance == null) {
            instance = new ChatClient();
        }
        return instance;
    }
    private static ChatClient instance;


    void send(CommunicationData communicationData) {
        if(this.client != null && this.client.getReadyState() == WebSocket.READYSTATE.OPEN) {
            ObjectMapper mapper = new ObjectMapper();
            try {
                String convertedText = mapper.writeValueAsString(communicationData);
                this.client.send(convertedText);
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
        }
    }

    void connectWebSocket(String address) {
        URI uri;
        try {
            String socketAddress = String.format("ws://%s:%s", address, "8080");
            String toastText = String.format("Connecting to %s", socketAddress);
            System.out.println(toastText);
            uri = new URI(socketAddress);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return;
        }

         this.client = new WebSocketClient(uri) {
            @Override
            public void onOpen(ServerHandshake handshakedata) {
               System.out.println("Opened");
            }

            @Override
            public void onMessage(final String message) {
               System.out.println(message);
            }

            @Override
            public void onClose(int code, String reason, boolean remote) {
                System.out.println("Closed " + reason);

            }

            @Override
            public void onError(Exception ex) {

               System.out.println( "Error " + ex.getMessage());
            }
        };

        this.client.connect();
    }

    private WebSocketClient client;
}
