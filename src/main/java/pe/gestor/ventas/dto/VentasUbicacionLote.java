/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.ventas.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "ventas_ubicacion_lote")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasUbicacionLote.findAll", query = "SELECT v FROM VentasUbicacionLote v"),
    @NamedQuery(name = "VentasUbicacionLote.findByCodiUbicLote", query = "SELECT v FROM VentasUbicacionLote v WHERE v.codiUbicLote = :codiUbicLote"),
    @NamedQuery(name = "VentasUbicacionLote.findByCodiStocLote", query = "SELECT v FROM VentasUbicacionLote v WHERE v.codiStocLote = :codiStocLote"),
    @NamedQuery(name = "VentasUbicacionLote.findByCantEnte", query = "SELECT v FROM VentasUbicacionLote v WHERE v.cantEnte = :cantEnte"),
    @NamedQuery(name = "VentasUbicacionLote.findByCantFrac", query = "SELECT v FROM VentasUbicacionLote v WHERE v.cantFrac = :cantFrac"),
    @NamedQuery(name = "VentasUbicacionLote.findByActiUbicLote", query = "SELECT v FROM VentasUbicacionLote v WHERE v.actiUbicLote = :actiUbicLote")})
public class VentasUbicacionLote implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiUbicLote")
    private Integer codiUbicLote;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiStocLote")
    private int codiStocLote;
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
    @Column(name = "actiUbicLote")
    private boolean actiUbicLote;

    public VentasUbicacionLote() {
    }

    public VentasUbicacionLote(Integer codiUbicLote) {
        this.codiUbicLote = codiUbicLote;
    }

    public VentasUbicacionLote(Integer codiUbicLote, int codiStocLote, int cantEnte, int cantFrac, boolean actiUbicLote) {
        this.codiUbicLote = codiUbicLote;
        this.codiStocLote = codiStocLote;
        this.cantEnte = cantEnte;
        this.cantFrac = cantFrac;
        this.actiUbicLote = actiUbicLote;
    }

    public Integer getCodiUbicLote() {
        return codiUbicLote;
    }

    public void setCodiUbicLote(Integer codiUbicLote) {
        this.codiUbicLote = codiUbicLote;
    }

    public int getCodiStocLote() {
        return codiStocLote;
    }

    public void setCodiStocLote(int codiStocLote) {
        this.codiStocLote = codiStocLote;
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

    public boolean getActiUbicLote() {
        return actiUbicLote;
    }

    public void setActiUbicLote(boolean actiUbicLote) {
        this.actiUbicLote = actiUbicLote;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiUbicLote != null ? codiUbicLote.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasUbicacionLote)) {
            return false;
        }
        VentasUbicacionLote other = (VentasUbicacionLote) object;
        if ((this.codiUbicLote == null && other.codiUbicLote != null) || (this.codiUbicLote != null && !this.codiUbicLote.equals(other.codiUbicLote))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasUbicacionLote[ codiUbicLote=" + codiUbicLote + " ]";
    }
    
}
