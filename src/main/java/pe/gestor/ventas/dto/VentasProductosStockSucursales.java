/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.ventas.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "ventas_productos_stock_sucursales")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasProductosStockSucursales.findAll", query = "SELECT v FROM VentasProductosStockSucursales v"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByCodigo", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByCategoria", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.categoria = :categoria"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByLaboratorio", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.laboratorio = :laboratorio"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByNombre", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.nombre = :nombre"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByPvp1", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.pvp1 = :pvp1"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByStockSuc1", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.stockSuc1 = :stockSuc1"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByStockSuc2", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.stockSuc2 = :stockSuc2"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByStockSuc3", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.stockSuc3 = :stockSuc3"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByStockSuc4", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.stockSuc4 = :stockSuc4"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByStockSuc5", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.stockSuc5 = :stockSuc5"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByStockSuc6", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.stockSuc6 = :stockSuc6"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByStockSuc7", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.stockSuc7 = :stockSuc7"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByStockSuc8", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.stockSuc8 = :stockSuc8"),
    @NamedQuery(name = "VentasProductosStockSucursales.findByStockTotal", query = "SELECT v FROM VentasProductosStockSucursales v WHERE v.stockTotal = :stockTotal")})
public class VentasProductosStockSucursales implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Codigo")
    private Long codigo;
    @Size(max = 255)
    @Column(name = "Categoria")
    private String categoria;
    @Size(max = 255)
    @Column(name = "Laboratorio")
    private String laboratorio;
    @Size(max = 255)
    @Column(name = "Nombre")
    private String nombre;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "PVP1")
    private BigDecimal pvp1;
    @Column(name = "stock_suc1")
    private Integer stockSuc1;
    @Column(name = "stock_suc2")
    private Integer stockSuc2;
    @Column(name = "stock_suc3")
    private Integer stockSuc3;
    @Column(name = "stock_suc4")
    private Integer stockSuc4;
    @Column(name = "stock_suc5")
    private Integer stockSuc5;
    @Column(name = "stock_suc6")
    private Integer stockSuc6;
    @Column(name = "stock_suc7")
    private Integer stockSuc7;
    @Column(name = "stock_suc8")
    private Integer stockSuc8;
    @Column(name = "stock_total")
    private Integer stockTotal;

    public VentasProductosStockSucursales() {
    }

    public VentasProductosStockSucursales(Long codigo) {
        this.codigo = codigo;
    }

    public Long getCodigo() {
        return codigo;
    }

    public void setCodigo(Long codigo) {
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

    public BigDecimal getPvp1() {
        return pvp1;
    }

    public void setPvp1(BigDecimal pvp1) {
        this.pvp1 = pvp1;
    }

    public Integer getStockSuc1() {
        return stockSuc1;
    }

    public void setStockSuc1(Integer stockSuc1) {
        this.stockSuc1 = stockSuc1;
    }

    public Integer getStockSuc2() {
        return stockSuc2;
    }

    public void setStockSuc2(Integer stockSuc2) {
        this.stockSuc2 = stockSuc2;
    }

    public Integer getStockSuc3() {
        return stockSuc3;
    }

    public void setStockSuc3(Integer stockSuc3) {
        this.stockSuc3 = stockSuc3;
    }

    public Integer getStockSuc4() {
        return stockSuc4;
    }

    public void setStockSuc4(Integer stockSuc4) {
        this.stockSuc4 = stockSuc4;
    }

    public Integer getStockSuc5() {
        return stockSuc5;
    }

    public void setStockSuc5(Integer stockSuc5) {
        this.stockSuc5 = stockSuc5;
    }

    public Integer getStockSuc6() {
        return stockSuc6;
    }

    public void setStockSuc6(Integer stockSuc6) {
        this.stockSuc6 = stockSuc6;
    }

    public Integer getStockSuc7() {
        return stockSuc7;
    }

    public void setStockSuc7(Integer stockSuc7) {
        this.stockSuc7 = stockSuc7;
    }

    public Integer getStockSuc8() {
        return stockSuc8;
    }

    public void setStockSuc8(Integer stockSuc8) {
        this.stockSuc8 = stockSuc8;
    }

    public Integer getStockTotal() {
        return stockTotal;
    }

    public void setStockTotal(Integer stockTotal) {
        this.stockTotal = stockTotal;
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
        if (!(object instanceof VentasProductosStockSucursales)) {
            return false;
        }
        VentasProductosStockSucursales other = (VentasProductosStockSucursales) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasProductosStockSucursales[ codigo=" + codigo + " ]";
    }
    
}
