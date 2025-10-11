package models;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.Date;

@Entity
@Table(name = "finanzas", schema = "public")

public class Finanzas {
  @Id
  @GeneratedValue
  @Column(name = "id_finanzas", nullable = false)
  private long id_finanzas;
  @Column(name = "ganancias", nullable = false)
  private double ganancias;
  @Column(name = "perdidas", nullable = false)
  private double perdidas;
  @Column(name = "mes", nullable = false)
  private Date mes;

  public long getId_finanzas() {
    return id_finanzas;
  }

  public void setId_finanzas(long id_finanzas) {
    this.id_finanzas = id_finanzas;
  }

  public double getGanancias() {
    return ganancias;
  }

  public void setGanancias(double ganancias) {
    this.ganancias = ganancias;
  }

  public double getPerdidas() {
    return perdidas;
  }

  public void setPerdidas(double perdidas) {
    this.perdidas = perdidas;
  }

  public Date getMes() {
    return mes;
  }

  public void setMes(Date mes) {
    this.mes = mes;
  }
}
