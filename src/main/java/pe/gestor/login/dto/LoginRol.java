/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dto;

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
 * @author USER
 */
@Entity
@Table(name = "login_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoginRol.findAll", query = "SELECT l FROM LoginRol l"),
    @NamedQuery(name = "LoginRol.findByCodiRol", query = "SELECT l FROM LoginRol l WHERE l.codiRol = :codiRol"),
    @NamedQuery(name = "LoginRol.findByNombRol", query = "SELECT l FROM LoginRol l WHERE l.nombRol = :nombRol"),
    @NamedQuery(name = "LoginRol.findByAdmiRol", query = "SELECT l FROM LoginRol l WHERE l.admiRol = :admiRol")})
public class LoginRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiRol")
    private Integer codiRol;
    @Size(max = 10)
    @Column(name = "nombRol")
    private String nombRol;
    @Column(name = "admiRol")
    private Boolean admiRol;

    public LoginRol() {
    }

    public LoginRol(Integer codiRol) {
        this.codiRol = codiRol;
    }

    public Integer getCodiRol() {
        return codiRol;
    }

    public void setCodiRol(Integer codiRol) {
        this.codiRol = codiRol;
    }

    public String getNombRol() {
        return nombRol;
    }

    public void setNombRol(String nombRol) {
        this.nombRol = nombRol;
    }

    public Boolean getAdmiRol() {
        return admiRol;
    }

    public void setAdmiRol(Boolean admiRol) {
        this.admiRol = admiRol;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiRol != null ? codiRol.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginRol)) {
            return false;
        }
        LoginRol other = (LoginRol) object;
        if ((this.codiRol == null && other.codiRol != null) || (this.codiRol != null && !this.codiRol.equals(other.codiRol))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.login.dto.LoginRol[ codiRol=" + codiRol + " ]";
    }
    
}
