package models;

import core.generic.Auditable;
import jakarta.json.bind.annotation.JsonbDateFormat;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import java.util.Date;

@Entity
@Table(name = "finanzas", schema = "public")
public class Finanzas extends Auditable {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id_finanzas", nullable = false)
  private Long idFinanzas;

  @NotNull(message = "Las ganancias son obligatorias")
  @DecimalMin(value = "0.0", message = "Las ganancias no pueden ser negativas")
  @Column(name = "ganancias", nullable = false)
  private Double ganancias;

  @NotNull(message = "Las pérdidas son obligatorias")
  @DecimalMin(value = "0.0", message = "Las pérdidas no pueden ser negativas")
  @Column(name = "perdidas", nullable = false)
  private Double perdidas;

  @NotNull(message = "La fecha es obligatoria")
  @Temporal(TemporalType.DATE)
  @Column(name = "mes", nullable = false)
  @JsonbDateFormat("yyyy-MM-dd")
  private Date mes;

  // Getters and Setters
  public Long getIdFinanzas() {
    return idFinanzas;
  }

  public void setIdFinanzas(Long idFinanzas) {
    this.idFinanzas = idFinanzas;
  }

  public Double getGanancias() {
    return ganancias;
  }

  public void setGanancias(Double ganancias) {
    this.ganancias = ganancias;
  }

  public Double getPerdidas() {
    return perdidas;
  }

  public void setPerdidas(Double perdidas) {
    this.perdidas = perdidas;
  }

  public Date getMes() {
    return mes;
  }

  public void setMes(Date mes) {
    this.mes = mes;
  }
}
