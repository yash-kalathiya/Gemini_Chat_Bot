package com.example.gemini_2;

public interface response {

    void onResponse(String response) throws InterruptedException;

    void onError(Throwable throwable);
}
