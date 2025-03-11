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
@Table(name = "planilla_sucursal")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaSucursal.findAll", query = "SELECT p FROM PlanillaSucursal p"),
    @NamedQuery(name = "PlanillaSucursal.findByCodiSucurs", query = "SELECT p FROM PlanillaSucursal p WHERE p.codiSucurs = :codiSucurs"),
    @NamedQuery(name = "PlanillaSucursal.findByNombSucurs", query = "SELECT p FROM PlanillaSucursal p WHERE p.nombSucurs = :nombSucurs")})
public class PlanillaSucursal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiSucurs")
    private Integer codiSucurs;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombSucurs")
    private String nombSucurs;

    public PlanillaSucursal() {
    }

    public PlanillaSucursal(Integer codiSucurs) {
        this.codiSucurs = codiSucurs;
    }

    public PlanillaSucursal(Integer codiSucurs, String nombSucurs) {
        this.codiSucurs = codiSucurs;
        this.nombSucurs = nombSucurs;
    }

    public Integer getCodiSucurs() {
        return codiSucurs;
    }

    public void setCodiSucurs(Integer codiSucurs) {
        this.codiSucurs = codiSucurs;
    }

    public String getNombSucurs() {
        return nombSucurs;
    }

    public void setNombSucurs(String nombSucurs) {
        this.nombSucurs = nombSucurs;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiSucurs != null ? codiSucurs.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaSucursal)) {
            return false;
        }
        PlanillaSucursal other = (PlanillaSucursal) object;
        if ((this.codiSucurs == null && other.codiSucurs != null) || (this.codiSucurs != null && !this.codiSucurs.equals(other.codiSucurs))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaSucursal[ codiSucurs=" + codiSucurs + " ]";
    }
    
}
