package repository;

import core.generic.GenericRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import models.Finanzas;
import java.util.Date;
import java.util.Optional;

@ApplicationScoped
public class FinanzasRepository extends GenericRepository<Finanzas, Long> {

  @Inject
  EntityManager entityManager;

  public Optional<Finanzas> findByMes(Date mes) {
    var results = entityManager.createQuery(
        "SELECT f FROM Finanzas f WHERE f.mes = :mes", Finanzas.class)
        .setParameter("mes", mes)
        .getResultList();
    return results.isEmpty() ? Optional.empty() : Optional.of(results.get(0));
  }
}
