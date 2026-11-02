package com.example.trabzongezirehberi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class FavorilerActivity extends AppCompatActivity {

    ListView lvFavoriler;
    DatabaseHelper dbHelper;
    ArrayList<Mekan> favoriListesi;
    MekanAdaptoru adaptor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favoriler);

        lvFavoriler = findViewById(R.id.lvFavoriler);
        dbHelper = new DatabaseHelper(this);

        // Veritabanından sadece favorileri çek
        favoriListesi = dbHelper.favoriMekanlariGetir();

        if (favoriListesi.isEmpty()) {
            Toast.makeText(this, "Henüz favorilere eklediğiniz bir mekan yok.", Toast.LENGTH_LONG).show();
        }

        // Ana sayfada kullandığımız aynı adaptörü burada da kullanıyoruz
        adaptor = new MekanAdaptoru(this, favoriListesi);
        lvFavoriler.setAdapter(adaptor);

        // Favorilerden birine tıklanırsa Detay Sayfasına git
        lvFavoriler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mekan secilenMekan = favoriListesi.get(position);

                startActivity(secilenMekan.detayIntentiOlustur(FavorilerActivity.this));
            }
        });
    }
    // SAYFAYA GERİ DÖNÜLDÜĞÜNDE LİSTEYİ ANINDA GÜNCELLE
    @Override
    protected void onResume() {
        super.onResume();
        if (favoriListesi != null && adaptor != null) {
            favoriListesi.clear(); // Eski listeyi temizler
            favoriListesi.addAll(dbHelper.favoriMekanlariGetir()); // Veritabanından en güncel halini çek
            adaptor.notifyDataSetChanged(); // Ekrana yansıtır
        }
    }
}