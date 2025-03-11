$(document).ready(function () {
  const tablePersona = initializeDataTable();
  const originalData = [];

  // Inicializar DataTable
  function initializeDataTable() {
    return $("#personaTable").DataTable({
      processing: true,
      ajax: {
        url: "personaservlet",
        type: "GET",
        dataSrc: function (json) {
          console.log("Datos recibidos:", json);
          originalData.length = 0; // Limpiar el array
          originalData.push(...(Array.isArray(json) ? json : []));
          return originalData.filter((persona) => persona.actiPers === true);
        },
        error: handleAjaxError("Error al cargar los datos de la tabla"),
      },
      columns: [
        { data: "nombTipoDoc" },
        { data: "numeDocu" },
        { data: "nombre_completo" },
        {
          data: null,
          render: function (data, type, row) {
            return row?.codiPers
              ? `
                            <button class="btn btn-primary btnEdit" data-id="${row.codiPers}">
                                <i class="fas fa-edit"></i>
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

  // Manejo de errores AJAX
  function handleAjaxError(message) {
    return function (xhr, error, thrown) {
      mostrarAlerta(message, "error");
    };
  }

  // Mostrar alertas
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

  // Filtrar datos
  $("#btnFiltrar").on("click", function () {
    const buttonText = $(this).val();
    const showInactive = buttonText === "true";
    const filteredData = showInactive
      ? originalData.filter((persona) => persona.actiPers === false)
      : originalData.filter((persona) => persona.actiPers === true);

    tablePersona.clear().rows.add(filteredData).draw();
    $(this).val(showInactive ? "false" : "true");
    $(this).text(showInactive ? "Mostrar Activos" : "Mostrar Inactivos");
  });

  // Abrir modal para editar
  $("#personaTable tbody").on("click", ".btnEdit", function () {
    const id = $(this).data("id");
    $.get(`personaservlet/${id}`, function (data) {
      populateModal(data);
      console.log(data);
      $("#personaModalLabel").text("Editar Persona");
      $("#personaModal").modal("show");
    }).fail(handleAjaxError("Error al cargar los datos de la persona"));
  });

  $("#personaModal .btn-close, #personaModal .btn-secondary").click(
    function () {
      $("#personaModal").modal("hide");
    }
  );

  $("#personaModal").on("hidden.bs.modal", function () {
    $("#nivel2, #nivel3")
      .prop("disabled", true)
      .append("<option value=''>Selecciona</option>");
    $("#codiAFP")
      .prop("disabled", true)
      .append("<option value=''>Selecciona</option>");
  });

  // Llenar el modal con datos
  function populateModal(data) {
    const fields = [
      "codiPers",
      "codiTipoDoc",
      "numeDocu",
      "appaPers",
      "apmaPers",
      "nombPers",
      "codiNaci",
      "sexoPers",
      "codiPaisEmis",
      "codiCLDN",
      "numeCelu",
      "mailPers",
      "codiTipoVia",
      "nombVia",
      "numeVia",
      "depaPers",
      "codiEntiBanc",
      "codiPlantilla",
      "codiArea",
      "codiCargo",
      "codiTipoContrato",
      "tipoSituacion",
      "fechaIngreso",
      "remuneracion",
      "tipoPago",
      "bancoSueldo",
      "cuentaSueldo",
      "regimenLaboral",
      "regimenSalud",
      "regimenPensionario",
      "cuspp",
      "nacionalidad",
      "estadoCivil",
      "sexo",
      "nivelEducativo",
      "ocupacion",
      "tipoTrabajador",
      "categoriaOcupacion",
      "periodicidadIngreso",
      "bancoCts",
      "cuentaCts",
      "jornadaLaboral",
      "situacionEspecial",
      "discapacidad",
      "sindicalizado",
      "statcExonerado",
      "convenio",
      "aportaSctr",
      "polizaSeguro",
      "asignacionFamiliar",
      "sucursal",
      "actiPers",
    ];

    fields.forEach((field) => $(`#${field}`).val(data[field] || ""));

    cargarFechs(data.fechNaci, "fechNaci");
    cargarFechs(data.fechaIngreso, "fechaIngreso");
    if (data.fechaCese) {
      cargarFechs(data.fechaCese, "fechaCese");
    }

    if (data.codiAFP) {
      $("#codiAFP").val(data.codiAFP);
      $("#codiAFP").prop("disabled", false);
      $("#snpPers").val("false");
    }

    if (data.snpPers) {
      $("#snpPers").val(data.snpPers ? "true" : "false");
    }

    $("#nivel1").val(data.nivel1);
    $("#nivel2").prop("disabled", false);
    $("#nivel3").prop("disabled", false);

    if (data.nivel1) {
      cargarNivel2(data.nivel1, data.nivel2, data.nivel3);
    }

    if (data.tipoPago === "2") {
      $("#bancoSueldo, #cuentaSueldo").prop("disabled", false);
    }
  }

  function cargarFechs(fecha, clave) {
    // Parse the date string manually
    const partes = fecha.split(" ");
    const mes = {
      Jan: "01",
      Feb: "02",
      Mar: "03",
      Apr: "04",
      May: "05",
      Jun: "06",
      Jul: "07",
      Aug: "08",
      Sep: "09",
      Oct: "10",
      Nov: "11",
      Dec: "12",
    }[partes[1]];
    const dia = partes[2];
    const año = partes[5];

    // Create formatted date string
    const fechaFormateada = `${año}-${mes}-${dia}`;

    $("#" + clave).val(fechaFormateada);
  }

  $("#tipoPago").change(function () {
    const tipoPago = $(this).val();
    if (tipoPago === "2") {
      $("#bancoSueldo, #cuentaSueldo").prop("disabled", false);
      $("#bancoSueldo, #cuentaSueldo").prop("required", true);
    } else {
      $("#bancoSueldo, #cuentaSueldo").prop("disabled", true);
      $("#bancoSueldo").prop("selectedIndex", 0);
      $("#cuentaSueldo").val("");
      $("#bancoSueldo, #cuentaSueldo").prop("required", false);
    }
  });

  // Enviar formulario
  $("#personaForm").submit(function (e) {
    e.preventDefault();
    const formData = $(this).serializeArray();
    const id = $("#codiPers").val();
    const url = id ? `personaservlet/${id}` : "personaservlet";
    const method = id ? "PUT" : "POST";
    const action = id ? "actualizar" : "crear";

    console.log(formData);

    const jsonData = {};

    formData.forEach((field) => {
      jsonData[field.name] = field.value;
    });

    console.log({ jsonData });

    $.ajax({
      url: url,
      type: method,
      contentType: "application/json",
      data: JSON.stringify(jsonData),
      success: function (response) {
        if (response.success) {
          $("#personaModal").modal("hide");
          tablePersona.ajax.reload(null, false);
          mostrarAlerta(`${action} exitoso`, "success");
        }
      },
      error: handleAjaxError(`Error al ${action} la persona`),
    });
  });

  // Agregar nueva persona
  $("#btnAddPersona").click(function () {
    $("#personaForm")[0].reset();
    $("#codiPers").val("");
    $("#personaModalLabel").text("Agregar Persona");
    $("#personaModal").modal("show");
  });

  // Exportar a PDF
  $("#btnPDF").click(exportToPDF);

  // Exportar a Excel
  $("#btnExportar").click(exportToExcel);

  // Cargar combos
  loadCombos();

  // Cargar niveles de ubigeo
  $("#nivel1").change(function () {
    const codiUbig = $(this).val();
    if (codiUbig) {
      $("#nivel2").prop("disabled", false);
      cargarNivel2(codiUbig);
    } else {
      $("#nivel2, #nivel3")
        .prop("disabled", true)
        .empty()
        .append('<option value="">Selecciona</option>');
    }
  });

  $("#nivel2").change(function () {
    const codiUbig = $(this).val();
    if (codiUbig) {
      $("#nivel3").prop("disabled", false);
      cargarNivel3(codiUbig);
    } else {
      $("#nivel3")
        .prop("disabled", true)
        .empty()
        .append('<option value="">Selecciona</option>');
    }
  });

  // Cargar AFP basado en SNP
  $("#snpPers").change(function () {
    cargarAFP();
  });
});

// Funciones auxiliares
function cargarNivel2(codiUbig, nivel2Value, nivel3Value) {
  loadCombo(
    "ubigeoservlet",
    "#nivel2",
    { nivel: 2, codiUbig: codiUbig },
    nivel2Value,
    () => {
      if (nivel2Value) cargarNivel3(nivel2Value, nivel3Value);
    }
  );
}

function cargarNivel3(codiUbig, nivel3Value) {
  loadCombo(
    "ubigeoservlet",
    "#nivel3",
    { nivel: 3, codiUbig: codiUbig },
    nivel3Value
  );
}

function cargarAFP() {
  const snp = $("#snpPers").val();
  $("#codiAFP").prop("disabled", snp === "true");
  if (snp === "false") {
    loadCombo("afpservlet", "#codiAFP");
  } else {
    loadCombo("afpservlet", "#codiAFP");
  }
}

function loadCombos() {
  const combos = [
    { url: "tipodocumentoservlet", selector: "#codiTipoDoc" },
    { url: "paisemisorservlet", selector: "#codiPaisEmis" },
    { url: "cldnservlet", selector: "#codiCLDN" },
    { url: "nacionalidadservlet", selector: "#nacionalidad" },
    { url: "tipoviaservlet", selector: "#codiTipoVia" },
    { url: "ubigeoservlet", selector: "#nivel1", data: { nivel: 1 } },
    { url: "entidadbancariaservlet", selector: "#bancoSueldo" },
    { url: "entidadbancariaservlet", selector: "#bancoCts" },
    { url: "afpservlet", selector: "#codiAFP" },
    { url: "plantillaservlet", selector: "#codiPlantilla" },
    { url: "areaservlet", selector: "#codiArea" },
    { url: "cargoservlet", selector: "#codiCargo" },
    { url: "categoriaocupacional", selector: "#categoriaOcupacion" },
    { url: "periocidadremuneracion", selector: "#periodicidadIngreso" },
    { url: "regimenlaboralservlet", selector: "#regimenLaboral" },
    { url: "regimenpensionarioservlet", selector: "#regimenPensionario" },
    { url: "regimensaludservlet", selector: "#regimenSalud" },
    { url: "situacioneducativaservlet", selector: "#nivelEducativo" },
    { url: "situacionespecialservlet", selector: "#situacionEspecial" },
    { url: "situacionservlet", selector: "#tipoSituacion" },
    { url: "tipocontrato", selector: "#codiTipoContrato" },
    { url: "tipoocupacion", selector: "#ocupacion" },
    { url: "tipopagoservlet", selector: "#tipoPago" },
    { url: "tipotrabajoservlet", selector: "#tipoTrabajador" },
    { url: "estadocivil", selector: "#estadoCivil" },
    { url: "jornadalaboral", selector: "#jornadaLaboral" },
    { url: "sucursalservlet", selector: "#sucursal" },
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
          `<option value="${
            item.codiUbig ||
            item.codiTipoDoc ||
            item.codiPaisEmis ||
            item.codiCldn ||
            item.codiNaci ||
            item.codiTipoVia ||
            item.codiEntiBanc ||
            item.codiAfp ||
            item.codiPlant ||
            item.codiEstadoCivil ||
            item.codiTipoTrab ||
            item.codiTipoPago ||
            item.codiTipo ||
            item.codiTipoCntr ||
            item.codiSitua ||
            item.codiSituEspe ||
            item.codiSituEduc ||
            item.codiRegiSal ||
            item.codiRegiPensi ||
            item.codiRegiLabo ||
            item.codiPeriRemu ||
            item.codiCategoria ||
            item.codiCargo ||
            item.codiArea ||
            item.codiSucurs ||
            item.codiJornadLab
          }">${
            item.nombUbig ||
            item.nombTipoDoc ||
            item.nombPaisEmis ||
            item.nombCldn ||
            item.nombNaci ||
            item.nombTipoVia ||
            item.nombEntiBanc ||
            item.nombAfp ||
            item.nombPlant ||
            item.nombEstadoCivil ||
            item.nombTipoTrab ||
            item.nombTipoPago ||
            item.nombTipo ||
            item.nombTipoCntr ||
            item.nombSitua ||
            item.nombSituEspe ||
            item.nombSituEduc ||
            item.nombRegiSal ||
            item.nombRegiPensi ||
            item.nombRegiLabo ||
            item.nombPeriRemu ||
            item.nombCategoria ||
            item.nombCargo ||
            item.nombArea ||
            item.nombSucurs ||
            item.nombJornadLab
          }</option>`
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

function exportToPDF() {
  const { jsPDF } = window.jspdf;
  const doc = new jsPDF();
  const fechaHora = new Date().toLocaleString();
  const nombreSistema = "Software de Planilla";
  const pageWidth = doc.internal.pageSize.getWidth();

  doc.setFontSize(8);
  doc.text(fechaHora, 10, 10);
  doc.text(nombreSistema, pageWidth - doc.getTextWidth(nombreSistema) - 10, 10);

  const titulo = "LISTADO DE PERSONAS";
  doc.setFontSize(12);
  doc.text(titulo, (pageWidth - doc.getTextWidth(titulo)) / 2, 20);

  const headers = [];
  $("#personaTable thead th").each(function (i, header) {
    if (i < $("#personaTable thead th").length - 1) {
      headers.push($(header).text());
    }
  });

  const rows = [];
  $("#personaTable tbody tr").each(function () {
    const row = [];
    $(this)
      .find("td")
      .each(function (index) {
        if (index < $(this).parent().find("td").length - 1) {
          row.push($(this).text());
        }
      });
    rows.push(row);
  });

  doc.autoTable({ head: [headers], body: rows, startY: 30 });
  doc.save("Persona.pdf");
}

function exportToExcel() {
  const tableData = [];
  const headers = [];
  $("#personaTable thead th").each(function (i, header) {
    if (i < $("#personaTable thead th").length - 1) {
      headers.push($(header).text());
    }
  });
  tableData.push(headers);

  $("#personaTable tbody tr").each(function () {
    const rowData = [];
    $(this)
      .find("td")
      .each(function (i, cell) {
        if (i < $(this).parent().find("td").length - 1) {
          rowData.push($(cell).text());
        }
      });
    tableData.push(rowData);
  });

  const ws = XLSX.utils.aoa_to_sheet(tableData);
  const wb = XLSX.utils.book_new();
  XLSX.utils.book_append_sheet(wb, ws, "Persona");
  XLSX.writeFile(wb, "persona.xlsx");
}
