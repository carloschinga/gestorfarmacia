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
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "asistencia_tareo")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsistenciaTareo.findAll", query = "SELECT a FROM AsistenciaTareo a"),
    @NamedQuery(name = "AsistenciaTareo.findByCodiTare", query = "SELECT a FROM AsistenciaTareo a WHERE a.codiTare = :codiTare"),
    @NamedQuery(name = "AsistenciaTareo.findByCodiPeri", query = "SELECT a FROM AsistenciaTareo a WHERE a.codiPeri = :codiPeri"),
    @NamedQuery(name = "AsistenciaTareo.findByCodiRang", query = "SELECT a FROM AsistenciaTareo a WHERE a.codiRang = :codiRang")})
public class AsistenciaTareo implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiTare")
    private Integer codiTare;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiPeri")
    private int codiPeri;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiRang")
    private int codiRang;

    public AsistenciaTareo() {
    }

    public AsistenciaTareo(Integer codiTare) {
        this.codiTare = codiTare;
    }

    public AsistenciaTareo(Integer codiTare, int codiPeri, int codiRang) {
        this.codiTare = codiTare;
        this.codiPeri = codiPeri;
        this.codiRang = codiRang;
    }

    public Integer getCodiTare() {
        return codiTare;
    }

    public void setCodiTare(Integer codiTare) {
        this.codiTare = codiTare;
    }

    public int getCodiPeri() {
        return codiPeri;
    }

    public void setCodiPeri(int codiPeri) {
        this.codiPeri = codiPeri;
    }

    public int getCodiRang() {
        return codiRang;
    }

    public void setCodiRang(int codiRang) {
        this.codiRang = codiRang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiTare != null ? codiTare.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaTareo)) {
            return false;
        }
        AsistenciaTareo other = (AsistenciaTareo) object;
        if ((this.codiTare == null && other.codiTare != null) || (this.codiTare != null && !this.codiTare.equals(other.codiTare))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.asistencia.dto.AsistenciaTareo[ codiTare=" + codiTare + " ]";
    }
    
}
