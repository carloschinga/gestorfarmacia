/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.compras.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "vista_compras_hoja_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaComprasHojaTrabajo.findAll", query = "SELECT v FROM VistaComprasHojaTrabajo v"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByCodigo", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByCategoria", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.categoria = :categoria"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByLaboratorio", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.laboratorio = :laboratorio"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByNombre", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByPrecioCaja", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.precioCaja = :precioCaja"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByPvp1", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.pvp1 = :pvp1"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByPvp2", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.pvp2 = :pvp2"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByPrecioCostoUnitario", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.precioCostoUnitario = :precioCostoUnitario"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByPrecioBlister", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.precioBlister = :precioBlister"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByMargenGanancia", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.margenGanancia = :margenGanancia"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByStockTotal", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.stockTotal = :stockTotal"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByVentas", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.ventas = :ventas"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByMes1", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.mes1 = :mes1"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByMes2", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.mes2 = :mes2")})
public class VistaComprasHojaTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Size(max = 15)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 255)
    @Column(name = "categoria")
    private String categoria;
    @Size(max = 255)
    @Column(name = "laboratorio")
    private String laboratorio;
    @Size(max = 255)
    @Column(name = "nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "precio_caja")
    private BigDecimal precioCaja;
    @Column(name = "pvp1")
    private BigDecimal pvp1;
    @Column(name = "pvp2")
    private BigDecimal pvp2;
    @Column(name = "precio_costo_unitario")
    private BigDecimal precioCostoUnitario;
    @Column(name = "precio_blister")
    private BigDecimal precioBlister;
    @Column(name = "margen_ganancia")
    private BigDecimal margenGanancia;
    @Column(name = "stock_total")
    private Integer stockTotal;
    @Column(name = "Ventas")
    private BigInteger ventas;
    @Column(name = "Mes1")
    private BigInteger mes1;
    @Column(name = "Mes2")
    private BigInteger mes2;

    public VistaComprasHojaTrabajo() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public BigDecimal getPrecioCaja() {
        return precioCaja;
    }

    public void setPrecioCaja(BigDecimal precioCaja) {
        this.precioCaja = precioCaja;
    }

    public BigDecimal getPvp1() {
        return pvp1;
    }

    public void setPvp1(BigDecimal pvp1) {
        this.pvp1 = pvp1;
    }

    public BigDecimal getPvp2() {
        return pvp2;
    }

    public void setPvp2(BigDecimal pvp2) {
        this.pvp2 = pvp2;
    }

    public BigDecimal getPrecioCostoUnitario() {
        return precioCostoUnitario;
    }

    public void setPrecioCostoUnitario(BigDecimal precioCostoUnitario) {
        this.precioCostoUnitario = precioCostoUnitario;
    }

    public BigDecimal getPrecioBlister() {
        return precioBlister;
    }

    public void setPrecioBlister(BigDecimal precioBlister) {
        this.precioBlister = precioBlister;
    }

    public BigDecimal getMargenGanancia() {
        return margenGanancia;
    }

    public void setMargenGanancia(BigDecimal margenGanancia) {
        this.margenGanancia = margenGanancia;
    }

    public Integer getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
    }

    public BigInteger getVentas() {
        return ventas;
    }

    public void setVentas(BigInteger ventas) {
        this.ventas = ventas;
    }

    public BigInteger getMes1() {
        return mes1;
    }

    public void setMes1(BigInteger mes1) {
        this.mes1 = mes1;
    }

    public BigInteger getMes2() {
        return mes2;
    }

    public void setMes2(BigInteger mes2) {
        this.mes2 = mes2;
    }
    
}
