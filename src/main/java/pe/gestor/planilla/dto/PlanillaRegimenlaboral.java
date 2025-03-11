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
@Table(name = "planilla_regimenlaboral")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaRegimenlaboral.findAll", query = "SELECT p FROM PlanillaRegimenlaboral p"),
    @NamedQuery(name = "PlanillaRegimenlaboral.findByCodiRegiLabo", query = "SELECT p FROM PlanillaRegimenlaboral p WHERE p.codiRegiLabo = :codiRegiLabo"),
    @NamedQuery(name = "PlanillaRegimenlaboral.findByNombRegiLabo", query = "SELECT p FROM PlanillaRegimenlaboral p WHERE p.nombRegiLabo = :nombRegiLabo"),
    @NamedQuery(name = "PlanillaRegimenlaboral.findByResuRegiLabo", query = "SELECT p FROM PlanillaRegimenlaboral p WHERE p.resuRegiLabo = :resuRegiLabo"),
    @NamedQuery(name = "PlanillaRegimenlaboral.findBySectPriv", query = "SELECT p FROM PlanillaRegimenlaboral p WHERE p.sectPriv = :sectPriv"),
    @NamedQuery(name = "PlanillaRegimenlaboral.findBySectPubl", query = "SELECT p FROM PlanillaRegimenlaboral p WHERE p.sectPubl = :sectPubl"),
    @NamedQuery(name = "PlanillaRegimenlaboral.findByOtraEnti", query = "SELECT p FROM PlanillaRegimenlaboral p WHERE p.otraEnti = :otraEnti")})
public class PlanillaRegimenlaboral implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codiRegiLabo")
    private String codiRegiLabo;
    @Size(max = 50)
    @Column(name = "nombRegiLabo")
    private String nombRegiLabo;
    @Size(max = 20)
    @Column(name = "resuRegiLabo")
    private String resuRegiLabo;
    @Size(max = 4)
    @Column(name = "sectPriv")
    private String sectPriv;
    @Size(max = 4)
    @Column(name = "sectPubl")
    private String sectPubl;
    @Size(max = 4)
    @Column(name = "otraEnti")
    private String otraEnti;

    public PlanillaRegimenlaboral() {
    }

    public PlanillaRegimenlaboral(String codiRegiLabo) {
        this.codiRegiLabo = codiRegiLabo;
    }

    public String getCodiRegiLabo() {
        return codiRegiLabo;
    }

    public void setCodiRegiLabo(String codiRegiLabo) {
        this.codiRegiLabo = codiRegiLabo;
    }

    public String getNombRegiLabo() {
        return nombRegiLabo;
    }

    public void setNombRegiLabo(String nombRegiLabo) {
        this.nombRegiLabo = nombRegiLabo;
    }

    public String getResuRegiLabo() {
        return resuRegiLabo;
    }

    public void setResuRegiLabo(String resuRegiLabo) {
        this.resuRegiLabo = resuRegiLabo;
    }

    public String getSectPriv() {
        return sectPriv;
    }

    public void setSectPriv(String sectPriv) {
        this.sectPriv = sectPriv;
    }

    public String getSectPubl() {
        return sectPubl;
    }

    public void setSectPubl(String sectPubl) {
        this.sectPubl = sectPubl;
    }

    public String getOtraEnti() {
        return otraEnti;
    }

    public void setOtraEnti(String otraEnti) {
        this.otraEnti = otraEnti;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiRegiLabo != null ? codiRegiLabo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaRegimenlaboral)) {
            return false;
        }
        PlanillaRegimenlaboral other = (PlanillaRegimenlaboral) object;
        if ((this.codiRegiLabo == null && other.codiRegiLabo != null) || (this.codiRegiLabo != null && !this.codiRegiLabo.equals(other.codiRegiLabo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaRegimenlaboral[ codiRegiLabo=" + codiRegiLabo + " ]";
    }
    
}
