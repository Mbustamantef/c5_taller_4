package repository;

import core.generic.GenericRepository;
import jakarta.enterprise.context.ApplicationScoped;
import models.Usuarios;
@ApplicationScoped
public class UsuariosRepository extends GenericRepository <Usuarios, Long> {

}

