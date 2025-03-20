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
@Table(name = "ventas_ubicacion_almacen")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasUbicacionAlmacen.findAll", query = "SELECT v FROM VentasUbicacionAlmacen v"),
    @NamedQuery(name = "VentasUbicacionAlmacen.findByCodiUbicAlmc", query = "SELECT v FROM VentasUbicacionAlmacen v WHERE v.codiUbicAlmc = :codiUbicAlmc"),
    @NamedQuery(name = "VentasUbicacionAlmacen.findByCodiAlmc", query = "SELECT v FROM VentasUbicacionAlmacen v WHERE v.codiAlmc = :codiAlmc"),
    @NamedQuery(name = "VentasUbicacionAlmacen.findByDescUbicAlmc", query = "SELECT v FROM VentasUbicacionAlmacen v WHERE v.descUbicAlmc = :descUbicAlmc"),
    @NamedQuery(name = "VentasUbicacionAlmacen.findByActiUbicAlmc", query = "SELECT v FROM VentasUbicacionAlmacen v WHERE v.actiUbicAlmc = :actiUbicAlmc")})
public class VentasUbicacionAlmacen implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiUbicAlmc")
    private Integer codiUbicAlmc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiAlmc")
    private int codiAlmc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descUbicAlmc")
    private String descUbicAlmc;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiUbicAlmc")
    private boolean actiUbicAlmc;

    public VentasUbicacionAlmacen() {
    }

    public VentasUbicacionAlmacen(Integer codiUbicAlmc) {
        this.codiUbicAlmc = codiUbicAlmc;
    }

    public VentasUbicacionAlmacen(Integer codiUbicAlmc, int codiAlmc, String descUbicAlmc, boolean actiUbicAlmc) {
        this.codiUbicAlmc = codiUbicAlmc;
        this.codiAlmc = codiAlmc;
        this.descUbicAlmc = descUbicAlmc;
        this.actiUbicAlmc = actiUbicAlmc;
    }

    public Integer getCodiUbicAlmc() {
        return codiUbicAlmc;
    }

    public void setCodiUbicAlmc(Integer codiUbicAlmc) {
        this.codiUbicAlmc = codiUbicAlmc;
    }

    public int getCodiAlmc() {
        return codiAlmc;
    }

    public void setCodiAlmc(int codiAlmc) {
        this.codiAlmc = codiAlmc;
    }

    public String getDescUbicAlmc() {
        return descUbicAlmc;
    }

    public void setDescUbicAlmc(String descUbicAlmc) {
        this.descUbicAlmc = descUbicAlmc;
    }

    public boolean getActiUbicAlmc() {
        return actiUbicAlmc;
    }

    public void setActiUbicAlmc(boolean actiUbicAlmc) {
        this.actiUbicAlmc = actiUbicAlmc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiUbicAlmc != null ? codiUbicAlmc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasUbicacionAlmacen)) {
            return false;
        }
        VentasUbicacionAlmacen other = (VentasUbicacionAlmacen) object;
        if ((this.codiUbicAlmc == null && other.codiUbicAlmc != null) || (this.codiUbicAlmc != null && !this.codiUbicAlmc.equals(other.codiUbicAlmc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasUbicacionAlmacen[ codiUbicAlmc=" + codiUbicAlmc + " ]";
    }
    
}
