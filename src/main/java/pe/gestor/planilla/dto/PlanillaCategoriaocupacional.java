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
@Table(name = "planilla_categoriaocupacional")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaCategoriaocupacional.findAll", query = "SELECT p FROM PlanillaCategoriaocupacional p"),
    @NamedQuery(name = "PlanillaCategoriaocupacional.findByCodiCateOcup", query = "SELECT p FROM PlanillaCategoriaocupacional p WHERE p.codiCateOcup = :codiCateOcup"),
    @NamedQuery(name = "PlanillaCategoriaocupacional.findByNombCateOcup", query = "SELECT p FROM PlanillaCategoriaocupacional p WHERE p.nombCateOcup = :nombCateOcup"),
    @NamedQuery(name = "PlanillaCategoriaocupacional.findBySectorPrivado", query = "SELECT p FROM PlanillaCategoriaocupacional p WHERE p.sectorPrivado = :sectorPrivado"),
    @NamedQuery(name = "PlanillaCategoriaocupacional.findBySectorPublico", query = "SELECT p FROM PlanillaCategoriaocupacional p WHERE p.sectorPublico = :sectorPublico"),
    @NamedQuery(name = "PlanillaCategoriaocupacional.findByOtras", query = "SELECT p FROM PlanillaCategoriaocupacional p WHERE p.otras = :otras")})
public class PlanillaCategoriaocupacional implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codiCateOcup")
    private String codiCateOcup;
    @Size(max = 50)
    @Column(name = "nombCateOcup")
    private String nombCateOcup;
    @Size(max = 4)
    @Column(name = "sectorPrivado")
    private String sectorPrivado;
    @Size(max = 4)
    @Column(name = "sectorPublico")
    private String sectorPublico;
    @Size(max = 4)
    @Column(name = "otras")
    private String otras;

    public PlanillaCategoriaocupacional() {
    }

    public PlanillaCategoriaocupacional(String codiCateOcup) {
        this.codiCateOcup = codiCateOcup;
    }

    public String getCodiCateOcup() {
        return codiCateOcup;
    }

    public void setCodiCateOcup(String codiCateOcup) {
        this.codiCateOcup = codiCateOcup;
    }

    public String getNombCateOcup() {
        return nombCateOcup;
    }

    public void setNombCateOcup(String nombCateOcup) {
        this.nombCateOcup = nombCateOcup;
    }

    public String getSectorPrivado() {
        return sectorPrivado;
    }

    public void setSectorPrivado(String sectorPrivado) {
        this.sectorPrivado = sectorPrivado;
    }

    public String getSectorPublico() {
        return sectorPublico;
    }

    public void setSectorPublico(String sectorPublico) {
        this.sectorPublico = sectorPublico;
    }

    public String getOtras() {
        return otras;
    }

    public void setOtras(String otras) {
        this.otras = otras;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiCateOcup != null ? codiCateOcup.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaCategoriaocupacional)) {
            return false;
        }
        PlanillaCategoriaocupacional other = (PlanillaCategoriaocupacional) object;
        if ((this.codiCateOcup == null && other.codiCateOcup != null) || (this.codiCateOcup != null && !this.codiCateOcup.equals(other.codiCateOcup))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaCategoriaocupacional[ codiCateOcup=" + codiCateOcup + " ]";
    }
    
}
