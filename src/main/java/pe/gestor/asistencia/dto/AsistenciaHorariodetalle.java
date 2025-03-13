/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.asistencia.dto;

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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "asistencia_horariodetalle")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "AsistenciaHorariodetalle.findAll", query = "SELECT a FROM AsistenciaHorariodetalle a"),
        @NamedQuery(name = "AsistenciaHorariodetalle.findByCodiHoraDeta", query = "SELECT a FROM AsistenciaHorariodetalle a WHERE a.codiHoraDeta = :codiHoraDeta"),
        @NamedQuery(name = "AsistenciaHorariodetalle.findByCodiHora", query = "SELECT a FROM AsistenciaHorariodetalle a WHERE a.codiHora = :codiHora"),
        @NamedQuery(name = "AsistenciaHorariodetalle.findByCodiDia", query = "SELECT a FROM AsistenciaHorariodetalle a WHERE a.codiDia = :codiDia"),
        @NamedQuery(name = "AsistenciaHorariodetalle.findByCodiTurn", query = "SELECT a FROM AsistenciaHorariodetalle a WHERE a.codiTurn = :codiTurn"),
        @NamedQuery(name = "AsistenciaHorariodetalle.findByHoraYDia", query = "SELECT a FROM AsistenciaHorariodetalle a WHERE a.codiHora = :codiHora AND a.codiDia = :codiDia") })
public class AsistenciaHorariodetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiHoraDeta")
    private Integer codiHoraDeta;
    @Column(name = "codiHora")
    private Integer codiHora;
    @Column(name = "codiDia")
    private Integer codiDia;
    @Column(name = "codiTurn")
    private Integer codiTurn;

    public AsistenciaHorariodetalle() {
    }

    public AsistenciaHorariodetalle(Integer codiHoraDeta) {
        this.codiHoraDeta = codiHoraDeta;
    }

    public Integer getCodiHoraDeta() {
        return codiHoraDeta;
    }

    public void setCodiHoraDeta(Integer codiHoraDeta) {
        this.codiHoraDeta = codiHoraDeta;
    }

    public Integer getCodiHora() {
        return codiHora;
    }

    public void setCodiHora(Integer codiHora) {
        this.codiHora = codiHora;
    }

    public Integer getCodiDia() {
        return codiDia;
    }

    public void setCodiDia(Integer codiDia) {
        this.codiDia = codiDia;
    }

    public Integer getCodiTurn() {
        return codiTurn;
    }

    public void setCodiTurn(Integer codiTurn) {
        this.codiTurn = codiTurn;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiHoraDeta != null ? codiHoraDeta.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaHorariodetalle)) {
            return false;
        }
        AsistenciaHorariodetalle other = (AsistenciaHorariodetalle) object;
        if ((this.codiHoraDeta == null && other.codiHoraDeta != null)
                || (this.codiHoraDeta != null && !this.codiHoraDeta.equals(other.codiHoraDeta))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.asistencia.dto.AsistenciaHorariodetalle[ codiHoraDeta=" + codiHoraDeta + " ]";
    }

}
