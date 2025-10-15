package core.generic;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;
import java.util.List;
import java.util.Optional;
import java.util.Date;

public abstract class GenericRepository<I, ID> implements PanacheRepositoryBase<I, ID> {
    // List all entities
    public List<I> listAll() {
        return findAll().list();
    }

    // Find by ID (Optional)
    public Optional<I> findByIdOptional(ID id) {
        return findByIdOptional(id);
    }

    // Save or update entity
    public void saveOrUpdate(I entity) {
        persist(entity);
    }

    // Delete by ID
    public boolean deleteByIdValue(ID id) {
        return deleteById(id);
    }

    // List all paged
    public List<I> listAllPaged(int page, int size) {
        return findAll().page(page, size).list();
    }

    // Count all
    public long countAll() {
        return count();
    }

    // Find by field (generic)
    public List<I> findByField(String field, Object value) {
        return find(field, value).list();
    }

    // Find by date range (for entities with date fields)
    public List<I> findByDateRange(String field, Date from, Date to) {
        return find(field + " between ?1 and ?2", from, to).list();
    }

    // Find by relation (for entities with relations)
    public List<I> findByRelation(String relationField, Object relatedId) {
        return find(relationField + ".id = ?1", relatedId).list();
    }

    // Find by boolean field (e.g., activo)
    public List<I> findByBooleanField(String field, boolean value) {
        return find(field, value).list();
    }

    // Find by string field (e.g., nombre, titulo, categoria)
    public List<I> findByStringField(String field, String value) {
        return find(field, value).list();
    }

    // Find by numeric comparison (e.g., monto > x)
    public List<I> findByNumericGreaterThan(String field, Number value) {
        return find(field + " > ?1", value).list();
    }
}
