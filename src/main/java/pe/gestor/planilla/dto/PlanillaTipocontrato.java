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
@Table(name = "planilla_tipocontrato")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaTipocontrato.findAll", query = "SELECT p FROM PlanillaTipocontrato p"),
    @NamedQuery(name = "PlanillaTipocontrato.findByCodiTipoCntr", query = "SELECT p FROM PlanillaTipocontrato p WHERE p.codiTipoCntr = :codiTipoCntr"),
    @NamedQuery(name = "PlanillaTipocontrato.findByNombTipoCntr", query = "SELECT p FROM PlanillaTipocontrato p WHERE p.nombTipoCntr = :nombTipoCntr"),
    @NamedQuery(name = "PlanillaTipocontrato.findByAbrvTipoCntr", query = "SELECT p FROM PlanillaTipocontrato p WHERE p.abrvTipoCntr = :abrvTipoCntr")})
public class PlanillaTipocontrato implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codiTipoCntr")
    private String codiTipoCntr;
    @Size(max = 200)
    @Column(name = "nombTipoCntr")
    private String nombTipoCntr;
    @Size(max = 50)
    @Column(name = "abrvTipoCntr")
    private String abrvTipoCntr;

    public PlanillaTipocontrato() {
    }

    public PlanillaTipocontrato(String codiTipoCntr) {
        this.codiTipoCntr = codiTipoCntr;
    }

    public String getCodiTipoCntr() {
        return codiTipoCntr;
    }

    public void setCodiTipoCntr(String codiTipoCntr) {
        this.codiTipoCntr = codiTipoCntr;
    }

    public String getNombTipoCntr() {
        return nombTipoCntr;
    }

    public void setNombTipoCntr(String nombTipoCntr) {
        this.nombTipoCntr = nombTipoCntr;
    }

    public String getAbrvTipoCntr() {
        return abrvTipoCntr;
    }

    public void setAbrvTipoCntr(String abrvTipoCntr) {
        this.abrvTipoCntr = abrvTipoCntr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiTipoCntr != null ? codiTipoCntr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaTipocontrato)) {
            return false;
        }
        PlanillaTipocontrato other = (PlanillaTipocontrato) object;
        if ((this.codiTipoCntr == null && other.codiTipoCntr != null) || (this.codiTipoCntr != null && !this.codiTipoCntr.equals(other.codiTipoCntr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaTipocontrato[ codiTipoCntr=" + codiTipoCntr + " ]";
    }
    
}
