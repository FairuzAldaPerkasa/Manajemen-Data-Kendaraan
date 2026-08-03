package com.company.datakendaraan.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;

@Entity
@Table(name = "kendaraan")
public class Kendaraan {

    @Id
    @NotBlank(message = "No Registrasi wajib diisi")
    @Size(max = 20, message = "No Registrasi maksimal 20 karakter")
    @Column(name = "no_registrasi", length = 20, nullable = false)
    private String noRegistrasi;

    @NotBlank(message = "Nama Pemilik wajib diisi")
    @Size(max = 100, message = "Nama Pemilik maksimal 100 karakter")
    @Column(name = "nama_pemilik", length = 100, nullable = false)
    private String namaPemilik;

    @Column(name = "alamat", columnDefinition = "TEXT")
    private String alamat;

    @Size(max = 50, message = "Merk Kendaraan maksimal 50 karakter")
    @Column(name = "merk_kendaraan", length = 50)
    private String merkKendaraan;

    @Digits(integer = 4, fraction = 0, message = "Tahun Pembuatan harus angka maksimal 4 digit")
    @Column(name = "tahun_pembuatan")
    private Integer tahunPembuatan;

    @Digits(integer = 6, fraction = 0, message = "Kapasitas Silinder harus berupa angka")
    @Column(name = "kapasitas_silinder")
    private Integer kapasitasSilinder;

    @Size(max = 30, message = "Warna Kendaraan maksimal 30 karakter")
    @Column(name = "warna_kendaraan", length = 30)
    private String warnaKendaraan;

    @Size(max = 30)
    @Column(name = "bahan_bakar", length = 30)
    private String bahanBakar;

    public Kendaraan() {
    }

    public String getNoRegistrasi() {
        return noRegistrasi;
    }

    public void setNoRegistrasi(String noRegistrasi) {
        this.noRegistrasi = noRegistrasi;
    }

    public String getNamaPemilik() {
        return namaPemilik;
    }

    public void setNamaPemilik(String namaPemilik) {
        this.namaPemilik = namaPemilik;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getMerkKendaraan() {
        return merkKendaraan;
    }

    public void setMerkKendaraan(String merkKendaraan) {
        this.merkKendaraan = merkKendaraan;
    }

    public Integer getTahunPembuatan() {
        return tahunPembuatan;
    }

    public void setTahunPembuatan(Integer tahunPembuatan) {
        this.tahunPembuatan = tahunPembuatan;
    }

    public Integer getKapasitasSilinder() {
        return kapasitasSilinder;
    }

    public void setKapasitasSilinder(Integer kapasitasSilinder) {
        this.kapasitasSilinder = kapasitasSilinder;
    }

    public String getWarnaKendaraan() {
        return warnaKendaraan;
    }

    public void setWarnaKendaraan(String warnaKendaraan) {
        this.warnaKendaraan = warnaKendaraan;
    }

    public String getBahanBakar() {
        return bahanBakar;
    }

    public void setBahanBakar(String bahanBakar) {
        this.bahanBakar = bahanBakar;
    }
}