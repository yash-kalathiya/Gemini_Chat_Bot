package com.example.gemini_2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    TextView tv1;
    EditText et1;
    Button bt1;

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

    private void typeText(String response, String Query) {
        tv1.append("User : "+Query);
        tv1.append("\n\nJarvis : ");
        for (int i = 0; i < response.length(); i++) {
            final int index = i;
            final Handler handler = new Handler(getMainLooper());
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {

                    tv1.append(String.valueOf(response.charAt(index)));
                    final ScrollView scrollView = (ScrollView) findViewById(R.id.scrollView2);
                    scrollView.post(new Runnable() {
                        @Override
                        public void run() {

                            scrollView.fullScroll(View.FOCUS_DOWN);
                        }
                    });
                }
            }, 5*i);
        }
        tv1.append("\n\n");
    }
}
