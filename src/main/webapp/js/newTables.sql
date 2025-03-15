-- Active: 1737403269267@@localhost@3333@gestor18
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

INSERT INTO asistencia_marcacion (codiPers, fechMarc, codihoraDeta, marcIngr, marcSald) VALUES
-- Persona 28 (VENTAS, Turno MAÑANA)
(28, '2024-03-01', 11 , '08:10:00', '12:10:00'),
(28, '2024-03-03', 1 , '08:10:00', '12:50:00'),
(28, '2024-03-04',  3, '08:10:00', '13:05:00'),
(28, '2024-03-05',  5, '08:10:00', '13:00:00'),
(28, '2024-03-06',  7, '08:10:00', '13:00:00'),
(28, '2024-03-07',  9, '08:10:00', '13:00:00'),
(28, '2024-03-08',  11, '08:10:00', '11:55:00'),
(28, '2024-03-11',  1, '08:10:00', '13:00:00'),
(28, '2024-03-12',  3, '08:10:00', '13:00:00'),
(28, '2024-03-13',  5, '08:10:00', '13:00:00'),
(28, '2024-03-14',  7, '08:10:00', '13:00:00'),
(28, '2024-03-03', 2, '15:02:00', '20:01:00'),
(28, '2024-03-04', 4, '15:10:00', '20:03:00'),
(28, '2024-03-05', 6, '15:10:00', '20:03:00'),
(28, '2024-03-06', 8, '15:10:00', '20:03:00'),
(28, '2024-03-07', 10, '15:10:00', '20:03:00');

-- Persona 29 (ADMINISTRATIVO, Turno TARDE)
(29, '2024-03-01', '15:00:00', '20:00:00'),
(29, '2024-03-04', '15:00:00', '20:00:00'),
(29, '2024-03-05', '15:00:00', '20:00:00'),
(29, '2024-03-06', '15:00:00', '20:00:00'),
(29, '2024-03-07', '15:00:00', '20:00:00'),
(29, '2024-03-08', '15:00:00', '20:00:00'),
(29, '2024-03-11', '15:00:00', '20:00:00'),
(29, '2024-03-12', '15:00:00', '20:00:00'),
(29, '2024-03-13', '15:00:00', '20:00:00'),
(29, '2024-03-14', '15:00:00', '20:00:00'),

-- Persona 30 (ADMINISTRATIVO, Turno TARDE)
(30, '2024-03-01', '15:00:00', '20:00:00'),
(30, '2024-03-04', '15:00:00', '20:00:00'),
(30, '2024-03-05', '15:00:00', '20:00:00'),
(30, '2024-03-06', '15:00:00', '20:00:00'),
(30, '2024-03-07', '15:00:00', '20:00:00'),
(30, '2024-03-08', '15:00:00', '20:00:00'),
(30, '2024-03-11', '15:00:00', '20:00:00'),
(30, '2024-03-12', '15:00:00', '20:00:00'),
(30, '2024-03-13', '15:00:00', '20:00:00'),
(30, '2024-03-14', '15:00:00', '20:00:00'),

-- Persona 31 (VENTAS, Turno MAÑANA)
(31, '2024-03-01', '08:10:00', '13:00:00'),
(31, '2024-03-04', '08:10:00', '13:00:00'),
(31, '2024-03-05', '08:10:00', '13:00:00'),
(31, '2024-03-06', '08:10:00', '13:00:00'),
(31, '2024-03-07', '08:10:00', '13:00:00'),
(31, '2024-03-08', '08:10:00', '13:00:00'),
(31, '2024-03-11', '08:10:00', '13:00:00'),
(31, '2024-03-12', '08:10:00', '13:00:00'),
(31, '2024-03-13', '08:10:00', '13:00:00'),
(31, '2024-03-14', '08:10:00', '13:00:00');