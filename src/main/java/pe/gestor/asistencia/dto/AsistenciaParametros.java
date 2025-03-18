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
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "asistencia_parametros")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AsistenciaParametros.findAll", query = "SELECT a FROM AsistenciaParametros a"),
    @NamedQuery(name = "AsistenciaParametros.findByCodiPara", query = "SELECT a FROM AsistenciaParametros a WHERE a.codiPara = :codiPara"),
    @NamedQuery(name = "AsistenciaParametros.findByValuTareoPara", query = "SELECT a FROM AsistenciaParametros a WHERE a.valuTareoPara = :valuTareoPara"),
    @NamedQuery(name = "AsistenciaParametros.findByDescripcion", query = "SELECT a FROM AsistenciaParametros a WHERE a.descripcion = :descripcion")})
public class AsistenciaParametros implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiPara")
    private Integer codiPara;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "valuTareoPara")
    private String valuTareoPara;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "descripcion")
    private String descripcion;

    public AsistenciaParametros() {
    }

    public AsistenciaParametros(Integer codiPara) {
        this.codiPara = codiPara;
    }

    public AsistenciaParametros(Integer codiPara, String valuTareoPara, String descripcion) {
        this.codiPara = codiPara;
        this.valuTareoPara = valuTareoPara;
        this.descripcion = descripcion;
    }

    public Integer getCodiPara() {
        return codiPara;
    }

    public void setCodiPara(Integer codiPara) {
        this.codiPara = codiPara;
    }

    public String getValuTareoPara() {
        return valuTareoPara;
    }

    public void setValuTareoPara(String valuTareoPara) {
        this.valuTareoPara = valuTareoPara;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiPara != null ? codiPara.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AsistenciaParametros)) {
            return false;
        }
        AsistenciaParametros other = (AsistenciaParametros) object;
        if ((this.codiPara == null && other.codiPara != null) || (this.codiPara != null && !this.codiPara.equals(other.codiPara))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.asistencia.dto.AsistenciaParametros[ codiPara=" + codiPara + " ]";
    }
    
}
