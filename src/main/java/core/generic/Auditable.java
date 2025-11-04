package core.generic;

import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.json.bind.annotation.JsonbTransient;
import jakarta.persistence.*;
import models.Usuarios;
import java.util.Date;

@MappedSuperclass
public abstract class Auditable {

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "creado_por")
  @JsonbTransient
  private Usuarios creadoPor;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fecha_creacion")
  @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
  private Date fechaCreacion;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "modificado_por")
  @JsonbTransient
  private Usuarios modificadoPor;

  @Temporal(TemporalType.TIMESTAMP)
  @Column(name = "fecha_modificacion")
  @JsonbDateFormat("yyyy-MM-dd'T'HH:mm:ss")
  private Date fechaModificacion;

  // Getters y Setters
  public Usuarios getCreadoPor() {
    return creadoPor;
  }

  public void setCreadoPor(Usuarios creadoPor) {
    this.creadoPor = creadoPor;
  }

  public Date getFechaCreacion() {
    return fechaCreacion;
  }

  public void setFechaCreacion(Date fechaCreacion) {
    this.fechaCreacion = fechaCreacion;
  }

  public Usuarios getModificadoPor() {
    return modificadoPor;
  }

  public void setModificadoPor(Usuarios modificadoPor) {
    this.modificadoPor = modificadoPor;
  }

  public Date getFechaModificacion() {
    return fechaModificacion;
  }

  public void setFechaModificacion(Date fechaModificacion) {
    this.fechaModificacion = fechaModificacion;
  }

  @PrePersist
  public void prePersist() {
    fechaCreacion = new Date();
  }

  @PreUpdate
  public void preUpdate() {
    fechaModificacion = new Date();
  }
}

