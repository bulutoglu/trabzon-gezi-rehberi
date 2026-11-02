package com.example.trabzongezirehberi;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import java.util.ArrayList;

public class MekanAdaptoru extends ArrayAdapter<Mekan> {
    public MekanAdaptoru(Context context, ArrayList<Mekan> mekanlar) {
        super(context, 0, mekanlar);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mekan anlikMekan = getItem(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.liste_ogesi, parent, false);
        }

        ImageView imgFoto = convertView.findViewById(R.id.imgMekanFoto);
        TextView tvAd = convertView.findViewById(R.id.tvMekanAdi);
        TextView tvIlce = convertView.findViewById(R.id.tvMekanIlcesi);
        TextView tvKategori = convertView.findViewById(R.id.tvMekanKategori);
        TextView tvKalp = convertView.findViewById(R.id.tvKalp);

        imgFoto.setImageResource(anlikMekan.gorsel);
        tvAd.setText(anlikMekan.ad);
        tvIlce.setText(anlikMekan.ilce);
        tvKategori.setText(anlikMekan.kategori);

        // Ekran çizilirken kalbin veritabanındaki durumuna göre boyanması
        if (anlikMekan.favori == 1) {
            tvKalp.setText("❤️");
        } else {
            tvKalp.setText("🤍");
        }

        //  Kalbe doğrudan tıklandığında çalışır
        tvKalp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatabaseHelper dbHelper = new DatabaseHelper(getContext());

                // Eğer boşsa(0) dolu(1) yap, doluysa(1) boş(0) yap
                int yeniDurum = (anlikMekan.favori == 0) ? 1 : 0;

                // Veritabanını güncelle
                dbHelper.favoriDurumunuGuncelle(anlikMekan.id, yeniDurum);

                // Listedeki anlık veriyi de güncelle ve ekranı anında yenile
                anlikMekan.favori = yeniDurum;
                notifyDataSetChanged();

                // Kullanıcıya şık bir bilgi mesajı göster
                String mesaj = (yeniDurum == 1) ? " favorilere eklendi ❤️" : " favorilerden çıkarıldı 🤍";
                Toast.makeText(getContext(), anlikMekan.ad + mesaj, Toast.LENGTH_SHORT).show();
            }
        });

        return convertView;
    }
}