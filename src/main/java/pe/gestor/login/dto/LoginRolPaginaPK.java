/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author USER
 */
@Embeddable
public class LoginRolPaginaPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "codiRol")
    private int codiRol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiPagi")
    private int codiPagi;

    public LoginRolPaginaPK() {
    }

    public LoginRolPaginaPK(int codiRol, int codiPagi) {
        this.codiRol = codiRol;
        this.codiPagi = codiPagi;
    }

    public int getCodiRol() {
        return codiRol;
    }

    public void setCodiRol(int codiRol) {
        this.codiRol = codiRol;
    }

    public int getCodiPagi() {
        return codiPagi;
    }

    public void setCodiPagi(int codiPagi) {
        this.codiPagi = codiPagi;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) codiRol;
        hash += (int) codiPagi;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginRolPaginaPK)) {
            return false;
        }
        LoginRolPaginaPK other = (LoginRolPaginaPK) object;
        if (this.codiRol != other.codiRol) {
            return false;
        }
        if (this.codiPagi != other.codiPagi) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.login.dto.LoginRolPaginaPK[ codiRol=" + codiRol + ", codiPagi=" + codiPagi + " ]";
    }
    
}
