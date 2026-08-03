// Helper AJAX terpusat untuk komunikasi ke REST API backend
const API_BASE_URL = "/api/kendaraan";

const KendaraanApi = {
  list: function (params) {
    return $.ajax({
      url: API_BASE_URL,
      method: "GET",
      data: params || {},
    });
  },

  getById: function (noRegistrasi) {
    return $.ajax({
      url: API_BASE_URL + "/" + encodeURIComponent(noRegistrasi),
      method: "GET",
    });
  },

  create: function (payload) {
    return $.ajax({
      url: API_BASE_URL,
      method: "POST",
      contentType: "application/json",
      data: JSON.stringify(payload),
    });
  },

  update: function (noRegistrasi, payload) {
    return $.ajax({
      url: API_BASE_URL + "/" + encodeURIComponent(noRegistrasi),
      method: "PUT",
      contentType: "application/json",
      data: JSON.stringify(payload),
    });
  },

  remove: function (noRegistrasi) {
    return $.ajax({
      url: API_BASE_URL + "/" + encodeURIComponent(noRegistrasi),
      method: "DELETE",
    });
  },
};

function showAlert(type, message) {
  const $box = $("#alertBox");
  $box.removeClass("d-none alert-success alert-danger")
      .addClass("alert-" + type)
      .text(message);
  setTimeout(() => $box.addClass("d-none"), 4000);
}

function extractErrorMessage(xhr) {
  if (xhr.responseJSON && xhr.responseJSON.message) {
    return xhr.responseJSON.message;
  }
  return "Terjadi kesalahan. Silakan coba lagi.";
}
