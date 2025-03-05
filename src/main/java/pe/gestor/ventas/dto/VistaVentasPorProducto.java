/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.ventas.dto;

import java.io.Serializable;
import java.math.BigInteger;
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
@Table(name = "vista_ventas_por_producto")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaVentasPorProducto.findAll", query = "SELECT v FROM VistaVentasPorProducto v"),
    @NamedQuery(name = "VistaVentasPorProducto.findByProducto", query = "SELECT v FROM VistaVentasPorProducto v WHERE v.producto = :producto"),
    @NamedQuery(name = "VistaVentasPorProducto.findByTotalVendido", query = "SELECT v FROM VistaVentasPorProducto v WHERE v.totalVendido = :totalVendido")})
public class VistaVentasPorProducto implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Size(max = 255)
    @Column(name = "producto")
    private String producto;
    @Column(name = "total_vendido")
    private BigInteger totalVendido;

    public VistaVentasPorProducto() {
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
    
}
