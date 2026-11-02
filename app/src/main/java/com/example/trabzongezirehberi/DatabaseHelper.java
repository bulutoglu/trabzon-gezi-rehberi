package com.example.trabzongezirehberi;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import java.util.ArrayList;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String VERITABANI_ADI = "TrabzonRehberi.db";
    private static final int VERITABANI_VERSIYONU = 5; // Versiyon 5 oldu!

    public static final String TABLO_KULLANICILAR = "kullanicilar";
    public static final String SUTUN_ID = "id";
    public static final String SUTUN_AD = "ad";
    public static final String SUTUN_SOYAD = "soyad";
    public static final String SUTUN_EPOSTA = "eposta";
    public static final String SUTUN_SIFRE = "sifre";
    public static final String SUTUN_CINSIYET = "cinsiyet";

    public static final String TABLO_MEKANLAR = "mekanlar";
    public static final String SUTUN_MEKAN_ID = "mekan_id";
    public static final String SUTUN_MEKAN_AD = "mekan_adi";
    public static final String SUTUN_MEKAN_ILCE = "mekan_ilcesi";
    public static final String SUTUN_MEKAN_KATEGORI = "mekan_kategori";
    public static final String SUTUN_MEKAN_GORSEL = "mekan_gorsel";
    public static final String SUTUN_MEKAN_FAVORI = "mekan_favori";
    public static final String SUTUN_MEKAN_ACIKLAMA = "mekan_aciklama"; // Yeni Sütun

    private static final String KULLANICI_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_KULLANICILAR + " (" +
            SUTUN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUTUN_AD + " TEXT, " +
            SUTUN_SOYAD + " TEXT, " + SUTUN_EPOSTA + " TEXT, " + SUTUN_SIFRE + " TEXT, " + SUTUN_CINSIYET + " TEXT)";

    private static final String MEKANLAR_TABLOSU_OLUSTUR = "CREATE TABLE " + TABLO_MEKANLAR + " (" +
            SUTUN_MEKAN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + SUTUN_MEKAN_AD + " TEXT, " +
            SUTUN_MEKAN_ILCE + " TEXT, " + SUTUN_MEKAN_KATEGORI + " TEXT, " +
            SUTUN_MEKAN_GORSEL + " INTEGER, " + SUTUN_MEKAN_FAVORI + " INTEGER, " +
            SUTUN_MEKAN_ACIKLAMA + " TEXT)";

    //Kurucu metot
    public DatabaseHelper(Context context) {
        super(context, VERITABANI_ADI, null, VERITABANI_VERSIYONU);
    }

    //Boş veritabanının içine tabloları inşa etme
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(KULLANICI_TABLOSU_OLUSTUR);
        db.execSQL(MEKANLAR_TABLOSU_OLUSTUR);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_KULLANICILAR);
        db.execSQL("DROP TABLE IF EXISTS " + TABLO_MEKANLAR);
        onCreate(db);
    }

    public void mekanEkle(String ad, String ilce, String kategori, int gorsel, String aciklama) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_MEKAN_AD, ad);
        values.put(SUTUN_MEKAN_ILCE, ilce);
        values.put(SUTUN_MEKAN_KATEGORI, kategori);
        values.put(SUTUN_MEKAN_GORSEL, gorsel);
        values.put(SUTUN_MEKAN_FAVORI, 0);
        values.put(SUTUN_MEKAN_ACIKLAMA, aciklama); // Veritabanına kaydediliyor
        db.insert(TABLO_MEKANLAR, null, values);
        db.close();
    }

    public void favoriDurumunuGuncelle(int id, int yeniDurum) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(SUTUN_MEKAN_FAVORI, yeniDurum);
        db.update(TABLO_MEKANLAR, values, SUTUN_MEKAN_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public boolean mekanTablosuBosMu() {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM " + TABLO_MEKANLAR, null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        return count == 0;
    }

    public ArrayList<Mekan> tumMekanlariGetir() {
        ArrayList<Mekan> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_MEKANLAR, null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_ID));
                String ad = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_AD));
                String ilce = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_ILCE));
                String kategori = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_KATEGORI));
                int gorsel = cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_GORSEL));
                int favori = cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_FAVORI));
                // Veritabanından açıklamayı çekiyoruz
                String aciklama = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_ACIKLAMA));

                liste.add(new Mekan(id, ad, ilce, kategori, gorsel, favori, aciklama));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return liste;
    }
    // ARAMA VE FİLTRELEME METODU
    public ArrayList<Mekan> mekanlariFiltrele(String aranacakKelime, String secilenKategori) {
        ArrayList<Mekan> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Temel SQL Sorgumuz (1=1 hilesi ile üzerine şartları kolayca ekleyebiliriz)
        String sorgu = "SELECT * FROM " + TABLO_MEKANLAR + " WHERE 1=1";
        ArrayList<String> parametreler = new ArrayList<>();

        // 1. Eğer bir kelime yazılmışsa (Ada veya İlçeye göre ara)
        if (!aranacakKelime.trim().isEmpty()) {
            sorgu += " AND (" + SUTUN_MEKAN_AD + " LIKE ? OR " + SUTUN_MEKAN_ILCE + " LIKE ?)";
            parametreler.add("%" + aranacakKelime + "%");
            parametreler.add("%" + aranacakKelime + "%");
        }

        // 2. Eğer "Tüm Kategoriler" dışında spesifik bir kategori seçilmişse
        if (!secilenKategori.equals("Tüm Kategoriler")) {
            sorgu += " AND " + SUTUN_MEKAN_KATEGORI + " = ?";
            parametreler.add(secilenKategori);
        }

        Cursor cursor = db.rawQuery(sorgu, parametreler.toArray(new String[0]));

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_ID));
                String ad = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_AD));
                String ilce = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_ILCE));
                String kategori = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_KATEGORI));
                int gorsel = cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_GORSEL));
                int favori = cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_FAVORI));
                String aciklama = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_ACIKLAMA));

                liste.add(new Mekan(id, ad, ilce, kategori, gorsel, favori, aciklama));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return liste;
    }
    // SADECE FAVORİ OLAN MEKANLARI GETİREN METOT
    public ArrayList<Mekan> favoriMekanlariGetir() {
        ArrayList<Mekan> liste = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        // Sadece mekan_favori sütunu 1 (yani kırmızı kalp) olanları seçiyoruz
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_MEKANLAR + " WHERE " + SUTUN_MEKAN_FAVORI + " = 1", null);

        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_ID));
                String ad = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_AD));
                String ilce = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_ILCE));
                String kategori = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_KATEGORI));
                int gorsel = cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_GORSEL));
                int favori = cursor.getInt(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_FAVORI));
                String aciklama = cursor.getString(cursor.getColumnIndexOrThrow(SUTUN_MEKAN_ACIKLAMA));

                liste.add(new Mekan(id, ad, ilce, kategori, gorsel, favori, aciklama));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return liste;
    }
    // E-POSTA KONTROLÜ İÇİN
    public boolean epostaKayitliMi(String eposta) {
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_KULLANICILAR + " WHERE " + SUTUN_EPOSTA + " = ?", new String[]{eposta});
        boolean varMi = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return varMi;
    }
    // GİRİŞ YAPARKEN E-POSTA VE ŞİFREYİ KONTROL EDEN METOT
    public boolean kullaniciGirisKontrolu(String eposta, String sifre) {
        SQLiteDatabase db = this.getReadableDatabase();
        android.database.Cursor cursor = db.rawQuery("SELECT * FROM " + TABLO_KULLANICILAR + " WHERE " + SUTUN_EPOSTA + " = ? AND " + SUTUN_SIFRE + " = ?", new String[]{eposta, sifre});
        boolean basarili = cursor.getCount() > 0;
        cursor.close();
        db.close();
        return basarili;
    }
}