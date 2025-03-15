/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.contable.dto;

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
@Table(name = "contable_periodo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ContablePeriodo.findAll", query = "SELECT c FROM ContablePeriodo c"),
    @NamedQuery(name = "ContablePeriodo.findByCodiPeri", query = "SELECT c FROM ContablePeriodo c WHERE c.codiPeri = :codiPeri"),
    @NamedQuery(name = "ContablePeriodo.findByNombPeri", query = "SELECT c FROM ContablePeriodo c WHERE c.nombPeri = :nombPeri"),
    @NamedQuery(name = "ContablePeriodo.findByYearPeri", query = "SELECT c FROM ContablePeriodo c WHERE c.yearPeri = :yearPeri"),
    @NamedQuery(name = "ContablePeriodo.findByMessPeri", query = "SELECT c FROM ContablePeriodo c WHERE c.messPeri = :messPeri"),
    @NamedQuery(name = "ContablePeriodo.findByAsisPeri", query = "SELECT c FROM ContablePeriodo c WHERE c.asisPeri = :asisPeri")})
public class ContablePeriodo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiPeri")
    private Integer codiPeri;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombPeri")
    private String nombPeri;
    @Basic(optional = false)
    @NotNull
    @Column(name = "yearPeri")
    private int yearPeri;
    @Basic(optional = false)
    @NotNull
    @Column(name = "messPeri")
    private int messPeri;
    @Basic(optional = false)
    @NotNull
    @Column(name = "asisPeri")
    private boolean asisPeri;

    public ContablePeriodo() {
    }

    public ContablePeriodo(Integer codiPeri) {
        this.codiPeri = codiPeri;
    }

    public ContablePeriodo(Integer codiPeri, String nombPeri, int yearPeri, int messPeri, boolean asisPeri) {
        this.codiPeri = codiPeri;
        this.nombPeri = nombPeri;
        this.yearPeri = yearPeri;
        this.messPeri = messPeri;
        this.asisPeri = asisPeri;
    }

    public Integer getCodiPeri() {
        return codiPeri;
    }

    public void setCodiPeri(Integer codiPeri) {
        this.codiPeri = codiPeri;
    }

    public String getNombPeri() {
        return nombPeri;
    }

    public void setNombPeri(String nombPeri) {
        this.nombPeri = nombPeri;
    }

    public int getYearPeri() {
        return yearPeri;
    }

    public void setYearPeri(int yearPeri) {
        this.yearPeri = yearPeri;
    }

    public int getMessPeri() {
        return messPeri;
    }

    public void setMessPeri(int messPeri) {
        this.messPeri = messPeri;
    }

    public boolean getAsisPeri() {
        return asisPeri;
    }

    public void setAsisPeri(boolean asisPeri) {
        this.asisPeri = asisPeri;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiPeri != null ? codiPeri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ContablePeriodo)) {
            return false;
        }
        ContablePeriodo other = (ContablePeriodo) object;
        if ((this.codiPeri == null && other.codiPeri != null) || (this.codiPeri != null && !this.codiPeri.equals(other.codiPeri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.contable.dto.ContablePeriodo[ codiPeri=" + codiPeri + " ]";
    }
    
}
