package com.example.gemini_2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    TextView tv1;
    EditText et1;
    Button bt1;
    private void typeText(String response,String Query) {
        tv1.append("User : "+Query);
        tv1.append("\n\nGemini : ");
        final Handler handler = new Handler(getMainLooper());
        handler.postDelayed(new Runnable() {
            int index = 0;

            @Override
            public void run() {
                tv1.append(String.valueOf(response.charAt(index)));
                index++;
                if (index < response.length()) {
                    // If there are more characters to type, post again with a delay
                    handler.postDelayed(this, 0); // Adjust delay as needed
                }
            }
        }, 0);
        tv1.append("\n\n");
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv1 = findViewById(R.id.tv1);
        bt1 = findViewById(R.id.bt1);
        et1 = findViewById(R.id.et1);

        bt1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "Generating....", Toast.LENGTH_SHORT).show();
                String query = et1.getText().toString();
                et1.setText("");
                Brain_2 b2 =  new Brain_2();
                b2.getResponse(query, new response() {
                    @Override
                    public void onResponse(String response) {
                        tv1.append("\n\n");
                        typeText(response,query);
                    }

                    @Override
                    public void onError(Throwable throwable) {
                        Toast.makeText(MainActivity.this,throwable.getMessage(), Toast.LENGTH_LONG).show();
                    }
                });

            }
        });

    }
}