CREATE TABLE almacen (
    id_almacen INT PRIMARY KEY AUTO_INCREMENT,
    nombre_almacen VARCHAR(100) NOT NULL,
    direccion TEXT NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO'
);

CREATE TABLE stock_lote (
    id_stock_lote INT PRIMARY KEY AUTO_INCREMENT,
    codigo_producto VARCHAR(50) NOT NULL,
    id_almacen INT NOT NULL,
    lote VARCHAR(50) NOT NULL,
    fecha_vencimiento DATE NOT NULL,
    stock_actual INT NOT NULL CHECK (stock_actual >= 0),
    stock_reservado INT NOT NULL DEFAULT 0 CHECK (stock_reservado >= 0),
    costo_unitario DECIMAL(10,2) NOT NULL,
    estado ENUM('ACTIVO', 'VENCIDO', 'AGOTADO') DEFAULT 'ACTIVO',
    CONSTRAINT fk_stock_prod FOREIGN KEY (codigo_producto) REFERENCES productos(codigo_producto),
    CONSTRAINT fk_stock_almacen FOREIGN KEY (id_almacen) REFERENCES almacen(id_almacen)
);

CREATE TABLE ventas_cabecera (
    id_venta INT PRIMARY KEY AUTO_INCREMENT,
    fecha_venta DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_cliente INT NOT NULL,
    id_tipo_comprobante INT NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    estado ENUM('EMITIDA', 'ANULADA') DEFAULT 'EMITIDA',
    CONSTRAINT fk_cliente FOREIGN KEY (id_cliente) REFERENCES clientes(id_cliente),
    CONSTRAINT fk_tipo_comprobante FOREIGN KEY (id_tipo_comprobante) REFERENCES tipo_comprobante(id_tipo_comprobante)
);

CREATE TABLE movimiento_cabecera (
    id_movimiento INT PRIMARY KEY AUTO_INCREMENT,
    id_venta INT NULL,  -- Relación con ventas_cabecera
    fecha_movimiento DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_tipo_movimiento INT NOT NULL,
    almacen_origen INT NULL,
    almacen_destino INT NULL,
    estado ENUM('PENDIENTE', 'PROCESADO', 'ANULADO') DEFAULT 'PENDIENTE',
    observaciones TEXT NULL,
    CONSTRAINT fk_venta FOREIGN KEY (id_venta) REFERENCES ventas_cabecera(id_venta),
    CONSTRAINT fk_tipo_mov FOREIGN KEY (id_tipo_movimiento) REFERENCES tipo_movimiento(id_tipo_movimiento),
    CONSTRAINT fk_almacen_origen FOREIGN KEY (almacen_origen) REFERENCES almacen(id_almacen),
    CONSTRAINT fk_almacen_destino FOREIGN KEY (almacen_destino) REFERENCES almacen(id_almacen)
);

CREATE TABLE movimiento_detalle (
    id_detalle INT PRIMARY KEY AUTO_INCREMENT,
    id_movimiento INT NOT NULL,
    codigo_producto VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    tipo_ajuste ENUM('INGRESO', 'SALIDA') NOT NULL,
    costo_unitario DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_mov FOREIGN KEY (id_movimiento) REFERENCES movimiento_cabecera(id_movimiento),
    CONSTRAINT fk_prod FOREIGN KEY (codigo_producto) REFERENCES productos(codigo_producto)
);


CREATE TABLE kardex (
    id_kardex INT PRIMARY KEY AUTO_INCREMENT,
    id_movimiento INT NOT NULL,
    id_detalle INT NOT NULL,
    codigo_producto VARCHAR(50) NOT NULL,
    fecha_movimiento DATETIME DEFAULT CURRENT_TIMESTAMP,
    id_tipo_movimiento INT NOT NULL,
    cantidad INT NOT NULL,
    costo DECIMAL(10,2) NOT NULL,
    CONSTRAINT fk_kardex_mov FOREIGN KEY (id_movimiento) REFERENCES movimiento_cabecera(id_movimiento),
    CONSTRAINT fk_kardex_det FOREIGN KEY (id_detalle) REFERENCES movimiento_detalle(id_detalle),
    CONSTRAINT fk_kardex_tipo_mov FOREIGN KEY (id_tipo_movimiento) REFERENCES tipo_movimiento(id_tipo_movimiento),
    CONSTRAINT fk_kardex_prod FOREIGN KEY (codigo_producto) REFERENCES productos(codigo_producto)
);

CREATE TABLE tipo_movimiento (
    id_tipo_movimiento INT PRIMARY KEY AUTO_INCREMENT,
    descripcion VARCHAR(100) NOT NULL,
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO'
);

CREATE TABLE ventas_detalle (
    id_detalle INT PRIMARY KEY AUTO_INCREMENT,
    id_venta INT NOT NULL,
    codigo_producto VARCHAR(50) NOT NULL,
    cantidad INT NOT NULL CHECK (cantidad > 0),
    precio_unitario DECIMAL(10,2) NOT NULL,
    descuento DECIMAL(10,2) DEFAULT 0.00,
    CONSTRAINT fk_venta FOREIGN KEY (id_venta) REFERENCES ventas_cabecera(id_venta),
    CONSTRAINT fk_prod FOREIGN KEY (codigo_producto) REFERENCES productos(codigo_producto)
);

CREATE TABLE clientes (
    id_cliente INT PRIMARY KEY AUTO_INCREMENT,
    nombre_cliente VARCHAR(100) NOT NULL,
    documento_identidad VARCHAR(50) UNIQUE NOT NULL,
    telefono VARCHAR(20),
    direccion TEXT,
    correo_electronico VARCHAR(100),
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO'
);

CREATE TABLE productos (
    codigo_producto VARCHAR(50) PRIMARY KEY,
    nombre_producto VARCHAR(100) NOT NULL,
    descripcion TEXT,
    unidad_medida VARCHAR(20) NOT NULL,
    categoria_producto VARCHAR(50),
    estado ENUM('ACTIVO', 'INACTIVO') DEFAULT 'ACTIVO'
);





CREATE TABLE ventas_tipo_comprobante (
    codiTipoComp INT(11) PRIMARY KEY AUTO_INCREMENT,
    descTipoComp VARCHAR(50) NOT NULL,
    actiTipoComp BOOLEAN NOT NULL
);

CREATE TABLE ventas_cliente (
    codiClie INT(11) PRIMARY KEY AUTO_INCREMENT,
    codiTipoDoc char(2) NOT NULL,
    numeDoc VARCHAR(15) NOT NULL,
    fonoClie CHAR(9) NOT NULL,
    mailClie VARCHAR(100) NOT NULL,
    direClie TEXT NOT NULL,
    actiClie BOOLEAN NOT NULL
);

CREATE TABLE ventas_tipo_movimiento (
    codiTipoMovi INT(11) PRIMARY KEY AUTO_INCREMENT,
    nombTipoMovi VARCHAR(100) NOT NULL,
    signTipoMovi BOOLEAN NOT NULL,
    actiTipoMovi BOOLEAN NOT NULL
);