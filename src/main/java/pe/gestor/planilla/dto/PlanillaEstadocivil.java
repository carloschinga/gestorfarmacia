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
@Table(name = "planilla_estadocivil")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaEstadocivil.findAll", query = "SELECT p FROM PlanillaEstadocivil p"),
    @NamedQuery(name = "PlanillaEstadocivil.findByCodiEstaCiv", query = "SELECT p FROM PlanillaEstadocivil p WHERE p.codiEstaCiv = :codiEstaCiv"),
    @NamedQuery(name = "PlanillaEstadocivil.findByNombEstaCiv", query = "SELECT p FROM PlanillaEstadocivil p WHERE p.nombEstaCiv = :nombEstaCiv")})
public class PlanillaEstadocivil implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiEstaCiv")
    private Integer codiEstaCiv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombEstaCiv")
    private String nombEstaCiv;

    public PlanillaEstadocivil() {
    }

    public PlanillaEstadocivil(Integer codiEstaCiv) {
        this.codiEstaCiv = codiEstaCiv;
    }

    public PlanillaEstadocivil(Integer codiEstaCiv, String nombEstaCiv) {
        this.codiEstaCiv = codiEstaCiv;
        this.nombEstaCiv = nombEstaCiv;
    }

    public Integer getCodiEstaCiv() {
        return codiEstaCiv;
    }

    public void setCodiEstaCiv(Integer codiEstaCiv) {
        this.codiEstaCiv = codiEstaCiv;
    }

    public String getNombEstaCiv() {
        return nombEstaCiv;
    }

    public void setNombEstaCiv(String nombEstaCiv) {
        this.nombEstaCiv = nombEstaCiv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiEstaCiv != null ? codiEstaCiv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaEstadocivil)) {
            return false;
        }
        PlanillaEstadocivil other = (PlanillaEstadocivil) object;
        if ((this.codiEstaCiv == null && other.codiEstaCiv != null) || (this.codiEstaCiv != null && !this.codiEstaCiv.equals(other.codiEstaCiv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaEstadocivil[ codiEstaCiv=" + codiEstaCiv + " ]";
    }
    
}
