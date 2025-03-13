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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "asistencia_horario")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsistenciaHorario.findAll", query = "SELECT a FROM AsistenciaHorario a"),
    @NamedQuery(name = "AsistenciaHorario.findByCodiHora", query = "SELECT a FROM AsistenciaHorario a WHERE a.codiHora = :codiHora"),
    @NamedQuery(name = "AsistenciaHorario.findByNombHora", query = "SELECT a FROM AsistenciaHorario a WHERE a.nombHora = :nombHora")})
public class AsistenciaHorario implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiHora")
    private Integer codiHora;
    @Size(max = 50)
    @Column(name = "nombHora")
    private String nombHora;

    public AsistenciaHorario() {
    }

    public AsistenciaHorario(Integer codiHora) {
        this.codiHora = codiHora;
    }

    public Integer getCodiHora() {
        return codiHora;
    }

    public void setCodiHora(Integer codiHora) {
        this.codiHora = codiHora;
    }

    public String getNombHora() {
        return nombHora;
    }

    public void setNombHora(String nombHora) {
        this.nombHora = nombHora;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiHora != null ? codiHora.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaHorario)) {
            return false;
        }
        AsistenciaHorario other = (AsistenciaHorario) object;
        if ((this.codiHora == null && other.codiHora != null) || (this.codiHora != null && !this.codiHora.equals(other.codiHora))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.asistencia.dto.AsistenciaHorario[ codiHora=" + codiHora + " ]";
    }
    
}
