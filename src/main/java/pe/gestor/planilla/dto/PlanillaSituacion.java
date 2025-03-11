/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.planilla.dto;

import java.io.Serializable;
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
@Table(name = "planilla_situacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaSituacion.findAll", query = "SELECT p FROM PlanillaSituacion p"),
    @NamedQuery(name = "PlanillaSituacion.findByCodiSitua", query = "SELECT p FROM PlanillaSituacion p WHERE p.codiSitua = :codiSitua"),
    @NamedQuery(name = "PlanillaSituacion.findByNombSitua", query = "SELECT p FROM PlanillaSituacion p WHERE p.nombSitua = :nombSitua"),
    @NamedQuery(name = "PlanillaSituacion.findByDescSitua", query = "SELECT p FROM PlanillaSituacion p WHERE p.descSitua = :descSitua")})
public class PlanillaSituacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "codiSitua")
    private String codiSitua;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombSitua")
    private String nombSitua;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 75)
    @Column(name = "descSitua")
    private String descSitua;

    public PlanillaSituacion() {
    }

    public PlanillaSituacion(String codiSitua) {
        this.codiSitua = codiSitua;
    }

    public PlanillaSituacion(String codiSitua, String nombSitua, String descSitua) {
        this.codiSitua = codiSitua;
        this.nombSitua = nombSitua;
        this.descSitua = descSitua;
    }

    public String getCodiSitua() {
        return codiSitua;
    }

    public void setCodiSitua(String codiSitua) {
        this.codiSitua = codiSitua;
    }

    public String getNombSitua() {
        return nombSitua;
    }

    public void setNombSitua(String nombSitua) {
        this.nombSitua = nombSitua;
    }

    public String getDescSitua() {
        return descSitua;
    }

    public void setDescSitua(String descSitua) {
        this.descSitua = descSitua;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiSitua != null ? codiSitua.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaSituacion)) {
            return false;
        }
        PlanillaSituacion other = (PlanillaSituacion) object;
        if ((this.codiSitua == null && other.codiSitua != null) || (this.codiSitua != null && !this.codiSitua.equals(other.codiSitua))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaSituacion[ codiSitua=" + codiSitua + " ]";
    }
    
}
