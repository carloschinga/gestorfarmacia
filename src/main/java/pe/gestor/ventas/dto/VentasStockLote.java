/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.ventas.dto;

import java.io.Serializable;
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
 * @author Adria
 */
@Entity
@Table(name = "ventas_stock_lote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasStockLote.findAll", query = "SELECT v FROM VentasStockLote v"),
    @NamedQuery(name = "VentasStockLote.findByCodiStocLote", query = "SELECT v FROM VentasStockLote v WHERE v.codiStocLote = :codiStocLote"),
    @NamedQuery(name = "VentasStockLote.findByCodiProd", query = "SELECT v FROM VentasStockLote v WHERE v.codiProd = :codiProd"),
    @NamedQuery(name = "VentasStockLote.findByCodiAlmc", query = "SELECT v FROM VentasStockLote v WHERE v.codiAlmc = :codiAlmc"),
    @NamedQuery(name = "VentasStockLote.findByLote", query = "SELECT v FROM VentasStockLote v WHERE v.lote = :lote"),
    @NamedQuery(name = "VentasStockLote.findByFechVent", query = "SELECT v FROM VentasStockLote v WHERE v.fechVent = :fechVent"),
    @NamedQuery(name = "VentasStockLote.findByCantEnte", query = "SELECT v FROM VentasStockLote v WHERE v.cantEnte = :cantEnte"),
    @NamedQuery(name = "VentasStockLote.findByCantFrac", query = "SELECT v FROM VentasStockLote v WHERE v.cantFrac = :cantFrac"),
    @NamedQuery(name = "VentasStockLote.findByCostoEnte", query = "SELECT v FROM VentasStockLote v WHERE v.costoEnte = :costoEnte"),
    @NamedQuery(name = "VentasStockLote.findByCostoFrac", query = "SELECT v FROM VentasStockLote v WHERE v.costoFrac = :costoFrac"),
    @NamedQuery(name = "VentasStockLote.findByActiStocLote", query = "SELECT v FROM VentasStockLote v WHERE v.actiStocLote = :actiStocLote")})
public class VentasStockLote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiStocLote")
    private Integer codiStocLote;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codiProd")
    private String codiProd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiAlmc")
    private int codiAlmc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "lote")
    private String lote;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechVent")
    @Temporal(TemporalType.DATE)
    private Date fechVent;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantEnte")
    private int cantEnte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantFrac")
    private int cantFrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "costoEnte")
    private int costoEnte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "costoFrac")
    private int costoFrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiStocLote")
    private boolean actiStocLote;

    public VentasStockLote() {
    }

    public VentasStockLote(Integer codiStocLote) {
        this.codiStocLote = codiStocLote;
    }

    public VentasStockLote(Integer codiStocLote, String codiProd, int codiAlmc, String lote, Date fechVent, int cantEnte, int cantFrac, int costoEnte, int costoFrac, boolean actiStocLote) {
        this.codiStocLote = codiStocLote;
        this.codiProd = codiProd;
        this.codiAlmc = codiAlmc;
        this.lote = lote;
        this.fechVent = fechVent;
        this.cantEnte = cantEnte;
        this.cantFrac = cantFrac;
        this.costoEnte = costoEnte;
        this.costoFrac = costoFrac;
        this.actiStocLote = actiStocLote;
    }

    public Integer getCodiStocLote() {
        return codiStocLote;
    }

    public void setCodiStocLote(Integer codiStocLote) {
        this.codiStocLote = codiStocLote;
    }

    public String getCodiProd() {
        return codiProd;
    }

    public void setCodiProd(String codiProd) {
        this.codiProd = codiProd;
    }

    public int getCodiAlmc() {
        return codiAlmc;
    }

    public void setCodiAlmc(int codiAlmc) {
        this.codiAlmc = codiAlmc;
    }

    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }

    public Date getFechVent() {
        return fechVent;
    }

    public void setFechVent(Date fechVent) {
        this.fechVent = fechVent;
    }

    public int getCantEnte() {
        return cantEnte;
    }

    public void setCantEnte(int cantEnte) {
        this.cantEnte = cantEnte;
    }

    public int getCantFrac() {
        return cantFrac;
    }

    public void setCantFrac(int cantFrac) {
        this.cantFrac = cantFrac;
    }

    public int getCostoEnte() {
        return costoEnte;
    }

    public void setCostoEnte(int costoEnte) {
        this.costoEnte = costoEnte;
    }

    public int getCostoFrac() {
        return costoFrac;
    }

    public void setCostoFrac(int costoFrac) {
        this.costoFrac = costoFrac;
    }

    public boolean getActiStocLote() {
        return actiStocLote;
    }

    public void setActiStocLote(boolean actiStocLote) {
        this.actiStocLote = actiStocLote;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiStocLote != null ? codiStocLote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasStockLote)) {
            return false;
        }
        VentasStockLote other = (VentasStockLote) object;
        if ((this.codiStocLote == null && other.codiStocLote != null) || (this.codiStocLote != null && !this.codiStocLote.equals(other.codiStocLote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasStockLote[ codiStocLote=" + codiStocLote + " ]";
    }
    
}
