# Aplikasi Data Kendaraan

Implementasi lengkap sesuai blueprint: Backend Java (Spring Boot + Hibernate/JPA), Frontend HTML/JQuery/Bootstrap.

## Struktur Proyek
```
src/main/java/com/company/datakendaraan/
  ├── model/Kendaraan.java              # Entity
  ├── repository/KendaraanRepository.java
  ├── service/KendaraanService.java
  ├── controller/KendaraanController.java
  └── exception/                        # Global error handling
src/main/resources/
  ├── db/migration/                     # Flyway (V1: create table, V2: seed data)
  ├── static/                           # index.html, form.html, css, js
  └── application*.properties           # config dev / prod / default
src/test/java/...                       # Unit test (service) & Integration test (controller)
Dockerfile, docker-compose.yml          # Deployment
.github/workflows/ci.yml                # CI pipeline
```

## Panduan Implementasi 1 Hari

### Jam 1 — Setup Environment
```bash
# Prasyarat: JDK 17, Maven, Docker (opsional tapi direkomendasikan)
java -version
mvn -version

# Opsi A (paling cepat): jalankan MySQL via Docker
docker run -d --name mysql-dev -e MYSQL_ROOT_PASSWORD=root \
  -e MYSQL_DATABASE=data_kendaraan_dev -p 3306:3306 mysql:8.0

# Opsi B: gunakan MySQL yang sudah terinstall lokal, sesuaikan
# username/password di src/main/resources/application-dev.properties
```

### Jam 2 — Jalankan Backend
```bash
cd data-kendaraan
mvn spring-boot:run
```
- Flyway otomatis membuat tabel `kendaraan` + seed 5 data dummy saat aplikasi start.
- Cek health check: http://localhost:8080/actuator/health
- Cek API: http://localhost:8080/api/kendaraan

### Jam 3 — Buka Frontend
- Buka browser: **http://localhost:8080/index.html**
- Halaman Monitoring akan langsung menampilkan data dari database (bukan dummy statis).
- Coba fitur: Search, Add, Edit, Detail, Delete (dengan modal konfirmasi).

### Jam 4-5 — Uji Coba & Penyesuaian
```bash
# Jalankan semua test (unit + integration, otomatis pakai H2 in-memory)
mvn test
```
- Sesuaikan styling di `static/css/style.css` jika perlu.
- **Catatan penting:** field dropdown "Bahan Bakar" pada blueprint asli berisi opsi warna
  (`Merah, Hitam, Biru, Abu-Abu`) — kemungkinan tertukar dengan field Warna Kendaraan.
  Sudah diimplementasikan **persis sesuai dokumen requirement**; jika ini bukan yang
  dimaksud, cukup ubah `<option>` di `static/form.html` menjadi opsi bahan bakar yang benar
  (mis. Bensin, Solar, Listrik).

### Jam 6 — Build Artifact
```bash
mvn clean package
# Hasil: target/data-kendaraan.jar
java -jar target/data-kendaraan.jar
```

### Jam 7 — Deployment (Docker)
```bash
docker compose up --build
```
Ini akan menjalankan MySQL + aplikasi sekaligus. Akses di http://localhost:8080/index.html

Untuk deployment ke server produksi:
```bash
# Set environment variable production terlebih dahulu
export DB_URL=jdbc:mysql://<host-db-prod>:3306/data_kendaraan
export DB_USERNAME=<user>
export DB_PASSWORD=<password>

java -jar data-kendaraan.jar --spring.profiles.active=prod
```

### Jam 8 — Smoke Test & Serah Terima
- Pastikan `GET /actuator/health` mengembalikan status `UP`.
- Lakukan uji CRUD end-to-end sekali lagi di environment target.
- Dokumentasikan kredensial & endpoint ke tim terkait.

## API Endpoint Reference

| Method | Endpoint | Keterangan |
|---|---|---|
| GET | `/api/kendaraan` | List semua data |
| GET | `/api/kendaraan?noRegistrasi=&namaPemilik=` | Search |
| GET | `/api/kendaraan/{noRegistrasi}` | Detail |
| POST | `/api/kendaraan` | Tambah |
| PUT | `/api/kendaraan/{noRegistrasi}` | Update |
| DELETE | `/api/kendaraan/{noRegistrasi}` | Hapus |

## CI/CD
Push ke branch `main`/`develop` akan otomatis menjalankan pipeline di `.github/workflows/ci.yml`:
test → build JAR → build Docker image.
