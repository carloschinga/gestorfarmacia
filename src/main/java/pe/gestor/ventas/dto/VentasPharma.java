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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "ventas_pharma")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasPharma.findAll", query = "SELECT v FROM VentasPharma v"),
    @NamedQuery(name = "VentasPharma.findById", query = "SELECT v FROM VentasPharma v WHERE v.id = :id"),
    @NamedQuery(name = "VentasPharma.findBySede", query = "SELECT v FROM VentasPharma v WHERE v.sede = :sede"),
    @NamedQuery(name = "VentasPharma.findByFechaVenta", query = "SELECT v FROM VentasPharma v WHERE v.fechaVenta = :fechaVenta"),
    @NamedQuery(name = "VentasPharma.findByUsuario", query = "SELECT v FROM VentasPharma v WHERE v.usuario = :usuario"),
    @NamedQuery(name = "VentasPharma.findByLaboratorio", query = "SELECT v FROM VentasPharma v WHERE v.laboratorio = :laboratorio"),
    @NamedQuery(name = "VentasPharma.findByCodigo", query = "SELECT v FROM VentasPharma v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "VentasPharma.findByProducto", query = "SELECT v FROM VentasPharma v WHERE v.producto = :producto"),
    @NamedQuery(name = "VentasPharma.findByDocVenta", query = "SELECT v FROM VentasPharma v WHERE v.docVenta = :docVenta"),
    @NamedQuery(name = "VentasPharma.findByComprobante", query = "SELECT v FROM VentasPharma v WHERE v.comprobante = :comprobante"),
    @NamedQuery(name = "VentasPharma.findByPago", query = "SELECT v FROM VentasPharma v WHERE v.pago = :pago"),
    @NamedQuery(name = "VentasPharma.findByEstado", query = "SELECT v FROM VentasPharma v WHERE v.estado = :estado"),
    @NamedQuery(name = "VentasPharma.findByCantidadVendida", query = "SELECT v FROM VentasPharma v WHERE v.cantidadVendida = :cantidadVendida"),
    @NamedQuery(name = "VentasPharma.findByCostoUnitario", query = "SELECT v FROM VentasPharma v WHERE v.costoUnitario = :costoUnitario"),
    @NamedQuery(name = "VentasPharma.findByTotalCosto", query = "SELECT v FROM VentasPharma v WHERE v.totalCosto = :totalCosto"),
    @NamedQuery(name = "VentasPharma.findByPrecioVenta", query = "SELECT v FROM VentasPharma v WHERE v.precioVenta = :precioVenta"),
    @NamedQuery(name = "VentasPharma.findByDescuento", query = "SELECT v FROM VentasPharma v WHERE v.descuento = :descuento"),
    @NamedQuery(name = "VentasPharma.findByTotalVenta", query = "SELECT v FROM VentasPharma v WHERE v.totalVenta = :totalVenta"),
    @NamedQuery(name = "VentasPharma.findByGanancia", query = "SELECT v FROM VentasPharma v WHERE v.ganancia = :ganancia"),
    @NamedQuery(name = "VentasPharma.findByPorcentajeGanancia", query = "SELECT v FROM VentasPharma v WHERE v.porcentajeGanancia = :porcentajeGanancia"),
    @NamedQuery(name = "VentasPharma.findByStockActual", query = "SELECT v FROM VentasPharma v WHERE v.stockActual = :stockActual")})
public class VentasPharma implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sede")
    private int sede;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fecha_venta")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechaVenta;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "usuario")
    private String usuario;
    @Size(max = 100)
    @Column(name = "laboratorio")
    private String laboratorio;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "codigo")
    private String codigo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "producto")
    private String producto;
    @Size(max = 50)
    @Column(name = "doc_venta")
    private String docVenta;
    @Size(max = 50)
    @Column(name = "comprobante")
    private String comprobante;
    @Size(max = 50)
    @Column(name = "pago")
    private String pago;
    @Size(max = 50)
    @Column(name = "estado")
    private String estado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantidad_vendida")
    private int cantidadVendida;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "costo_unitario")
    private BigDecimal costoUnitario;
    @Column(name = "total_costo")
    private BigDecimal totalCosto;
    @Column(name = "precio_venta")
    private BigDecimal precioVenta;
    @Column(name = "descuento")
    private BigDecimal descuento;
    @Column(name = "total_venta")
    private BigDecimal totalVenta;
    @Column(name = "ganancia")
    private BigDecimal ganancia;
    @Column(name = "porcentaje_ganancia")
    private BigDecimal porcentajeGanancia;
    @Column(name = "stock_actual")
    private Integer stockActual;

    public VentasPharma() {
    }

    public VentasPharma(Integer id) {
        this.id = id;
    }

    public VentasPharma(Integer id, int sede, Date fechaVenta, String usuario, String codigo, String producto, int cantidadVendida) {
        this.id = id;
        this.sede = sede;
        this.fechaVenta = fechaVenta;
        this.usuario = usuario;
        this.codigo = codigo;
        this.producto = producto;
        this.cantidadVendida = cantidadVendida;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getSede() {
        return sede;
    }

    public void setSede(int sede) {
        this.sede = sede;
    }

    public Date getFechaVenta() {
        return fechaVenta;
    }

    public void setFechaVenta(Date fechaVenta) {
        this.fechaVenta = fechaVenta;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
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

    public String getDocVenta() {
        return docVenta;
    }

    public void setDocVenta(String docVenta) {
        this.docVenta = docVenta;
    }

    public String getComprobante() {
        return comprobante;
    }

    public void setComprobante(String comprobante) {
        this.comprobante = comprobante;
    }

    public String getPago() {
        return pago;
    }

    public void setPago(String pago) {
        this.pago = pago;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getCantidadVendida() {
        return cantidadVendida;
    }

    public void setCantidadVendida(int cantidadVendida) {
        this.cantidadVendida = cantidadVendida;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getTotalCosto() {
        return totalCosto;
    }

    public void setTotalCosto(BigDecimal totalCosto) {
        this.totalCosto = totalCosto;
    }

    public BigDecimal getPrecioVenta() {
        return precioVenta;
    }

    public void setPrecioVenta(BigDecimal precioVenta) {
        this.precioVenta = precioVenta;
    }

    public BigDecimal getDescuento() {
        return descuento;
    }

    public void setDescuento(BigDecimal descuento) {
        this.descuento = descuento;
    }

    public BigDecimal getTotalVenta() {
        return totalVenta;
    }

    public void setTotalVenta(BigDecimal totalVenta) {
        this.totalVenta = totalVenta;
    }

    public BigDecimal getGanancia() {
        return ganancia;
    }

    public void setGanancia(BigDecimal ganancia) {
        this.ganancia = ganancia;
    }

    public BigDecimal getPorcentajeGanancia() {
        return porcentajeGanancia;
    }

    public void setPorcentajeGanancia(BigDecimal porcentajeGanancia) {
        this.porcentajeGanancia = porcentajeGanancia;
    }

    public Integer getStockActual() {
        return stockActual;
    }

    public void setStockActual(Integer stockActual) {
        this.stockActual = stockActual;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasPharma)) {
            return false;
        }
        VentasPharma other = (VentasPharma) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasPharma[ id=" + id + " ]";
    }
    
}
