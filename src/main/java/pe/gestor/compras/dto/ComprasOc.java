/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.compras.dto;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "compras_oc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComprasOc.findAll", query = "SELECT c FROM ComprasOc c"),
    @NamedQuery(name = "ComprasOc.findByCodiOC", query = "SELECT c FROM ComprasOc c WHERE c.codiOC = :codiOC"),
    @NamedQuery(name = "ComprasOc.findByFechOC", query = "SELECT c FROM ComprasOc c WHERE c.fechOC = :fechOC"),
    @NamedQuery(name = "ComprasOc.findByCodiProv", query = "SELECT c FROM ComprasOc c WHERE c.codiProv = :codiProv"),
    @NamedQuery(name = "ComprasOc.findByCodiEstdOC", query = "SELECT c FROM ComprasOc c WHERE c.codiEstdOC = :codiEstdOC"),
    @NamedQuery(name = "ComprasOc.findByCodiUsuaRegi", query = "SELECT c FROM ComprasOc c WHERE c.codiUsuaRegi = :codiUsuaRegi"),
    @NamedQuery(name = "ComprasOc.findByFechUsuaRegi", query = "SELECT c FROM ComprasOc c WHERE c.fechUsuaRegi = :fechUsuaRegi"),
    @NamedQuery(name = "ComprasOc.findByActiOC", query = "SELECT c FROM ComprasOc c WHERE c.actiOC = :actiOC")})
public class ComprasOc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiOC")
    private Integer codiOC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechOC")
    @Temporal(TemporalType.DATE)
    private Date fechOC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiProv")
    private int codiProv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiEstdOC")
    private int codiEstdOC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiUsuaRegi")
    private int codiUsuaRegi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechUsuaRegi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechUsuaRegi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiOC")
    private short actiOC;

    public ComprasOc() {
    }

    public ComprasOc(Integer codiOC) {
        this.codiOC = codiOC;
    }

    public ComprasOc(Integer codiOC, Date fechOC, int codiProv, int codiEstdOC, int codiUsuaRegi, Date fechUsuaRegi, short actiOC) {
        this.codiOC = codiOC;
        this.fechOC = fechOC;
        this.codiProv = codiProv;
        this.codiEstdOC = codiEstdOC;
        this.codiUsuaRegi = codiUsuaRegi;
        this.fechUsuaRegi = fechUsuaRegi;
        this.actiOC = actiOC;
    }

    public Integer getCodiOC() {
        return codiOC;
    }

    public void setCodiOC(Integer codiOC) {
        this.codiOC = codiOC;
    }

    public Date getFechOC() {
        return fechOC;
    }

    public void setFechOC(Date fechOC) {
        this.fechOC = fechOC;
    }

    public int getCodiProv() {
        return codiProv;
    }

    public void setCodiProv(int codiProv) {
        this.codiProv = codiProv;
    }

    public int getCodiEstdOC() {
        return codiEstdOC;
    }

    public void setCodiEstdOC(int codiEstdOC) {
        this.codiEstdOC = codiEstdOC;
    }

    public int getCodiUsuaRegi() {
        return codiUsuaRegi;
    }

    public void setCodiUsuaRegi(int codiUsuaRegi) {
        this.codiUsuaRegi = codiUsuaRegi;
    }

    public Date getFechUsuaRegi() {
        return fechUsuaRegi;
    }

    public void setFechUsuaRegi(Date fechUsuaRegi) {
        this.fechUsuaRegi = fechUsuaRegi;
    }

    public short getActiOC() {
        return actiOC;
    }

    public void setActiOC(short actiOC) {
        this.actiOC = actiOC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiOC != null ? codiOC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComprasOc)) {
            return false;
        }
        ComprasOc other = (ComprasOc) object;
        if ((this.codiOC == null && other.codiOC != null) || (this.codiOC != null && !this.codiOC.equals(other.codiOC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.compras.dto.ComprasOc[ codiOC=" + codiOC + " ]";
    }
    
}
