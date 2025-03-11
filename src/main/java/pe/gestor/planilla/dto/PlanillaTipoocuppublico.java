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
@Table(name = "planilla_tipoocuppublico")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaTipoocuppublico.findAll", query = "SELECT p FROM PlanillaTipoocuppublico p"),
    @NamedQuery(name = "PlanillaTipoocuppublico.findByCodiTipoOcupPubl", query = "SELECT p FROM PlanillaTipoocuppublico p WHERE p.codiTipoOcupPubl = :codiTipoOcupPubl"),
    @NamedQuery(name = "PlanillaTipoocuppublico.findByNombTipoOcupPubl", query = "SELECT p FROM PlanillaTipoocuppublico p WHERE p.nombTipoOcupPubl = :nombTipoOcupPubl")})
public class PlanillaTipoocuppublico implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "codiTipoOcupPubl")
    private String codiTipoOcupPubl;
    @Size(max = 300)
    @Column(name = "nombTipoOcupPubl")
    private String nombTipoOcupPubl;

    public PlanillaTipoocuppublico() {
    }

    public PlanillaTipoocuppublico(String codiTipoOcupPubl) {
        this.codiTipoOcupPubl = codiTipoOcupPubl;
    }

    public String getCodiTipoOcupPubl() {
        return codiTipoOcupPubl;
    }

    public void setCodiTipoOcupPubl(String codiTipoOcupPubl) {
        this.codiTipoOcupPubl = codiTipoOcupPubl;
    }

    public String getNombTipoOcupPubl() {
        return nombTipoOcupPubl;
    }

    public void setNombTipoOcupPubl(String nombTipoOcupPubl) {
        this.nombTipoOcupPubl = nombTipoOcupPubl;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiTipoOcupPubl != null ? codiTipoOcupPubl.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaTipoocuppublico)) {
            return false;
        }
        PlanillaTipoocuppublico other = (PlanillaTipoocuppublico) object;
        if ((this.codiTipoOcupPubl == null && other.codiTipoOcupPubl != null) || (this.codiTipoOcupPubl != null && !this.codiTipoOcupPubl.equals(other.codiTipoOcupPubl))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaTipoocuppublico[ codiTipoOcupPubl=" + codiTipoOcupPubl + " ]";
    }
    
}
