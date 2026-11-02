package com.example.trabzongezirehberi;

import android.content.Intent;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lvYerler;
    Button btnSorgula, btnFavorilerim;
    ArrayList<Mekan> mekanListesi;
    MekanAdaptoru adaptor;
    DatabaseHelper dbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        lvYerler = findViewById(R.id.lvGezilecekYerler);
        btnSorgula = findViewById(R.id.btnSorgulamaSayfasinaGit);
        btnFavorilerim = findViewById(R.id.btnFavorilerim);
        dbHelper = new DatabaseHelper(this);

        if (dbHelper.mekanTablosuBosMu()) {
            dbHelper.mekanEkle("Sümela Manastırı", "Maçka", "Tarihi Yer", R.drawable.sumela_manastiri,
                    "Karadeniz’in sisli dağları arasında, sarp kayalıkların yamacına adeta asılı duran Sümela Manastırı, Trabzon’un en büyüleyici yapılarından biridir. İlk bakışta insanı etkileyen şey, böylesine görkemli bir yapının yüzlerce yıl önce bu kadar ulaşılması zor bir noktaya nasıl inşa edildiğidir. Rivayete göre Atina’dan gelen iki keşiş, Meryem Ana’ya ait kutsal bir ikonayı burada bulduktan sonra manastırın temellerini atmıştır. Bu efsane, Sümela’yı yalnızca mimari bir yapı olmaktan çıkarıp mistik bir ziyaret noktasına dönüştürmüştür.\n\n" +
                            "Yaklaşık 1600 yıllık geçmişe sahip olan manastır, Bizans döneminde önemli bir dini merkez olarak kullanılmıştır. Kayalara oyulmuş odaları, şapelleri ve duvarlarını süsleyen freskleri, dönemin sanat anlayışını gözler önüne serer.\n\n" +
                            "Ziyaretçiler burada yalnızca tarihî bir yapıyı gezmez; aynı zamanda Altındere Vadisi’nin yemyeşil doğasında yürüyüş yapar, manzara noktalarında fotoğraf çeker ve sisler arasındaki bu görkemli yapının atmosferini deneyimler. Bölgedeki restoranlarda kuymak, kara lahana çorbası ve alabalık en çok tercih edilen yöresel lezzetler arasındadır.");

            dbHelper.mekanEkle("Trabzon Kalesi", "Ortahisar", "Tarihi Yer", R.drawable.trabzon_kalesi,
                    "Trabzon’un kalbinde yükselen Trabzon Kalesi, şehrin geçmişine açılan taş bir kapı gibidir. Roma döneminde temelleri atıldığı düşünülen kale, Bizans ve Osmanlı dönemlerinde güçlendirilerek yüzyıllar boyunca şehrin savunmasında kullanılmıştır.\n\n" +
                            "Kale; Yukarı Hisar, Orta Hisar ve Aşağı Hisar olmak üzere üç bölümden oluşur. Bu yapısıyla yalnızca savunma amacı taşımamış, aynı zamanda yönetim merkezi olarak da kullanılmıştır.\n\n" +
                            "Bugün ziyaretçiler burada tarihî surlar arasında yürüyerek Trabzon’un eski dokusunu hisseder. Özellikle gün batımında kaleden görülen şehir manzarası oldukça etkileyicidir. Kale çevresindeki küçük işletmelerde Akçaabat köftesi ve Trabzon pidesi yemek, ziyaretin vazgeçilmez bir parçasıdır.");

            dbHelper.mekanEkle("Memiş Ağa Konağı", "Sürmene", "Tarihi Yer", R.drawable.memisagakonagi,
                    "Sürmene’nin tarih kokan yapılarından biri olan Memiş Ağa Konağı, Karadeniz’de geleneksel Osmanlı yaşamının izlerini taşıyan önemli bir eserdir. 19. yüzyılda yaptırılan konak, dönemin zengin aile yaşamını ve estetik anlayışını gözler önüne serer.\n\n" +
                            "Konağın en dikkat çekici yönü ahşap işçiliğidir. Tavan süslemeleri, işlemeli kapıları ve detaylı oymaları, dönemin ustalarının ne kadar ince çalıştığını gösterir.\n\n" +
                            "Ziyaretçiler konağı gezerken adeta geçmişe gider; odalarda dolaşırken yüz yıl öncesinin yaşamını hayal eder. Sürmene’ye gelmişken meşhur tereyağlı pidesi ve bölgeye özgü sütlü tatlılar mutlaka denenir.");

            dbHelper.mekanEkle("Uzungöl", "Çaykara", "Doğal Güzellik", R.drawable.uzungol,
                    "Karadeniz denince akla ilk gelen manzaralardan biri olan Uzungöl, yemyeşil dağların arasında saklı bir doğa harikasıdır. Bir heyelan sonucu oluştuğu düşünülen göl, zamanla Trabzon’un en çok ziyaret edilen noktalarından biri hâline gelmiştir.\n\n" +
                            "Bölge hakkında anlatılan halk hikâyelerinde, gölün oluşumunun doğanın insanlara bir armağanı olduğu söylenir. Özellikle sabah saatlerinde gölün üzerinde oluşan sis tabakası, buraya masalsı bir görünüm kazandırır.\n\n" +
                            "Ziyaretçiler burada göl çevresinde yürüyüş yapar, bisiklete biner, seyir teraslarından manzarayı izler ve bol bol fotoğraf çeker. Uzungöl’e gelip de alabalık yemeden dönmek neredeyse düşünülemez. Ayrıca muhlama ve laz böreği de burada en çok tercih edilen lezzetlerdendir.");

            dbHelper.mekanEkle("Çal Mağarası", "Düzköy", "Doğal Güzellik", R.drawable.cal_magarasi,
                    "Dünyanın en uzun mağaralarından biri olarak kabul edilen Çal Mağarası, Trabzon’un gizemli doğa hazinelerinden biridir. Yaklaşık 8 milyon yıllık geçmişe sahip olduğu tahmin edilen mağara, ziyaretçilerine adeta yer altı dünyasına açılan bir kapı sunar.\n\n" +
                            "Halk arasında mağaranın derinliklerinde hâlâ keşfedilmemiş bölümler olduğu ve gizli geçitler barındırdığına dair söylentiler vardır. İçerisindeki doğal sarkıtlar, dikitler ve yer altı deresi oldukça etkileyici bir atmosfer yaratır.\n\n" +
                            "Ziyaretçiler mağara içinde yürüyüş yaparken serin havayı hisseder, doğal oluşumları inceler ve fotoğraf çeker. Çıkışta bölgedeki tesislerde tereyağlı mısır ekmeği ve kuymak sıkça tercih edilir.");

            dbHelper.mekanEkle("Sera Gölü", "Akçaabat", "Doğal Güzellik", R.drawable.sera_golu,
                    "1950 yılında büyük bir heyelan sonucu oluşan Sera Gölü, bugün Trabzon’un en huzurlu noktalarından biridir. Dağların arasında sakin sularıyla uzanan göl, doğaseverler için gerçek bir kaçış alanıdır.\n\n" +
                            "Buraya gelenler göl çevresinde yürüyüş yapar, tekne turuna çıkar ya da göl manzarasına karşı çay içer. Özellikle sabah saatlerinde gölün dinginliği ziyaretçilere unutulmaz bir huzur hissi verir.\n\n" +
                            "Sera Gölü çevresinde Akçaabat köftesi en çok tercih edilen yemektir. Yanında közlenmiş biber ve yayık ayranla servis edildiğinde bölgenin klasik lezzet deneyimini sunar.");

            dbHelper.mekanEkle("Canik Şelalesi", "Tonya", "Doğal Güzellik", R.drawable.canik_selalesi,
                    "Tonya’nın saklı güzelliklerinden biri olan Canik Şelalesi, gürül gürül akan suları ve yemyeşil çevresiyle büyüleyici bir doğa alanıdır.\n\n" +
                            "Bölge halkı arasında, şelalenin suyunun insanı dinlendirdiğine ve huzur verdiğine inanılır. Özellikle yaz aylarında serinlemek isteyen ziyaretçilerin uğrak noktasıdır.\n\n" +
                            "Ziyaretçiler burada piknik yapar, doğa yürüyüşüne çıkar ve şelalenin sesi eşliğinde vakit geçirir. Tonya tereyağıyla hazırlanan kuymak ise burada mutlaka tadılması gereken lezzetlerden biridir.");

            dbHelper.mekanEkle("Atatürk Köşkü", "Ortahisar", "Müze / Köşk", R.drawable.ataturk_kosku,
                    "Trabzon’un Soğuksu semtinde yer alan Atatürk Köşkü, hem mimarisi hem de Cumhuriyet tarihindeki önemiyle dikkat çeker. Mustafa Kemal Atatürk, Trabzon ziyaretlerinde burada konaklamış ve mal varlığını millete bağışlama kararını bu köşkte açıklamıştır.\n\n" +
                            "Avrupa mimarisi etkisi taşıyan köşk, zarif detayları ve geniş bahçesiyle öne çıkar. İçeride Atatürk’e ait kişisel eşyalar, fotoğraflar ve tarihî belgeler sergilenmektedir.\n\n" +
                            "Ziyaretçiler köşkü gezerken hem Cumhuriyet tarihine tanıklık eder hem de bahçeden Trabzon manzarasını izler. Çıkışta bölgede çay eşliğinde Trabzon hasırı şeklinde yapılan tatlılar tercih edilir.");

            dbHelper.mekanEkle("Ayasofya Camii", "Ortahisar", "Müze / Köşk", R.drawable.ayasofya,
                    "13. yüzyılda kilise olarak inşa edilen Trabzon Ayasofya Camii, Bizans mimarisinin Karadeniz’deki en etkileyici örneklerinden biridir.\n\n" +
                            "Duvarlarındaki freskler, taş kabartmaları ve mimari detayları yüzyılların izini taşır. Osmanlı döneminde camiye çevrilen yapı, tarih boyunca farklı kültürlerin izlerini bünyesinde toplamıştır.\n\n" +
                            "Ziyaretçiler burada hem ibadet eder hem de tarihî detayları inceleyerek kültürel bir keşif yaşar. Çevredeki işletmelerde Trabzon pidesi yemek oldukça popülerdir.");

            dbHelper.mekanEkle("Boztepe", "Ortahisar", "Seyir Alanı", R.drawable.boztepe,
                    "Trabzon’u yukarıdan izlemek isteyen herkesin yolu mutlaka Boztepe’ye düşer. Şehrin en güzel seyir noktalarından biri olan bu tepe, özellikle gün batımında büyüleyici bir manzara sunar.\n\n" +
                            "Eskiden dervişlerin inzivaya çekildiği bir bölge olduğu anlatılır. Bu nedenle Boztepe’nin mistik bir havası olduğu söylenir.\n\n" +
                            "Ziyaretçiler burada manzaraya karşı semaver çayı içer, fotoğraf çeker ve Trabzon’u kuşbakışı izler. Yanında gelen tereyağlı sıcak pideler ve sütlaç en çok tercih edilen lezzetlerdir.");

            dbHelper.mekanEkle("Zağnos Vadisi", "Ortahisar", "Park", R.drawable.zagnos_vadisi_parki,
                    "Şehrin merkezinde yer alan Zağnos Vadisi, tarih ile modern yaşamın buluştuğu özel alanlardan biridir. Osmanlı döneminden kalma köprüleri ve çevresindeki tarihî yapılarla dikkat çeker.\n\n" +
                            "Restorasyon çalışmalarıyla yeniden canlandırılan vadi, bugün yürüyüş yolları, oturma alanları ve sosyal etkinlikleriyle Trabzon’un nefes alma noktalarından biridir.\n\n" +
                            "Ziyaretçiler burada yürüyüş yapar, tarihî köprülerde fotoğraf çeker ve şehir merkezinde kısa bir doğa molası verir.");

            dbHelper.mekanEkle("Şahinkaya", "Düzköy", "Seyir Alanı / Tırmanış", R.drawable.sahinkaya,
                    "Şahinkaya, Trabzon’un bulutların üzerine çıkılmış hissi veren en etkileyici seyir noktalarından biridir. Özellikle sabah erken saatlerde oluşan bulut denizi manzarası, ziyaretçilerine unutulmaz görüntüler sunar.\n\n" +
                            "Bölge halkı, burada dilek tutmanın uğur getirdiğine inanır. Bu nedenle birçok ziyaretçi manzaraya karşı dilek tutar.\n\n" +
                            "Doğa yürüyüşü, kamp ve gün doğumu fotoğrafçılığı için oldukça popülerdir. Buraya gelenler genellikle yanlarında termosla çay getirir ya da çevrede yöresel mısır ekmeği ve peynirle kahvaltı yapar.");
            dbHelper.mekanEkle("Çamburnu Tabiat Parkı", "Sürmene", "Doğal Güzellik", R.drawable.camburnutabiatparki,
                    "Sürmene'de bulunan Çamburnu Tabiat Parkı, Karadeniz kıyısında deniz ve çam ormanlarının bir araya geldiği nadir doğal alanlardan biridir. Bir tarafta masmavi Karadeniz, diğer tarafta yemyeşil çam ağaçları ziyaretçilere eşsiz bir manzara sunar. Geçmişte denizcilerin kıyıyı bulmak için uzaktan gördükleri çamlık alanlardan biri olduğu anlatılır.\n\n" +
                            "Günümüzde ziyaretçiler burada yürüyüş yapar, piknik yapar ve deniz manzarası eşliğinde dinlenir. Özellikle gün doğumu ve gün batımı saatlerinde ortaya çıkan manzara fotoğraf tutkunlarının ilgisini çeker. Bölgeye gelenler Sürmene pidesi, taze balık ve Karadeniz mutfağının diğer yöresel lezzetlerini deneyebilir. Şehrin kalabalığından uzaklaşmak ve doğayla baş başa kalmak isteyenler için Çamburnu huzurlu bir kaçış noktasıdır.");

            dbHelper.mekanEkle("Ganita Sahili", "Ortahisar", "Seyir Alanı", R.drawable.ganita,
                    "Ganita, Trabzon'un en sevilen sahil noktalarından biri ve şehir yaşamının önemli simgelerinden biridir. Yıllar boyunca Trabzonluların buluşma, sohbet etme ve denizi izleme noktası olan Ganita, günümüzde yenilenen yürüyüş yolları ve dinlenme alanlarıyla ziyaretçilerini ağırlamaktadır.\n\n" +
                            "Karadeniz'in dalgalarını yakından hissedebileceğiniz sahilde yürüyüş yapmak, bisiklete binmek ve gün batımını izlemek en popüler aktiviteler arasındadır. Özellikle akşam saatlerinde oluşan manzara oldukça etkileyicidir. Çevredeki kafelerde çay eşliğinde Trabzon pidesi veya Akçaabat köftesi yiyerek Karadeniz atmosferini deneyimleyebilirsiniz. Trabzon'u keşfetmeye başlayan birçok ziyaretçi için Ganita ilk duraklardan biridir.");

            dbHelper.mekanEkle("Trabzon Botanik Parkı", "Ortahisar", "Park", R.drawable.botanik,
                    "Trabzon Botanik Parkı, Karadeniz'in zengin bitki çeşitliliğini tanıtmak amacıyla kurulmuş önemli bir yaşam alanıdır. Şehir merkezine yakın konumda bulunan park, hem doğayla vakit geçirmek hem de Trabzon manzarasını izlemek isteyenler için ideal bir noktadır.\n\n" +
                            "Park içerisinde çok sayıda ağaç, çiçek ve süs bitkisi yer alır. İlkbahar ve yaz aylarında rengârenk görüntüsüyle dikkat çeken alanlar, fotoğraf çekmek isteyen ziyaretçilerin uğrak noktasıdır. Yürüyüş yolları ve seyir terasları sayesinde ziyaretçiler hem temiz havanın tadını çıkarabilir hem de şehri yüksekten izleyebilir. Sessiz atmosferiyle dinlenmek ve doğayla buluşmak isteyenler için Trabzon'un en keyifli noktalarından biridir.");

            dbHelper.mekanEkle("Vazelon Manastırı", "Maçka", "Tarihi Yer", R.drawable.vazelonmanastiri,
                    "Maçka ilçesinde bulunan Vazelon Manastırı, Trabzon'un en eski tarihî yapılarından biridir. Tarihinin 3. yüzyıla kadar uzandığı düşünülen manastır, Sümela Manastırı'ndan bile daha eski kabul edilmektedir. Ormanlarla çevrili konumu ve taş mimarisiyle ziyaretçilerine etkileyici bir atmosfer sunar.\n\n" +
                            "Yüzyıllar boyunca keşişlerin yaşam merkezi olan manastır, duvarlarındaki fresk kalıntılarıyla dikkat çeker. Bölge halkı arasında manastırın altında gizli geçitler bulunduğuna dair çeşitli efsaneler anlatılmaktadır. Günümüzde ziyaretçiler hem tarihî yapıyı keşfetmek hem de çevredeki doğal güzellikleri görmek için buraya gelmektedir. Tarih ve doğanın bir araya geldiği bu yapı, Trabzon'un gizli kalmış hazinelerinden biridir.");

            dbHelper.mekanEkle("Gülbahar Hatun Camii", "Ortahisar", "Tarihi Yer", R.drawable.gulbaharhatuncami,
                    "Trabzon'un en önemli Osmanlı eserlerinden biri olan Gülbahar Hatun Camii, 16. yüzyılın başlarında Yavuz Sultan Selim tarafından annesi Gülbahar Hatun adına yaptırılmıştır. Zarif mimarisi ve tarihî önemi sayesinde şehrin en değerli yapıları arasında yer almaktadır.\n\n" +
                            "Caminin yanında Gülbahar Hatun'un türbesi de bulunmaktadır. Kesme taşlardan inşa edilen yapı, Osmanlı mimarisinin sade ve estetik özelliklerini yansıtır. Tarih boyunca önemli bir ibadet merkezi olarak kullanılan cami, günümüzde de ziyaretçilerini ağırlamaya devam etmektedir. Buraya gelen ziyaretçiler hem tarihî atmosferi hisseder hem de Trabzon'un Osmanlı dönemine uzanan geçmişini yakından tanıma fırsatı bulur. Bu yönüyle şehirdeki en önemli kültürel duraklardan biridir.");
        }

        // 2. Veritabanından listeyi çekip mekan adaptörüne bağlıyoruz
        mekanListesi = dbHelper.tumMekanlariGetir();
        adaptor = new MekanAdaptoru(this, mekanListesi);
        lvYerler.setAdapter(adaptor);

        // 3. Uzun basma menüsünü listeye tanımlıyoruz
        registerForContextMenu(lvYerler);

        // 4. LİSTEDEN BİR YERE NORMAL TIKLANDIĞINDA
        lvYerler.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Mekan secilenMekan = mekanListesi.get(position);

                startActivity(secilenMekan.detayIntentiOlustur(MainActivity.this));
            }
        });

        // Arama Butonuna Tıklama Olayı (Sorgulama Sayfasına Geçiş)
        btnSorgula.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, SorgulamaActivity.class);
                startActivity(intent);
            }
        });
        btnFavorilerim.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, FavorilerActivity.class);
                startActivity(intent);
            }
        });
    }

    // UZUN BASINCA AÇILACAK MENÜ TASARIMI
    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        if (v.getId() == R.id.lvGezilecekYerler) {
            menu.setHeaderTitle("Bir İşlem Seçin");
            menu.add(0, v.getId(), 0, "Favorilere ekle");
            menu.add(0, v.getId(), 0, "Detayları görüntüle");
        }
    }

    // MENÜDEN BİR SEÇENEK SEÇİLDİĞİNDE
    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        Mekan islemYapilanMekan = mekanListesi.get(info.position);

        if (item.getTitle().equals("Favorilere ekle")) {
            // Favori durumunu tersine çevir (0 ise 1, 1 ise 0 yap)
            int yeniDurum = (islemYapilanMekan.favori == 0) ? 1 : 0;

            dbHelper.favoriDurumunuGuncelle(islemYapilanMekan.id, yeniDurum);
            mekanListesi.clear();
            mekanListesi.addAll(dbHelper.tumMekanlariGetir());
            adaptor.notifyDataSetChanged();

            String mesaj = (yeniDurum == 1) ? " favorilere eklendi ❤️" : " favorilerden çıkarıldı 🤍";
            Toast.makeText(this, islemYapilanMekan.ad + mesaj, Toast.LENGTH_SHORT).show();
            return true;

        } else if (item.getTitle().equals("Detayları görüntüle")) {
            startActivity(islemYapilanMekan.detayIntentiOlustur(MainActivity.this));
            return true;
        }
        return super.onContextItemSelected(item);
    }
}