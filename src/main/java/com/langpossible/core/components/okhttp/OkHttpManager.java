package com.langpossible.core.components.okhttp;

import okhttp3.*;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpManager {

    private final OkHttpClient client;

    private static volatile OkHttpManager manager;

    private OkHttpManager() {
        this.client = new OkHttpClient().newBuilder()
                .readTimeout(100, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
    }

    public static OkHttpManager getInstance() {
        if (manager == null) {
            synchronized (OkHttpManager.class) {
                if (manager == null) {
                    manager = new OkHttpManager();
                }
            }
        }
        return manager;
    }

    public String get(String url, Map<String, String> headerParams, Map<String, String> bodyParams) {
        try {
            Request request = new Request.Builder()
                    .url(this.parsingUrl(url, bodyParams))
                    .headers(this.parsingHeaders(headerParams))
                    .get()
                    .build();
            Response response = this.client.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (IOException ioException) {
            // log.error("[OkHttpManager] <GET 请求> <执行异常>", ioException);
        }
        return null;
    }

    public String post(String url, Map<String, String> headerParams, String body) {
        try {
            RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(url)
                    .headers(this.parsingHeaders(headerParams))
                    .post(requestBody)
                    .build();
            Response response = this.client.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            }
        } catch (IOException ioException) {
            // log.error("[OkHttpManager] <POST 请求> <执行异常>", ioException);
        }
        return null;
    }

    public void stream(String url, Map<String, String> headerParams, String body, Callback callback) {
        try {
            RequestBody requestBody = RequestBody.create(body, MediaType.parse("application/json; charset=utf-8"));
            Request request = new Request.Builder()
                    .url(url)
                    .headers(this.parsingHeaders(headerParams))
                    .post(requestBody)
                    .build();
            this.client.newCall(request).enqueue(callback);
        } catch (Exception e) {
            // log.error("[OkHttpManager] <POST Stream 请求> <执行异常>", e);
        }
    }

    private String parsingUrl(String url, Map<String, String> bodyParams) {
        if (bodyParams == null) {
            return url;
        }
        return url + this.parsingQueryString(bodyParams);
    }

    private String parsingQueryString(Map<String, String> bodyParams) {
        if (bodyParams == null || bodyParams.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder("?");
        for (Map.Entry<String, String> entry : bodyParams.entrySet()) {
            if (!sb.isEmpty()) {
                sb.append("&");
            }
            sb.append(entry.getKey()).append("=").append(entry.getValue());
        }
        return sb.toString();
    }

    private Headers parsingHeaders(Map<String, String> headerParams) {
        if (headerParams == null || headerParams.isEmpty()) {
            return new Headers.Builder().build();
        }
        Headers.Builder builder = new Headers.Builder();
        for (Map.Entry<String, String> entry : headerParams.entrySet()) {
            builder.add(entry.getKey(), entry.getValue());
        }
        return builder.build();
    }

}
