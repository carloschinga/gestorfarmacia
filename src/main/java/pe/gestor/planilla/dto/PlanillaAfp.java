/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.planilla.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "planilla_afp")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaAfp.findAll", query = "SELECT p FROM PlanillaAfp p"),
    @NamedQuery(name = "PlanillaAfp.findByCodiAFP", query = "SELECT p FROM PlanillaAfp p WHERE p.codiAFP = :codiAFP"),
    @NamedQuery(name = "PlanillaAfp.findByNombAFP", query = "SELECT p FROM PlanillaAfp p WHERE p.nombAFP = :nombAFP"),
    @NamedQuery(name = "PlanillaAfp.findByMontAFP", query = "SELECT p FROM PlanillaAfp p WHERE p.montAFP = :montAFP"),
    @NamedQuery(name = "PlanillaAfp.findBySeguAFP", query = "SELECT p FROM PlanillaAfp p WHERE p.seguAFP = :seguAFP"),
    @NamedQuery(name = "PlanillaAfp.findByComiAFP", query = "SELECT p FROM PlanillaAfp p WHERE p.comiAFP = :comiAFP")})
public class PlanillaAfp implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiAFP")
    private Integer codiAFP;
    @Size(max = 150)
    @Column(name = "nombAFP")
    private String nombAFP;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montAFP")
    private BigDecimal montAFP;
    @Column(name = "seguAFP")
    private BigDecimal seguAFP;
    @Column(name = "comiAFP")
    private BigDecimal comiAFP;

    public PlanillaAfp() {
    }

    public PlanillaAfp(Integer codiAFP) {
        this.codiAFP = codiAFP;
    }

    public Integer getCodiAFP() {
        return codiAFP;
    }

    public void setCodiAFP(Integer codiAFP) {
        this.codiAFP = codiAFP;
    }

    public String getNombAFP() {
        return nombAFP;
    }

    public void setNombAFP(String nombAFP) {
        this.nombAFP = nombAFP;
    }

    public BigDecimal getMontAFP() {
        return montAFP;
    }

    public void setMontAFP(BigDecimal montAFP) {
        this.montAFP = montAFP;
    }

    public BigDecimal getSeguAFP() {
        return seguAFP;
    }

    public void setSeguAFP(BigDecimal seguAFP) {
        this.seguAFP = seguAFP;
    }

    public BigDecimal getComiAFP() {
        return comiAFP;
    }

    public void setComiAFP(BigDecimal comiAFP) {
        this.comiAFP = comiAFP;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiAFP != null ? codiAFP.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaAfp)) {
            return false;
        }
        PlanillaAfp other = (PlanillaAfp) object;
        if ((this.codiAFP == null && other.codiAFP != null) || (this.codiAFP != null && !this.codiAFP.equals(other.codiAFP))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaAfp[ codiAFP=" + codiAFP + " ]";
    }
    
}
