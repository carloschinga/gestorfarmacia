$(document).ready(function () {
  $("#btnProcesar").click(function () {
    const codiPeriodo = $("#comboPeriodos").val();
    if (codiPeriodo === "") {
      mostrarAlerta("Seleccione un periodo");
      return;
    }
    limpiarTable();
    const tableTareo = initializeDataTable(codiPeriodo);
  });

  function limpiarTable() {
    $("#tableTareo").DataTable().clear().draw();
  }

  function initializeDataTable(codiPeriodo) {
    return $("#tableTareo").DataTable({
      processing: true,
      ajax: {
        url: "asistenciatareo",
        // url: "contableperiodo",
        type: "GET",
        data: { codiPeriodo: codiPeriodo },
        dataSrc: function (json) {
          // Validar que el objeto JSON no este vacío
          if (json.length === 0) {
            mostrarAlerta("Seleccione un periodo");
            return;
          }
          return json;
        },
        error: handleAjaxError("Error al cargar los datos en la tabla"),
      },
      columns: [
        { data: "nombre" },
        { data: "minutosTotales" },
        { data: "diasFaltantes" },
        {
          data: null,
          render: function (data, type, row) {
            return row?.codiPers
              ? `
                            <button class="btn btn-primary btnDetail" data-id="${row.codiPers}">
                                <i class="fa-solid fa-circle-info"></i>
                            </button>`
              : "";
          },
        },
      ],
      language: {
        url: "https://cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
      },
    });
  }

  function handleAjaxError(message) {
    return function (xhr, error, thrown) {
      mostrarAlerta(message, "error");
    };
  }

  function mostrarAlerta(mensaje, icono) {
    Swal.fire({
      toast: true,
      position: "top-end",
      icon: icono,
      title: mensaje,
      showConfirmButton: false,
      timer: 2000,
      timerProgressBar: true,
      customClass: { popup: "swal2-sm" },
    });
  }

  function loadCombos() {
    const combos = [{ url: "contableperiodo", selector: "#comboPeriodos" }];

    combos.forEach((combo) => loadCombo(combo.url, combo.selector, combo.data));
  }

  function loadCombo(
    url,
    selector,
    data = {},
    defaultValue = null,
    callback = null
  ) {
    $.ajax({
      url: url,
      type: "GET",
      data: data,
      success: function (response) {
        const combo = $(selector);
        combo.empty().append('<option value="">Selecciona</option>');
        response.forEach((item) => {
          combo.append(
            `<option value="${item.codiPeri}">${item.nombPeri}</option>`
          );
        });
        if (defaultValue) combo.val(defaultValue);
        if (callback) callback();
      },
      error: function () {
        mostrarAlerta(`Error al cargar los datos de ${selector}`, "error");
      },
    });
  }

  loadCombos();
});
