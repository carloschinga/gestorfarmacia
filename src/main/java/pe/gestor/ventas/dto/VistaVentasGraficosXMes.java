/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.ventas.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "vista_ventas_graficos_x_mes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaVentasGraficosXMes.findAll", query = "SELECT v FROM VistaVentasGraficosXMes v"),
    @NamedQuery(name = "VistaVentasGraficosXMes.findByMes", query = "SELECT v FROM VistaVentasGraficosXMes v WHERE v.mes = :mes"),
    @NamedQuery(name = "VistaVentasGraficosXMes.findByMonto", query = "SELECT v FROM VistaVentasGraficosXMes v WHERE v.monto = :monto")})
public class VistaVentasGraficosXMes implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Size(max = 35)
    @Column(name = "mes")
    private String mes;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "monto")
    private BigDecimal monto;

    public VistaVentasGraficosXMes() {
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }
    
}
