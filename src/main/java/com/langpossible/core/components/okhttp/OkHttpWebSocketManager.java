package com.langpossible.core.components.okhttp;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;

import java.util.concurrent.TimeUnit;

public class OkHttpWebSocketManager {

    private final WebSocket ws;

    private static volatile OkHttpWebSocketManager manager;

    private OkHttpWebSocketManager(String url, WebSocketListener listener) {
        OkHttpClient client = new OkHttpClient.Builder()
                .pingInterval(10, TimeUnit.SECONDS)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .build();
        this.ws = client.newWebSocket(request, listener);
    }

    public static OkHttpWebSocketManager getInstance(String url, WebSocketListener listener) {
        if (manager == null) {
            synchronized (OkHttpManager.class) {
                if (manager == null) {
                    manager = new OkHttpWebSocketManager(url, listener);
                }
            }
        }
        return manager;
    }

    public void send(String message) {
        this.ws.send(message);
    }

}
