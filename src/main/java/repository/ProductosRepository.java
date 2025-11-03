package repository;

import core.generic.GenericRepository;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import models.Productos;
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
}
