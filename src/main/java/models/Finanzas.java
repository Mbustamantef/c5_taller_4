package models;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import java.util.Date;

@Entity

public class Finanzas {
  @Id
  @GeneratedValue
  private long id_finanzas;
  private double ganancias;
  private double perdidas;
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
