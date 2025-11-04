package service;

import dto.ClienteDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import models.Clientes;
import models.Ventas;
import repository.ClientesRepository;
import repository.VentasRepository;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@ApplicationScoped
public class ClientesService {

  @Inject
  ClientesRepository clientesRepository;

  @Inject
  VentasRepository ventasRepository;

  public List<ClienteDTO> findAll() {
    return clientesRepository.listAll().stream()
        .map(this::toDTO)
        .collect(Collectors.toList());
  }

  public Optional<ClienteDTO> findById(Long id) {
    return clientesRepository.findByIdOptional(id)
        .map(this::toDTO);
  }

  public ClienteDTO create(ClienteDTO dto) {
    Clientes cliente = toEntity(dto);
    clientesRepository.persist(cliente);
    return toDTO(cliente);
  }

  public Optional<ClienteDTO> update(Long id, ClienteDTO dto) {
    Optional<Clientes> existing = clientesRepository.findByIdOptional(id);
    if (existing.isEmpty()) {
      return Optional.empty();
    }

    Clientes cliente = existing.get();
    cliente.setNombreCliente(dto.getNombre_cliente());
    cliente.setApellidoCliente(dto.getApellido_cliente());
    cliente.setCiCliente(dto.getCi_cliente());
    cliente.setCorreoCliente(dto.getCorreo_cliente());


    return Optional.of(toDTO(cliente));
  }

  public boolean delete(Long id) {
    return clientesRepository.deleteById(id);
  }

  private ClienteDTO toDTO(Clientes cliente) {
    ClienteDTO dto = new ClienteDTO();
    dto.setId_cliente(cliente.getIdCliente());
    dto.setNombre_cliente(cliente.getNombreCliente());
    dto.setApellido_cliente(cliente.getApellidoCliente());
    dto.setCi_cliente(cliente.getCiCliente());
    dto.setCorreo_cliente(cliente.getCorreoCliente());
    return dto;
  }

  private Clientes toEntity(ClienteDTO dto) {
    Clientes cliente = new Clientes();
    cliente.setNombreCliente(dto.getNombre_cliente());
    cliente.setApellidoCliente(dto.getApellido_cliente());
    cliente.setCiCliente(dto.getCi_cliente());
    cliente.setCorreoCliente(dto.getCorreo_cliente());
    return cliente;
  }
}

