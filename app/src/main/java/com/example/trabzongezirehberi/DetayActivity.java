package com.example.trabzongezirehberi;

import android.content.Intent;
import android.os.Bundle;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class DetayActivity extends AppCompatActivity {

    ImageView imgFoto;
    TextView tvAd, tvIlce, tvKategori, tvAciklama;
    RatingBar ratingBar;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detay);

        // Arayüz bileşenlerini Javaya bağlıyoruz
        imgFoto = findViewById(R.id.imgDetayFoto);
        tvAd = findViewById(R.id.tvDetayAd);
        tvIlce = findViewById(R.id.tvDetayIlce);
        tvKategori = findViewById(R.id.tvDetayKategori);
        tvAciklama = findViewById(R.id.tvDetayAciklama);
        ratingBar = findViewById(R.id.ratingBar);
        webView = findViewById(R.id.webView);

        // Ana sayfadan veya diğer sayfalardan gönderilen verileri alıyoruz
        Intent gelenIntent = getIntent();
        String ad = gelenIntent.getStringExtra("ad");
        String ilce = gelenIntent.getStringExtra("ilce");
        String kategori = gelenIntent.getStringExtra("kategori");
        int gorsel = gelenIntent.getIntExtra("gorsel", R.drawable.trabzonlogo);
        String gercekAciklama = gelenIntent.getStringExtra("aciklama");

        // Verileri ekrandaki yerlerine yerleştiriyoruz
        imgFoto.setImageResource(gorsel);
        tvAd.setText(ad);
        tvIlce.setText(ilce);
        tvKategori.setText(kategori);
        tvAciklama.setText(gercekAciklama);

        // Puanlama (RatingBar) dinleyicisi
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Toast.makeText(DetayActivity.this, ad + " mekanına " + v + " yıldız verdiniz!", Toast.LENGTH_SHORT).show();
            }
        });

        // WebView Güvenlik ve Performans Ayarları
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setAllowFileAccess(false);

        // Akıllı Filtreleme Yapan WebViewClient
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                String url = request.getUrl().toString();

                // EĞER KULLANICI EKRANA DOKUNDUYSA (Tıklama İşlemi)
                if (request.hasGesture()) {
                    // "Uygulamada Aç" (intent://) VEYA "Yol Tarifi" (/dir) linklerine tıklandıysa
                    if (url.startsWith("intent://") || url.contains("/dir") || url.contains("maps")) {
                        try {
                            Intent intent;
                            if (url.startsWith("intent://")) {
                                intent = Intent.parseUri(url, Intent.URI_INTENT_SCHEME);
                            } else {
                                // Yol tarifi linkini doğrudan telefondaki haritalar uygulamasına gönder
                                intent = new Intent(Intent.ACTION_VIEW, android.net.Uri.parse(url));
                            }
                            startActivity(intent);
                            return true; // WebViewi durdur
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                } else {
                    // KULLANICI DOKUNMADIYSA (Haritanın kendi kendine yönlendirme girişimi)
                    if (url.startsWith("intent://")) {
                        return true; // Sayfayı aşağı kaydırırken otomatik dışarı atma girişimlerini BLOKE ET
                    }
                }

                // Normal harita yüklemelerine (sadece görüntülemeye) izin ver
                if (url.startsWith("http://") || url.startsWith("https://")) {
                    return false;
                }

                return true;
            }
        });

        // Haritayı Orijinal Modda Yükleme
        try {
            String aranacakKelime = ad + " Trabzon";
            String guvenliKelime = URLEncoder.encode(aranacakKelime, "UTF-8");

            // Orijinal arayüz linki (Otomatik atması engellendiği için artık güvenle kullanılabilir)
            String haritaUrl = "https://www.google.com/maps/search/?api=1&query=" + guvenliKelime;
            webView.loadUrl(haritaUrl);

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            Toast.makeText(this, "Harita yüklenirken bir sorun oluştu.", Toast.LENGTH_SHORT).show();
        }
    }
}