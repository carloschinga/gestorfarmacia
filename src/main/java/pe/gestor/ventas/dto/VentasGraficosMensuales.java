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
@Table(name = "ventas_graficos_mensuales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasGraficosMensuales.findAll", query = "SELECT v FROM VentasGraficosMensuales v"),
    @NamedQuery(name = "VentasGraficosMensuales.findByCodiSede", query = "SELECT v FROM VentasGraficosMensuales v WHERE v.codiSede = :codiSede"),
    @NamedQuery(name = "VentasGraficosMensuales.findByCodigo", query = "SELECT v FROM VentasGraficosMensuales v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "VentasGraficosMensuales.findByProducto", query = "SELECT v FROM VentasGraficosMensuales v WHERE v.producto = :producto"),
    @NamedQuery(name = "VentasGraficosMensuales.findByTotalVendido", query = "SELECT v FROM VentasGraficosMensuales v WHERE v.totalVendido = :totalVendido"),
    @NamedQuery(name = "VentasGraficosMensuales.findByMes", query = "SELECT v FROM VentasGraficosMensuales v WHERE v.mes = :mes")
})
public class VentasGraficosMensuales implements Serializable {

    private static final long serialVersionUID = 1L;

    @Basic(optional = false)
    @NotNull
    @Column(name = "codiSede")
    @Id
    private int codiSede;

    @Size(max = 15)
    @Column(name = "codigo")
    private String codigo;

    @Size(max = 255)
    @Column(name = "producto")
    private String producto;

    @Column(name = "total_vendido")
    private BigInteger totalVendido;

    @Size(max = 7)
    @Column(name = "mes")
    private String mes;

    public VentasGraficosMensuales() {
    }

    public int getCodiSede() {
        return codiSede;
    }

    public void setCodiSede(int codiSede) {
        this.codiSede = codiSede;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getProducto() {
        return producto;
    }

    public void setProducto(String producto) {
        this.producto = producto;
    }

    public BigInteger getTotalVendido() {
        return totalVendido;
    }

    public void setTotalVendido(BigInteger totalVendido) {
        this.totalVendido = totalVendido;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }
}
