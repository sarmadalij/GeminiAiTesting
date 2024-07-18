package com.sarmadali.geminiaitesting;

import android.view.WindowInsetsAnimation;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.BlockThreshold;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.ai.client.generativeai.type.GenerationConfig;
import com.google.ai.client.generativeai.type.HarmCategory;
import com.google.ai.client.generativeai.type.SafetySetting;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.Collections;
import java.util.concurrent.Executor;

public class GeminiAiModel {

    //to get response
    public void getResponse(String query, ResponseInterface callBack){

        GenerativeModelFutures model = getModel();
        Content content = new Content.Builder().addText(query).build();
        Executor executor = Runnable::run;

        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);
        Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
            @Override
            public void onSuccess(GenerateContentResponse result) {
                String resultText = result.getText();

                callBack.onResponse(resultText);
            }

            @Override
            public void onFailure(Throwable t) {
                t.printStackTrace();
                callBack.onError(t);
            }
        }, executor);
    }
    private GenerativeModelFutures getModel(){
        String apiKey = BuildConfig.apiKey;

        //use to control unsafe content
        SafetySetting safetySetting = new SafetySetting(HarmCategory.HARASSMENT,
                BlockThreshold.ONLY_HIGH);

        GenerationConfig.Builder configBuilder = new GenerationConfig.Builder();
        configBuilder.temperature = 0.9f;
        configBuilder.topK = 16;
        configBuilder.topP = 0.1f;

        GenerationConfig generationConfig = configBuilder.build();

        GenerativeModel generativeModel = new GenerativeModel(
                "gemini-1.5-flash",apiKey, generationConfig,
                Collections.singletonList(safetySetting)
        );

        return GenerativeModelFutures.from(generativeModel);
    }
}
