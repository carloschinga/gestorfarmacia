-- Active: 1737403269267@@localhost@3333@gestor
CREATE TABLE contable_periodo (
    codiPeri INT PRIMARY KEY AUTO_INCREMENT,
    nombPeri VARCHAR(100) NOT NULL,
    yearPeri INT NOT NULL,
    messPeri INT NOT NULL,
    asisPeri BOOLEAN NOT NULL
);

CREATE TABLE asistencia_rango_periodo (
    codiRang int PRIMARY KEY,
    inicRang DATE NOT NULL,
    finaRang DATE NOT NULL
);

CREATE TABLE asistencia_tareo (
    codiTare INT PRIMARY KEY AUTO_INCREMENT,
    codiPeri INT NOT NULL,
    codiRang INT NOT NULL
);

DROP TRIGGER IF EXISTS trg_after_insert_contable_periodo;

CREATE TRIGGER trg_after_insert_contable_periodo
AFTER INSERT ON contable_periodo
FOR EACH ROW
BEGIN
    DECLARE v_inicRang DATE;
    DECLARE v_finaRang DATE;
    DECLARE v_day INT;
    DECLARE v_last_day_of_month INT;
    DECLARE v_valuTareoPara INT;

    -- Obtener el valor de valuTareoPara del registro con codiPara = 2 en asistencia_parametros
    SELECT CAST(valuTareoPara AS UNSIGNED) INTO v_valuTareoPara
    FROM asistencia_parametros
    WHERE codiPara = 2;
    
    SET v_last_day_of_month = DAY(LAST_DAY(CURDATE()));

    -- Calcular la fecha de inicio (inicRang) usando el día proporcionado
    SET v_inicRang = DATE_ADD(DATE_FORMAT(CURDATE(), '%Y-%m-01'), INTERVAL (v_valuTareoPara - 1) DAY);

    -- Calcular la fecha de fin (finaRang) como un día antes del mismo día del próximo mes
    SET v_finaRang = DATE_SUB(DATE_ADD(v_inicRang, INTERVAL 1 MONTH), INTERVAL 1 DAY);
    
    -- Insertar el nuevo registro en la tabla asistencia_rango_periodo
    INSERT INTO asistencia_rango_periodo (codiRang, inicRang, finaRang)
    VALUES (NEW.codiPeri,v_inicRang, v_finaRang);
    
    -- Insertar el nuevo registro en la tabla asistencia_tareo
    INSERT INTO asistencia_tareo (codiPeri, codiRang)
    VALUES (NEW.codiPeri, NEW.codiPeri);
END;

SELECT
    COALESCE(vp.codigo, v.codigo) AS codigo,
    vpro.nombre_producto AS nombre_producto,
    cat.codiCate AS codiCate,
    vps.categoria AS categoria,
    lab.codiLabo AS codiLabo,
    vps.laboratorio AS laboratorio,
    vpro.unidades_por_caja AS unidxcaja,
    vpro.precio_venta_caja AS pvc,
    vpro.costo_caja AS pcc,
    vpro.precio_venta_unitario_1 AS pvu,
    vpro.costo_unitario AS pcu,
    vlol.venta_30_ult_dias AS ventas30ultmdias,
    COALESCE(v.Mes1, 0) + COALESCE(v.Mes2, 0) + COALESCE(v.Mes3, 0) AS Ventas,
    COALESCE(v.Mes1, 0) AS Mes1,
    COALESCE(v.Mes2, 0) AS Mes2,
    COALESCE(v.Mes3, 0) AS Mes3,
    vps.stock_total AS stock,
    ROUND(
        (
            vpro.precio_venta_caja - vpro.costo_caja
        ) / vpro.precio_venta_caja * 100,
        2
    ) AS gananciacaja,
    ROUND(
        (
            vpro.precio_venta_unitario_1 - vpro.costo_unitario
        ) / vpro.precio_venta_unitario_1 * 100,
        2
    ) AS gananciauni,
    ROUND(
        (
            COALESCE(vlol.venta_30_ult_dias, 0) / 30
        ) * 3.5,
        2
    ) AS stockmin,
    ROUND(
        vps.stock_total / NULLIF(
            vlol.venta_30_ult_dias / 30,
            0
        ),
        2
    ) AS indiinve,
    ROUND(
        vlol.venta_30_ult_dias / NULLIF(vps.stock_total, 0),
        2
    ) AS indirota
