/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
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
 * @author Adria
 */
@Entity
@Table(name = "ventas_categoria")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasCategoria.findAll", query = "SELECT v FROM VentasCategoria v"),
    @NamedQuery(name = "VentasCategoria.findByCodiCate", query = "SELECT v FROM VentasCategoria v WHERE v.codiCate = :codiCate"),
    @NamedQuery(name = "VentasCategoria.findByNombCate", query = "SELECT v FROM VentasCategoria v WHERE v.nombCate = :nombCate"),
    @NamedQuery(name = "VentasCategoria.findByDescCate", query = "SELECT v FROM VentasCategoria v WHERE v.descCate = :descCate"),
    @NamedQuery(name = "VentasCategoria.findByOpciCAte", query = "SELECT v FROM VentasCategoria v WHERE v.opciCAte = :opciCAte")})
public class VentasCategoria implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiCate")
    private Integer codiCate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombCate")
    private String nombCate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "descCate")
    private String descCate;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "opciCAte")
    private String opciCAte;

    public VentasCategoria() {
    }

    public VentasCategoria(Integer codiCate) {
        this.codiCate = codiCate;
    }

    public VentasCategoria(Integer codiCate, String nombCate, String descCate, String opciCAte) {
        this.codiCate = codiCate;
        this.nombCate = nombCate;
        this.descCate = descCate;
        this.opciCAte = opciCAte;
    }

    public Integer getCodiCate() {
        return codiCate;
    }

    public void setCodiCate(Integer codiCate) {
        this.codiCate = codiCate;
    }

    public String getNombCate() {
        return nombCate;
    }

    public void setNombCate(String nombCate) {
        this.nombCate = nombCate;
    }

    public String getDescCate() {
        return descCate;
    }

    public void setDescCate(String descCate) {
        this.descCate = descCate;
    }

    public String getOpciCAte() {
        return opciCAte;
    }

    public void setOpciCAte(String opciCAte) {
        this.opciCAte = opciCAte;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiCate != null ? codiCate.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasCategoria)) {
            return false;
        }
        VentasCategoria other = (VentasCategoria) object;
        if ((this.codiCate == null && other.codiCate != null) || (this.codiCate != null && !this.codiCate.equals(other.codiCate))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasCategoria[ codiCate=" + codiCate + " ]";
    }
    
}
