/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.ventas.dto;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author san21
 */
@Entity
@Table(name = "ventas_graficos_x_sede")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasGraficosXSede.findAll", query = "SELECT v FROM VentasGraficosXSede v"),
    @NamedQuery(name = "VentasGraficosXSede.findBySede", query = "SELECT v FROM VentasGraficosXSede v WHERE v.sede = :sede"),
    @NamedQuery(name = "VentasGraficosXSede.findByNombreSede", query = "SELECT v FROM VentasGraficosXSede v WHERE v.nombreSede = :nombreSede"),
    @NamedQuery(name = "VentasGraficosXSede.findByTotalVendido", query = "SELECT v FROM VentasGraficosXSede v WHERE v.totalVendido = :totalVendido")})
public class VentasGraficosXSede implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "sede")
    private int sede;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombre_sede")
    private String nombreSede;
    @Column(name = "total_vendido")
    private BigInteger totalVendido;

    public VentasGraficosXSede() {
    }

    public int getSede() {
        return sede;
    }

    public void setSede(int sede) {
        this.sede = sede;
    }

    public String getNombreSede() {
        return nombreSede;
    }

    public void setNombreSede(String nombreSede) {
        this.nombreSede = nombreSede;
    }

    public BigInteger getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(BigInteger totalVendido) {
        this.totalVendido = totalVendido;
    }
    
}
