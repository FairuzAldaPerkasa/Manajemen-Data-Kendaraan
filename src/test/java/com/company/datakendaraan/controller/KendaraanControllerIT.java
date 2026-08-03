package com.company.datakendaraan.controller;

import com.company.datakendaraan.model.Kendaraan;
import com.company.datakendaraan.repository.KendaraanRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
class KendaraanControllerIT {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private KendaraanRepository repository;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        repository.deleteAll();
    }

    @Test
    void create_dataValid_return201() throws Exception {
        Kendaraan kendaraan = sample();

        mockMvc.perform(post("/api/kendaraan")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(kendaraan)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.noRegistrasi", is("B-7763-TXY")));
    }

    @Test
    void create_noRegistrasiKosong_return400() throws Exception {
        Kendaraan kendaraan = sample();
        kendaraan.setNoRegistrasi("");

        mockMvc.perform(post("/api/kendaraan")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(kendaraan)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void create_duplicateNoRegistrasi_return409() throws Exception {
        repository.save(sample());

        mockMvc.perform(post("/api/kendaraan")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sample())))
                .andExpect(status().isConflict());
    }

    @Test
    void getById_dataTidakAda_return404() throws Exception {
        mockMvc.perform(get("/api/kendaraan/TIDAK-ADA"))
                .andExpect(status().isNotFound());
    }

    @Test
    void search_byNamaPemilik_returnFiltered() throws Exception {
        repository.save(sample());

        mockMvc.perform(get("/api/kendaraan").param("namaPemilik", "Messi"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void delete_dataAda_return204() throws Exception {
        repository.save(sample());

        mockMvc.perform(delete("/api/kendaraan/B-7763-TXY"))
                .andExpect(status().isNoContent());
    }

    private Kendaraan sample() {
        Kendaraan k = new Kendaraan();
        k.setNoRegistrasi("B-7763-TXY");
        k.setNamaPemilik("Lionel Messi");
        k.setMerkKendaraan("Honda PCX");
        k.setTahunPembuatan(2018);
        k.setKapasitasSilinder(150);
        k.setWarnaKendaraan("Hitam");
        k.setBahanBakar("Bensin");
        return k;
    }
}
