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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "ventas_movimiento_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasMovimientoDetalle.findAll", query = "SELECT v FROM VentasMovimientoDetalle v"),
    @NamedQuery(name = "VentasMovimientoDetalle.findByCodiMoviDeta", query = "SELECT v FROM VentasMovimientoDetalle v WHERE v.codiMoviDeta = :codiMoviDeta"),
    @NamedQuery(name = "VentasMovimientoDetalle.findByCodiMoviCabe", query = "SELECT v FROM VentasMovimientoDetalle v WHERE v.codiMoviCabe = :codiMoviCabe"),
    @NamedQuery(name = "VentasMovimientoDetalle.findByCodiProdu", query = "SELECT v FROM VentasMovimientoDetalle v WHERE v.codiProdu = :codiProdu"),
    @NamedQuery(name = "VentasMovimientoDetalle.findByCantEnte", query = "SELECT v FROM VentasMovimientoDetalle v WHERE v.cantEnte = :cantEnte"),
    @NamedQuery(name = "VentasMovimientoDetalle.findByCantFrac", query = "SELECT v FROM VentasMovimientoDetalle v WHERE v.cantFrac = :cantFrac"),
    @NamedQuery(name = "VentasMovimientoDetalle.findByCostoEnte", query = "SELECT v FROM VentasMovimientoDetalle v WHERE v.costoEnte = :costoEnte"),
    @NamedQuery(name = "VentasMovimientoDetalle.findByCostoFrac", query = "SELECT v FROM VentasMovimientoDetalle v WHERE v.costoFrac = :costoFrac"),
    @NamedQuery(name = "VentasMovimientoDetalle.findByActiMoviDeta", query = "SELECT v FROM VentasMovimientoDetalle v WHERE v.actiMoviDeta = :actiMoviDeta")})
public class VentasMovimientoDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiMoviDeta")
    private Integer codiMoviDeta;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiMoviCabe")
    private int codiMoviCabe;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "codiProdu")
    private String codiProdu;
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
    private long costoEnte;
    @Basic(optional = false)
    @NotNull
    @Column(name = "costoFrac")
    private long costoFrac;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiMoviDeta")
    private boolean actiMoviDeta;

    public VentasMovimientoDetalle() {
    }

    public VentasMovimientoDetalle(Integer codiMoviDeta) {
        this.codiMoviDeta = codiMoviDeta;
    }

    public VentasMovimientoDetalle(Integer codiMoviDeta, int codiMoviCabe, String codiProdu, int cantEnte, int cantFrac, long costoEnte, long costoFrac, boolean actiMoviDeta) {
        this.codiMoviDeta = codiMoviDeta;
        this.codiMoviCabe = codiMoviCabe;
        this.codiProdu = codiProdu;
        this.cantEnte = cantEnte;
        this.cantFrac = cantFrac;
        this.costoEnte = costoEnte;
        this.costoFrac = costoFrac;
        this.actiMoviDeta = actiMoviDeta;
    }

    public Integer getCodiMoviDeta() {
        return codiMoviDeta;
    }

    public void setCodiMoviDeta(Integer codiMoviDeta) {
        this.codiMoviDeta = codiMoviDeta;
    }

    public int getCodiMoviCabe() {
        return codiMoviCabe;
    }

    public void setCodiMoviCabe(int codiMoviCabe) {
        this.codiMoviCabe = codiMoviCabe;
    }

    public String getCodiProdu() {
        return codiProdu;
    }

    public void setCodiProdu(String codiProdu) {
        this.codiProdu = codiProdu;
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

    public long getCostoEnte() {
        return costoEnte;
    }

    public void setCostoEnte(long costoEnte) {
        this.costoEnte = costoEnte;
    }

    public long getCostoFrac() {
        return costoFrac;
    }

    public void setCostoFrac(long costoFrac) {
        this.costoFrac = costoFrac;
    }

    public boolean getActiMoviDeta() {
        return actiMoviDeta;
    }

    public void setActiMoviDeta(boolean actiMoviDeta) {
        this.actiMoviDeta = actiMoviDeta;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiMoviDeta != null ? codiMoviDeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasMovimientoDetalle)) {
            return false;
        }
        VentasMovimientoDetalle other = (VentasMovimientoDetalle) object;
        if ((this.codiMoviDeta == null && other.codiMoviDeta != null) || (this.codiMoviDeta != null && !this.codiMoviDeta.equals(other.codiMoviDeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasMovimientoDetalle[ codiMoviDeta=" + codiMoviDeta + " ]";
    }
    
}
