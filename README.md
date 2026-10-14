
# Sistem Manajemen Data Menu Restoran

## Nama: Muhammad Arzad
## Kelas: A'25
## NIM: 2509116014

---

## 1. Deskripsi Singkat Program

Sistem Manajemen Data Menu Restoran merupakan program berbasis Java yang digunakan untuk mengelola data kategori dan menu restoran. Program ini memiliki fitur untuk menambah, menampilkan, mengubah, dan menghapus data menu serta menambah dan menampilkan data kategori.

---

## 2. Tujuan Program

Program ini dibuat dengan beberapa tujuan, yaitu:

1. Mengembangkan Sistem Manajemen Data Menu Restoran dari Mini Project 3.
2. Menerapkan konsep Pemrograman Berorientasi Objek menggunakan bahasa Java.
3. Menerapkan encapsulation, inheritance, polymorphism, dan abstraction.
4. Mempertahankan struktur MVC agar tanggung jawab setiap bagian program terpisah dengan jelas.
5. Mengintegrasikan aplikasi Java dengan database MySQL menggunakan JDBC.
6. Menggunakan `PreparedStatement` untuk menjalankan perintah SQL dengan parameter.
7. Menerapkan pola DAO untuk memisahkan proses akses database dari alur program.
8. Mengimplementasikan operasi SELECT, INSERT, UPDATE, dan DELETE sesuai kebutuhan fitur aplikasi.
9. Mempertahankan data kategori dan menu di dalam database agar dapat digunakan kembali setelah aplikasi dijalankan ulang.
10. Menerapkan validasi input dan penanganan kesalahan untuk membantu menjaga konsistensi data.

---

## 3. Fitur Program

### 3.1 Tambah Kategori

Pengguna dapat menambahkan kategori baru dengan memasukkan:

- ID kategori
- Nama kategori
- Deskripsi kategori

Data kategori disimpan ke tabel `kategori` di dalam database MySQL melalui method `tambahKategori()` pada class `RestoranDAO`.

ID kategori digunakan sebagai primary key sehingga database menolak data dengan ID yang sudah digunakan. Jika proses penyimpanan gagal, program menampilkan pesan kegagalan kepada pengguna.

### 3.2 Tampilkan Kategori

Program dapat menampilkan seluruh kategori yang tersimpan pada database.

Informasi yang ditampilkan meliputi:

- ID kategori
- Nama kategori
- Deskripsi kategori

Data diambil menggunakan perintah `SELECT` pada class `RestoranDAO`, kemudian dikirim ke `RestoranView` untuk ditampilkan.

### 3.3 Tambah Menu

Pengguna dapat menambahkan menu baru dengan memasukkan:

- ID menu
- Nama menu
- Kategori
- Harga
- Jenis menu
- Detail jenis menu

Pengguna dapat memilih salah satu jenis menu:

1. Makanan
2. Minuman

Jika pengguna memilih makanan, program akan membuat object `MenuMakanan`.

Jika pengguna memilih minuman, program akan membuat object `MenuMinuman`.

Data menu kemudian disimpan ke tabel `menu` melalui method `tambahMenu()` pada class `RestoranDAO`.

Setiap data menu terhubung dengan kategori melalui kolom `id_kategori` sebagai foreign key.

### 3.4 Tampilkan Menu

Program dapat menampilkan seluruh menu yang tersimpan di database.

Informasi yang ditampilkan menyesuaikan jenis menu. Menu makanan menampilkan informasi tambahan berupa `jenisMakanan`, sedangkan menu minuman menampilkan informasi berupa `jenisMinuman`.

Data diambil dari tabel `menu` yang digabungkan dengan tabel `kategori` menggunakan perintah `JOIN`.

Proses pembentukan object disesuaikan dengan nilai `jenis_menu` yang tersimpan di database, yaitu `MAKANAN` atau `MINUMAN`.

Method `tampilkanInfo()` pada setiap subclass digunakan untuk menampilkan informasi sesuai jenis object.

### 3.5 Update Menu

Pengguna dapat memperbarui data menu berdasarkan ID menu.

Data yang dapat diperbarui meliputi:

- Nama menu
- Kategori
- Harga
- Detail jenis makanan atau minuman

Program terlebih dahulu mencari menu di database menggunakan method `getMenuById()` pada class `RestoranDAO`.

Jika menu ditemukan, pengguna dapat memasukkan informasi baru. Perubahan kemudian disimpan menggunakan method `updateMenu()`.

Jenis menu yang sudah ada tetap dipertahankan. Pembaruan dilakukan pada nama menu, kategori, harga, dan detail jenisnya.

### 3.6 Hapus Menu

Pengguna dapat menghapus menu berdasarkan ID menu.

Program menjalankan perintah SQL `DELETE` melalui method `hapusMenu()` pada class `RestoranDAO`.

Jika data ditemukan dan berhasil dihapus, program menampilkan pesan keberhasilan. Jika ID tidak ditemukan atau operasi gagal, program menampilkan pesan kegagalan.

Penghapusan dilakukan langsung pada database sehingga data tersebut tidak lagi muncul ketika daftar menu dibaca kembali dari MySQL.

### 3.7 Validasi Input dan Penanganan Kesalahan

Program memiliki validasi input untuk mengurangi kesalahan saat pengguna memasukkan data.

