$(document).ready(function () {
  const tableHojaTrabajo = initDataTable();
  const originalData = [];

  let fechaActual = moment().format("YYYY-MM-DD");

  loadCombos();

  $("#fecha").val(fechaActual);
  cargarProveedores();
  $("#cmbProveedor").select2({
    placeholder: "Seleccione un proveedor",
    allowClear: true,
  });
  let tablaSeleccionados = $("#tablaSeleccionados").DataTable({
    autoWidth: false,
    pageLength: 10,
    language: {
      url: "https://cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
    },
  });

  $("#comboCategorias, #comboLaboratorios").on("change", function () {
    const cateId = $("#comboCategorias").val();
    const laboratorioId = $("#comboLaboratorios").val();

    filtrarTabla(cateId, laboratorioId);
  });

  function filtrarTabla(categoriaId, laboratorioId) {
    let dataFiltrada = originalData;

    // Aplicar filtro de categoría si está seleccionado
    if (categoriaId) {
      const catId = parseInt(categoriaId);
      dataFiltrada = dataFiltrada.filter((item) => item.cateId === catId);
    }

    // Aplicar filtro de laboratorio si está seleccionado
    if (laboratorioId) {
      const labId = parseInt(laboratorioId);
      dataFiltrada = dataFiltrada.filter((item) => item.laboId === labId);
    }

    // Actualizar la tabla con la data filtrada
    tableHojaTrabajo.clear().rows.add(dataFiltrada).draw();
  }

  function loadCombos() {
    const combos = [
      { url: "ventascategoria", selector: "#comboCategorias" },
      { url: "ventaslaboratorio", selector: "#comboLaboratorios" },
    ];

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
            `<option value="${item.codigo}">${item.nombre}</option>`
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

  function initDataTable() {
    return $("#comprasTable").DataTable({
      ajax: {
        url: "hojatrabajoservlet",
        type: "GET",
        dataSrc: function (json) {
          originalData.length = 0;
          originalData.push(...(Array.isArray(json) ? json : []));
          return json;
        },
        error: function () {
          mostrarAlerta("Error al cargar la tabla", "error");
        },
      },
      columns: [
        { data: "codigo" },
        {
          data: "nombre",
        },
        {
          data: "unidxcaja",
        },
        {
          data: "pvc",
        },
        {
          data: "pcc",
        },
        {
          data: "pvu",
        },
        {
          data: "pcu",
        },
        {
          data: "ventas30ultmdias",
        },
        {
          data: "ventas",
        },
        {
          data: "mes1",
        },
        {
          data: "mes2",
        },
        {
          data: "mes3",
        },
        {
          data: "stock",
        },
        {
          data: "gananciacaja",
        },
        {
          data: "gananciauni",
        },
        {
          data: "stockmin",
        },
        {
          data: "indiinve",
        },
        {
          data: "indirota",
        },
        {
          data: "codigo",
          render: function (data, type, row) {
            return (
              "<input type='number' class='cant' value='' data-codpro='" +
              data +
              "' min='0' style='width: 50px;'>"
            );
          },
        },
      ],
      pageLength: 10,
      language: {
        url: "https://cdn.datatables.net/plug-ins/1.10.21/i18n/Spanish.json",
      },
      scrollX: true,
    });
  }

  // Resaltar fila cuando se ingresa cantidad
  $(document).on("input", ".cant", function () {
    let $row = $(this).closest("tr");

    if ($(this).val().trim() !== "") {
      $row.addClass("fila-resaltada");
    } else {
      $row.removeClass("fila-resaltada");
    }
  });

  // **Recalcular valores cuando cambia el precio de la caja**
  $(document).on("input", ".precio-caja", function () {
    let fila = $(this).closest("tr");
    let precioCaja = parseFloat($(this).val()) || 0;
    let cantidad = parseInt(fila.find("td:eq(4)").text()) || 0;
    let subtotal = (cantidad * precioCaja).toFixed(2);
    let igv = (subtotal * 0.18).toFixed(2);
    let total = (parseFloat(subtotal) + parseFloat(igv)).toFixed(2);

    fila.find(".subtotal").text(subtotal);
    fila.find(".igv").text(igv);
    fila.find(".total").text(total);

    actualizarTotales();
  });

  // **Eliminar fila y recalcular totales**
  $("#modalTable tbody").on("click", ".btnEliminar", function () {
    $(this).closest("tr").remove();
    actualizarTotales();
  });

  // Botón para limpiar selección
  $("#btnLimpiarSeleccion").on("click", function () {
    $(".cant").val("").removeClass("resaltado");
    $("#comprasTable tbody tr").removeClass("fila-resaltada");
    $("#modalTable tbody").empty();
    mostrarAlerta("Se limpió correctamente", "success");
  });

  // Botón para generar orden de compra
  $("#btnOrdenCompra").on("click", function () {
    let filasSeleccionadas = [];

    $("#comprasTable tbody tr").each(function () {
      let inputCant = $(this).find(".cant").val().trim();
      if (inputCant !== "" && !isNaN(inputCant)) {
        let codigo = $(this).find("td:eq(0)").text();
        let categoria = $(this).find("td:eq(1)").text();
        let laboratorio = $(this).find("td:eq(2)").text();
        let nombre = $(this).find("td:eq(3)").text();
        let cantidad = parseInt(inputCant) || 0;
        let precioCaja =
          parseFloat($(this).find("td:eq(4)").text().trim()) || 0;
        let subtotal = (cantidad * precioCaja).toFixed(2);
        let igv = (subtotal * 0.18).toFixed(2);
        let total = (parseFloat(subtotal) + parseFloat(igv)).toFixed(2);

        let filaHTML = `
                <tr>
                    <td>${codigo}</td>
                    <td>${categoria}</td>
                    <td>${laboratorio}</td>
                    <td>${nombre}</td>
                    <td>${cantidad}</td>
                    <td><input type="number" class="form-control precio-caja" style="width: 80px; font-size: 12px;" value="${precioCaja}" min="0"></td>
                    <td class="subtotal text-right">${subtotal}</td>
                    <td class="igv text-right">${igv}</td>
                    <td class="total text-right">${total}</td>
                    <td><button class="btn btn-danger btn-sm btnEliminar"><i class="fas fa-trash-alt"></i></button></td>
                </tr>`;

        filasSeleccionadas.push(filaHTML);
      }
    });

    if (filasSeleccionadas.length > 0) {
      let modalTableBody = $("#modalTable tbody");
      modalTableBody.empty();
      filasSeleccionadas.forEach((fila) => modalTableBody.append(fila));
      $("#modalSeleccionados").modal("show");
      actualizarTotales();
    } else {
      mostrarAlerta("No existen datos seleccionados", "error");
      return;
    }

    tablaSeleccionados.clear().draw();
    tablaSeleccionados.draw();
  });

  $.fn.dataTable.ext.order["dom-text-numeric"] = function (settings, col) {
    return this.api()
      .column(col, { order: "index" })
      .nodes()
      .map(function (td) {
        let inputVal = $("input", td).val();
        return inputVal !== "" ? parseFloat(inputVal) || 0 : 0;
      });
  };

  // Cargar Proveedores
  function cargarProveedores() {
    $.ajax({
      url: "proveedorservlet", // Ajusta la URL según sea necesario
      method: "GET",
      dataType: "json",
      success: function (data) {
        let select = $("#cmbProveedor");
        select.empty(); // Limpiar opciones previas
        select.append('<option value="">Seleccione un proveedor</option>');
        data.forEach((proveedor) => {
          select.append(
            `<option value="${proveedor.codiProv}">${proveedor.nombProv}</option>`
          );
        });
        select.prop("disabled", false); // Habilitar el select
        select.select2({
          placeholder: "Seleccione un proveedor",
          allowClear: true,
        });
      },
      error: function () {
        mostrarAlerta("Error al cargar los proveedores", "error");
      },
    });
  }

  // **Actualizar Subtotal, IGV y Total General**
  function actualizarTotales() {
    let subtotalGeneral = 0,
      igvGeneral = 0,
      totalGeneral = 0;

    $("#modalTable tbody tr").each(function () {
      let $fila = $(this);
      let subtotal = parseFloat($fila.find(".subtotal").text()) || 0;
      let igv = parseFloat($fila.find(".igv").text()) || 0;
      let total = parseFloat($fila.find(".total").text()) || 0;

      subtotalGeneral += subtotal;
      igvGeneral += igv;
      totalGeneral += total;
    });

    $("#subtotalGeneral").text(subtotalGeneral.toFixed(2));
    $("#igvGeneral").text(igvGeneral.toFixed(2));
    $("#totalGeneral").text("S/ " + totalGeneral.toFixed(2));
    $("#totalGeneralTexto").text(
      "SON: " + numeroALetras(totalGeneral.toFixed(2))
    );
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
      customClass: {
        popup: "swal2-sm",
      },
    });
  }
  function numeroALetras(num) {
    let unidades = [
      "",
      "UNO",
      "DOS",
      "TRES",
      "CUATRO",
      "CINCO",
      "SEIS",
      "SIETE",
      "OCHO",
      "NUEVE",
    ];
    let decenas = [
      "DIEZ",
      "ONCE",
      "DOCE",
      "TRECE",
      "CATORCE",
      "QUINCE",
      "DIECISÉIS",
      "DIECISIETE",
      "DIECIOCHO",
      "DIECINUEVE",
    ];
    let decenasMayores = [
      "",
      "",
      "VEINTE",
      "TREINTA",
      "CUARENTA",
      "CINCUENTA",
      "SESENTA",
      "SETENTA",
      "OCHENTA",
      "NOVENTA",
    ];
    let centenas = [
      "",
      "CIENTO",
      "DOSCIENTOS",
      "TRESCIENTOS",
      "CUATROCIENTOS",
      "QUINIENTOS",
      "SEISCIENTOS",
      "SETECIENTOS",
      "OCHOCIENTOS",
      "NOVECIENTOS",
    ];

    function convertirNumero(n) {
      if (n < 10) return unidades[n];
      if (n < 20) return decenas[n - 10];
      if (n < 100)
        return (
          decenasMayores[Math.floor(n / 10)] +
          (n % 10 > 0 ? " Y " + unidades[n % 10] : "")
        );
      if (n < 1000)
        return (
          centenas[Math.floor(n / 100)] +
          (n % 100 > 0 ? " " + convertirNumero(n % 100) : "")
        );
      if (n < 1000000)
        return (
          convertirNumero(Math.floor(n / 1000)) +
          " MIL" +
          (n % 1000 > 0 ? " " + convertirNumero(n % 1000) : "")
        );
      return "Número muy grande";
    }

    let parteEntera = Math.floor(num);
    let parteDecimal = Math.round((num - parteEntera) * 100);

    let textoEntero = convertirNumero(parteEntera);
    let textoDecimal =
      parteDecimal > 0 ? `CON ${parteDecimal}/100` : "CON 00/100";

    return textoEntero + " " + textoDecimal;
  }

  $("#modalSeleccionados").on(
    "click",
    ".btn-close, .btn-secondary",
    function () {
      $("#modalSeleccionados").modal("hide");
    }
  );

  $("#btnGenerarOC").click(function () {
    let proveedorId = $("#cmbProveedor").val();
    let fechaOC = $("#fecha").val();

    if (!proveedorId || !fechaOC) {
      mostrarAlerta("Debe seleccionar un proveedor y una fecha.", "error");
      return;
    }

    let detalles = [];
    $("#modalTable tbody tr").each(function () {
      let codiProd = $(this).find("td:eq(0)").text();
      let nombProd = $(this).find("td:eq(3)").text();
      let cantiProd = $(this).find("td:eq(4) input").val();
      let precProd = $(this).find("td:eq(5)").text();
      let subtProd = $(this).find("td:eq(6)").text();
      let igvProd = $(this).find("td:eq(7)").text();
      let totaProd = $(this).find("td:eq(8)").text();

      detalles.push({
        codiProd,
        nombProd,
        cantiProd: parseInt(cantiProd),
        precProd: parseFloat(precProd),
        subtProd: parseFloat(subtProd),
        igvProd: parseFloat(igvProd),
        totaProd: parseFloat(totaProd),
      });
    });

    let data = {
      codiProv: parseInt(proveedorId),
      fechOC: fechaOC,
      detalles: detalles,
    };

    enviarOrdenCompra(data);
  });

  function enviarOrdenCompra(data) {
    $.ajax({
      url: "ochtservlet",
      type: "POST",
      contentType: "application/json",
      data: JSON.stringify(data),
      success: function (response) {
        alert("Orden de Compra registrada con éxito.");
        $("#modalSeleccionados").modal("hide");
      },
      error: function () {
        alert("Error al registrar la Orden de Compra.");
      },
    });
  }
});
