# Aplikasi Data Kendaraan

![Java](https://img.shields.io/badge/Java-17-orange.svg?style=flat-square&logo=java)
![Spring Boot](https://img.shields.io/badge/Spring_Boot-3.x-brightgreen.svg?style=flat-square&logo=spring)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue.svg?style=flat-square&logo=mysql)
![Docker](https://img.shields.io/badge/Docker-Ready-2496ED.svg?style=flat-square&logo=docker)

Aplikasi Data Kendaraan adalah sistem manajemen informasi berbasis web yang dirancang untuk mengelola pendataan kendaraan secara efisien dan terstruktur. Dibangun dengan pendekatan arsitektur yang bersih, proyek ini memastikan performa yang andal untuk kebutuhan pemrosesan data sehari-hari.

---

## 🛠️ Stack Teknologi

Proyek ini dikembangkan menggunakan teknologi standar industri modern:
*   **Backend Engine:** Java 17 dengan framework Spring Boot untuk pemrosesan API yang cepat dan stabil.
*   **Manajemen Database:** MySQL sebagai penyimpanan utama dan H2 Database untuk kebutuhan pengujian terisolasi.
*   **Migrasi Skema:** Flyway, yang secara otomatis membangun struktur tabel dan menyuntikkan data awal saat aplikasi pertama kali dijalankan.
*   **Antarmuka Pengguna:** Dibangun secara native menggunakan HTML5, CSS3, Bootstrap, dan JQuery untuk menghasilkan antarmuka yang responsif dan intuitif.
*   **Infrastruktur:** Menggunakan Docker untuk kontainerisasi dan disiapkan untuk integrasi CI/CD melalui GitHub Actions.

---

## ✨ Fitur Utama

*   **Dashboard Monitoring:** Menampilkan ikhtisar seluruh data kendaraan yang terdaftar dalam satu tampilan tabel yang komprehensif.
*   **Pencarian Presisi:** Memudahkan penelusuran data spesifik berdasarkan Nomor Registrasi atau Nama Pemilik.
*   **Manajemen Data (CRUD):** Fungsionalitas penuh untuk menambah, melihat detail, memperbarui, dan menghapus data kendaraan. 
*   **Kontrol Entri Data:** Sistem ini mengedepankan akurasi. Oleh karena itu, antarmuka dirancang dengan aksi simpan manual pada setiap formulir entri data untuk memberikan kontrol validasi sepenuhnya kepada pengguna sebelum data direkam ke dalam database.

---

## 🚀 Gambaran Arsitektur & Alur Kerja

Aplikasi ini dirancang agar mudah diimplementasikan (plug-and-play) baik di lingkungan pengembangan maupun produksi. 

Secara garis besar, aplikasi beroperasi dengan alur berikut:
1.  **Inisialisasi Lingkungan:** Aplikasi membutuhkan database relasional (disarankan menggunakan kontainer MySQL untuk isolasi yang lebih baik).
2.  **Proses Bootstrapping:** Saat *backend* dijalankan menggunakan Maven, sistem akan langsung terkoneksi ke database, mengeksekusi skrip migrasi Flyway untuk membentuk struktur tabel, dan menjalankan server lokal.
3.  **Akses Antarmuka:** Pengguna dapat langsung berinteraksi dengan sistem melalui peramban web tanpa memerlukan konfigurasi sisi klien yang rumit.

Untuk fase *Deployment*, seluruh ekosistem aplikasi (Backend dan Database) telah dibungkus dalam konfigurasi **Docker Compose**. Hal ini memastikan aplikasi dapat langsung dijalankan di server produksi mana pun hanya dengan satu baris perintah, meminimalisasi isu inkompatibilitas antar sistem operasi.

---

## 📌 Catatan Implementasi (Requirement Note)

Berdasarkan dokumen *blueprint* referensi awal, terdapat anomali pada struktur formulir antarmuka (`form.html`). Kolom *dropdown* **Warba Kendaraan** saat ini berisi opsi turunan warna (`Merah, Hitam, Biru, Abu-Abu`). 

Implementasi saat ini dibuat persis mengikuti dokumen tersebut untuk menjaga integritas *requirement*. Namun, jika ini merupakan kesalahan penulisan pada *blueprint*, tag `<option>` pada berkas HTML tersebut dapat dengan mudah disesuaikan menjadi jenis bahan bakar yang relevan (misalnya: Bensin, Solar, Listrik) sebelum masuk ke tahap produksi.