Validasi yang diterapkan meliputi:

- Input tidak boleh kosong.
- Pilihan menu harus berupa angka.
- Pilihan harus berada pada rentang yang ditentukan.
- Harga harus berupa angka.
- Harga tidak boleh bernilai negatif.
- Persentase diskon harus berada antara 0 sampai 100.
- Kategori yang dipilih harus tersedia di database.
- ID kategori dan ID menu harus mengikuti ketentuan primary key database.
- Kegagalan operasi database ditangani menggunakan `SQLException`.

Jika terjadi kesalahan saat operasi database, DAO menampilkan pesan kesalahan pada console dan mengembalikan nilai yang menunjukkan operasi gagal, daftar kosong, atau `null`, sesuai jenis method yang dijalankan.

### 3.8 Hitung Harga Setelah Diskon

Program menyediakan fitur untuk menghitung harga menu setelah mendapatkan diskon.

Pengguna dapat:

1. Memasukkan ID menu.
2. Mendapatkan data menu dari database.
3. Melihat harga normal.
4. Memasukkan persentase diskon.
5. Melihat harga akhir setelah diskon.

Fitur ini menggunakan method `getMenuById()` pada DAO untuk mendapatkan data menu dari database.

Perhitungan tetap dilakukan oleh class `Menu` melalui dua method:

```java
hitungHargaSetelahDiskon()
```

dan:

```java
hitungHargaSetelahDiskon(double persentaseDiskon)
```

Dengan demikian, integrasi database tidak menghilangkan penerapan polymorphism overloading pada Mini Project 3.

---

## 4. Struktur Package

Program menggunakan struktur package yang memisahkan bagian Model, View, Controller, konfigurasi koneksi database, dan DAO.

```text
SistemManajemenDataMenuRestoran/
│
├── pom.xml
│
└── src/
    └── main/
        └── java/
            │
            ├── com/mycompany/sistemmanajemendatamenurestoran/
            │   └── SistemManajemenDataMenuRestoran.java
            │
            ├── config/
            │   ├── Koneksi.java
            │   └── TestKoneksi.java
            │
            ├── controller/
            │   └── RestoranController.java
            │
            ├── dao/
            │   └── RestoranDAO.java
            │
            ├── model/
            │   ├── Diskonable.java
            │   ├── Kategori.java
            │   ├── Menu.java
            │   ├── MenuMakanan.java
            │   ├── MenuMinuman.java
            │   └── Restoran.java
            │
            └── view/
                └── RestoranView.java
```

### 4.1 Package `config`

Package `config` digunakan untuk mengatur koneksi database dan menguji koneksi tersebut.

Class yang terdapat di dalam package ini adalah:

- `Koneksi`
- `TestKoneksi`

### 4.2 Package `dao`

Package `dao` digunakan untuk menampung operasi akses database.

Class yang terdapat di dalam package ini adalah:

- `RestoranDAO`

Class tersebut menangani query SQL menggunakan `PreparedStatement`, mengambil hasil query, dan mengubah hasilnya menjadi object model.

### 4.3 Package `model`

Package `model` digunakan untuk merepresentasikan data dan konsep objek yang digunakan dalam sistem.

Class dan interface yang terdapat di dalam package ini adalah:

- `Kategori`
- `Menu`
- `MenuMakanan`
- `MenuMinuman`
- `Restoran`
- `Diskonable`

Class `Restoran` tetap terdapat di dalam source project sebagai bagian dari model versi sebelumnya. Pada alur aplikasi Mini Project 4 saat ini, operasi kategori dan menu dilakukan melalui `RestoranDAO`, bukan dengan menyimpan data utama ke dalam object `Restoran`.

### 4.4 Package `view`

Package `view` digunakan untuk menangani interaksi program dengan pengguna melalui console.

Class yang terdapat di dalam package ini adalah:

- `RestoranView`

Class tersebut menangani tampilan menu utama, input pengguna, validasi input, serta tampilan kategori, menu, dan hasil perhitungan diskon.

### 4.5 Package `controller`

Package `controller` digunakan untuk mengatur alur program dan menghubungkan View dengan proses pengolahan data.

Class yang terdapat di dalam package ini adalah:

- `RestoranController`

Controller menerima input dari pengguna melalui View, memanggil method DAO yang sesuai, lalu meneruskan hasilnya ke View untuk ditampilkan.

### 4.6 Class Utama

`SistemManajemenDataMenuRestoran` merupakan class utama yang menjadi titik awal program.

Class ini membuat object `RestoranView` dan `RestoranController`, kemudian menjalankan program melalui method `jalankanProgram()`.

---

## 5. Konfigurasi dan Persiapan Database

Bagian ini menjelaskan kebutuhan dan konfigurasi yang diperlukan agar program dapat dijalankan di lingkungan pengembangan lain.

### 5.1 Kebutuhan Sistem

Untuk menjalankan program, diperlukan:

- JDK 26 atau versi yang sesuai dengan konfigurasi compiler pada `pom.xml`.
- Apache Maven melalui project Maven di NetBeans.
- MySQL Server.
- NetBeans IDE atau IDE Java yang mendukung Maven.
- MySQL Connector/J sebagai driver JDBC.

