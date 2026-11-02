package com.example.trabzongezirehberi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class SorgulamaActivity extends AppCompatActivity {

    EditText etArama;
    Spinner spinnerKategori;
    Button btnFiltrele;
    ListView lvSonuclar;

    DatabaseHelper dbHelper;
    MekanAdaptoru adaptor;
    ArrayList<Mekan> sonucListesi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sorgulama);

        etArama = findViewById(R.id.etAramaKelimesi);
        spinnerKategori = findViewById(R.id.spinnerKategori);
        btnFiltrele = findViewById(R.id.btnFiltrele);
        lvSonuclar = findViewById(R.id.lvSorguSonuclari);

        dbHelper = new DatabaseHelper(this);

        // Kategori Menüsünü Doldururuz
        String[] kategoriler = {"Tüm Kategoriler", "Tarihi Yer", "Doğal Güzellik", "Müze / Köşk", "Seyir Alanı", "Park"};
        ArrayAdapter<String> spinnerAdaptoru = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, kategoriler);
        spinnerKategori.setAdapter(spinnerAdaptoru);

        // Sayfa ilk açıldığında tüm mekanlar listelensin
        sonucListesi = dbHelper.tumMekanlariGetir();
        adaptor = new MekanAdaptoru(this, sonucListesi);
        lvSonuclar.setAdapter(adaptor);

        // FİLTRELE BUTONUNA BASILINCA YAPILACAKLAR
        btnFiltrele.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String arananKelime = etArama.getText().toString();
                String secilenKategori = spinnerKategori.getSelectedItem().toString();

                // Veritabanından yeni sonuçları çek ve listeyi güncelle
                sonucListesi.clear();
                sonucListesi.addAll(dbHelper.mekanlariFiltrele(arananKelime, secilenKategori));
                adaptor.notifyDataSetChanged();

                if(sonucListesi.isEmpty()) {
                    Toast.makeText(SorgulamaActivity.this, "Kayıt bulunamadı!", Toast.LENGTH_SHORT).show();
                }
            }
        });

        // Arama Sonucundan Bir Mekana Tıklayınca Detay Sayfasına Gitme
        lvSonuclar.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mekan secilenMekan = sonucListesi.get(position);

                // ESKİ UZUN INTENT SATIRLARI YERİNE SADECE BU TEK SATIRI YAZIYORUZ:
                startActivity(secilenMekan.detayIntentiOlustur(SorgulamaActivity.this));
            }
        });
    }
}