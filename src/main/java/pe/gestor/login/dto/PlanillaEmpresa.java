/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dto;

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

/**
 *
 * @author san21
 */
@Entity
@Table(name = "planilla_empresa")
@NamedQueries({
    @NamedQuery(name = "PlanillaEmpresa.findAll", query = "SELECT p FROM PlanillaEmpresa p"),
    @NamedQuery(name = "PlanillaEmpresa.findByCodiEmpr", query = "SELECT p FROM PlanillaEmpresa p WHERE p.codiEmpr = :codiEmpr"),
    @NamedQuery(name = "PlanillaEmpresa.findByNrucEmpr", query = "SELECT p FROM PlanillaEmpresa p WHERE p.nrucEmpr = :nrucEmpr"),
    @NamedQuery(name = "PlanillaEmpresa.findByNombEmpr", query = "SELECT p FROM PlanillaEmpresa p WHERE p.nombEmpr = :nombEmpr"),
    @NamedQuery(name = "PlanillaEmpresa.findByDefaEmpr", query = "SELECT p FROM PlanillaEmpresa p WHERE p.defaEmpr = :defaEmpr")})
public class PlanillaEmpresa implements Serializable {

private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiEmpr")
    private Integer codiEmpr;
    @Size(max = 11)
    @Column(name = "nrucEmpr")
    private String nrucEmpr;
    @Size(max = 200)
    @Column(name = "nombEmpr")
    private String nombEmpr;
    @Column(name = "defaEmpr")
    private Boolean defaEmpr;

    public PlanillaEmpresa() {
    }

    public PlanillaEmpresa(Integer codiEmpr) {
        this.codiEmpr = codiEmpr;
    }

    public Integer getCodiEmpr() {
        return codiEmpr;
    }

    public void setCodiEmpr(Integer codiEmpr) {
        this.codiEmpr = codiEmpr;
    }

    public String getNrucEmpr() {
        return nrucEmpr;
    }

    public void setNrucEmpr(String nrucEmpr) {
        this.nrucEmpr = nrucEmpr;
    }

    public String getNombEmpr() {
        return nombEmpr;
    }

    public void setNombEmpr(String nombEmpr) {
        this.nombEmpr = nombEmpr;
    }

    public Boolean getDefaEmpr() {
        return defaEmpr;
    }

    public void setDefaEmpr(Boolean defaEmpr) {
        this.defaEmpr = defaEmpr;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiEmpr != null ? codiEmpr.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaEmpresa)) {
            return false;
        }
        PlanillaEmpresa other = (PlanillaEmpresa) object;
        if ((this.codiEmpr == null && other.codiEmpr != null) || (this.codiEmpr != null && !this.codiEmpr.equals(other.codiEmpr))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.PlanillaEmpresa[ codiEmpr=" + codiEmpr + " ]";
    }
    
}   