package repository;

import jakarta.enterprise.context.ApplicationScoped;
import models.MovimientosInventario;
import core.generic.GenericRepository;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class MovimientosInventarioRepository extends GenericRepository<MovimientosInventario, Long> {

  public List<MovimientosInventario> findByProductoId(Long idProducto) {
    return find("producto.idProducto", idProducto).list();
  }

  public List<MovimientosInventario> findByDepositoOrigen(Long idDeposito) {
    return find("depositoOrigen.idDeposito", idDeposito).list();
  }

  public List<MovimientosInventario> findByDepositoDestino(Long idDeposito) {
    return find("depositoDestino.idDeposito", idDeposito).list();
  }

  public List<MovimientosInventario> findByTipoMovimiento(String tipo) {
    return find("tipoMovimiento", MovimientosInventario.TipoMovimiento.valueOf(tipo)).list();
  }

  public List<MovimientosInventario> findByFechaRange(Date fechaInicio, Date fechaFin) {
    return find("fechaMovimiento between ?1 and ?2", fechaInicio, fechaFin).list();
  }
}

