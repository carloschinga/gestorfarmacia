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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "asistencia_feriado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsistenciaFeriado.findAll", query = "SELECT a FROM AsistenciaFeriado a"),
    @NamedQuery(name = "AsistenciaFeriado.findByCodiFeri", query = "SELECT a FROM AsistenciaFeriado a WHERE a.codiFeri = :codiFeri"),
    @NamedQuery(name = "AsistenciaFeriado.findByFechFeri", query = "SELECT a FROM AsistenciaFeriado a WHERE a.fechFeri = :fechFeri")})
public class AsistenciaFeriado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiFeri")
    private Integer codiFeri;
    @Column(name = "fechFeri")
    @Temporal(TemporalType.DATE)
    private Date fechFeri;

    public AsistenciaFeriado() {
    }

    public AsistenciaFeriado(Integer codiFeri) {
        this.codiFeri = codiFeri;
    }

    public Integer getCodiFeri() {
        return codiFeri;
    }

    public void setCodiFeri(Integer codiFeri) {
        this.codiFeri = codiFeri;
    }

    public Date getFechFeri() {
        return fechFeri;
    }

    public void setFechFeri(Date fechFeri) {
        this.fechFeri = fechFeri;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiFeri != null ? codiFeri.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaFeriado)) {
            return false;
        }
        AsistenciaFeriado other = (AsistenciaFeriado) object;
        if ((this.codiFeri == null && other.codiFeri != null) || (this.codiFeri != null && !this.codiFeri.equals(other.codiFeri))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.asistencia.dto.AsistenciaFeriado[ codiFeri=" + codiFeri + " ]";
    }
    
}
