package com.sarmadali.geminiaitesting;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

public class MainActivity extends AppCompatActivity {

    private EditText promptQuestion;
    private AppCompatButton getResponseBtn;
    private TextView answerText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        promptQuestion = findViewById(R.id.question);
        getResponseBtn = findViewById(R.id.btnAnswer);
        answerText = findViewById(R.id.answer);

        getResponseBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                GeminiAiModel geminiAiModel = new GeminiAiModel();
                String query = promptQuestion.getText().toString();

                answerText.setText("");
                promptQuestion.setText("");

                geminiAiModel.getResponse(query, new ResponseInterface() {
                    @Override
                    public void onResponse(String response) {
                        answerText.setText(response);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(MainActivity.this, "Error: See logs for details", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }
}