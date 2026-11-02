package com.example.trabzongezirehberi;


import android.content.ContentValues;
import android.content.DialogInterface;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

public class KayitOlActivity extends AppCompatActivity {
    EditText etAd, etSoyad, etEposta, etSifre;
    RadioGroup rgCinsiyet;
    CheckBox cbSozlesme;
    Button btnKaydet;
    DatabaseHelper veritabaniYardimcisi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kayit_ol);

        etAd = findViewById(R.id.etKayitAd);
        etSoyad = findViewById(R.id.etKayitSoyad);
        etEposta = findViewById(R.id.etKayitEposta);
        etSifre = findViewById(R.id.etKayitSifre);
        rgCinsiyet = findViewById(R.id.rgCinsiyet);
        cbSozlesme = findViewById(R.id.cbSozlesme);
        btnKaydet = findViewById(R.id.btnKaydet);

        veritabaniYardimcisi = new DatabaseHelper(this);

        btnKaydet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                kayitYap();
            }
        });

        TextView tvGirisDon = findViewById(R.id.tvGirisDon);
        // Metne tıklama özelliği eklenir
        tvGirisDon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Giriş Yap sayfasına geri döndürür.
                finish();
            }
        });
    }
    private void kayitYap() {
        String ad = etAd.getText().toString().trim();
        String soyad = etSoyad.getText().toString().trim();
        String eposta = etEposta.getText().toString().trim();
        String sifre = etSifre.getText().toString().trim();

        // Cinsiyet seçimi kontrolü
        int secilenId = rgCinsiyet.getCheckedRadioButtonId();
        String cinsiyet = "";
        if (secilenId != -1) {
            RadioButton rb = findViewById(secilenId);
            cinsiyet = rb.getText().toString();
        }

        // Doğrulamalar
        if (ad.isEmpty() || soyad.isEmpty() || eposta.isEmpty() || sifre.isEmpty() || cinsiyet.isEmpty()) {
            Toast.makeText(this, "Lütfen tüm alanları doldurun!", Toast.LENGTH_SHORT).show();
            return;
        }

        if (!cbSozlesme.isChecked()) {
            Toast.makeText(this, "Sözleşmeyi onaylamanız gerekiyor!", Toast.LENGTH_SHORT).show();
            return;
        }


        // E-posta formatı doğru mu? (@ işareti vb. var mı)
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(eposta).matches()) {
            Toast.makeText(KayitOlActivity.this, "Lütfen geçerli bir e-posta adresi giriniz!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Şifre en az 6 karakter mi?
        if (sifre.length() < 6) {
            Toast.makeText(KayitOlActivity.this, "Şifreniz en az 6 karakter olmalıdır!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Bu e-posta daha önce kullanılmış mı?
        if (veritabaniYardimcisi.epostaKayitliMi(eposta)) {
            Toast.makeText(KayitOlActivity.this, "Bu e-posta adresi sistemde zaten kayıtlı!", Toast.LENGTH_SHORT).show();
            return;
        }

        // Veritabanına yazma işlemi (Yukarıdaki engelleri aşarsa buraya gelir)
        SQLiteDatabase db = veritabaniYardimcisi.getWritableDatabase();
        ContentValues degerler = new ContentValues();
        degerler.put(DatabaseHelper.SUTUN_AD, ad);
        degerler.put(DatabaseHelper.SUTUN_SOYAD, soyad);
        degerler.put(DatabaseHelper.SUTUN_EPOSTA, eposta);
        degerler.put(DatabaseHelper.SUTUN_SIFRE, sifre);
        degerler.put(DatabaseHelper.SUTUN_CINSIYET, cinsiyet);

        long sonuc = db.insert(DatabaseHelper.TABLO_KULLANICILAR, null, degerler);

        if (sonuc != -1) {
            basariliUyariGoster();
        } else {
            Toast.makeText(this, "Kayıt sırasında bir hata oluştu!", Toast.LENGTH_SHORT).show();
        }
    }

    private void basariliUyariGoster() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Başarılı!");
        builder.setMessage("Kayıt başarıyla oluşturuldu.");
        builder.setPositiveButton("Tamam", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish(); // Kayıt bitince sayfayı kapatıp giriş ekranına döner
            }
        });
        builder.show();
    }
}
