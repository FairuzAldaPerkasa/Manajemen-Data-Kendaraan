let deleteModal;
let currentDeleteId = null;

$(document).ready(function () {
  deleteModal = new bootstrap.Modal(document.getElementById("deleteModal"));
  loadData();

  $("#btnSearch").on("click", function () {
    loadData();
  });

  $("#btnAdd").on("click", function () {
    window.location.href = "form.html?mode=add";
  });

  $("#tblBody").on("click", ".action-link.detail", function () {
    const id = $(this).data("id");
    window.location.href = "form.html?mode=detail&id=" + encodeURIComponent(id);
  });

  $("#tblBody").on("click", ".action-link.edit", function () {
    const id = $(this).data("id");
    window.location.href = "form.html?mode=edit&id=" + encodeURIComponent(id);
  });

  $("#tblBody").on("click", ".action-link.delete", function () {
    currentDeleteId = $(this).data("id");
    $("#deleteTargetId").text(currentDeleteId);
    deleteModal.show();
  });

  $("#btnConfirmDelete").on("click", function () {
    if (!currentDeleteId) return;
    KendaraanApi.remove(currentDeleteId)
      .done(function () {
        deleteModal.hide();
        showAlert("success", "Data " + currentDeleteId + " berhasil dihapus.");
        loadData();
      })
      .fail(function (xhr) {
        deleteModal.hide();
        showAlert("danger", extractErrorMessage(xhr));
      });
  });
});

function loadData() {
  const params = {
    noRegistrasi: $("#searchNoRegistrasi").val() || "",
    namaPemilik: $("#searchNamaPemilik").val() || "",
  };

  KendaraanApi.list(params)
    .done(function (data) {
      renderTable(data);
    })
    .fail(function (xhr) {
      showAlert("danger", extractErrorMessage(xhr));
    });
}

function renderTable(data) {
  const $tbody = $("#tblBody");
  $tbody.empty();

  if (!data || data.length === 0) {
    $("#emptyState").removeClass("d-none");
    return;
  }
  $("#emptyState").addClass("d-none");

  data.forEach(function (row, index) {
    const tr = $("<tr></tr>");
    tr.append($("<td></td>").text(index + 1));
    tr.append($("<td></td>").text(row.noRegistrasi));
    tr.append($("<td></td>").text(row.namaPemilik));
    tr.append($("<td></td>").text(row.merkKendaraan || ""));
    tr.append($("<td></td>").text(row.tahunPembuatan || ""));
    tr.append($("<td></td>").text((row.kapasitasSilinder || "") + (row.kapasitasSilinder ? " cc" : "")));
    tr.append($("<td></td>").text(row.warnaKendaraan || ""));
    tr.append($("<td></td>").text(row.bahanBakar || ""));

    const actionTd = $("<td></td>");
    actionTd.append($('<span class="action-link detail"></span>').text("Detail").data("id", row.noRegistrasi));
    actionTd.append($('<span class="action-link edit"></span>').text("Edit").data("id", row.noRegistrasi));
    actionTd.append($('<span class="action-link delete"></span>').text("Delete").data("id", row.noRegistrasi));
    tr.append(actionTd);

    $tbody.append(tr);
  });
}
