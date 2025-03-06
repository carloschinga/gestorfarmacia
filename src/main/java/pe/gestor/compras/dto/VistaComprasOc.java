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
import javax.persistence.Id;
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
 * @author USER
 */
@Entity
@Table(name = "vista_compras_oc")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaComprasOc.findAll", query = "SELECT v FROM VistaComprasOc v"),
    @NamedQuery(name = "VistaComprasOc.findByCodiOC", query = "SELECT v FROM VistaComprasOc v WHERE v.codiOC = :codiOC"),
    @NamedQuery(name = "VistaComprasOc.findByFechOC", query = "SELECT v FROM VistaComprasOc v WHERE v.fechOC = :fechOC"),
    @NamedQuery(name = "VistaComprasOc.findByCodiProv", query = "SELECT v FROM VistaComprasOc v WHERE v.codiProv = :codiProv"),
    @NamedQuery(name = "VistaComprasOc.findByCodiEstdOC", query = "SELECT v FROM VistaComprasOc v WHERE v.codiEstdOC = :codiEstdOC"),
    @NamedQuery(name = "VistaComprasOc.findByNombEstdOC", query = "SELECT v FROM VistaComprasOc v WHERE v.nombEstdOC = :nombEstdOC"),
    @NamedQuery(name = "VistaComprasOc.findByActiOC", query = "SELECT v FROM VistaComprasOc v WHERE v.actiOC = :actiOC"),
    @NamedQuery(name = "VistaComprasOc.findByCodiUsuaRegi", query = "SELECT v FROM VistaComprasOc v WHERE v.codiUsuaRegi = :codiUsuaRegi"),
    @NamedQuery(name = "VistaComprasOc.findByFechUsuaRegi", query = "SELECT v FROM VistaComprasOc v WHERE v.fechUsuaRegi = :fechUsuaRegi")})
public class VistaComprasOc implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiOC")
    private int codiOC;
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
    @Size(min = 1, max = 50)
    @Column(name = "nombEstdOC")
    private String nombEstdOC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiOC")
    private short actiOC;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiUsuaRegi")
    private int codiUsuaRegi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fechUsuaRegi")
    @Temporal(TemporalType.TIMESTAMP)
    private Date fechUsuaRegi;

    public VistaComprasOc() {
    }

    public int getCodiOC() {
        return codiOC;
    }

    public void setCodiOC(int codiOC) {
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

    public String getNombEstdOC() {
        return nombEstdOC;
    }

    public void setNombEstdOC(String nombEstdOC) {
        this.nombEstdOC = nombEstdOC;
    }

    public short getActiOC() {
        return actiOC;
    }

    public void setActiOC(short actiOC) {
        this.actiOC = actiOC;
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
    
}
