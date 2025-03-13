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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "planilla_dia")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaDia.findAll", query = "SELECT p FROM PlanillaDia p"),
    @NamedQuery(name = "PlanillaDia.findByCodiDia", query = "SELECT p FROM PlanillaDia p WHERE p.codiDia = :codiDia"),
    @NamedQuery(name = "PlanillaDia.findByNombDia", query = "SELECT p FROM PlanillaDia p WHERE p.nombDia = :nombDia")})
public class PlanillaDia implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiDia")
    private Integer codiDia;
    @Size(max = 50)
    @Column(name = "nombDia")
    private String nombDia;

    public PlanillaDia() {
    }

    public PlanillaDia(Integer codiDia) {
        this.codiDia = codiDia;
    }

    public Integer getCodiDia() {
        return codiDia;
    }

    public void setCodiDia(Integer codiDia) {
        this.codiDia = codiDia;
    }

    public String getNombDia() {
        return nombDia;
    }

    public void setNombDia(String nombDia) {
        this.nombDia = nombDia;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiDia != null ? codiDia.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaDia)) {
            return false;
        }
        PlanillaDia other = (PlanillaDia) object;
        if ((this.codiDia == null && other.codiDia != null) || (this.codiDia != null && !this.codiDia.equals(other.codiDia))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaDia[ codiDia=" + codiDia + " ]";
    }
    
}
