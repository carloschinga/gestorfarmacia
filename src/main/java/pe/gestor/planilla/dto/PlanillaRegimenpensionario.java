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
@Table(name = "planilla_regimenpensionario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaRegimenpensionario.findAll", query = "SELECT p FROM PlanillaRegimenpensionario p"),
    @NamedQuery(name = "PlanillaRegimenpensionario.findByCodiRegiPensi", query = "SELECT p FROM PlanillaRegimenpensionario p WHERE p.codiRegiPensi = :codiRegiPensi"),
    @NamedQuery(name = "PlanillaRegimenpensionario.findByNombRegiPensi", query = "SELECT p FROM PlanillaRegimenpensionario p WHERE p.nombRegiPensi = :nombRegiPensi"),
    @NamedQuery(name = "PlanillaRegimenpensionario.findBySectPrivado", query = "SELECT p FROM PlanillaRegimenpensionario p WHERE p.sectPrivado = :sectPrivado"),
    @NamedQuery(name = "PlanillaRegimenpensionario.findBySectPublico", query = "SELECT p FROM PlanillaRegimenpensionario p WHERE p.sectPublico = :sectPublico"),
    @NamedQuery(name = "PlanillaRegimenpensionario.findBySectOthers", query = "SELECT p FROM PlanillaRegimenpensionario p WHERE p.sectOthers = :sectOthers")})
public class PlanillaRegimenpensionario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codiRegiPensi")
    private String codiRegiPensi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombRegiPensi")
    private String nombRegiPensi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sectPrivado")
    private boolean sectPrivado;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sectPublico")
    private boolean sectPublico;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sectOthers")
    private boolean sectOthers;

    public PlanillaRegimenpensionario() {
    }

    public PlanillaRegimenpensionario(String codiRegiPensi) {
        this.codiRegiPensi = codiRegiPensi;
    }

    public PlanillaRegimenpensionario(String codiRegiPensi, String nombRegiPensi, boolean sectPrivado, boolean sectPublico, boolean sectOthers) {
        this.codiRegiPensi = codiRegiPensi;
        this.nombRegiPensi = nombRegiPensi;
        this.sectPrivado = sectPrivado;
        this.sectPublico = sectPublico;
        this.sectOthers = sectOthers;
    }

    public String getCodiRegiPensi() {
        return codiRegiPensi;
    }

    public void setCodiRegiPensi(String codiRegiPensi) {
        this.codiRegiPensi = codiRegiPensi;
    }

    public String getNombRegiPensi() {
        return nombRegiPensi;
    }

    public void setNombRegiPensi(String nombRegiPensi) {
        this.nombRegiPensi = nombRegiPensi;
    }

    public boolean getSectPrivado() {
        return sectPrivado;
    }

    public void setSectPrivado(boolean sectPrivado) {
        this.sectPrivado = sectPrivado;
    }

    public boolean getSectPublico() {
        return sectPublico;
    }

    public void setSectPublico(boolean sectPublico) {
        this.sectPublico = sectPublico;
    }

    public boolean getSectOthers() {
        return sectOthers;
    }

    public void setSectOthers(boolean sectOthers) {
        this.sectOthers = sectOthers;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiRegiPensi != null ? codiRegiPensi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaRegimenpensionario)) {
            return false;
        }
        PlanillaRegimenpensionario other = (PlanillaRegimenpensionario) object;
        if ((this.codiRegiPensi == null && other.codiRegiPensi != null) || (this.codiRegiPensi != null && !this.codiRegiPensi.equals(other.codiRegiPensi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaRegimenpensionario[ codiRegiPensi=" + codiRegiPensi + " ]";
    }
    
}
