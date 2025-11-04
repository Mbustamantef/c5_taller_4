package repository;

import core.generic.GenericRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import models.Productos;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class ProductosRepository extends GenericRepository<Productos, Long> {

  @Inject
  EntityManager entityManager;

  @Override
  public List<Productos> listAll() {
    return entityManager.createQuery(
        "SELECT DISTINCT p FROM Productos p " +
        "LEFT JOIN FETCH p.depositos " +
        "ORDER BY p.idProducto", Productos.class)
        .getResultList();
  }

  @Override
  public Optional<Productos> findByIdOptional(Long id) {
    List<Productos> results = entityManager.createQuery(
        "SELECT p FROM Productos p " +
        "LEFT JOIN FETCH p.depositos " +
        "WHERE p.idProducto = :id", Productos.class)
        .setParameter("id", id)
        .getResultList();
    return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
  }

  public List<Productos> findByMesCompra(int mes, int anio) {
    Calendar cal = Calendar.getInstance();
    cal.set(anio, mes - 1, 1, 0, 0, 0);
    cal.set(Calendar.MILLISECOND, 0);
    Date startDate = cal.getTime();

    cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
    cal.set(Calendar.HOUR_OF_DAY, 23);
    cal.set(Calendar.MINUTE, 59);
    cal.set(Calendar.SECOND, 59);
    Date endDate = cal.getTime();

    return entityManager.createQuery(
        "SELECT p FROM Productos p " +
        "WHERE p.mesCompra BETWEEN :startDate AND :endDate", Productos.class)
        .setParameter("startDate", startDate)
        .setParameter("endDate", endDate)
        .getResultList();
  }
}
