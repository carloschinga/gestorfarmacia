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
@Table(name = "ventas_tipo_movimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasTipoMovimiento.findAll", query = "SELECT v FROM VentasTipoMovimiento v"),
    @NamedQuery(name = "VentasTipoMovimiento.findByCodiTipoMovi", query = "SELECT v FROM VentasTipoMovimiento v WHERE v.codiTipoMovi = :codiTipoMovi"),
    @NamedQuery(name = "VentasTipoMovimiento.findByNombTipoMovi", query = "SELECT v FROM VentasTipoMovimiento v WHERE v.nombTipoMovi = :nombTipoMovi"),
    @NamedQuery(name = "VentasTipoMovimiento.findBySignTipoMovi", query = "SELECT v FROM VentasTipoMovimiento v WHERE v.signTipoMovi = :signTipoMovi"),
    @NamedQuery(name = "VentasTipoMovimiento.findByActiTipoMovi", query = "SELECT v FROM VentasTipoMovimiento v WHERE v.actiTipoMovi = :actiTipoMovi")})
public class VentasTipoMovimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiTipoMovi")
    private Integer codiTipoMovi;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombTipoMovi")
    private String nombTipoMovi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "signTipoMovi")
    private boolean signTipoMovi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiTipoMovi")
    private boolean actiTipoMovi;

    public VentasTipoMovimiento() {
    }

    public VentasTipoMovimiento(Integer codiTipoMovi) {
        this.codiTipoMovi = codiTipoMovi;
    }

    public VentasTipoMovimiento(Integer codiTipoMovi, String nombTipoMovi, boolean signTipoMovi, boolean actiTipoMovi) {
        this.codiTipoMovi = codiTipoMovi;
        this.nombTipoMovi = nombTipoMovi;
        this.signTipoMovi = signTipoMovi;
        this.actiTipoMovi = actiTipoMovi;
    }

    public Integer getCodiTipoMovi() {
        return codiTipoMovi;
    }

    public void setCodiTipoMovi(Integer codiTipoMovi) {
        this.codiTipoMovi = codiTipoMovi;
    }

    public String getNombTipoMovi() {
        return nombTipoMovi;
    }

    public void setNombTipoMovi(String nombTipoMovi) {
        this.nombTipoMovi = nombTipoMovi;
    }

    public boolean getSignTipoMovi() {
        return signTipoMovi;
    }

    public void setSignTipoMovi(boolean signTipoMovi) {
        this.signTipoMovi = signTipoMovi;
    }

    public boolean getActiTipoMovi() {
        return actiTipoMovi;
    }

    public void setActiTipoMovi(boolean actiTipoMovi) {
        this.actiTipoMovi = actiTipoMovi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiTipoMovi != null ? codiTipoMovi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasTipoMovimiento)) {
            return false;
        }
        VentasTipoMovimiento other = (VentasTipoMovimiento) object;
        if ((this.codiTipoMovi == null && other.codiTipoMovi != null) || (this.codiTipoMovi != null && !this.codiTipoMovi.equals(other.codiTipoMovi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasTipoMovimiento[ codiTipoMovi=" + codiTipoMovi + " ]";
    }
    
}
