let currentMode = "add"; // add | edit | detail
let currentId = null;

$(document).ready(function () {
  const params = new URLSearchParams(window.location.search);
  currentMode = params.get("mode") || "add";
  currentId = params.get("id");

  setupModeUI();

  if (currentMode !== "add" && currentId) {
    loadDetail(currentId);
  }

  $("#btnBack").on("click", function () {
    window.location.href = "index.html";
  });

  // Validasi numeric-only saat mengetik
  $("#tahunPembuatan, #kapasitasSilinder").on("input", function () {
    this.value = this.value.replace(/[^0-9]/g, "");
  });

  $("#kendaraanForm").on("submit", function (e) {
    e.preventDefault();
    if (currentMode === "detail") return;
    handleSubmit();
  });
});

function setupModeUI() {
  if (currentMode === "add") {
    $("#formTitle").text("Tambah Data Kendaraan");
    $("#btnSubmit").text("Simpan");
  } else if (currentMode === "edit") {
    $("#formTitle").text("Edit Data Kendaraan");
    $("#btnSubmit").text("Ubah");
    $("#noRegistrasi").prop("readonly", true); // PK tidak boleh diubah
  } else if (currentMode === "detail") {
    $("#formTitle").text("Detail Data Kendaraan");
    $("#btnSubmit").hide();
    $("#kendaraanForm :input").prop("disabled", true);
  }
}

function loadDetail(id) {
  KendaraanApi.getById(id)
    .done(function (data) {
      $("#noRegistrasi").val(data.noRegistrasi);
      $("#namaPemilik").val(data.namaPemilik);
      $("#alamat").val(data.alamat);
      $("#merkKendaraan").val(data.merkKendaraan);
      $("#tahunPembuatan").val(data.tahunPembuatan);
      $("#kapasitasSilinder").val(data.kapasitasSilinder);
      $("#warnaKendaraan").val(data.warnaKendaraan);
      $("#bahanBakar").val(data.bahanBakar || "");
    })
    .fail(function (xhr) {
      showAlert("danger", extractErrorMessage(xhr));
    });
}

function validateForm() {
  let valid = true;
  const form = document.getElementById("kendaraanForm");

  // Mandatory: No Registrasi & Nama Pemilik
  ["#noRegistrasi", "#namaPemilik"].forEach(function (sel) {
    const $el = $(sel);
    if (!$el.val() || $el.val().trim() === "") {
      $el.addClass("is-invalid");
      valid = false;
    } else {
      $el.removeClass("is-invalid");
    }
  });

  // Numeric checks
  const tahun = $("#tahunPembuatan").val();
  if (tahun && (!/^\d{1,4}$/.test(tahun))) {
    $("#tahunPembuatan").addClass("is-invalid");
    valid = false;
  } else {
    $("#tahunPembuatan").removeClass("is-invalid");
  }

  const kapasitas = $("#kapasitasSilinder").val();
  if (kapasitas && !/^\d+$/.test(kapasitas)) {
    $("#kapasitasSilinder").addClass("is-invalid");
    valid = false;
  } else {
    $("#kapasitasSilinder").removeClass("is-invalid");
  }

  return valid;
}

function buildPayload() {
  return {
    noRegistrasi: $("#noRegistrasi").val().trim(),
    namaPemilik: $("#namaPemilik").val().trim(),
    alamat: $("#alamat").val(),
    merkKendaraan: $("#merkKendaraan").val(),
    tahunPembuatan: $("#tahunPembuatan").val() ? parseInt($("#tahunPembuatan").val(), 10) : null,
    kapasitasSilinder: $("#kapasitasSilinder").val() ? parseInt($("#kapasitasSilinder").val(), 10) : null,
    warnaKendaraan: $("#warnaKendaraan").val(),
    bahanBakar: $("#bahanBakar").val(),
  };
}

function handleSubmit() {
  if (!validateForm()) {
    showAlert("danger", "Mohon lengkapi field yang wajib diisi dengan benar.");
    return;
  }

  const payload = buildPayload();

  if (currentMode === "add") {
    KendaraanApi.create(payload)
      .done(function () {
        window.location.href = "index.html";
      })
      .fail(function (xhr) {
        showAlert("danger", extractErrorMessage(xhr));
      });
  } else if (currentMode === "edit") {
    KendaraanApi.update(currentId, payload)
      .done(function () {
        window.location.href = "index.html";
      })
      .fail(function (xhr) {
        showAlert("danger", extractErrorMessage(xhr));
      });
  }
}
