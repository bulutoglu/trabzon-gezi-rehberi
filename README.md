# 📍 Trabzon Gezi Rehberi

Trabzon Gezi Rehberi, kullanıcıların Trabzon'daki tarihi, doğal ve turistik mekanları keşfedebileceği, mekanları filtreleyebileceği ve beğendikleri yerleri favorilerine ekleyebileceği çevrimdışı (offline) çalışan bir Android uygulamasıdır.

## 🚀 Özellikler

- **Kullanıcı Kimlik Doğrulama:** Sisteme yeni kullanıcı kaydı ve giriş (Login/Register) işlemleri.
- **Mekan Listeleme:** Trabzon'un önemli turistik noktalarının görsel, isim ve ilçe bilgileriyle birlikte listelenmesi.
- **Dinamik Arama ve Filtreleme:** Mekanları isme, ilçeye veya kategorisine (Örn: Tarihi Yer, Doğal Güzellik) göre anlık olarak filtreleyebilme.
- **Favori Sistemi:** Kullanıcıların sevdikleri mekanları kalp ikonuna dokunarak favorilerine ekleyip çıkarabilmesi.
- **Detay Görünümü:** Seçilen mekanın açıklaması ve geniş görseli ile detaylı incelenebilmesi.
- **Çevrimdışı Çalışma:** Tüm verilerin cihazın yerel hafızasında tutulması sayesinde internet bağlantısı gerektirmemesi.

## 🛠️ Kullanılan Teknolojiler

- **Dil:** Java
- **Platform:** Android (Android Studio)
- **Veri Tabanı:** SQLite (Yerel Veri Depolama)
- **Kullanıcı Arayüzü (UI):** XML (ListView, Custom Adapter, EditText, Buttons vb.)

## 🗄️ Veri Tabanı Mimarisi

Uygulamada yerel veri depolama için **SQLite** kullanılmış ve `TrabzonRehberi.db` veri tabanı oluşturulmuştur. Tüm SQL operasyonları `DatabaseHelper` sınıfı üzerinden merkezi olarak yönetilmektedir. Sistemde iki ana tablo bulunmaktadır:

### 1. kullanicilar

Kayıt ve giriş aktivitelerinde kimlik doğrulama işlemleri için kullanılır.

- **Sütunlar:** `id` (PK, AutoIncrement), `eposta`, `sifre`

### 2. mekanlar

Uygulamanın içerik yönetimini sağlar. `MainActivity`'de listeleme, `MekanAdaptoru`'nde favori kontrolü ve `DetayActivity`'de veri gösterimi için kullanılır.

- **Sütunlar:** `id` (PK, AutoIncrement), `ad`, `ilce`, `kategori`, `gorsel`, `favori`, `aciklama`

*Not: Mobil uygulamanın tek kullanıcılı yapısı göz önünde bulundurularak performansı artırmak adına karmaşık "Yabancı Anahtar" (Foreign Key) ilişkileri yerine, iş mantığı üzerinden sadeleştirilmiş bir yaklaşım tercih edilmiştir.*

## 📱 Ekranlar ve Sınıflar

- **Giriş ve Kayıt Ekranı:** Kullanıcıların sisteme üye olup giriş yapmasını sağlar.
- **MainActivity:** Mekanların listelendiği, arama çubuğu ve kategori filtrelerinin bulunduğu ana sayfadır.
- **MekanAdaptoru (Custom Adapter):** Veri tabanından gelen bilgileri `liste_ogesi.xml` tasarımıyla eşleştirerek ListView üzerinde gösterir. Favori butonu tetiklemeleri bu sınıf üzerinden yapılır.
- **DetayActivity:** Listeden seçilen mekanın geniş ekran görüntüsünü ve uzun açıklama metnini barındırır.

## ⚙️ Kurulum ve Çalıştırma

1. Projeyi bilgisayarınıza ZIP olarak indirin veya Git kullanarak klonlayın.
2. **Android Studio**'yu açın.
3. `File > Open` adımlarını takip ederek proje klasörünü seçin.
4. Gradle senkronizasyonunun tamamlanmasını bekleyin.
5. Bir Android Emülatör veya USB ile bağlı gerçek bir Android cihaz seçin.
6. **Run (Shift + F10)** butonuna tıklayarak uygulamayı çalıştırın.
