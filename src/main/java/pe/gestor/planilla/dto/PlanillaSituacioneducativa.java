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
@Table(name = "planilla_situacioneducativa")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaSituacioneducativa.findAll", query = "SELECT p FROM PlanillaSituacioneducativa p"),
    @NamedQuery(name = "PlanillaSituacioneducativa.findByCodiSituEduc", query = "SELECT p FROM PlanillaSituacioneducativa p WHERE p.codiSituEduc = :codiSituEduc"),
    @NamedQuery(name = "PlanillaSituacioneducativa.findByNombSituEduc", query = "SELECT p FROM PlanillaSituacioneducativa p WHERE p.nombSituEduc = :nombSituEduc"),
    @NamedQuery(name = "PlanillaSituacioneducativa.findByAbrvSituEduc", query = "SELECT p FROM PlanillaSituacioneducativa p WHERE p.abrvSituEduc = :abrvSituEduc")})
public class PlanillaSituacioneducativa implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codiSituEduc")
    private String codiSituEduc;
    @Size(max = 100)
    @Column(name = "nombSituEduc")
    private String nombSituEduc;
    @Size(max = 30)
    @Column(name = "abrvSituEduc")
    private String abrvSituEduc;

    public PlanillaSituacioneducativa() {
    }

    public PlanillaSituacioneducativa(String codiSituEduc) {
        this.codiSituEduc = codiSituEduc;
    }

    public String getCodiSituEduc() {
        return codiSituEduc;
    }

    public void setCodiSituEduc(String codiSituEduc) {
        this.codiSituEduc = codiSituEduc;
    }

    public String getNombSituEduc() {
        return nombSituEduc;
    }

    public void setNombSituEduc(String nombSituEduc) {
        this.nombSituEduc = nombSituEduc;
    }

    public String getAbrvSituEduc() {
        return abrvSituEduc;
    }

    public void setAbrvSituEduc(String abrvSituEduc) {
        this.abrvSituEduc = abrvSituEduc;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiSituEduc != null ? codiSituEduc.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaSituacioneducativa)) {
            return false;
        }
        PlanillaSituacioneducativa other = (PlanillaSituacioneducativa) object;
        if ((this.codiSituEduc == null && other.codiSituEduc != null) || (this.codiSituEduc != null && !this.codiSituEduc.equals(other.codiSituEduc))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaSituacioneducativa[ codiSituEduc=" + codiSituEduc + " ]";
    }
    
}
