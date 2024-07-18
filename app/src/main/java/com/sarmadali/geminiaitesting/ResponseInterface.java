package com.sarmadali.geminiaitesting;

public interface ResponseInterface {

    void onResponse(String response);
    void onError(Throwable throwable);
}
