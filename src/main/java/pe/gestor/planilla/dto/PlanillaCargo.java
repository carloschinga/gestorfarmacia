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
@Table(name = "planilla_cargo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaCargo.findAll", query = "SELECT p FROM PlanillaCargo p"),
    @NamedQuery(name = "PlanillaCargo.findByCodiCargo", query = "SELECT p FROM PlanillaCargo p WHERE p.codiCargo = :codiCargo"),
    @NamedQuery(name = "PlanillaCargo.findByNombCargo", query = "SELECT p FROM PlanillaCargo p WHERE p.nombCargo = :nombCargo")})
public class PlanillaCargo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiCargo")
    private Integer codiCargo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombCargo")
    private String nombCargo;

    public PlanillaCargo() {
    }

    public PlanillaCargo(Integer codiCargo) {
        this.codiCargo = codiCargo;
    }

    public PlanillaCargo(Integer codiCargo, String nombCargo) {
        this.codiCargo = codiCargo;
        this.nombCargo = nombCargo;
    }

    public Integer getCodiCargo() {
        return codiCargo;
    }

    public void setCodiCargo(Integer codiCargo) {
        this.codiCargo = codiCargo;
    }

    public String getNombCargo() {
        return nombCargo;
    }

    public void setNombCargo(String nombCargo) {
        this.nombCargo = nombCargo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiCargo != null ? codiCargo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaCargo)) {
            return false;
        }
        PlanillaCargo other = (PlanillaCargo) object;
        if ((this.codiCargo == null && other.codiCargo != null) || (this.codiCargo != null && !this.codiCargo.equals(other.codiCargo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaCargo[ codiCargo=" + codiCargo + " ]";
    }
    
}
