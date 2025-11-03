package repository;

import jakarta.enterprise.context.ApplicationScoped;
import models.Stock;
import core.generic.GenericRepository;
import jakarta.persistence.EntityManager;
import jakarta.inject.Inject;
import java.util.List;

@ApplicationScoped
public class StockRepository extends GenericRepository<Stock, Long> {

  @Inject
  EntityManager entityManager;

  public List<Stock> findByProductoId(Long idProducto) {
    return entityManager.createQuery(
        "SELECT s FROM Stock s " +
        "LEFT JOIN FETCH s.producto p " +
        "LEFT JOIN FETCH s.deposito d " +
        "WHERE p.idProducto = :idProducto", Stock.class)
        .setParameter("idProducto", idProducto)
        .getResultList();
  }

  public List<Stock> findByDepositoId(Long idDeposito) {
    return entityManager.createQuery(
        "SELECT s FROM Stock s " +
        "LEFT JOIN FETCH s.producto p " +
        "LEFT JOIN FETCH s.deposito d " +
        "WHERE d.idDeposito = :idDeposito", Stock.class)
        .setParameter("idDeposito", idDeposito)
        .getResultList();
  }

  public Stock findByProductoAndDeposito(Long idProducto, Long idDeposito) {
    List<Stock> results = entityManager.createQuery(
        "SELECT s FROM Stock s " +
        "LEFT JOIN FETCH s.producto p " +
        "LEFT JOIN FETCH s.deposito d " +
        "WHERE p.idProducto = :idProducto AND d.idDeposito = :idDeposito", Stock.class)
        .setParameter("idProducto", idProducto)
        .setParameter("idDeposito", idDeposito)
        .getResultList();
    return results.isEmpty() ? null : results.get(0);
  }

  public List<Stock> findStockBajoMinimo() {
    return entityManager.createQuery(
        "SELECT s FROM Stock s " +
        "LEFT JOIN FETCH s.producto p " +
        "LEFT JOIN FETCH s.deposito d " +
        "WHERE s.cantidad < s.stockMinimo AND s.stockMinimo IS NOT NULL", Stock.class)
        .getResultList();
  }

  @Override
  public List<Stock> listAll() {
    return entityManager.createQuery(
        "SELECT s FROM Stock s " +
        "LEFT JOIN FETCH s.producto p " +
        "LEFT JOIN FETCH s.deposito d", Stock.class)
        .getResultList();
  }

  /**
   * Obtiene el stock calculado desde los movimientos de inventario
   * Agrupa por producto y depósito, sumando entradas y restando salidas
   */
  public List<Object[]> getStockFromMovimientos() {
    return entityManager.createQuery(
        "SELECT m.producto.idProducto, m.producto.titulo, " +
        "CASE WHEN m.depositoDestino IS NOT NULL THEN m.depositoDestino.idDeposito " +
        "     WHEN m.depositoOrigen IS NOT NULL THEN m.depositoOrigen.idDeposito END as idDeposito, " +
        "CASE WHEN m.depositoDestino IS NOT NULL THEN m.depositoDestino.nombreDeposito " +
        "     WHEN m.depositoOrigen IS NOT NULL THEN m.depositoOrigen.nombreDeposito END as nombreDeposito, " +
        "SUM(CASE WHEN m.tipoMovimiento = 'ENTRADA' OR m.tipoMovimiento = 'AJUSTE' THEN m.cantidad " +
        "         WHEN m.tipoMovimiento = 'SALIDA' THEN -m.cantidad " +
        "         WHEN m.tipoMovimiento = 'TRANSFERENCIA' AND m.depositoDestino.idDeposito = " +
        "              (CASE WHEN m.depositoDestino IS NOT NULL THEN m.depositoDestino.idDeposito " +
        "                    WHEN m.depositoOrigen IS NOT NULL THEN m.depositoOrigen.idDeposito END) THEN m.cantidad " +
        "         WHEN m.tipoMovimiento = 'TRANSFERENCIA' AND m.depositoOrigen.idDeposito = " +
        "              (CASE WHEN m.depositoDestino IS NOT NULL THEN m.depositoDestino.idDeposito " +
        "                    WHEN m.depositoOrigen IS NOT NULL THEN m.depositoOrigen.idDeposito END) THEN -m.cantidad " +
        "         ELSE 0 END) as stockCalculado " +
        "FROM MovimientosInventario m " +
        "GROUP BY m.producto.idProducto, m.producto.titulo, idDeposito, nombreDeposito " +
        "HAVING SUM(CASE WHEN m.tipoMovimiento = 'ENTRADA' OR m.tipoMovimiento = 'AJUSTE' THEN m.cantidad " +
        "                WHEN m.tipoMovimiento = 'SALIDA' THEN -m.cantidad ELSE 0 END) > 0",
        Object[].class)
        .getResultList();
  }

  /**
   * Versión simplificada: calcula stock real por producto y depósito desde movimientos
   */
  public List<Object[]> getStockRealPorProductoYDeposito() {
    return entityManager.createNativeQuery(
        "SELECT " +
        "  p.id_producto, " +
        "  p.titulo as nombre_producto, " +
        "  p.precio_costo, " +
        "  p.precio_venta, " +
        "  d.id_deposito, " +
        "  d.nombre_deposito, " +
        "  COALESCE(entradas.cantidad, 0) - COALESCE(salidas.cantidad, 0) as stock_actual " +
        "FROM productos p " +
        "CROSS JOIN depositos d " +
        "LEFT JOIN ( " +
        "  SELECT id_producto, id_deposito_destino as id_deposito, SUM(cantidad) as cantidad " +
        "  FROM movimientos_inventario " +
        "  WHERE tipo_movimiento IN ('ENTRADA', 'AJUSTE') AND id_deposito_destino IS NOT NULL " +
        "  GROUP BY id_producto, id_deposito_destino " +
        ") entradas ON p.id_producto = entradas.id_producto AND d.id_deposito = entradas.id_deposito " +
        "LEFT JOIN ( " +
        "  SELECT id_producto, id_deposito_origen as id_deposito, SUM(cantidad) as cantidad " +
        "  FROM movimientos_inventario " +
        "  WHERE tipo_movimiento IN ('SALIDA', 'TRANSFERENCIA') AND id_deposito_origen IS NOT NULL " +
        "  GROUP BY id_producto, id_deposito_origen " +
        ") salidas ON p.id_producto = salidas.id_producto AND d.id_deposito = salidas.id_deposito " +
        "WHERE COALESCE(entradas.cantidad, 0) - COALESCE(salidas.cantidad, 0) > 0 " +
        "ORDER BY p.titulo, d.nombre_deposito")
        .getResultList();
  }
}

