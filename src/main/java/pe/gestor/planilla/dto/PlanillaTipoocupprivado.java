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
@Table(name = "planilla_tipoocupprivado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaTipoocupprivado.findAll", query = "SELECT p FROM PlanillaTipoocupprivado p"),
    @NamedQuery(name = "PlanillaTipoocupprivado.findByCodiTipoOcupPriv", query = "SELECT p FROM PlanillaTipoocupprivado p WHERE p.codiTipoOcupPriv = :codiTipoOcupPriv"),
    @NamedQuery(name = "PlanillaTipoocupprivado.findByNombTipoOcupPriv", query = "SELECT p FROM PlanillaTipoocupprivado p WHERE p.nombTipoOcupPriv = :nombTipoOcupPriv"),
    @NamedQuery(name = "PlanillaTipoocupprivado.findByEjecTipoOcupPriv", query = "SELECT p FROM PlanillaTipoocupprivado p WHERE p.ejecTipoOcupPriv = :ejecTipoOcupPriv"),
    @NamedQuery(name = "PlanillaTipoocupprivado.findByEmplTipoOcupPriv", query = "SELECT p FROM PlanillaTipoocupprivado p WHERE p.emplTipoOcupPriv = :emplTipoOcupPriv"),
    @NamedQuery(name = "PlanillaTipoocupprivado.findByObreTipoOcupPriv", query = "SELECT p FROM PlanillaTipoocupprivado p WHERE p.obreTipoOcupPriv = :obreTipoOcupPriv")})
public class PlanillaTipoocupprivado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 6)
    @Column(name = "codiTipoOcupPriv")
    private String codiTipoOcupPriv;
    @Size(max = 150)
    @Column(name = "nombTipoOcupPriv")
    private String nombTipoOcupPriv;
    @Column(name = "ejecTipoOcupPriv")
    private Character ejecTipoOcupPriv;
    @Column(name = "emplTipoOcupPriv")
    private Character emplTipoOcupPriv;
    @Column(name = "obreTipoOcupPriv")
    private Character obreTipoOcupPriv;

    public PlanillaTipoocupprivado() {
    }

    public PlanillaTipoocupprivado(String codiTipoOcupPriv) {
        this.codiTipoOcupPriv = codiTipoOcupPriv;
    }

    public String getCodiTipoOcupPriv() {
        return codiTipoOcupPriv;
    }

    public void setCodiTipoOcupPriv(String codiTipoOcupPriv) {
        this.codiTipoOcupPriv = codiTipoOcupPriv;
    }

    public String getNombTipoOcupPriv() {
        return nombTipoOcupPriv;
    }

    public void setNombTipoOcupPriv(String nombTipoOcupPriv) {
        this.nombTipoOcupPriv = nombTipoOcupPriv;
    }

    public Character getEjecTipoOcupPriv() {
        return ejecTipoOcupPriv;
    }

    public void setEjecTipoOcupPriv(Character ejecTipoOcupPriv) {
        this.ejecTipoOcupPriv = ejecTipoOcupPriv;
    }

    public Character getEmplTipoOcupPriv() {
        return emplTipoOcupPriv;
    }

    public void setEmplTipoOcupPriv(Character emplTipoOcupPriv) {
        this.emplTipoOcupPriv = emplTipoOcupPriv;
    }

    public Character getObreTipoOcupPriv() {
        return obreTipoOcupPriv;
    }

    public void setObreTipoOcupPriv(Character obreTipoOcupPriv) {
        this.obreTipoOcupPriv = obreTipoOcupPriv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiTipoOcupPriv != null ? codiTipoOcupPriv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaTipoocupprivado)) {
            return false;
        }
        PlanillaTipoocupprivado other = (PlanillaTipoocupprivado) object;
        if ((this.codiTipoOcupPriv == null && other.codiTipoOcupPriv != null) || (this.codiTipoOcupPriv != null && !this.codiTipoOcupPriv.equals(other.codiTipoOcupPriv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaTipoocupprivado[ codiTipoOcupPriv=" + codiTipoOcupPriv + " ]";
    }
    
}
