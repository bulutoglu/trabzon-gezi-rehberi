package com.example.trabzongezirehberi;

import android.content.Intent;
import android.content.SharedPreferences; // YENİ: Hafıza kütüphanesi eklendi
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox; // YENİ: CheckBox kütüphanesi eklendi
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class GirisYapActivity extends AppCompatActivity {

    EditText etEposta, etSifre;
    CheckBox cbBeniHatirla;
    Button btnGiris;
    TextView tvKayitGit;
    DatabaseHelper veritabaniYardimcisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_giris_yap);

        etEposta = findViewById(R.id.etGirisEposta);
        etSifre = findViewById(R.id.etGirisSifre);
        cbBeniHatirla = findViewById(R.id.cbBeniHatirla);
        btnGiris = findViewById(R.id.btnGirisYap);
        tvKayitGit = findViewById(R.id.tvKayitOlGit);

        veritabaniYardimcisi = new DatabaseHelper(this);

        // Giriş Yap butonuna tıklandığında
        btnGiris.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                girisKontrol();
            }
        });

        // Kayıt Ol yazısına tıklandığında Kayıt sayfasına git
        tvKayitGit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(GirisYapActivity.this, KayitOlActivity.class);
                startActivity(intent);
            }
        });
    }

    private void girisKontrol() {
        String eposta = etEposta.getText().toString().trim();
        String sifre = etSifre.getText().toString().trim();
        boolean beniHatirla = cbBeniHatirla.isChecked(); // CheckBox işaretli mi kontrolü

        if (eposta.isEmpty() || sifre.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Veritabanı sorgusunu artık doğrudan DatabaseHelper içindeki metot yapıyor
        if (veritabaniYardimcisi.kullaniciGirisKontrolu(eposta, sifre)) {

            // Eğer "Beni Hatırla" işaretlendiyse telefona kalıcı olarak kaydet
            if (beniHatirla) {
                SharedPreferences sp = getSharedPreferences("GirisBilgisi", MODE_PRIVATE);
                sp.edit().putBoolean("otomatikGiris", true).apply();
            }

            Toast.makeText(this, "Hoş geldiniz!", Toast.LENGTH_SHORT).show();

            // Ana sayfaya yönlendir
            Intent intent = new Intent(GirisYapActivity.this, MainActivity.class);
            startActivity(intent);
            finish(); // Geri tuşuyla tekrar bu ekrana dönmesin diye

        } else {
            // Bilgiler hatalıysa
            Toast.makeText(this, "E-posta veya şifre hatalı!", Toast.LENGTH_SHORT).show();
        }
    }
}