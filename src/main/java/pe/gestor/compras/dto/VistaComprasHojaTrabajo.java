/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.compras.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * @author Adria
 */
@Entity
@Table(name = "vista_compras_hoja_trabajo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaComprasHojaTrabajo.findAll", query = "SELECT v FROM VistaComprasHojaTrabajo v"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByCodigo", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByNombreProducto", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByCodiCate", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.codiCate = :codiCate"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByCategoria", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.categoria = :categoria"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByCodiLabo", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.codiLabo = :codiLabo"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByLaboratorio", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.laboratorio = :laboratorio"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByUnidxcaja", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.unidxcaja = :unidxcaja"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByPvc", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.pvc = :pvc"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByPcc", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.pcc = :pcc"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByPvu", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.pvu = :pvu"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByPcu", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.pcu = :pcu"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByVentas30ultmdias", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.ventas30ultmdias = :ventas30ultmdias"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByVentas", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.ventas = :ventas"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByMes1", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.mes1 = :mes1"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByMes2", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.mes2 = :mes2"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByMes3", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.mes3 = :mes3"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByStock", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.stock = :stock"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByGananciacaja", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.gananciacaja = :gananciacaja"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByGananciauni", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.gananciauni = :gananciauni"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByStockmin", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.stockmin = :stockmin"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByIndiinve", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.indiinve = :indiinve"),
    @NamedQuery(name = "VistaComprasHojaTrabajo.findByIndirota", query = "SELECT v FROM VistaComprasHojaTrabajo v WHERE v.indirota = :indirota")})
public class VistaComprasHojaTrabajo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 15)
    @Id
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 255)
    @Column(name = "nombre_producto")
    private String nombreProducto;
    @Column(name = "codiCate")
    private Integer codiCate;
    @Size(max = 255)
    @Column(name = "categoria")
    private String categoria;
    @Column(name = "codiLabo")
    private Integer codiLabo;
    @Size(max = 255)
    @Column(name = "laboratorio")
    private String laboratorio;
    @Column(name = "unidxcaja")
    private Integer unidxcaja;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pvc")
    private BigDecimal pvc;
    @Column(name = "pcc")
    private BigDecimal pcc;
    @Column(name = "pvu")
    private BigDecimal pvu;
    @Column(name = "pcu")
    private BigDecimal pcu;
    @Column(name = "ventas30ultmdias")
    private BigInteger ventas30ultmdias;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Ventas")
    private BigInteger ventas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Mes1")
    private BigInteger mes1;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Mes2")
    private BigInteger mes2;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Mes3")
    private BigInteger mes3;
    @Column(name = "stock")
    private Integer stock;
    @Column(name = "gananciacaja")
    private BigDecimal gananciacaja;
    @Column(name = "gananciauni")
    private BigDecimal gananciauni;
    @Column(name = "stockmin")
    private BigDecimal stockmin;
    @Column(name = "indiinve")
    private BigDecimal indiinve;
    @Column(name = "indirota")
    private BigDecimal indirota;

    public VistaComprasHojaTrabajo() {
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public Integer getCodiCate() {
        return codiCate;
    }

    public void setCodiCate(Integer codiCate) {
        this.codiCate = codiCate;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public Integer getCodiLabo() {
        return codiLabo;
    }

    public void setCodiLabo(Integer codiLabo) {
        this.codiLabo = codiLabo;
    }

    public String getLaboratorio() {
        return laboratorio;
    }

    public void setLaboratorio(String laboratorio) {
        this.laboratorio = laboratorio;
    }

    public Integer getUnidxcaja() {
        return unidxcaja;
    }

    public void setUnidxcaja(Integer unidxcaja) {
        this.unidxcaja = unidxcaja;
    }

    public BigDecimal getPvc() {
        return pvc;
    }

    public void setPvc(BigDecimal pvc) {
        this.pvc = pvc;
    }

    public BigDecimal getPcc() {
        return pcc;
    }

    public void setPcc(BigDecimal pcc) {
        this.pcc = pcc;
    }

    public BigDecimal getPvu() {
        return pvu;
    }

    public void setPvu(BigDecimal pvu) {
        this.pvu = pvu;
    }

    public BigDecimal getPcu() {
        return pcu;
    }

    public void setPcu(BigDecimal pcu) {
        this.pcu = pcu;
    }

    public BigInteger getVentas30ultmdias() {
        return ventas30ultmdias;
    }

    public void setVentas30ultmdias(BigInteger ventas30ultmdias) {
        this.ventas30ultmdias = ventas30ultmdias;
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

    public BigInteger getMes3() {
        return mes3;
    }

    public void setMes3(BigInteger mes3) {
        this.mes3 = mes3;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public BigDecimal getGananciacaja() {
        return gananciacaja;
    }

    public void setGananciacaja(BigDecimal gananciacaja) {
        this.gananciacaja = gananciacaja;
    }

    public BigDecimal getGananciauni() {
        return gananciauni;
    }

    public void setGananciauni(BigDecimal gananciauni) {
        this.gananciauni = gananciauni;
    }

    public BigDecimal getStockmin() {
        return stockmin;
    }

    public void setStockmin(BigDecimal stockmin) {
        this.stockmin = stockmin;
    }

    public BigDecimal getIndiinve() {
        return indiinve;
    }

    public void setIndiinve(BigDecimal indiinve) {
        this.indiinve = indiinve;
    }

    public BigDecimal getIndirota() {
        return indirota;
    }

    public void setIndirota(BigDecimal indirota) {
        this.indirota = indirota;
    }
    
}
