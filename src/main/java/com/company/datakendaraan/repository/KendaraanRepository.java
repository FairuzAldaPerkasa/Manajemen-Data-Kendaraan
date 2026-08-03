package com.company.datakendaraan.repository;

import com.company.datakendaraan.model.Kendaraan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface KendaraanRepository extends JpaRepository<Kendaraan, String> {

    List<Kendaraan> findByNoRegistrasiContainingIgnoreCaseAndNamaPemilikContainingIgnoreCase(
            String noRegistrasi, String namaPemilik);
}
