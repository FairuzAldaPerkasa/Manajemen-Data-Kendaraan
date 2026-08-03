package com.company.datakendaraan.service;

import com.company.datakendaraan.exception.DuplicateKeyException;
import com.company.datakendaraan.exception.ResourceNotFoundException;
import com.company.datakendaraan.model.Kendaraan;
import com.company.datakendaraan.repository.KendaraanRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class KendaraanServiceTest {

    @Mock
    private KendaraanRepository repository;

    @InjectMocks
    private KendaraanService service;

    private Kendaraan sample;

    @BeforeEach
    void setUp() {
        sample = new Kendaraan();
        sample.setNoRegistrasi("B-7763-TXY");
        sample.setNamaPemilik("Lionel Messi");
        sample.setMerkKendaraan("Honda PCX");
        sample.setTahunPembuatan(2018);
        sample.setKapasitasSilinder(150);
        sample.setWarnaKendaraan("Hitam");
        sample.setBahanBakar("Bensin");
    }

    @Test
    void findById_dataAda_returnKendaraan() {
        when(repository.findById("B-7763-TXY")).thenReturn(Optional.of(sample));

        Kendaraan result = service.findById("B-7763-TXY");

        assertEquals("Lionel Messi", result.getNamaPemilik());
    }

    @Test
    void findById_dataTidakAda_throwResourceNotFound() {
        when(repository.findById("X")).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> service.findById("X"));
    }

    @Test
    void create_noRegistrasiSudahAda_throwDuplicateKey() {
        when(repository.existsById("B-7763-TXY")).thenReturn(true);

        assertThrows(DuplicateKeyException.class, () -> service.create(sample));
        verify(repository, never()).save(any());
    }

    @Test
    void create_dataBaru_berhasilTersimpan() {
        when(repository.existsById("B-7763-TXY")).thenReturn(false);
        when(repository.save(sample)).thenReturn(sample);

        Kendaraan result = service.create(sample);

        assertEquals("B-7763-TXY", result.getNoRegistrasi());
        verify(repository, times(1)).save(sample);
    }

    @Test
    void update_dataAda_fieldTerupdate() {
        when(repository.findById("B-7763-TXY")).thenReturn(Optional.of(sample));
        when(repository.save(any(Kendaraan.class))).thenAnswer(inv -> inv.getArgument(0));

        Kendaraan payload = new Kendaraan();
        payload.setNamaPemilik("Lionel Messi Updated");
        payload.setWarnaKendaraan("Merah");

        Kendaraan result = service.update("B-7763-TXY", payload);

        assertEquals("Lionel Messi Updated", result.getNamaPemilik());
        assertEquals("Merah", result.getWarnaKendaraan());
    }

    @Test
    void delete_dataAda_repositoryDeleteDipanggil() {
        when(repository.findById("B-7763-TXY")).thenReturn(Optional.of(sample));

        service.delete("B-7763-TXY");

        verify(repository, times(1)).delete(sample);
    }

    @Test
    void search_menggunakanQueryContains() {
        when(repository.findByNoRegistrasiContainingIgnoreCaseAndNamaPemilikContainingIgnoreCase("B-77", ""))
                .thenReturn(List.of(sample));

        List<Kendaraan> result = service.search("B-77", null);

        assertEquals(1, result.size());
    }
}
