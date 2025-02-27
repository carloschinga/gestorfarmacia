/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 * @author USER
 */
@Entity
@Table(name = "planilla_plantilla")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaPlantilla.findAll", query = "SELECT p FROM PlanillaPlantilla p"),
    @NamedQuery(name = "PlanillaPlantilla.findByCodiPlant", query = "SELECT p FROM PlanillaPlantilla p WHERE p.codiPlant = :codiPlant"),
    @NamedQuery(name = "PlanillaPlantilla.findByNombPlant", query = "SELECT p FROM PlanillaPlantilla p WHERE p.nombPlant = :nombPlant"),
    @NamedQuery(name = "PlanillaPlantilla.findByActvPlant", query = "SELECT p FROM PlanillaPlantilla p WHERE p.actvPlant = :actvPlant")})
public class PlanillaPlantilla implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiPlant")
    private Integer codiPlant;
    @Size(max = 50)
    @Column(name = "nombPlant")
    private String nombPlant;
    @Column(name = "actvPlant")
    private Boolean actvPlant;

    public PlanillaPlantilla() {
    }

    public PlanillaPlantilla(Integer codiPlant) {
        this.codiPlant = codiPlant;
    }

    public Integer getCodiPlant() {
        return codiPlant;
    }

    public void setCodiPlant(Integer codiPlant) {
        this.codiPlant = codiPlant;
    }

    public String getNombPlant() {
        return nombPlant;
    }

    public void setNombPlant(String nombPlant) {
        this.nombPlant = nombPlant;
    }

    public Boolean getActvPlant() {
        return actvPlant;
    }

    public void setActvPlant(Boolean actvPlant) {
        this.actvPlant = actvPlant;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiPlant != null ? codiPlant.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaPlantilla)) {
            return false;
        }
        PlanillaPlantilla other = (PlanillaPlantilla) object;
        if ((this.codiPlant == null && other.codiPlant != null) || (this.codiPlant != null && !this.codiPlant.equals(other.codiPlant))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaPlantilla[ codiPlant=" + codiPlant + " ]";
    }
    
}
