package com.example.trabzongezirehberi;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new Handler(Looper.getMainLooper()).postDelayed(new Runnable() {
            @Override
            public void run() {
                // Beni Hatırla kontrolü
                SharedPreferences sp = getSharedPreferences("GirisBilgisi", MODE_PRIVATE);
                boolean otomatikGiris = sp.getBoolean("otomatikGiris", false);

                Intent intent;
                if (otomatikGiris) {
                    // Kullanıcı beni hatırla demiş, direkt ana sayfaya
                    intent = new Intent(SplashActivity.this, MainActivity.class);
                } else {
                    // Giriş yapmamış, giriş ekranına
                    intent = new Intent(SplashActivity.this, GirisYapActivity.class);
                }

                startActivity(intent);
                finish();
            }
        }, 3000);

    }
}