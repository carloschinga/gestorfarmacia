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
@Table(name = "planilla_situacionespecial")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaSituacionespecial.findAll", query = "SELECT p FROM PlanillaSituacionespecial p"),
    @NamedQuery(name = "PlanillaSituacionespecial.findByCodiSituEspe", query = "SELECT p FROM PlanillaSituacionespecial p WHERE p.codiSituEspe = :codiSituEspe"),
    @NamedQuery(name = "PlanillaSituacionespecial.findByNombSituEspe", query = "SELECT p FROM PlanillaSituacionespecial p WHERE p.nombSituEspe = :nombSituEspe"),
    @NamedQuery(name = "PlanillaSituacionespecial.findByAbrvSituEspe", query = "SELECT p FROM PlanillaSituacionespecial p WHERE p.abrvSituEspe = :abrvSituEspe")})
public class PlanillaSituacionespecial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "codiSituEspe")
    private String codiSituEspe;
    @Size(max = 100)
    @Column(name = "nombSituEspe")
    private String nombSituEspe;
    @Size(max = 50)
    @Column(name = "abrvSituEspe")
    private String abrvSituEspe;

    public PlanillaSituacionespecial() {
    }

    public PlanillaSituacionespecial(String codiSituEspe) {
        this.codiSituEspe = codiSituEspe;
    }

    public String getCodiSituEspe() {
        return codiSituEspe;
    }

    public void setCodiSituEspe(String codiSituEspe) {
        this.codiSituEspe = codiSituEspe;
    }

    public String getNombSituEspe() {
        return nombSituEspe;
    }

    public void setNombSituEspe(String nombSituEspe) {
        this.nombSituEspe = nombSituEspe;
    }

    public String getAbrvSituEspe() {
        return abrvSituEspe;
    }

    public void setAbrvSituEspe(String abrvSituEspe) {
        this.abrvSituEspe = abrvSituEspe;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiSituEspe != null ? codiSituEspe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaSituacionespecial)) {
            return false;
        }
        PlanillaSituacionespecial other = (PlanillaSituacionespecial) object;
        if ((this.codiSituEspe == null && other.codiSituEspe != null) || (this.codiSituEspe != null && !this.codiSituEspe.equals(other.codiSituEspe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaSituacionespecial[ codiSituEspe=" + codiSituEspe + " ]";
    }
    
}
