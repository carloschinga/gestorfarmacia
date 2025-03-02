/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 * @author USER
 */
@Entity
@Table(name = "planilla_nacionalidad")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaNacionalidad.findAll", query = "SELECT p FROM PlanillaNacionalidad p"),
    @NamedQuery(name = "PlanillaNacionalidad.findByCodiNaci", query = "SELECT p FROM PlanillaNacionalidad p WHERE p.codiNaci = :codiNaci"),
    @NamedQuery(name = "PlanillaNacionalidad.findByNombNaci", query = "SELECT p FROM PlanillaNacionalidad p WHERE p.nombNaci = :nombNaci")})
public class PlanillaNacionalidad implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "codiNaci")
    private String codiNaci;
    @Size(max = 50)
    @Column(name = "nombNaci")
    private String nombNaci;

    public PlanillaNacionalidad() {
    }

    public PlanillaNacionalidad(String codiNaci) {
        this.codiNaci = codiNaci;
    }

    public String getCodiNaci() {
        return codiNaci;
    }

    public void setCodiNaci(String codiNaci) {
        this.codiNaci = codiNaci;
    }

    public String getNombNaci() {
        return nombNaci;
    }

    public void setNombNaci(String nombNaci) {
        this.nombNaci = nombNaci;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiNaci != null ? codiNaci.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaNacionalidad)) {
            return false;
        }
        PlanillaNacionalidad other = (PlanillaNacionalidad) object;
        if ((this.codiNaci == null && other.codiNaci != null) || (this.codiNaci != null && !this.codiNaci.equals(other.codiNaci))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaNacionalidad[ codiNaci=" + codiNaci + " ]";
    }
    
}
