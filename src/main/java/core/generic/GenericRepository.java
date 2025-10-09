package core.generic;

import io.quarkus.hibernate.orm.panache.PanacheRepositoryBase;

public abstract class GenericRepository <I , ID > implements PanacheRepositoryBase<I , ID> {

}
