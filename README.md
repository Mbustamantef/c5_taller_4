# 🏢 Sistema de Gestión de Inventario (Java + Quarkus)

## 📋 Descripción General
Este proyecto es un **sistema de gestión de inventario empresarial**, diseñado para controlar productos, proveedores, clientes y depósitos internos.  
El objetivo es brindar una visión clara de las **entradas, salidas, transferencias, márgenes y stock valorizado**, permitiendo conocer en todo momento la situación de la empresa.

El sistema está pensado para ser escalable, multi-depósito y multi-proveedor, con trazabilidad completa de los movimientos de stock.

---

## 🧩 Objetivos Principales
- Controlar productos, proveedores, clientes y depósitos.
- Gestionar movimientos de inventario (entradas, salidas, transferencias, ajustes).
- Mantener stock actualizado y valorizado.
- Ofrecer una estructura base para integrar módulos de compras, ventas o facturación.

---

## ⚙️ Alcance Funcional

### 🏷️ Productos
- Registro con id, nombre, categoría y estado (activo/inactivo).
- Asociación a múltiples proveedores y múltiples depósitos.
- Control de stock mínimo por depósito.

### 🏬 Depósitos
- Definición de múltiples depósitos o sucursales.
- Asignación de un **responsable** por depósito (relación 1:N).
- Transferencias entre depósitos con trazabilidad (salida + entrada vinculadas).

### 🚚 Proveedores
- Registro de proveedores con datos básicos (nombre, contacto, moneda,).
- Asociación a múltiples productos (relación M:N).
- Información de precios de compra, plazo de entrega y prioridad.

### 👥 Clientes
- Registro de clientes con datos de contacto y tipo (minorista, mayorista, etc.).
- Relación **1:N con Ventas**, donde cada cliente puede tener múltiples operaciones.
- Historial de compras, montos totales y frecuencia.

### 💳 Ventas
- Registro de ventas asociadas a un cliente.
- Detalle de productos vendidos, cantidades, precios y descuentos.
- Impacto inmediato en el stock (salida de depósito).

### 📦 Movimientos de Inventario
- Tipos: **Entrada, Salida, Transferencia, Ajuste**.
- Motivos definidos (compra, venta, pérdida, ajuste manual, etc.).
- Auditoría completa (usuario, fecha, cantidad, depósito origen/destino).

### 🔐 Roles del Sistema
| Rol | Funciones principales |
|------|------------------------|
| **Administrador** | Configura usuarios, depósitos, categorías y políticas del sistema. |
| **Depósito** | Registra movimientos, entradas, salidas y transferencias. |
| **Aprobador** | Autoriza ajustes negativos o movimientos especiales. |
| **Auditor/Finanzas** | Accede a reportes , sin modificar stock. |

---

## 🧮 Relaciones del Modelo de Datos

| Entidades | Tipo de relación | Descripción |
|------------|------------------|--------------|
| **Responsable → Depósito** | 1 : N | Un responsable puede administrar varios depósitos. |
| **Producto ↔ Depósito** | M : N | Un producto puede estar en varios depósitos (tabla intermedia `Stock`). |
| **Producto ↔ Proveedor** | M : N | Un producto puede tener varios proveedores asociados (`ProductoProveedor`). |
| **Cliente → Venta** | 1 : N | Un cliente puede realizar múltiples ventas. |
| **Venta → DetalleVenta** | 1 : N | Cada venta contiene varios productos vendidos. |

---

## 📊 Reportes Clave
- Stock valorizado por depósito, categoría o proveedor.
- Productos con stock por debajo del mínimo.
- Productos sin movimiento (últimos X días).
- Rotación de inventario.
- Ventas por cliente, categoría o periodo.
- Margen bruto por producto o por venta.
---

