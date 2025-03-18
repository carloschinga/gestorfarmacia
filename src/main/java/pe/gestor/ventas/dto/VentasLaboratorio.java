/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.ventas.dto;

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
@Table(name = "ventas_laboratorio")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasLaboratorio.findAll", query = "SELECT v FROM VentasLaboratorio v"),
    @NamedQuery(name = "VentasLaboratorio.findByCodiLabo", query = "SELECT v FROM VentasLaboratorio v WHERE v.codiLabo = :codiLabo"),
    @NamedQuery(name = "VentasLaboratorio.findByNombLabo", query = "SELECT v FROM VentasLaboratorio v WHERE v.nombLabo = :nombLabo"),
    @NamedQuery(name = "VentasLaboratorio.findByDescLabo", query = "SELECT v FROM VentasLaboratorio v WHERE v.descLabo = :descLabo"),
    @NamedQuery(name = "VentasLaboratorio.findByOpciLabo", query = "SELECT v FROM VentasLaboratorio v WHERE v.opciLabo = :opciLabo")})
public class VentasLaboratorio implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiLabo")
    private Integer codiLabo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombLabo")
    private String nombLabo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descLabo")
    private String descLabo;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "opciLabo")
    private String opciLabo;

    public VentasLaboratorio() {
    }

    public VentasLaboratorio(Integer codiLabo) {
        this.codiLabo = codiLabo;
    }

    public VentasLaboratorio(Integer codiLabo, String nombLabo, String descLabo, String opciLabo) {
        this.codiLabo = codiLabo;
        this.nombLabo = nombLabo;
        this.descLabo = descLabo;
        this.opciLabo = opciLabo;
    }

    public Integer getCodiLabo() {
        return codiLabo;
    }

    public void setCodiLabo(Integer codiLabo) {
        this.codiLabo = codiLabo;
    }

    public String getNombLabo() {
        return nombLabo;
    }

    public void setNombLabo(String nombLabo) {
        this.nombLabo = nombLabo;
    }

    public String getDescLabo() {
        return descLabo;
    }

    public void setDescLabo(String descLabo) {
        this.descLabo = descLabo;
    }

    public String getOpciLabo() {
        return opciLabo;
    }

    public void setOpciLabo(String opciLabo) {
        this.opciLabo = opciLabo;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiLabo != null ? codiLabo.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasLaboratorio)) {
            return false;
        }
        VentasLaboratorio other = (VentasLaboratorio) object;
        if ((this.codiLabo == null && other.codiLabo != null) || (this.codiLabo != null && !this.codiLabo.equals(other.codiLabo))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasLaboratorio[ codiLabo=" + codiLabo + " ]";
    }
    
}
