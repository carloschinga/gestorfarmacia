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
@Table(name = "planilla_regimensalud")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaRegimensalud.findAll", query = "SELECT p FROM PlanillaRegimensalud p"),
    @NamedQuery(name = "PlanillaRegimensalud.findByCodiRegiSal", query = "SELECT p FROM PlanillaRegimensalud p WHERE p.codiRegiSal = :codiRegiSal"),
    @NamedQuery(name = "PlanillaRegimensalud.findByNombRegiSal", query = "SELECT p FROM PlanillaRegimensalud p WHERE p.nombRegiSal = :nombRegiSal")})
public class PlanillaRegimensalud implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codiRegiSal")
    private String codiRegiSal;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombRegiSal")
    private String nombRegiSal;

    public PlanillaRegimensalud() {
    }

    public PlanillaRegimensalud(String codiRegiSal) {
        this.codiRegiSal = codiRegiSal;
    }

    public PlanillaRegimensalud(String codiRegiSal, String nombRegiSal) {
        this.codiRegiSal = codiRegiSal;
        this.nombRegiSal = nombRegiSal;
    }

    public String getCodiRegiSal() {
        return codiRegiSal;
    }

    public void setCodiRegiSal(String codiRegiSal) {
        this.codiRegiSal = codiRegiSal;
    }

    public String getNombRegiSal() {
        return nombRegiSal;
    }

    public void setNombRegiSal(String nombRegiSal) {
        this.nombRegiSal = nombRegiSal;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiRegiSal != null ? codiRegiSal.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaRegimensalud)) {
            return false;
        }
        PlanillaRegimensalud other = (PlanillaRegimensalud) object;
        if ((this.codiRegiSal == null && other.codiRegiSal != null) || (this.codiRegiSal != null && !this.codiRegiSal.equals(other.codiRegiSal))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaRegimensalud[ codiRegiSal=" + codiRegiSal + " ]";
    }
    
}
