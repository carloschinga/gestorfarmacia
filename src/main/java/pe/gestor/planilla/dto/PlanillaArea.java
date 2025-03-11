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
@Table(name = "planilla_area")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaArea.findAll", query = "SELECT p FROM PlanillaArea p"),
    @NamedQuery(name = "PlanillaArea.findByCodiArea", query = "SELECT p FROM PlanillaArea p WHERE p.codiArea = :codiArea"),
    @NamedQuery(name = "PlanillaArea.findByNombArea", query = "SELECT p FROM PlanillaArea p WHERE p.nombArea = :nombArea")})
public class PlanillaArea implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiArea")
    private Integer codiArea;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombArea")
    private String nombArea;

    public PlanillaArea() {
    }

    public PlanillaArea(Integer codiArea) {
        this.codiArea = codiArea;
    }

    public PlanillaArea(Integer codiArea, String nombArea) {
        this.codiArea = codiArea;
        this.nombArea = nombArea;
    }

    public Integer getCodiArea() {
        return codiArea;
    }

    public void setCodiArea(Integer codiArea) {
        this.codiArea = codiArea;
    }

    public String getNombArea() {
        return nombArea;
    }

    public void setNombArea(String nombArea) {
        this.nombArea = nombArea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiArea != null ? codiArea.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaArea)) {
            return false;
        }
        PlanillaArea other = (PlanillaArea) object;
        if ((this.codiArea == null && other.codiArea != null) || (this.codiArea != null && !this.codiArea.equals(other.codiArea))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaArea[ codiArea=" + codiArea + " ]";
    }
    
}
