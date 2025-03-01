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
 * @author USER
 */
@Entity
@Table(name = "ventas_productos_stock")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasProductosStock.findAll", query = "SELECT v FROM VentasProductosStock v"),
    @NamedQuery(name = "VentasProductosStock.findByCodigo", query = "SELECT v FROM VentasProductosStock v WHERE v.codigo = :codigo"),
    @NamedQuery(name = "VentasProductosStock.findByCategoria", query = "SELECT v FROM VentasProductosStock v WHERE v.categoria = :categoria"),
    @NamedQuery(name = "VentasProductosStock.findByLaboratorio", query = "SELECT v FROM VentasProductosStock v WHERE v.laboratorio = :laboratorio"),
    @NamedQuery(name = "VentasProductosStock.findByNombreProducto", query = "SELECT v FROM VentasProductosStock v WHERE v.nombreProducto = :nombreProducto"),
    @NamedQuery(name = "VentasProductosStock.findByPvp", query = "SELECT v FROM VentasProductosStock v WHERE v.pvp = :pvp"),
    @NamedQuery(name = "VentasProductosStock.findByStockSuc1", query = "SELECT v FROM VentasProductosStock v WHERE v.stockSuc1 = :stockSuc1"),
    @NamedQuery(name = "VentasProductosStock.findByStockSuc2", query = "SELECT v FROM VentasProductosStock v WHERE v.stockSuc2 = :stockSuc2"),
    @NamedQuery(name = "VentasProductosStock.findByStockSuc3", query = "SELECT v FROM VentasProductosStock v WHERE v.stockSuc3 = :stockSuc3"),
    @NamedQuery(name = "VentasProductosStock.findByStockSuc4", query = "SELECT v FROM VentasProductosStock v WHERE v.stockSuc4 = :stockSuc4"),
    @NamedQuery(name = "VentasProductosStock.findByStockSuc5", query = "SELECT v FROM VentasProductosStock v WHERE v.stockSuc5 = :stockSuc5"),
    @NamedQuery(name = "VentasProductosStock.findByStockSuc6", query = "SELECT v FROM VentasProductosStock v WHERE v.stockSuc6 = :stockSuc6"),
    @NamedQuery(name = "VentasProductosStock.findByStockSuc7", query = "SELECT v FROM VentasProductosStock v WHERE v.stockSuc7 = :stockSuc7"),
    @NamedQuery(name = "VentasProductosStock.findByStockSuc8", query = "SELECT v FROM VentasProductosStock v WHERE v.stockSuc8 = :stockSuc8"),
    @NamedQuery(name = "VentasProductosStock.findByStockTotal", query = "SELECT v FROM VentasProductosStock v WHERE v.stockTotal = :stockTotal")})
public class VentasProductosStock implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "codigo")
    private String codigo;
    @Size(max = 255)
    @Column(name = "categoria")
    private String categoria;
    @Size(max = 255)
    @Column(name = "laboratorio")
    private String laboratorio;
    @Size(max = 255)
    @Column(name = "nombre_producto")
    private String nombreProducto;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "pvp")
    private BigDecimal pvp;
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

    public VentasProductosStock() {
    }

    public VentasProductosStock(String codigo) {
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

    public String getNombreProducto() {
        return nombreProducto;
    }

    public void setNombreProducto(String nombreProducto) {
        this.nombreProducto = nombreProducto;
    }

    public BigDecimal getPvp() {
        return pvp;
    }

    public void setPvp(BigDecimal pvp) {
        this.pvp = pvp;
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
        if (!(object instanceof VentasProductosStock)) {
            return false;
        }
        VentasProductosStock other = (VentasProductosStock) object;
        if ((this.codigo == null && other.codigo != null) || (this.codigo != null && !this.codigo.equals(other.codigo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasProductosStock[ codigo=" + codigo + " ]";
    }
    
}
