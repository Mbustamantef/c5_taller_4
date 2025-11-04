package repository;

import core.generic.GenericRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import models.Ventas;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class VentasRepository extends GenericRepository<Ventas, Long> {

  @Inject
  EntityManager entityManager;

  public List<Ventas> findByMesAndAnio(int mes, int anio) {
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
        "SELECT v FROM Ventas v " +
        "LEFT JOIN FETCH v.productos p " +
        "WHERE v.mes BETWEEN :startDate AND :endDate", Ventas.class)
        .setParameter("startDate", startDate)
        .setParameter("endDate", endDate)
        .getResultList();
  }
}
