package com.company.datakendaraan.service;

import com.company.datakendaraan.exception.DuplicateKeyException;
import com.company.datakendaraan.exception.ResourceNotFoundException;
import com.company.datakendaraan.model.Kendaraan;
import com.company.datakendaraan.repository.KendaraanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class KendaraanService {

    private final KendaraanRepository repository;

    public KendaraanService(KendaraanRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<Kendaraan> findAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public List<Kendaraan> search(String noRegistrasi, String namaPemilik) {
        String no = noRegistrasi == null ? "" : noRegistrasi;
        String nama = namaPemilik == null ? "" : namaPemilik;
        return repository.findByNoRegistrasiContainingIgnoreCaseAndNamaPemilikContainingIgnoreCase(no, nama);
    }

    @Transactional(readOnly = true)
    public Kendaraan findById(String noRegistrasi) {
        return repository.findById(noRegistrasi)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Data kendaraan dengan No Registrasi '" + noRegistrasi + "' tidak ditemukan"));
    }

    @Transactional
    public Kendaraan create(Kendaraan kendaraan) {
        if (repository.existsById(kendaraan.getNoRegistrasi())) {
            throw new DuplicateKeyException(
                    "No Registrasi '" + kendaraan.getNoRegistrasi() + "' sudah terdaftar");
        }
        return repository.save(kendaraan);
    }

    @Transactional
    public Kendaraan update(String noRegistrasi, Kendaraan payload) {
        Kendaraan existing = findById(noRegistrasi);
        existing.setNamaPemilik(payload.getNamaPemilik());
        existing.setAlamat(payload.getAlamat());
        existing.setMerkKendaraan(payload.getMerkKendaraan());
        existing.setTahunPembuatan(payload.getTahunPembuatan());
        existing.setKapasitasSilinder(payload.getKapasitasSilinder());
        existing.setWarnaKendaraan(payload.getWarnaKendaraan());
        existing.setBahanBakar(payload.getBahanBakar());
        return repository.save(existing);
    }

    @Transactional
    public void delete(String noRegistrasi) {
        Kendaraan existing = findById(noRegistrasi);
        repository.delete(existing);
    }
}