Project menggunakan Maven untuk mengelola dependensi. Konfigurasi dependensi MySQL Connector/J berada di dalam file `pom.xml`.

Informasi lebih lanjut mengenai driver dapat dibaca melalui dokumentasi resmi [MySQL Connector/J](https://dev.mysql.com/doc/connector-j/en/).

### 5.2 Dependensi JDBC

MySQL Connector/J digunakan agar aplikasi Java dapat berkomunikasi dengan database MySQL.

Dependensi yang digunakan pada file `pom.xml` adalah:

```xml
<dependency>
    <groupId>com.mysql</groupId>
    <artifactId>mysql-connector-j</artifactId>
    <version>26.7.0</version>
</dependency>
```

Maven akan mengunduh dependensi tersebut ketika proyek dibangun, selama repository Maven dapat diakses.

### 5.3 Membuat Database

Database yang digunakan oleh program bernama `db_restoran`.

Buat database melalui phpMyAdmin atau jalankan perintah SQL berikut:

```sql
CREATE DATABASE IF NOT EXISTS db_restoran;

USE db_restoran;
```

### 5.4 Membuat Tabel `kategori`

Tabel `kategori` digunakan untuk menyimpan informasi kategori menu restoran.

```sql
CREATE TABLE IF NOT EXISTS kategori (
    id_kategori VARCHAR(10) PRIMARY KEY,
    nama_kategori VARCHAR(100) NOT NULL,
    deskripsi VARCHAR(255)
);
```

Kolom pada tabel `kategori` terdiri dari:

| Kolom | Keterangan |
|---|---|
| `id_kategori` | ID kategori sebagai primary key |
| `nama_kategori` | Nama kategori |
| `deskripsi` | Deskripsi kategori |

### 5.5 Membuat Tabel `menu`

Tabel `menu` digunakan untuk menyimpan informasi menu restoran.

```sql
CREATE TABLE IF NOT EXISTS menu (
    id_menu VARCHAR(10) PRIMARY KEY,
    nama_menu VARCHAR(100) NOT NULL,
    id_kategori VARCHAR(10) NOT NULL,
    harga DECIMAL(12,2) NOT NULL,
    jenis_menu VARCHAR(20) NOT NULL,
    detail_jenis VARCHAR(100) NOT NULL,

    CONSTRAINT fk_menu_kategori
        FOREIGN KEY (id_kategori)
        REFERENCES kategori(id_kategori)
);
```

Kolom pada tabel `menu` terdiri dari:

| Kolom | Keterangan |
|---|---|
| `id_menu` | ID menu sebagai primary key |
| `nama_menu` | Nama menu |
| `id_kategori` | ID kategori sebagai foreign key |
| `harga` | Harga menu |
| `jenis_menu` | Jenis menu, yaitu `MAKANAN` atau `MINUMAN` |
| `detail_jenis` | Detail jenis makanan atau minuman |

Kolom `id_kategori` menghubungkan tabel `menu` dengan tabel `kategori`. Dengan relasi tersebut, setiap menu dapat dikaitkan dengan kategori yang sesuai.

**Catatan:** Jika tabel sudah dibuat sebelumnya, pastikan nama tabel dan kolomnya sesuai dengan query dalam `RestoranDAO.java` sebelum menjalankan program.

### 5.6 Contoh Data Awal

Data berikut bersifat opsional dan dapat digunakan untuk mencoba fitur aplikasi.

```sql
INSERT IGNORE INTO kategori
    (id_kategori, nama_kategori, deskripsi)
VALUES
    ('K001', 'Makanan', 'Kategori makanan restoran'),
    ('K002', 'Minuman', 'Kategori minuman restoran');

INSERT IGNORE INTO menu
    (id_menu, nama_menu, id_kategori, harga, jenis_menu, detail_jenis)
VALUES
    ('M001', 'Nasi Goreng', 'K001', 25000, 'MAKANAN', 'Makanan Nusantara'),
    ('M002', 'Es Teh', 'K002', 8000, 'MINUMAN', 'Minuman Dingin');
```

Data awal tersebut dimasukkan melalui SQL ke database, bukan dibuat otomatis oleh controller saat program dijalankan.

### 5.7 Konfigurasi Koneksi Database

Konfigurasi koneksi berada pada file:

`src/main/java/config/Koneksi.java`

URL koneksi yang digunakan adalah:

```java
private static final String URL =
        "jdbc:mysql://localhost:3306/db_restoran";
```

Username dan password disesuaikan dengan konfigurasi MySQL pada komputer pengguna.

Pada konfigurasi saat ini, username yang digunakan adalah `root` dan password kosong. Jika database menggunakan username atau password lain, ubah nilai pada file `Koneksi.java`.

### 5.8 Menguji Koneksi

Program menyediakan class `TestKoneksi` yang dapat dijalankan untuk memeriksa koneksi database.

Jika berhasil, console akan menampilkan:

```text
Test Koneksi
Koneksi Berhasil
```

Jika koneksi gagal, periksa status MySQL Server, nama database, port, username, password, dan dependensi JDBC.

### 5.9 Menjalankan Aplikasi

Tahapan menjalankan aplikasi adalah:

1. Pastikan MySQL Server sudah berjalan.
2. Pastikan database `db_restoran` dan kedua tabelnya sudah tersedia.
3. Buka project Maven di NetBeans.
4. Tunggu hingga dependensi pada `pom.xml` selesai diproses.
5. Jalankan class `TestKoneksi` untuk menguji koneksi database.
6. Jika koneksi berhasil, jalankan class `SistemManajemenDataMenuRestoran`.
7. Pilih fitur yang tersedia melalui menu utama pada console.

---

## 6. Penjelasan Class

### 6.1 `SistemManajemenDataMenuRestoran`

Class `SistemManajemenDataMenuRestoran` merupakan class utama yang digunakan sebagai titik awal program.

Class ini membuat object `RestoranView` dan `RestoranController`, kemudian menjalankan program melalui method `jalankanProgram()`.

Contohnya:

```java
RestoranView view = new RestoranView();

RestoranController controller =
        new RestoranController(view);

controller.jalankanProgram();
```

Class utama tidak menangani query SQL secara langsung karena akses database telah ditempatkan pada class DAO.

### 6.2 `Koneksi`

Class `Koneksi` digunakan untuk membuat dan menyediakan koneksi antara aplikasi Java dengan MySQL.

Class ini menggunakan:

- `Connection`
- `DriverManager`
- `SQLException`

Method `getConnection()` digunakan untuk mendapatkan object koneksi yang dipakai oleh DAO saat menjalankan query SQL.

### 6.3 `TestKoneksi`

Class `TestKoneksi` digunakan untuk menguji apakah aplikasi dapat terhubung dengan database yang telah dikonfigurasi.

Class ini memanggil `Koneksi.getConnection()` dan menampilkan informasi mengenai hasil pengujian koneksi.

### 6.4 `RestoranDAO`

Class `RestoranDAO` merupakan bagian yang menangani akses data ke database MySQL.

Seluruh operasi SQL yang digunakan oleh aplikasi ditempatkan pada class ini.

Method yang tersedia meliputi:

| Method | Fungsi |
|---|---|
| `getAllKategori()` | Mengambil seluruh data kategori |
| `tambahKategori(Kategori kategori)` | Menambahkan kategori ke database |
| `getAllMenu()` | Mengambil seluruh menu beserta informasi kategori |
| `tambahMenu(Menu menu)` | Menambahkan menu ke database |
| `getMenuById(String idMenu)` | Mengambil satu menu berdasarkan ID |
| `updateMenu(...)` | Memperbarui data menu |
| `hapusMenu(String idMenu)` | Menghapus menu berdasarkan ID |

Class `RestoranDAO` menggunakan `PreparedStatement` untuk menjalankan query SQL dan `ResultSet` untuk membaca hasil query SELECT.

Dengan pemisahan tersebut, controller tidak perlu menulis query SQL secara langsung.

### 6.5 `RestoranController`

Class `RestoranController` digunakan untuk mengatur alur program dan menghubungkan View dengan DAO.

Proses yang ditangani antara lain:

- Tambah kategori
- Tampilkan kategori
- Tambah menu
- Tampilkan menu
- Update menu
- Hapus menu
- Hitung harga setelah diskon
- Memilih kategori yang tersedia

Controller memanggil method pada `RestoranDAO` untuk mendapatkan atau memodifikasi data di database.

### 6.6 `RestoranView`

Class `RestoranView` digunakan untuk menangani interaksi program dengan pengguna melalui console.

Class ini menangani:

- Tampilan menu utama
- Input string
- Input pilihan
- Input harga
- Input persentase diskon
- Tampilan daftar kategori
- Tampilan pilihan kategori
- Tampilan daftar menu
- Tampilan data menu saat akan diperbarui
- Tampilan hasil perhitungan diskon
- Penanganan Scanner

Class View tidak menjalankan query SQL dan tidak bertanggung jawab menyimpan data ke database.

### 6.7 `Kategori`

Class `Kategori` digunakan untuk merepresentasikan data kategori menu restoran.

Attribute yang dimiliki adalah:

- `idKategori`
- `namaKategori`
- `deskripsi`

Object `Kategori` dibentuk dari input pengguna atau dari hasil pembacaan data database.

### 6.8 `Menu`

Class `Menu` merupakan abstract class yang digunakan sebagai dasar untuk jenis menu yang terdapat dalam program.

Attribute yang dimiliki adalah:

- `idMenu`
- `namaMenu`
- `kategori`
- `harga`

Class ini memiliki abstract method `tampilkanInfo()` serta mengimplementasikan interface `Diskonable`.

Class `Menu` juga memiliki method perhitungan harga setelah diskon.

### 6.9 `MenuMakanan`

Class `MenuMakanan` merupakan subclass dari `Menu`.

Class ini memiliki attribute tambahan:

- `jenisMakanan`

Class `MenuMakanan` melakukan overriding terhadap method `tampilkanInfo()` untuk menampilkan informasi menu makanan.

### 6.10 `MenuMinuman`

Class `MenuMinuman` merupakan subclass dari `Menu`.

Class ini memiliki attribute tambahan:

- `jenisMinuman`

Class `MenuMinuman` melakukan overriding terhadap method `tampilkanInfo()` untuk menampilkan informasi menu minuman.

### 6.11 `Diskonable`

`Diskonable` merupakan interface yang digunakan sebagai kontrak untuk proses perhitungan harga setelah diskon.

Interface ini memiliki dua method:

```java
double hitungHargaSetelahDiskon();

double hitungHargaSetelahDiskon(
        double persentaseDiskon
);
```

Interface tersebut diimplementasikan oleh class `Menu`.

### 6.12 `Restoran`

Class `Restoran` tetap terdapat di dalam package `model` sebagai bagian dari rancangan pada Mini Project 3.

Class tersebut sebelumnya digunakan untuk menyimpan daftar kategori dan menu menggunakan `ArrayList`. Pada alur aplikasi Mini Project 4 yang aktif, penyimpanan dan pengambilan data kategori maupun menu telah dialihkan ke database melalui `RestoranDAO`.

---

## 7. Alur Program

### 7.1 Inisialisasi Program

Ketika program dijalankan, class utama membuat object `RestoranView` dan `RestoranController`.

Controller kemudian membuat object `RestoranDAO` yang digunakan untuk berinteraksi dengan database.

Tidak ada proses inisialisasi otomatis berupa penambahan kategori dan menu contoh pada controller. Data yang ditampilkan berasal dari database yang sudah tersedia.

### 7.2 Menu Utama

Setelah program dijalankan, aplikasi menampilkan menu utama:

```text
=== SISTEM MANAJEMEN MENU RESTORAN ===
1. Tambah Kategori
2. Tampilkan Kategori
3. Tambah Menu
4. Tampilkan Menu
5. Update Menu
6. Hapus Menu
7. Keluar
8. Hitung Harga Setelah Diskon
```

Pengguna dapat memilih operasi dengan memasukkan nomor pilihan.

### 7.3 Alur Tambah Kategori

Pengguna memilih menu nomor 1.

Program meminta ID kategori, nama kategori, dan deskripsi.

Controller membuat object `Kategori`, kemudian memanggil `RestoranDAO.tambahKategori()`.

DAO menjalankan perintah SQL `INSERT` menggunakan `PreparedStatement`. Jika operasi berhasil, program menampilkan pesan keberhasilan.

### 7.4 Alur Tampilkan Kategori

Pengguna memilih menu nomor 2.

Controller memanggil method `getAllKategori()` pada DAO.

DAO menjalankan query `SELECT`, membaca data menggunakan `ResultSet`, lalu membentuk daftar object `Kategori`.

Daftar tersebut dikirim ke View untuk ditampilkan pada console.

### 7.5 Alur Tambah Menu

Pengguna memilih menu nomor 3.

Program meminta ID menu dan nama menu, kemudian menampilkan kategori yang tersedia berdasarkan data database.

Setelah pengguna memilih kategori, program meminta harga dan jenis menu.

Controller membuat object `MenuMakanan` atau `MenuMinuman`, kemudian memanggil `RestoranDAO.tambahMenu()`.

DAO menyimpan data ke tabel `menu` menggunakan `PreparedStatement`.

### 7.6 Alur Tampilkan Menu

Pengguna memilih menu nomor 4.

Controller memanggil method `getAllMenu()` pada DAO.

DAO menjalankan query yang menggabungkan tabel `menu` dengan tabel `kategori`. Data hasil query digunakan untuk membentuk object `Kategori`, `MenuMakanan`, dan `MenuMinuman`.

Object menu kemudian dikirim ke View agar informasi menu dapat ditampilkan sesuai jenisnya.

### 7.7 Alur Update Menu

Pengguna memilih menu nomor 5 dan memasukkan ID menu.

Controller memanggil method `getMenuById()` untuk mencari menu pada database.

Jika menu ditemukan, pengguna dapat memasukkan perubahan data. Controller kemudian memanggil method `updateMenu()` pada DAO untuk menjalankan perintah SQL `UPDATE`.

### 7.8 Alur Hapus Menu

Pengguna memilih menu nomor 6 dan memasukkan ID menu.

Controller memanggil method `hapusMenu()` pada DAO.

DAO menjalankan perintah SQL `DELETE` berdasarkan ID yang dimasukkan.

Jika terdapat baris yang berhasil dihapus, DAO mengembalikan nilai `true`; jika tidak, DAO mengembalikan nilai `false`.

### 7.9 Alur Keluar Program

Pengguna memilih menu nomor 7.

Perulangan program dihentikan dan aplikasi menampilkan pesan:

```text
Program selesai. Terima kasih
```

Scanner kemudian ditutup.

### 7.10 Alur Hitung Harga Setelah Diskon

Pengguna memilih menu nomor 8 dan memasukkan ID menu.

Controller memanggil `getMenuById()` untuk mendapatkan object menu dari database.

Setelah menu ditemukan, program mendapatkan harga normal menggunakan:

```java
menu.hitungHargaSetelahDiskon();
```

Pengguna kemudian memasukkan persentase diskon dan program menghitung harga akhir dengan:

```java
menu.hitungHargaSetelahDiskon(
        persentaseDiskon
);
```

Hasil perhitungan ditampilkan melalui `RestoranView`.

### 7.11 Alur MVC dan DAO

Alur interaksi utama program adalah:

```text
Pengguna
    ↓
RestoranView
    ↓
RestoranController
    ↓
RestoranDAO
    ↓
Koneksi / JDBC
    ↓
Database MySQL
    ↓
RestoranDAO
    ↓
RestoranController
    ↓
RestoranView
    ↓
Pengguna
```

Contoh alur penambahan menu:

```text
Pengguna memasukkan data menu
    ↓
RestoranView menerima input
    ↓
RestoranController membuat object menu
    ↓
RestoranDAO menerima object menu
    ↓
PreparedStatement menjalankan INSERT
    ↓
Database MySQL menyimpan data
    ↓
RestoranDAO mengembalikan hasil operasi
    ↓
RestoranController menentukan pesan
    ↓
RestoranView menampilkan hasil
```

---

## 8. Penerapan Integrasi Database

### 8.1 JDBC (Java Database Connectivity)

JDBC digunakan sebagai mekanisme penghubung antara aplikasi Java dengan database MySQL.

Implementasi koneksi berada pada class `Koneksi` dengan method `getConnection()`.

Koneksi yang diperoleh kemudian digunakan oleh class `RestoranDAO` untuk menjalankan query SQL.

### 8.2 PreparedStatement

`PreparedStatement` digunakan untuk menjalankan perintah SQL dengan parameter.

Contohnya pada operasi penambahan kategori:

```java
String sql = "INSERT INTO kategori "
        + "(id_kategori, nama_kategori, deskripsi) "
        + "VALUES (?, ?, ?)";

try (PreparedStatement ps = conn.prepareStatement(sql)) {

    ps.setString(1, kategori.getIdKategori());
    ps.setString(2, kategori.getNamaKategori());
    ps.setString(3, kategori.getDeskripsi());

    return ps.executeUpdate() > 0;
}
```

Tanda `?` digunakan sebagai placeholder untuk nilai yang dimasukkan melalui parameter `PreparedStatement`.

Penggunaan parameter membantu memisahkan struktur query SQL dari nilai input pengguna.

### 8.3 Data Access Object (DAO)

DAO digunakan untuk memisahkan proses akses database dari controller.

Pada program ini, class `RestoranDAO` menampung operasi SQL yang digunakan oleh fitur aplikasi, antara lain:

- `SELECT` kategori
- `INSERT` kategori
- `SELECT` menu
- `INSERT` menu
- `UPDATE` menu
- `DELETE` menu

Method `getMenuById()` juga menggunakan query `SELECT` untuk mencari satu menu berdasarkan ID.

Dengan pemisahan ini, controller berfokus mengatur alur program, sedangkan DAO berfokus mengakses dan memodifikasi data di database.

### 8.4 ResultSet

`ResultSet` digunakan untuk membaca baris hasil query `SELECT`.

Contohnya:

```java
while (rs.next()) {

    Kategori kategori = new Kategori(
            rs.getString("id_kategori"),
            rs.getString("nama_kategori"),
            rs.getString("deskripsi")
    );

    daftarKategori.add(kategori);
}
```

Data yang dibaca dari database digunakan untuk membentuk object model agar dapat diproses dan ditampilkan oleh aplikasi.

### 8.5 Relasi Tabel

Database menggunakan dua tabel utama, yaitu `kategori` dan `menu`.

Tabel `kategori` menyimpan informasi kategori, sedangkan tabel `menu` menyimpan informasi setiap menu.

Relasi antara keduanya menggunakan:

```text
kategori.id_kategori
        │
        │ 1 ke banyak
        ▼
menu.id_kategori
```

Satu kategori dapat digunakan oleh lebih dari satu menu. Setiap menu mengacu pada kategori melalui foreign key `id_kategori`.

Pada fitur tampil menu, kedua tabel digabungkan melalui query `JOIN` untuk menampilkan nama kategori bersama informasi menu.

### 8.6 Persistensi Data

Penyimpanan pada database membuat data kategori dan menu yang berhasil disimpan dapat digunakan kembali setelah aplikasi ditutup dan dijalankan kembali.

Berbeda dengan penyimpanan sementara menggunakan `ArrayList`, perubahan pada database tidak hilang hanya karena program Java berhenti.

Untuk memastikan persistensi, pengguna dapat menutup aplikasi, menjalankannya kembali, kemudian memilih fitur tampil kategori atau tampil menu.

---

## 9. Penerapan Konsep PBO

### 9.1 Class dan Object

Program menggunakan beberapa class untuk merepresentasikan bagian-bagian sistem restoran.

Object dibuat berdasarkan class yang telah didefinisikan.

Contoh object kategori:

```java
Kategori kategori =
        new Kategori(
                "K001",
                "Makanan",
                "Kategori makanan restoran"
        );
```

Object menu makanan juga dibuat menggunakan subclass:

```java
Menu menu = new MenuMakanan(
        "M001",
        "Nasi Goreng",
        kategori,
        25000,
        "Makanan Nusantara"
);
```

### 9.2 Constructor

Constructor digunakan untuk memberikan nilai awal ketika object dibuat.

Pada program ini, constructor terdapat pada beberapa class, antara lain:

- `Kategori`
- `Menu`
- `MenuMakanan`
- `MenuMinuman`
- `Restoran`
- `RestoranView`
- `RestoranController`

Constructor membantu membentuk object dengan nilai yang dibutuhkan.

### 9.3 Access Modifier

Program menggunakan access modifier `private` pada attribute untuk membatasi akses langsung dari luar class.

Contohnya:

```java
private String namaMenu;
private double harga;
```

Attribute tersebut diakses melalui method yang disediakan oleh class terkait.

### 9.4 Encapsulation

Encapsulation diterapkan dengan menyembunyikan data internal object melalui attribute `private` serta menyediakan getter dan setter.

Contohnya:

```java
public String getNamaMenu() {
    return namaMenu;
}
```

dan:

```java
public void setNamaMenu(String namaMenu) {
    this.namaMenu = namaMenu;
}
```

### 9.5 Inheritance

Inheritance diterapkan melalui hubungan antara class `Menu` dengan `MenuMakanan` dan `MenuMinuman`.

Implementasinya:

```java
public class MenuMakanan extends Menu
```

dan:

```java
public class MenuMinuman extends Menu
```

Kedua subclass mewarisi attribute dan method dari class `Menu`, serta memiliki attribute tambahan sesuai jenis menunya.

### 9.6 Polymorphism Overriding

Polymorphism overriding diterapkan melalui method `tampilkanInfo()`.

Class `Menu` menyediakan abstract method:

```java
public abstract void tampilkanInfo();
```

Method tersebut kemudian diimplementasikan oleh `MenuMakanan` dan `MenuMinuman`.

Dengan demikian, method yang dijalankan akan menyesuaikan jenis object yang digunakan.

### 9.7 Polymorphism Overloading

Polymorphism overloading diterapkan melalui dua method yang memiliki nama sama, tetapi parameter berbeda:

```java
hitungHargaSetelahDiskon()
```

dan:

```java
hitungHargaSetelahDiskon(double persentaseDiskon)
```

Method pertama menghasilkan harga normal, sedangkan method kedua menghitung harga akhir berdasarkan persentase diskon.

### 9.8 Abstraction

Abstraction diterapkan melalui abstract class `Menu`.

Deklarasinya:

```java
public abstract class Menu
```

Class tersebut menyediakan struktur umum menu dan abstract method `tampilkanInfo()` yang diimplementasikan oleh subclass.

### 9.9 Abstract Method

Class `Menu` memiliki abstract method:

```java
public abstract void tampilkanInfo();
```

Method tersebut tidak memiliki implementasi langsung di class `Menu` dan harus diimplementasikan oleh subclass konkret.

### 9.10 Interface `Diskonable`

Interface `Diskonable` digunakan sebagai kontrak untuk perhitungan harga setelah diskon.

Interface ini memiliki method:

```java
double hitungHargaSetelahDiskon();

double hitungHargaSetelahDiskon(
        double persentaseDiskon
);
```

Class `Menu` mengimplementasikan interface tersebut:

```java
public abstract class Menu implements Diskonable
```

Interface ini merupakan bagian dari rancangan PBO yang telah ada pada Mini Project 3 dan tetap digunakan dalam Mini Project 4.

---

## 10. Validasi dan Penanganan Kesalahan

### 10.1 Validasi Input Kosong

Program tidak menerima input string kosong untuk field yang diminta melalui method `inputString()`.

Jika input kosong, program menampilkan:

```text
Input tidak boleh kosong
```

### 10.2 Validasi Pilihan

Pilihan menu harus berupa angka dan berada pada rentang yang ditentukan.

Jika input bukan angka, program menampilkan:

```text
Input harus berupa angka
```

Jika pilihan berada di luar rentang, program menampilkan pesan bahwa pilihan harus sesuai dengan rentang yang tersedia.

### 10.3 Validasi Harga

Harga harus berupa angka dan tidak boleh bernilai negatif.

Jika input bukan angka, program meminta pengguna memasukkan harga yang valid. Jika harga negatif, program menampilkan:

```text
Harga tidak boleh negatif
```

### 10.4 Validasi Persentase Diskon

Persentase diskon harus berada antara 0 sampai 100.

Jika nilai berada di luar rentang tersebut, program menampilkan:

```text
Persentase harus antara 0 sampai 100
```

Class `Menu` juga memeriksa nilai persentase pada method perhitungan diskon.

### 10.5 Validasi ID dan Relasi Database

Kolom `id_kategori` dan `id_menu` digunakan sebagai primary key sehingga database menolak ID duplikat.

Foreign key `menu.id_kategori` memastikan bahwa kategori yang dirujuk oleh menu harus tersedia di tabel `kategori`.

### 10.6 Penanganan SQLException

Operasi database dalam DAO menggunakan blok `try-catch` untuk menangani `SQLException`.

Jika query gagal, program mencetak pesan kesalahan di console dan mengembalikan nilai sesuai kebutuhan method.

Pesan kegagalan tersebut dapat membantu proses pemeriksaan ketika terjadi kesalahan koneksi, query, atau data yang tidak memenuhi constraint database.

---

## 11. Dokumentasi Program

Bagian ini digunakan untuk menunjukkan hasil implementasi program Mini Project 4.

### 11.1 Tampilan Menu Utama

Menunjukkan menu utama program yang menampilkan seluruh pilihan operasi.

<img width="435" height="261" alt="image" src="https://github.com/user-attachments/assets/b454a263-cf7e-472b-bcc4-f4c33424a61e" />

### 11.2 Pengujian Koneksi Database

Menunjukkan hasil pengujian koneksi menggunakan class `TestKoneksi`.

<img width="1507" height="537" alt="image" src="https://github.com/user-attachments/assets/4da62f83-d11a-471d-870a-a92f1b73bd17" />

### 11.3 Menampilkan Kategori dari MySQL

Menunjukkan bahwa daftar kategori diperoleh dari tabel `kategori`.

<img width="521" height="716" alt="image" src="https://github.com/user-attachments/assets/f655a072-38be-46d4-9593-4f7c629be218" />

### 11.4 Menambahkan Kategori

Menunjukkan proses menambahkan kategori melalui aplikasi dan hasil penyimpanannya ke tabel `kategori`.

<img width="487" height="431" alt="image" src="https://github.com/user-attachments/assets/583c3e5c-f328-4b3d-9667-62c687dec06f" />

<img width="475" height="555" alt="image" src="https://github.com/user-attachments/assets/7628d07b-bc93-403a-aa90-0ac6d627052d" />

### 11.5 Menampilkan Menu dari MySQL

Menunjukkan data menu yang diambil dari tabel `menu` dan nama kategori yang diperoleh melalui relasi tabel.

<img width="457" height="770" alt="image" src="https://github.com/user-attachments/assets/8f544829-10d8-4fa3-b494-4c9eeed7ba22" />

### 11.6 Menambahkan Menu

Menunjukkan proses menambahkan menu makanan atau minuman dan hasil penyimpanannya ke database.

<img width="473" height="792" alt="image" src="https://github.com/user-attachments/assets/d7505676-6869-4b80-9cfd-e3a57203fa88" />

### 11.7 Memperbarui Menu

Menunjukkan proses perubahan data menu berdasarkan ID dan hasil perubahan pada database.

<img width="490" height="862" alt="image" src="https://github.com/user-attachments/assets/f41e7594-82de-440f-84b9-3d1b58a39d2d" />

### 11.8 Menghapus Menu

Menunjukkan proses penghapusan menu dan hasil pemeriksaan bahwa data sudah tidak berada di tabel `menu`.

<img width="460" height="372" alt="image" src="https://github.com/user-attachments/assets/00a46ebd-527b-46cb-a9f3-2d8ddacdd45a" />

### 11.9 Hitung Harga Setelah Diskon

Menunjukkan proses mengambil menu dari database dan menghitung harga akhirnya setelah diskon.

<img width="475" height="482" alt="image" src="https://github.com/user-attachments/assets/c8de4349-54ca-4a5d-b2e1-0e4f79c265de" />

### 11.10 Struktur Package

Menunjukkan struktur package yang terdiri dari `config`, `dao`, `model`, `view`, dan `controller`.

<img width="287" height="632" alt="image" src="https://github.com/user-attachments/assets/8e315a9a-03e5-4e8b-bb77-e9d996da4319" />

---

## 12. Ringkasan Penerapan Konsep

Berikut ringkasan konsep yang diterapkan pada program:

| Konsep | Penerapan |
|---|---|
| Encapsulation | Attribute `private`, getter, dan setter |
| Inheritance | `MenuMakanan extends Menu` dan `MenuMinuman extends Menu` |
| Polymorphism Overriding | `tampilkanInfo()` pada `MenuMakanan` dan `MenuMinuman` |
| Polymorphism Overloading | `hitungHargaSetelahDiskon()` dan `hitungHargaSetelahDiskon(double)` |
| Abstraction | Abstract class `Menu` |
| Abstract Method | `abstract void tampilkanInfo()` |
| Interface | `Diskonable` |
| MVC | Pemisahan Model, View, dan Controller |
| JDBC | Menghubungkan aplikasi Java dengan MySQL |
| PreparedStatement | Menjalankan query SQL dengan parameter |
| DAO | Memusatkan operasi SQL pada `RestoranDAO` |
| Database Relasional | Relasi tabel `kategori` dan `menu` melalui foreign key |
| Persistensi Data | Data kategori dan menu disimpan di MySQL |

---

## 13. Kesimpulan

Sistem Manajemen Data Menu Restoran merupakan pengembangan dari Mini Project 3 yang menambahkan integrasi database MySQL ke dalam aplikasi Java.

Program mempertahankan konsep Pemrograman Berorientasi Objek yang telah diterapkan sebelumnya, yaitu encapsulation, inheritance, polymorphism, abstraction, serta interface `Diskonable`.

Pengembangan pada Mini Project 4 berfokus pada penggunaan JDBC sebagai penghubung aplikasi dengan database, `PreparedStatement` untuk menjalankan perintah SQL dengan parameter, serta DAO untuk memisahkan operasi database dari alur program.

Data kategori dan menu dikelola melalui tabel `kategori` dan `menu`. Operasi penambahan dan penampilan tersedia untuk kategori, sedangkan operasi penambahan, penampilan, pembaruan, dan penghapusan tersedia untuk menu.

Struktur MVC tetap digunakan agar interaksi pengguna, alur program, dan akses data memiliki tanggung jawab masing-masing. Dengan adanya database, data yang berhasil disimpan dapat digunakan kembali ketika aplikasi dijalankan ulang.

Melalui pengembangan ini, program tidak hanya menerapkan konsep PBO, tetapi juga menerapkan dasar integrasi database menggunakan Java dan MySQL dengan struktur kode yang lebih terpisah dan terorganisasi.
