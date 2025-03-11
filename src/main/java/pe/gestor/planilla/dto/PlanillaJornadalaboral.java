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
@Table(name = "planilla_jornadalaboral")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaJornadalaboral.findAll", query = "SELECT p FROM PlanillaJornadalaboral p"),
    @NamedQuery(name = "PlanillaJornadalaboral.findByCodiJornadLab", query = "SELECT p FROM PlanillaJornadalaboral p WHERE p.codiJornadLab = :codiJornadLab"),
    @NamedQuery(name = "PlanillaJornadalaboral.findByNombJornadLab", query = "SELECT p FROM PlanillaJornadalaboral p WHERE p.nombJornadLab = :nombJornadLab")})
public class PlanillaJornadalaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiJornadLab")
    private Integer codiJornadLab;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombJornadLab")
    private String nombJornadLab;

    public PlanillaJornadalaboral() {
    }

    public PlanillaJornadalaboral(Integer codiJornadLab) {
        this.codiJornadLab = codiJornadLab;
    }

    public PlanillaJornadalaboral(Integer codiJornadLab, String nombJornadLab) {
        this.codiJornadLab = codiJornadLab;
        this.nombJornadLab = nombJornadLab;
    }

    public Integer getCodiJornadLab() {
        return codiJornadLab;
    }

    public void setCodiJornadLab(Integer codiJornadLab) {
        this.codiJornadLab = codiJornadLab;
    }

    public String getNombJornadLab() {
        return nombJornadLab;
    }

    public void setNombJornadLab(String nombJornadLab) {
        this.nombJornadLab = nombJornadLab;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiJornadLab != null ? codiJornadLab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaJornadalaboral)) {
            return false;
        }
        PlanillaJornadalaboral other = (PlanillaJornadalaboral) object;
        if ((this.codiJornadLab == null && other.codiJornadLab != null) || (this.codiJornadLab != null && !this.codiJornadLab.equals(other.codiJornadLab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaJornadalaboral[ codiJornadLab=" + codiJornadLab + " ]";
    }
    
}
