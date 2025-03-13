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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "asistencia_turno")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsistenciaTurno.findAll", query = "SELECT a FROM AsistenciaTurno a"),
    @NamedQuery(name = "AsistenciaTurno.findByCodiTurn", query = "SELECT a FROM AsistenciaTurno a WHERE a.codiTurn = :codiTurn"),
    @NamedQuery(name = "AsistenciaTurno.findByDescTurn", query = "SELECT a FROM AsistenciaTurno a WHERE a.descTurn = :descTurn"),
    @NamedQuery(name = "AsistenciaTurno.findByHoraIngr", query = "SELECT a FROM AsistenciaTurno a WHERE a.horaIngr = :horaIngr"),
    @NamedQuery(name = "AsistenciaTurno.findByHoraSald", query = "SELECT a FROM AsistenciaTurno a WHERE a.horaSald = :horaSald")})
public class AsistenciaTurno implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiTurn")
    private Integer codiTurn;
    @Size(max = 50)
    @Column(name = "descTurn")
    private String descTurn;
    @Column(name = "horaIngr")
    @Temporal(TemporalType.TIME)
    private Date horaIngr;
    @Column(name = "horaSald")
    @Temporal(TemporalType.TIME)
    private Date horaSald;

    public AsistenciaTurno() {
    }

    public AsistenciaTurno(Integer codiTurn) {
        this.codiTurn = codiTurn;
    }

    public Integer getCodiTurn() {
        return codiTurn;
    }

    public void setCodiTurn(Integer codiTurn) {
        this.codiTurn = codiTurn;
    }

    public String getDescTurn() {
        return descTurn;
    }

    public void setDescTurn(String descTurn) {
        this.descTurn = descTurn;
    }

    public Date getHoraIngr() {
        return horaIngr;
    }

    public void setHoraIngr(Date horaIngr) {
        this.horaIngr = horaIngr;
    }

    public Date getHoraSald() {
        return horaSald;
    }

    public void setHoraSald(Date horaSald) {
        this.horaSald = horaSald;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiTurn != null ? codiTurn.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaTurno)) {
            return false;
        }
        AsistenciaTurno other = (AsistenciaTurno) object;
        if ((this.codiTurn == null && other.codiTurn != null) || (this.codiTurn != null && !this.codiTurn.equals(other.codiTurn))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.asistencia.dto.AsistenciaTurno[ codiTurn=" + codiTurn + " ]";
    }
    
}
