package com.company.datakendaraan.controller;

import com.company.datakendaraan.model.Kendaraan;
import com.company.datakendaraan.service.KendaraanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/kendaraan")
@CrossOrigin(origins = "*") 
public class KendaraanController {

    private final KendaraanService service;

    public KendaraanController(KendaraanService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<Kendaraan>> list(
            @RequestParam(required = false) String noRegistrasi,
            @RequestParam(required = false) String namaPemilik) {

        if (noRegistrasi != null || namaPemilik != null) {
            return ResponseEntity.ok(service.search(noRegistrasi, namaPemilik));
        }
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/{noRegistrasi}")
    public ResponseEntity<Kendaraan> detail(@PathVariable String noRegistrasi) {
        return ResponseEntity.ok(service.findById(noRegistrasi));
    }

    @PostMapping
    public ResponseEntity<Kendaraan> create(@Valid @RequestBody Kendaraan kendaraan) {
        Kendaraan saved = service.create(kendaraan);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @PutMapping("/{noRegistrasi}")
    public ResponseEntity<Kendaraan> update(@PathVariable String noRegistrasi,
                                             @Valid @RequestBody Kendaraan kendaraan) {
        return ResponseEntity.ok(service.update(noRegistrasi, kendaraan));
    }

    @DeleteMapping("/{noRegistrasi}")
    public ResponseEntity<Void> delete(@PathVariable String noRegistrasi) {
        service.delete(noRegistrasi);
        return ResponseEntity.noContent().build();
    }
}
