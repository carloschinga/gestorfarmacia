/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.ventas.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "ventas_sede")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasSede.findAll", query = "SELECT v FROM VentasSede v"),
    @NamedQuery(name = "VentasSede.findByCodiSede", query = "SELECT v FROM VentasSede v WHERE v.codiSede = :codiSede"),
    @NamedQuery(name = "VentasSede.findByNombSede", query = "SELECT v FROM VentasSede v WHERE v.nombSede = :nombSede")})
public class VentasSede implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiSede")
    private Integer codiSede;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombSede")
    private String nombSede;

    public VentasSede() {
    }

    public VentasSede(Integer codiSede) {
        this.codiSede = codiSede;
    }

    public VentasSede(Integer codiSede, String nombSede) {
        this.codiSede = codiSede;
        this.nombSede = nombSede;
    }

    public Integer getCodiSede() {
        return codiSede;
    }

    public void setCodiSede(Integer codiSede) {
        this.codiSede = codiSede;
    }

    public String getNombSede() {
        return nombSede;
    }

    public void setNombSede(String nombSede) {
        this.nombSede = nombSede;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiSede != null ? codiSede.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasSede)) {
            return false;
        }
        VentasSede other = (VentasSede) object;
        if ((this.codiSede == null && other.codiSede != null) || (this.codiSede != null && !this.codiSede.equals(other.codiSede))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasSede[ codiSede=" + codiSede + " ]";
    }
    
}
