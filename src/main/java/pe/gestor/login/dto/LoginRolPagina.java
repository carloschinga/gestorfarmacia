/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "login_rol_pagina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoginRolPagina.findAll", query = "SELECT l FROM LoginRolPagina l"),
    @NamedQuery(name = "LoginRolPagina.findByCodiRol", query = "SELECT l FROM LoginRolPagina l WHERE l.loginRolPaginaPK.codiRol = :codiRol"),
    @NamedQuery(name = "LoginRolPagina.findByCodiPagi", query = "SELECT l FROM LoginRolPagina l WHERE l.loginRolPaginaPK.codiPagi = :codiPagi"),
    @NamedQuery(name = "LoginRolPagina.findByAsigPerm", query = "SELECT l FROM LoginRolPagina l WHERE l.asigPerm = :asigPerm")})
public class LoginRolPagina implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected LoginRolPaginaPK loginRolPaginaPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "asigPerm")
    private int asigPerm;

    public LoginRolPagina() {
    }

    public LoginRolPagina(LoginRolPaginaPK loginRolPaginaPK) {
        this.loginRolPaginaPK = loginRolPaginaPK;
    }

    public LoginRolPagina(LoginRolPaginaPK loginRolPaginaPK, int asigPerm) {
        this.loginRolPaginaPK = loginRolPaginaPK;
        this.asigPerm = asigPerm;
    }

    public LoginRolPagina(int codiRol, int codiPagi) {
        this.loginRolPaginaPK = new LoginRolPaginaPK(codiRol, codiPagi);
    }

    public LoginRolPaginaPK getLoginRolPaginaPK() {
        return loginRolPaginaPK;
    }

    public void setLoginRolPaginaPK(LoginRolPaginaPK loginRolPaginaPK) {
        this.loginRolPaginaPK = loginRolPaginaPK;
    }

    public int getAsigPerm() {
        return asigPerm;
    }

    public void setAsigPerm(int asigPerm) {
        this.asigPerm = asigPerm;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (loginRolPaginaPK != null ? loginRolPaginaPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginRolPagina)) {
            return false;
        }
        LoginRolPagina other = (LoginRolPagina) object;
        if ((this.loginRolPaginaPK == null && other.loginRolPaginaPK != null) || (this.loginRolPaginaPK != null && !this.loginRolPaginaPK.equals(other.loginRolPaginaPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.login.dto.LoginRolPagina[ loginRolPaginaPK=" + loginRolPaginaPK + " ]";
    }
    
}
