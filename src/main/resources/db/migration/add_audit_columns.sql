-- Script para agregar campos de auditoría a todas las tablas
-- Ejecutar este script en la base de datos antes de usar el sistema de auditoría

-- Tabla productos
ALTER TABLE productos
ADD COLUMN IF NOT EXISTS creado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_creacion TIMESTAMP,
ADD COLUMN IF NOT EXISTS modificado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_modificacion TIMESTAMP,
ADD CONSTRAINT fk_productos_creado_por FOREIGN KEY (creado_por) REFERENCES usuarios(id_usuario),
ADD CONSTRAINT fk_productos_modificado_por FOREIGN KEY (modificado_por) REFERENCES usuarios(id_usuario);

-- Tabla clientes
ALTER TABLE clientes
ADD COLUMN IF NOT EXISTS creado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_creacion TIMESTAMP,
ADD COLUMN IF NOT EXISTS modificado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_modificacion TIMESTAMP,
ADD CONSTRAINT fk_clientes_creado_por FOREIGN KEY (creado_por) REFERENCES usuarios(id_usuario),
ADD CONSTRAINT fk_clientes_modificado_por FOREIGN KEY (modificado_por) REFERENCES usuarios(id_usuario);

-- Tabla depositos
ALTER TABLE depositos
ADD COLUMN IF NOT EXISTS creado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_creacion TIMESTAMP,
ADD COLUMN IF NOT EXISTS modificado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_modificacion TIMESTAMP,
ADD CONSTRAINT fk_depositos_creado_por FOREIGN KEY (creado_por) REFERENCES usuarios(id_usuario),
ADD CONSTRAINT fk_depositos_modificado_por FOREIGN KEY (modificado_por) REFERENCES usuarios(id_usuario);

-- Tabla finanzas
ALTER TABLE finanzas
ADD COLUMN IF NOT EXISTS creado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_creacion TIMESTAMP,
ADD COLUMN IF NOT EXISTS modificado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_modificacion TIMESTAMP,
ADD CONSTRAINT fk_finanzas_creado_por FOREIGN KEY (creado_por) REFERENCES usuarios(id_usuario),
ADD CONSTRAINT fk_finanzas_modificado_por FOREIGN KEY (modificado_por) REFERENCES usuarios(id_usuario);

-- Tabla stock
ALTER TABLE stock
ADD COLUMN IF NOT EXISTS creado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_creacion TIMESTAMP,
ADD COLUMN IF NOT EXISTS modificado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_modificacion TIMESTAMP,
ADD CONSTRAINT fk_stock_creado_por FOREIGN KEY (creado_por) REFERENCES usuarios(id_usuario),
ADD CONSTRAINT fk_stock_modificado_por FOREIGN KEY (modificado_por) REFERENCES usuarios(id_usuario);

-- Tabla ventas
ALTER TABLE ventas
ADD COLUMN IF NOT EXISTS creado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_creacion TIMESTAMP,
ADD COLUMN IF NOT EXISTS modificado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_modificacion TIMESTAMP,
ADD CONSTRAINT fk_ventas_creado_por FOREIGN KEY (creado_por) REFERENCES usuarios(id_usuario),
ADD CONSTRAINT fk_ventas_modificado_por FOREIGN KEY (modificado_por) REFERENCES usuarios(id_usuario);

-- Tabla responsables
ALTER TABLE responsables
ADD COLUMN IF NOT EXISTS creado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_creacion TIMESTAMP,
ADD COLUMN IF NOT EXISTS modificado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_modificacion TIMESTAMP,
ADD CONSTRAINT fk_responsables_creado_por FOREIGN KEY (creado_por) REFERENCES usuarios(id_usuario),
ADD CONSTRAINT fk_responsables_modificado_por FOREIGN KEY (modificado_por) REFERENCES usuarios(id_usuario);

-- Tabla movimientos_inventario
ALTER TABLE movimientos_inventario
ADD COLUMN IF NOT EXISTS creado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_creacion TIMESTAMP,
ADD COLUMN IF NOT EXISTS modificado_por BIGINT,
ADD COLUMN IF NOT EXISTS fecha_modificacion TIMESTAMP,
ADD CONSTRAINT fk_movimientos_creado_por FOREIGN KEY (creado_por) REFERENCES usuarios(id_usuario),
ADD CONSTRAINT fk_movimientos_modificado_por FOREIGN KEY (modificado_por) REFERENCES usuarios(id_usuario);

-- Crear un índice para mejorar el rendimiento de las consultas de auditoría
CREATE INDEX IF NOT EXISTS idx_productos_creado_por ON productos(creado_por);
CREATE INDEX IF NOT EXISTS idx_productos_modificado_por ON productos(modificado_por);
CREATE INDEX IF NOT EXISTS idx_clientes_creado_por ON clientes(creado_por);
CREATE INDEX IF NOT EXISTS idx_clientes_modificado_por ON clientes(modificado_por);
CREATE INDEX IF NOT EXISTS idx_depositos_creado_por ON depositos(creado_por);
CREATE INDEX IF NOT EXISTS idx_depositos_modificado_por ON depositos(modificado_por);
CREATE INDEX IF NOT EXISTS idx_finanzas_creado_por ON finanzas(creado_por);
CREATE INDEX IF NOT EXISTS idx_finanzas_modificado_por ON finanzas(modificado_por);
CREATE INDEX IF NOT EXISTS idx_stock_creado_por ON stock(creado_por);
CREATE INDEX IF NOT EXISTS idx_stock_modificado_por ON stock(modificado_por);
CREATE INDEX IF NOT EXISTS idx_ventas_creado_por ON ventas(creado_por);
CREATE INDEX IF NOT EXISTS idx_ventas_modificado_por ON ventas(modificado_por);
CREATE INDEX IF NOT EXISTS idx_responsables_creado_por ON responsables(creado_por);
CREATE INDEX IF NOT EXISTS idx_responsables_modificado_por ON responsables(modificado_por);
CREATE INDEX IF NOT EXISTS idx_movimientos_creado_por ON movimientos_inventario(creado_por);
CREATE INDEX IF NOT EXISTS idx_movimientos_modificado_por ON movimientos_inventario(modificado_por);

