package com.example.projeto_adopet;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.content.Intent;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final int SPLASH_TIME_OUT = 5000; // Tempo de espera de 5 segundos

        new Handler().postDelayed(() -> {
            Intent homeIntent = new Intent(MainActivity.this, Login.class);
            startActivity(homeIntent);
            finish();
        }, SPLASH_TIME_OUT);
    }
}
