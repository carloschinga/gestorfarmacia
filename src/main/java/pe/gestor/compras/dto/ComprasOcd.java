/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.compras.dto;

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
 * @author USER
 */
@Entity
@Table(name = "compras_ocd")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComprasOcd.findAll", query = "SELECT c FROM ComprasOcd c"),
    @NamedQuery(name = "ComprasOcd.findByCodiOCD", query = "SELECT c FROM ComprasOcd c WHERE c.codiOCD = :codiOCD"),
    @NamedQuery(name = "ComprasOcd.findByCodiOC", query = "SELECT c FROM ComprasOcd c WHERE c.codiOC = :codiOC"),
    @NamedQuery(name = "ComprasOcd.findByCodiProd", query = "SELECT c FROM ComprasOcd c WHERE c.codiProd = :codiProd"),
    @NamedQuery(name = "ComprasOcd.findByNombProd", query = "SELECT c FROM ComprasOcd c WHERE c.nombProd = :nombProd"),
    @NamedQuery(name = "ComprasOcd.findByCantiProd", query = "SELECT c FROM ComprasOcd c WHERE c.cantiProd = :cantiProd"),
    @NamedQuery(name = "ComprasOcd.findByPrecProd", query = "SELECT c FROM ComprasOcd c WHERE c.precProd = :precProd"),
    @NamedQuery(name = "ComprasOcd.findBySubtProd", query = "SELECT c FROM ComprasOcd c WHERE c.subtProd = :subtProd"),
    @NamedQuery(name = "ComprasOcd.findByIgvProd", query = "SELECT c FROM ComprasOcd c WHERE c.igvProd = :igvProd"),
    @NamedQuery(name = "ComprasOcd.findByTotaProd", query = "SELECT c FROM ComprasOcd c WHERE c.totaProd = :totaProd")})
public class ComprasOcd implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiOCD")
    private Integer codiOCD;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiOC")
    private int codiOC;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "codiProd")
    private String codiProd;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombProd")
    private String nombProd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cantiProd")
    private int cantiProd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "precProd")
    private double precProd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "subtProd")
    private double subtProd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "igvProd")
    private double igvProd;
    @Basic(optional = false)
    @NotNull
    @Column(name = "totaProd")
    private double totaProd;

    public ComprasOcd() {
    }

    public ComprasOcd(Integer codiOCD) {
        this.codiOCD = codiOCD;
    }

    public ComprasOcd(Integer codiOCD, int codiOC, String codiProd, String nombProd, int cantiProd, double precProd, double subtProd, double igvProd, double totaProd) {
        this.codiOCD = codiOCD;
        this.codiOC = codiOC;
        this.codiProd = codiProd;
        this.nombProd = nombProd;
        this.cantiProd = cantiProd;
        this.precProd = precProd;
        this.subtProd = subtProd;
        this.igvProd = igvProd;
        this.totaProd = totaProd;
    }

    public Integer getCodiOCD() {
        return codiOCD;
    }

    public void setCodiOCD(Integer codiOCD) {
        this.codiOCD = codiOCD;
    }

    public int getCodiOC() {
        return codiOC;
    }

    public void setCodiOC(int codiOC) {
        this.codiOC = codiOC;
    }

    public String getCodiProd() {
        return codiProd;
    }

    public void setCodiProd(String codiProd) {
        this.codiProd = codiProd;
    }

    public String getNombProd() {
        return nombProd;
    }

    public void setNombProd(String nombProd) {
        this.nombProd = nombProd;
    }

    public int getCantiProd() {
        return cantiProd;
    }

    public void setCantiProd(int cantiProd) {
        this.cantiProd = cantiProd;
    }

    public double getPrecProd() {
        return precProd;
    }

    public void setPrecProd(double precProd) {
        this.precProd = precProd;
    }

    public double getSubtProd() {
        return subtProd;
    }

    public void setSubtProd(double subtProd) {
        this.subtProd = subtProd;
    }

    public double getIgvProd() {
        return igvProd;
    }

    public void setIgvProd(double igvProd) {
        this.igvProd = igvProd;
    }

    public double getTotaProd() {
        return totaProd;
    }

    public void setTotaProd(double totaProd) {
        this.totaProd = totaProd;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiOCD != null ? codiOCD.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComprasOcd)) {
            return false;
        }
        ComprasOcd other = (ComprasOcd) object;
        if ((this.codiOCD == null && other.codiOCD != null) || (this.codiOCD != null && !this.codiOCD.equals(other.codiOCD))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.compras.dto.ComprasOcd[ codiOCD=" + codiOCD + " ]";
    }
    
}
