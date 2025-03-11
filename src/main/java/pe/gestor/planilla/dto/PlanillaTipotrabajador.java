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
@Table(name = "planilla_tipotrabajador")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaTipotrabajador.findAll", query = "SELECT p FROM PlanillaTipotrabajador p"),
    @NamedQuery(name = "PlanillaTipotrabajador.findByCodiTipoTrab", query = "SELECT p FROM PlanillaTipotrabajador p WHERE p.codiTipoTrab = :codiTipoTrab"),
    @NamedQuery(name = "PlanillaTipotrabajador.findByNombTipoTrab", query = "SELECT p FROM PlanillaTipotrabajador p WHERE p.nombTipoTrab = :nombTipoTrab"),
    @NamedQuery(name = "PlanillaTipotrabajador.findBySectPrivado", query = "SELECT p FROM PlanillaTipotrabajador p WHERE p.sectPrivado = :sectPrivado"),
    @NamedQuery(name = "PlanillaTipotrabajador.findBySectPublico", query = "SELECT p FROM PlanillaTipotrabajador p WHERE p.sectPublico = :sectPublico"),
    @NamedQuery(name = "PlanillaTipotrabajador.findBySectOthers", query = "SELECT p FROM PlanillaTipotrabajador p WHERE p.sectOthers = :sectOthers")})
public class PlanillaTipotrabajador implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codiTipoTrab")
    private String codiTipoTrab;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombTipoTrab")
    private String nombTipoTrab;
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

    public PlanillaTipotrabajador() {
    }

    public PlanillaTipotrabajador(String codiTipoTrab) {
        this.codiTipoTrab = codiTipoTrab;
    }

    public PlanillaTipotrabajador(String codiTipoTrab, String nombTipoTrab, boolean sectPrivado, boolean sectPublico, boolean sectOthers) {
        this.codiTipoTrab = codiTipoTrab;
        this.nombTipoTrab = nombTipoTrab;
        this.sectPrivado = sectPrivado;
        this.sectPublico = sectPublico;
        this.sectOthers = sectOthers;
    }

    public String getCodiTipoTrab() {
        return codiTipoTrab;
    }

    public void setCodiTipoTrab(String codiTipoTrab) {
        this.codiTipoTrab = codiTipoTrab;
    }

    public String getNombTipoTrab() {
        return nombTipoTrab;
    }

    public void setNombTipoTrab(String nombTipoTrab) {
        this.nombTipoTrab = nombTipoTrab;
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
        hash += (codiTipoTrab != null ? codiTipoTrab.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaTipotrabajador)) {
            return false;
        }
        PlanillaTipotrabajador other = (PlanillaTipotrabajador) object;
        if ((this.codiTipoTrab == null && other.codiTipoTrab != null) || (this.codiTipoTrab != null && !this.codiTipoTrab.equals(other.codiTipoTrab))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaTipotrabajador[ codiTipoTrab=" + codiTipoTrab + " ]";
    }
    
}
