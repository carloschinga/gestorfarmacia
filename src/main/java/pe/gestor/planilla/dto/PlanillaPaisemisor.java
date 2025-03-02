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
@Table(name = "planilla_paisemisor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaPaisemisor.findAll", query = "SELECT p FROM PlanillaPaisemisor p"),
    @NamedQuery(name = "PlanillaPaisemisor.findByCodiPaisEmis", query = "SELECT p FROM PlanillaPaisemisor p WHERE p.codiPaisEmis = :codiPaisEmis"),
    @NamedQuery(name = "PlanillaPaisemisor.findByNombPaisEmis", query = "SELECT p FROM PlanillaPaisemisor p WHERE p.nombPaisEmis = :nombPaisEmis")})
public class PlanillaPaisemisor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 3)
    @Column(name = "codiPaisEmis")
    private String codiPaisEmis;
    @Size(max = 50)
    @Column(name = "nombPaisEmis")
    private String nombPaisEmis;

    public PlanillaPaisemisor() {
    }

    public PlanillaPaisemisor(String codiPaisEmis) {
        this.codiPaisEmis = codiPaisEmis;
    }

    public String getCodiPaisEmis() {
        return codiPaisEmis;
    }

    public void setCodiPaisEmis(String codiPaisEmis) {
        this.codiPaisEmis = codiPaisEmis;
    }

    public String getNombPaisEmis() {
        return nombPaisEmis;
    }

    public void setNombPaisEmis(String nombPaisEmis) {
        this.nombPaisEmis = nombPaisEmis;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiPaisEmis != null ? codiPaisEmis.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaPaisemisor)) {
            return false;
        }
        PlanillaPaisemisor other = (PlanillaPaisemisor) object;
        if ((this.codiPaisEmis == null && other.codiPaisEmis != null) || (this.codiPaisEmis != null && !this.codiPaisEmis.equals(other.codiPaisEmis))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaPaisemisor[ codiPaisEmis=" + codiPaisEmis + " ]";
    }
    
}
