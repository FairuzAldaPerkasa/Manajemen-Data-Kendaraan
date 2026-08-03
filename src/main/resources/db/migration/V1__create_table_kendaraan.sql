CREATE TABLE kendaraan (
    no_registrasi        VARCHAR(20)  NOT NULL,
    nama_pemilik         VARCHAR(100) NOT NULL,
    alamat               TEXT,
    merk_kendaraan       VARCHAR(50),
    tahun_pembuatan      INT,
    kapasitas_silinder   INT,
    warna_kendaraan      VARCHAR(30),
    bahan_bakar          VARCHAR(30),
    CONSTRAINT pk_kendaraan PRIMARY KEY (no_registrasi)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;