FROM
    ventas_pharma vp
    LEFT JOIN vista_ventas_productos_ultimos_meses v ON vp.codigo = v.codigo
    LEFT JOIN ventas_producto vpro ON vp.codigo = vpro.codigo
    LEFT JOIN ventas_categoria cat on vpro.categoria_id = cat.codiCate
    LEFT JOIN ventas_laboratorio lab on vpro.laboratorio_id = lab.codiLabo
    LEFT JOIN ventas_productos_stock vps ON vpro.codigo = vps.codigo
    LEFT JOIN (
        SELECT codigo, SUM(cantidad_vendida) AS venta_30_ult_dias
        FROM ventas_pharma
        WHERE
            fecha_venta >= (CURDATE() - INTERVAL 31 DAY)
        GROUP BY
            codigo
    ) vlol ON vp.codigo = vlol.codigo;

CREATE VIEW vista_compras_hoja_trabajo AS
SELECT
    COALESCE(vp.codigo, v.codigo) AS codigo,
    vpro.nombre_producto AS nombre_producto,
    cat.codiCate AS codiCate,
    vps.categoria AS categoria,
    lab.codiLabo AS codiLabo,
    vps.laboratorio AS laboratorio,
    vpro.unidades_por_caja AS unidxcaja,
    vpro.precio_venta_caja AS pvc,
    vpro.costo_caja AS pcc,
    vpro.precio_venta_unitario_1 AS pvu,
    vpro.costo_unitario AS pcu,
    vlol.venta_30_ult_dias AS ventas30ultmdias,
    COALESCE(v.Mes1, 0) + COALESCE(v.Mes2, 0) + COALESCE(v.Mes3, 0) AS Ventas,
    COALESCE(v.Mes1, 0) AS Mes1,
    COALESCE(v.Mes2, 0) AS Mes2,
    COALESCE(v.Mes3, 0) AS Mes3,
    vps.stock_total AS stock,
    ROUND(
        (
            vpro.precio_venta_caja - vpro.costo_caja
        ) / vpro.precio_venta_caja * 100,
        2
    ) AS gananciacaja,
    ROUND(
        (
            vpro.precio_venta_unitario_1 - vpro.costo_unitario
        ) / vpro.precio_venta_unitario_1 * 100,
        2
    ) AS gananciauni,
    ROUND(
        (
            COALESCE(vlol.venta_30_ult_dias, 0) / 30
        ) * 3.5,
        2
    ) AS stockmin,
    ROUND(
        vps.stock_total / NULLIF(
            vlol.venta_30_ult_dias / 30,
            0
        ),
        2
    ) AS indiinve,
    ROUND(
        vlol.venta_30_ult_dias / NULLIF(vps.stock_total, 0),
        2
    ) AS indirota
FROM
    (SELECT DISTINCT codigo FROM ventas_pharma) vp
    LEFT JOIN vista_ventas_productos_ultimos_meses v ON vp.codigo = v.codigo
    LEFT JOIN ventas_producto vpro ON vp.codigo = vpro.codigo
    LEFT JOIN ventas_categoria cat ON vpro.categoria_id = cat.codiCate
    LEFT JOIN ventas_laboratorio lab ON vpro.laboratorio_id = lab.codiLabo
    LEFT JOIN ventas_productos_stock vps ON vpro.codigo = vps.codigo
    LEFT JOIN (
        SELECT codigo, SUM(cantidad_vendida) AS venta_30_ult_dias
        FROM ventas_pharma
        WHERE
            fecha_venta >= (CURDATE() - INTERVAL 31 DAY)
        GROUP BY
            codigo
    ) vlol ON vp.codigo = vlol.codigo;