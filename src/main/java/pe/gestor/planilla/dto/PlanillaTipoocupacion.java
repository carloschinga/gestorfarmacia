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
@Table(name = "planilla_tipoocupacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaTipoocupacion.findAll", query = "SELECT p FROM PlanillaTipoocupacion p"),
    @NamedQuery(name = "PlanillaTipoocupacion.findByCodiTipoOcup", query = "SELECT p FROM PlanillaTipoocupacion p WHERE p.codiTipoOcup = :codiTipoOcup"),
    @NamedQuery(name = "PlanillaTipoocupacion.findByNombTipoOcup", query = "SELECT p FROM PlanillaTipoocupacion p WHERE p.nombTipoOcup = :nombTipoOcup")})
public class PlanillaTipoocupacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "codiTipoOcup")
    private String codiTipoOcup;
    @Size(max = 30)
    @Column(name = "nombTipoOcup")
    private String nombTipoOcup;

    public PlanillaTipoocupacion() {
    }

    public PlanillaTipoocupacion(String codiTipoOcup) {
        this.codiTipoOcup = codiTipoOcup;
    }

    public String getCodiTipoOcup() {
        return codiTipoOcup;
    }

    public void setCodiTipoOcup(String codiTipoOcup) {
        this.codiTipoOcup = codiTipoOcup;
    }

    public String getNombTipoOcup() {
        return nombTipoOcup;
    }

    public void setNombTipoOcup(String nombTipoOcup) {
        this.nombTipoOcup = nombTipoOcup;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiTipoOcup != null ? codiTipoOcup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaTipoocupacion)) {
            return false;
        }
        PlanillaTipoocupacion other = (PlanillaTipoocupacion) object;
        if ((this.codiTipoOcup == null && other.codiTipoOcup != null) || (this.codiTipoOcup != null && !this.codiTipoOcup.equals(other.codiTipoOcup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaTipoocupacion[ codiTipoOcup=" + codiTipoOcup + " ]";
    }
    
}
