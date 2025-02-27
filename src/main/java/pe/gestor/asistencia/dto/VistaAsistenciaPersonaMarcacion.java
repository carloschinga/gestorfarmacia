/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 * @author USER
 */
@Entity
@Table(name = "vista_asistencia_persona_marcacion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaAsistenciaPersonaMarcacion.findAll", query = "SELECT v FROM VistaAsistenciaPersonaMarcacion v"),
    @NamedQuery(name = "VistaAsistenciaPersonaMarcacion.findByCodiMarc", query = "SELECT v FROM VistaAsistenciaPersonaMarcacion v WHERE v.codiMarc = :codiMarc"),
    @NamedQuery(name = "VistaAsistenciaPersonaMarcacion.findByCodiPers", query = "SELECT v FROM VistaAsistenciaPersonaMarcacion v WHERE v.codiPers = :codiPers"),
    @NamedQuery(name = "VistaAsistenciaPersonaMarcacion.findByNombPers", query = "SELECT v FROM VistaAsistenciaPersonaMarcacion v WHERE v.nombPers = :nombPers"),
    @NamedQuery(name = "VistaAsistenciaPersonaMarcacion.findByFechMarc", query = "SELECT v FROM VistaAsistenciaPersonaMarcacion v WHERE v.fechMarc = :fechMarc"),
    @NamedQuery(name = "VistaAsistenciaPersonaMarcacion.findByCodiHoraDeta", query = "SELECT v FROM VistaAsistenciaPersonaMarcacion v WHERE v.codiHoraDeta = :codiHoraDeta"),
    @NamedQuery(name = "VistaAsistenciaPersonaMarcacion.findByMarcIngr", query = "SELECT v FROM VistaAsistenciaPersonaMarcacion v WHERE v.marcIngr = :marcIngr"),
    @NamedQuery(name = "VistaAsistenciaPersonaMarcacion.findByMarcSald", query = "SELECT v FROM VistaAsistenciaPersonaMarcacion v WHERE v.marcSald = :marcSald")})
public class VistaAsistenciaPersonaMarcacion implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiMarc")
    private int codiMarc;
    @Column(name = "codiPers")
    private Integer codiPers;
    @Size(max = 122)
    @Column(name = "nombPers")
    private String nombPers;
    @Column(name = "fechMarc")
    @Temporal(TemporalType.DATE)
    private Date fechMarc;
    @Column(name = "codiHoraDeta")
    private Integer codiHoraDeta;
    @Column(name = "marcIngr")
    @Temporal(TemporalType.TIME)
    private Date marcIngr;
    @Column(name = "marcSald")
    @Temporal(TemporalType.TIME)
    private Date marcSald;

    public VistaAsistenciaPersonaMarcacion() {
    }

    public int getCodiMarc() {
        return codiMarc;
    }

    public void setCodiMarc(int codiMarc) {
        this.codiMarc = codiMarc;
    }

    public Integer getCodiPers() {
        return codiPers;
    }

    public void setCodiPers(Integer codiPers) {
        this.codiPers = codiPers;
    }

    public String getNombPers() {
        return nombPers;
    }

    public void setNombPers(String nombPers) {
        this.nombPers = nombPers;
    }

    public Date getFechMarc() {
        return fechMarc;
    }

    public void setFechMarc(Date fechMarc) {
        this.fechMarc = fechMarc;
    }

    public Integer getCodiHoraDeta() {
        return codiHoraDeta;
    }

    public void setCodiHoraDeta(Integer codiHoraDeta) {
        this.codiHoraDeta = codiHoraDeta;
    }

    public Date getMarcIngr() {
        return marcIngr;
    }

    public void setMarcIngr(Date marcIngr) {
        this.marcIngr = marcIngr;
    }

    public Date getMarcSald() {
        return marcSald;
    }

    public void setMarcSald(Date marcSald) {
        this.marcSald = marcSald;
    }
    
}
