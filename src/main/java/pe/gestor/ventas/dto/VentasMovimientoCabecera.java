/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.ventas.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "ventas_movimiento_cabecera")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasMovimientoCabecera.findAll", query = "SELECT v FROM VentasMovimientoCabecera v"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByCodiMoviCabe", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.codiMoviCabe = :codiMoviCabe"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByCodiVentCabe", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.codiVentCabe = :codiVentCabe"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByCodiTipoMovi", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.codiTipoMovi = :codiTipoMovi"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByCodiAlmcOrign", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.codiAlmcOrign = :codiAlmcOrign"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByCodiAlmcDest", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.codiAlmcDest = :codiAlmcDest"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByFechMovi", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.fechMovi = :fechMovi"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByCodiEstd", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.codiEstd = :codiEstd"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByActiMovi", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.actiMovi = :actiMovi"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByCodiUsuaCrea", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.codiUsuaCrea = :codiUsuaCrea"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByFechUsuaCrea", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.fechUsuaCrea = :fechUsuaCrea"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByCodiModiCrea", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.codiModiCrea = :codiModiCrea"),
    @NamedQuery(name = "VentasMovimientoCabecera.findByFechModiCrea", query = "SELECT v FROM VentasMovimientoCabecera v WHERE v.fechModiCrea = :fechModiCrea")})
public class VentasMovimientoCabecera implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiMoviCabe")
    private Integer codiMoviCabe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiVentCabe")
    private int codiVentCabe;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiTipoMovi")
    private int codiTipoMovi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiAlmcOrign")
    private int codiAlmcOrign;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiAlmcDest")
    private int codiAlmcDest;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechMovi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechMovi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiEstd")
    private int codiEstd;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "obsvMovi")
    private String obsvMovi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiMovi")
    private boolean actiMovi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiUsuaCrea")
    private int codiUsuaCrea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechUsuaCrea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechUsuaCrea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiModiCrea")
    private int codiModiCrea;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechModiCrea")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechModiCrea;

    public VentasMovimientoCabecera() {
    }

    public VentasMovimientoCabecera(Integer codiMoviCabe) {
        this.codiMoviCabe = codiMoviCabe;
    }

    public VentasMovimientoCabecera(Integer codiMoviCabe, int codiVentCabe, int codiTipoMovi, int codiAlmcOrign, int codiAlmcDest, Date fechMovi, int codiEstd, String obsvMovi, boolean actiMovi, int codiUsuaCrea, Date fechUsuaCrea, int codiModiCrea, Date fechModiCrea) {
        this.codiMoviCabe = codiMoviCabe;
        this.codiVentCabe = codiVentCabe;
        this.codiTipoMovi = codiTipoMovi;
        this.codiAlmcOrign = codiAlmcOrign;
        this.codiAlmcDest = codiAlmcDest;
        this.fechMovi = fechMovi;
        this.codiEstd = codiEstd;
        this.obsvMovi = obsvMovi;
        this.actiMovi = actiMovi;
        this.codiUsuaCrea = codiUsuaCrea;
        this.fechUsuaCrea = fechUsuaCrea;
        this.codiModiCrea = codiModiCrea;
        this.fechModiCrea = fechModiCrea;
    }

    public Integer getCodiMoviCabe() {
        return codiMoviCabe;
    }

    public void setCodiMoviCabe(Integer codiMoviCabe) {
        this.codiMoviCabe = codiMoviCabe;
    }

    public int getCodiVentCabe() {
        return codiVentCabe;
    }

    public void setCodiVentCabe(int codiVentCabe) {
        this.codiVentCabe = codiVentCabe;
    }

    public int getCodiTipoMovi() {
        return codiTipoMovi;
    }

    public void setCodiTipoMovi(int codiTipoMovi) {
        this.codiTipoMovi = codiTipoMovi;
    }

    public int getCodiAlmcOrign() {
        return codiAlmcOrign;
    }

    public void setCodiAlmcOrign(int codiAlmcOrign) {
        this.codiAlmcOrign = codiAlmcOrign;
    }

    public int getCodiAlmcDest() {
        return codiAlmcDest;
    }

    public void setCodiAlmcDest(int codiAlmcDest) {
        this.codiAlmcDest = codiAlmcDest;
    }

    public Date getFechMovi() {
        return fechMovi;
    }

    public void setFechMovi(Date fechMovi) {
        this.fechMovi = fechMovi;
    }

    public int getCodiEstd() {
        return codiEstd;
    }

    public void setCodiEstd(int codiEstd) {
        this.codiEstd = codiEstd;
    }

    public String getObsvMovi() {
        return obsvMovi;
    }

    public void setObsvMovi(String obsvMovi) {
        this.obsvMovi = obsvMovi;
    }

    public boolean getActiMovi() {
        return actiMovi;
    }

    public void setActiMovi(boolean actiMovi) {
        this.actiMovi = actiMovi;
    }

    public int getCodiUsuaCrea() {
        return codiUsuaCrea;
    }

    public void setCodiUsuaCrea(int codiUsuaCrea) {
        this.codiUsuaCrea = codiUsuaCrea;
    }

    public Date getFechUsuaCrea() {
        return fechUsuaCrea;
    }

    public void setFechUsuaCrea(Date fechUsuaCrea) {
        this.fechUsuaCrea = fechUsuaCrea;
    }

    public int getCodiModiCrea() {
        return codiModiCrea;
    }

    public void setCodiModiCrea(int codiModiCrea) {
        this.codiModiCrea = codiModiCrea;
    }

    public Date getFechModiCrea() {
        return fechModiCrea;
    }

    public void setFechModiCrea(Date fechModiCrea) {
        this.fechModiCrea = fechModiCrea;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiMoviCabe != null ? codiMoviCabe.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasMovimientoCabecera)) {
            return false;
        }
        VentasMovimientoCabecera other = (VentasMovimientoCabecera) object;
        if ((this.codiMoviCabe == null && other.codiMoviCabe != null) || (this.codiMoviCabe != null && !this.codiMoviCabe.equals(other.codiMoviCabe))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasMovimientoCabecera[ codiMoviCabe=" + codiMoviCabe + " ]";
    }
    
}
