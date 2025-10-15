package repository;

import core.generic.GenericRepository;
import jakarta.enterprise.context.ApplicationScoped;
import models.Clientes;
@ApplicationScoped
public class ClientesRepository extends GenericRepository<Clientes, Long> {

}
