/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.asistencia.dto;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "asistencia_rango_periodo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsistenciaRangoPeriodo.findAll", query = "SELECT a FROM AsistenciaRangoPeriodo a"),
    @NamedQuery(name = "AsistenciaRangoPeriodo.findByCodiRang", query = "SELECT a FROM AsistenciaRangoPeriodo a WHERE a.codiRang = :codiRang"),
    @NamedQuery(name = "AsistenciaRangoPeriodo.findByInicRang", query = "SELECT a FROM AsistenciaRangoPeriodo a WHERE a.inicRang = :inicRang"),
    @NamedQuery(name = "AsistenciaRangoPeriodo.findByFinaRang", query = "SELECT a FROM AsistenciaRangoPeriodo a WHERE a.finaRang = :finaRang")})
public class AsistenciaRangoPeriodo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiRang")
    private Integer codiRang;
    @Basic(optional = false)
    @NotNull
    @Column(name = "inicRang")
    @Temporal(TemporalType.DATE)
    private Date inicRang;
    @Basic(optional = false)
    @NotNull
    @Column(name = "finaRang")
    @Temporal(TemporalType.DATE)
    private Date finaRang;

    public AsistenciaRangoPeriodo() {
    }

    public AsistenciaRangoPeriodo(Integer codiRang) {
        this.codiRang = codiRang;
    }

    public AsistenciaRangoPeriodo(Integer codiRang, Date inicRang, Date finaRang) {
        this.codiRang = codiRang;
        this.inicRang = inicRang;
        this.finaRang = finaRang;
    }

    public Integer getCodiRang() {
        return codiRang;
    }

    public void setCodiRang(Integer codiRang) {
        this.codiRang = codiRang;
    }

    public Date getInicRang() {
        return inicRang;
    }

    public void setInicRang(Date inicRang) {
        this.inicRang = inicRang;
    }

    public Date getFinaRang() {
        return finaRang;
    }

    public void setFinaRang(Date finaRang) {
        this.finaRang = finaRang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiRang != null ? codiRang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaRangoPeriodo)) {
            return false;
        }
        AsistenciaRangoPeriodo other = (AsistenciaRangoPeriodo) object;
        if ((this.codiRang == null && other.codiRang != null) || (this.codiRang != null && !this.codiRang.equals(other.codiRang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.asistencia.dto.AsistenciaRangoPeriodo[ codiRang=" + codiRang + " ]";
    }
    
}
