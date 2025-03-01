/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.ventas.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "ventas_productos")
@NamedQueries({
    @NamedQuery(name = "VentasProductos.findAll", query = "SELECT v FROM VentasProductos v"),
    @NamedQuery(name = "VentasProductos.findByCodigo", query = "SELECT v FROM VentasProductos v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "VentasProductos.findByCategoria", query = "SELECT v FROM VentasProductos v WHERE v.categoria = :categoria"),
    @NamedQuery(name = "VentasProductos.findByLaboratorio", query = "SELECT v FROM VentasProductos v WHERE v.laboratorio = :laboratorio"),
    @NamedQuery(name = "VentasProductos.findByNombre", query = "SELECT v FROM VentasProductos v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VentasProductos.findByPrecioCaja", query = "SELECT v FROM VentasProductos v WHERE v.precioCaja = :precioCaja"),
    @NamedQuery(name = "VentasProductos.findByPvp1", query = "SELECT v FROM VentasProductos v WHERE v.pvp1 = :pvp1"),
    @NamedQuery(name = "VentasProductos.findByPvp2", query = "SELECT v FROM VentasProductos v WHERE v.pvp2 = :pvp2"),
    @NamedQuery(name = "VentasProductos.findByPrecioCostoUnitario", query = "SELECT v FROM VentasProductos v WHERE v.precioCostoUnitario = :precioCostoUnitario"),
    @NamedQuery(name = "VentasProductos.findByPrecioBlister", query = "SELECT v FROM VentasProductos v WHERE v.precioBlister = :precioBlister"),
    @NamedQuery(name = "VentasProductos.findByStock", query = "SELECT v FROM VentasProductos v WHERE v.stock = :stock"),
    @NamedQuery(name = "VentasProductos.findByStockMin", query = "SELECT v FROM VentasProductos v WHERE v.stockMin = :stockMin"),
    @NamedQuery(name = "VentasProductos.findByLote", query = "SELECT v FROM VentasProductos v WHERE v.lote = :lote"),
    @NamedQuery(name = "VentasProductos.findByFechaVencimiento", query = "SELECT v FROM VentasProductos v WHERE v.fechaVencimiento = :fechaVencimiento")})
public class VentasProductos implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 100)
    @Column(name = "categoria")
    private String categoria;
    @Size(max = 100)
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
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "stock_min")
    private Integer stockMin;
    @Size(max = 50)
    @Column(name = "lote")
    private String lote;
    @Column(name = "fecha_vencimiento")
    @Temporal(TemporalType.DATE)
    private Date fechaVencimiento;
    @Lob
    @Size(max = 65535)
    @Column(name = "principales_activos")
    private String principalesActivos;

    public VentasProductos() {
    }

    public VentasProductos(String codigo) {
        this.codigo = codigo;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStockMin() {
        return stockMin;
    }

    public void setStockMin(Integer stockMin) {
        this.stockMin = stockMin;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechaVencimiento() {
        return fechaVencimiento;
    }

    public void setFechaVencimiento(Date fechaVencimiento) {
        this.fechaVencimiento = fechaVencimiento;
    }

    public String getPrincipalesActivos() {
        return principalesActivos;
    }

    public void setPrincipalesActivos(String principalesActivos) {
        this.principalesActivos = principalesActivos;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codigo != null ? codigo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasProductos)) {
            return false;
        }
        VentasProductos other = (VentasProductos) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasProductos[ codigo=" + codigo + " ]";
    }
    
}
