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
@Table(name = "planilla_periocidadremuneracion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaPeriocidadremuneracion.findAll", query = "SELECT p FROM PlanillaPeriocidadremuneracion p"),
    @NamedQuery(name = "PlanillaPeriocidadremuneracion.findByCodiPeriRemu", query = "SELECT p FROM PlanillaPeriocidadremuneracion p WHERE p.codiPeriRemu = :codiPeriRemu"),
    @NamedQuery(name = "PlanillaPeriocidadremuneracion.findByNombPeriRemu", query = "SELECT p FROM PlanillaPeriocidadremuneracion p WHERE p.nombPeriRemu = :nombPeriRemu")})
public class PlanillaPeriocidadremuneracion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "codiPeriRemu")
    private String codiPeriRemu;
    @Size(max = 50)
    @Column(name = "nombPeriRemu")
    private String nombPeriRemu;

    public PlanillaPeriocidadremuneracion() {
    }

    public PlanillaPeriocidadremuneracion(String codiPeriRemu) {
        this.codiPeriRemu = codiPeriRemu;
    }

    public String getCodiPeriRemu() {
        return codiPeriRemu;
    }

    public void setCodiPeriRemu(String codiPeriRemu) {
        this.codiPeriRemu = codiPeriRemu;
    }

    public String getNombPeriRemu() {
        return nombPeriRemu;
    }

    public void setNombPeriRemu(String nombPeriRemu) {
        this.nombPeriRemu = nombPeriRemu;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiPeriRemu != null ? codiPeriRemu.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaPeriocidadremuneracion)) {
            return false;
        }
        PlanillaPeriocidadremuneracion other = (PlanillaPeriocidadremuneracion) object;
        if ((this.codiPeriRemu == null && other.codiPeriRemu != null) || (this.codiPeriRemu != null && !this.codiPeriRemu.equals(other.codiPeriRemu))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaPeriocidadremuneracion[ codiPeriRemu=" + codiPeriRemu + " ]";
    }
    
}
