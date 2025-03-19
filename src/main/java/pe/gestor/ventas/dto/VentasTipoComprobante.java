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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
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
@Table(name = "ventas_tipo_comprobante")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasTipoComprobante.findAll", query = "SELECT v FROM VentasTipoComprobante v"),
    @NamedQuery(name = "VentasTipoComprobante.findByCodiTipoComp", query = "SELECT v FROM VentasTipoComprobante v WHERE v.codiTipoComp = :codiTipoComp"),
    @NamedQuery(name = "VentasTipoComprobante.findByDescTipoComp", query = "SELECT v FROM VentasTipoComprobante v WHERE v.descTipoComp = :descTipoComp"),
    @NamedQuery(name = "VentasTipoComprobante.findByActiTipoComp", query = "SELECT v FROM VentasTipoComprobante v WHERE v.actiTipoComp = :actiTipoComp")})
public class VentasTipoComprobante implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiTipoComp")
    private Integer codiTipoComp;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "descTipoComp")
    private String descTipoComp;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiTipoComp")
    private boolean actiTipoComp;

    public VentasTipoComprobante() {
    }

    public VentasTipoComprobante(Integer codiTipoComp) {
        this.codiTipoComp = codiTipoComp;
    }

    public VentasTipoComprobante(Integer codiTipoComp, String descTipoComp, boolean actiTipoComp) {
        this.codiTipoComp = codiTipoComp;
        this.descTipoComp = descTipoComp;
        this.actiTipoComp = actiTipoComp;
    }

    public Integer getCodiTipoComp() {
        return codiTipoComp;
    }

    public void setCodiTipoComp(Integer codiTipoComp) {
        this.codiTipoComp = codiTipoComp;
    }

    public String getDescTipoComp() {
        return descTipoComp;
    }

    public void setDescTipoComp(String descTipoComp) {
        this.descTipoComp = descTipoComp;
    }

    public boolean getActiTipoComp() {
        return actiTipoComp;
    }

    public void setActiTipoComp(boolean actiTipoComp) {
        this.actiTipoComp = actiTipoComp;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiTipoComp != null ? codiTipoComp.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasTipoComprobante)) {
            return false;
        }
        VentasTipoComprobante other = (VentasTipoComprobante) object;
        if ((this.codiTipoComp == null && other.codiTipoComp != null) || (this.codiTipoComp != null && !this.codiTipoComp.equals(other.codiTipoComp))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasTipoComprobante[ codiTipoComp=" + codiTipoComp + " ]";
    }
    
}
