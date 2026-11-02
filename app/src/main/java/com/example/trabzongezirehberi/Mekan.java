package com.example.trabzongezirehberi;

import android.content.Context;
import android.content.Intent;

public class Mekan {
    int id;
    String ad;
    String ilce;
    String kategori;
    int gorsel;
    int favori;
    String aciklama;

    public Mekan(int id, String ad, String ilce, String kategori, int gorsel, int favori, String aciklama) {
        this.id = id;
        this.ad = ad;
        this.ilce = ilce;
        this.kategori = kategori;
        this.gorsel = gorsel;
        this.favori = favori;
        this.aciklama = aciklama;
    }

    //Kod tekrarını önleyen akıllı Intent oluşturucu metot
    public Intent detayIntentiOlustur(Context context) {
        Intent intent = new Intent(context, DetayActivity.class);
        intent.putExtra("ad", this.ad);
        intent.putExtra("ilce", this.ilce);
        intent.putExtra("kategori", this.kategori);
        intent.putExtra("gorsel", this.gorsel);
        intent.putExtra("aciklama", this.aciklama);
        return intent;
    }
}