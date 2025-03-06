/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.compras.dto;

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
 * @author USER
 */
@Entity
@Table(name = "compras_oc_estado")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComprasOcEstado.findAll", query = "SELECT c FROM ComprasOcEstado c"),
    @NamedQuery(name = "ComprasOcEstado.findByCodiEstdOC", query = "SELECT c FROM ComprasOcEstado c WHERE c.codiEstdOC = :codiEstdOC"),
    @NamedQuery(name = "ComprasOcEstado.findByNombEstdOC", query = "SELECT c FROM ComprasOcEstado c WHERE c.nombEstdOC = :nombEstdOC")})
public class ComprasOcEstado implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiEstdOC")
    private Integer codiEstdOC;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombEstdOC")
    private String nombEstdOC;

    public ComprasOcEstado() {
    }

    public ComprasOcEstado(Integer codiEstdOC) {
        this.codiEstdOC = codiEstdOC;
    }

    public ComprasOcEstado(Integer codiEstdOC, String nombEstdOC) {
        this.codiEstdOC = codiEstdOC;
        this.nombEstdOC = nombEstdOC;
    }

    public Integer getCodiEstdOC() {
        return codiEstdOC;
    }

    public void setCodiEstdOC(Integer codiEstdOC) {
        this.codiEstdOC = codiEstdOC;
    }

    public String getNombEstdOC() {
        return nombEstdOC;
    }

    public void setNombEstdOC(String nombEstdOC) {
        this.nombEstdOC = nombEstdOC;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiEstdOC != null ? codiEstdOC.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComprasOcEstado)) {
            return false;
        }
        ComprasOcEstado other = (ComprasOcEstado) object;
        if ((this.codiEstdOC == null && other.codiEstdOC != null) || (this.codiEstdOC != null && !this.codiEstdOC.equals(other.codiEstdOC))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.compras.dto.ComprasOcEstado[ codiEstdOC=" + codiEstdOC + " ]";
    }
    
}
