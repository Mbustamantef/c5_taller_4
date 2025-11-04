package dto;

public class AuditRequest<T> {

  private Long idUsuario;
  private T data;

  public AuditRequest() {
  }

  public AuditRequest(Long idUsuario, T data) {
    this.idUsuario = idUsuario;
    this.data = data;
  }

  public Long getIdUsuario() {
    return idUsuario;
  }

  public void setIdUsuario(Long idUsuario) {
    this.idUsuario = idUsuario;
  }

  public T getData() {
    return data;
  }

  public void setData(T data) {
    this.data = data;
  }
}

