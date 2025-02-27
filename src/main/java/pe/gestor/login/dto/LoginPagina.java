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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author USER
 */
@Entity
@Table(name = "login_pagina")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "LoginPagina.findAll", query = "SELECT l FROM LoginPagina l"),
    @NamedQuery(name = "LoginPagina.findByCodiPagi", query = "SELECT l FROM LoginPagina l WHERE l.codiPagi = :codiPagi"),
    @NamedQuery(name = "LoginPagina.findByNombPagi", query = "SELECT l FROM LoginPagina l WHERE l.nombPagi = :nombPagi"),
    @NamedQuery(name = "LoginPagina.findByHtmlPagi", query = "SELECT l FROM LoginPagina l WHERE l.htmlPagi = :htmlPagi"),
    @NamedQuery(name = "LoginPagina.findByIclass", query = "SELECT l FROM LoginPagina l WHERE l.iclass = :iclass"),
    @NamedQuery(name = "LoginPagina.findByHtml", query = "SELECT l FROM LoginPagina l WHERE l.html = :html"),
    @NamedQuery(name = "LoginPagina.findByTipoMenu", query = "SELECT l FROM LoginPagina l WHERE l.tipoMenu = :tipoMenu"),
    @NamedQuery(name = "LoginPagina.findByCodmas", query = "SELECT l FROM LoginPagina l WHERE l.codmas = :codmas"),
    @NamedQuery(name = "LoginPagina.findByNombAsig", query = "SELECT l FROM LoginPagina l WHERE l.nombAsig = :nombAsig")})
public class LoginPagina implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiPagi")
    private Integer codiPagi;
    @Size(max = 50)
    @Column(name = "nombPagi")
    private String nombPagi;
    @Size(max = 200)
    @Column(name = "htmlPagi")
    private String htmlPagi;
    @Size(max = 30)
    @Column(name = "iclass")
    private String iclass;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "html")
    private String html;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipoMenu")
    private Character tipoMenu;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codmas")
    private int codmas;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "nombAsig")
    private String nombAsig;

    public LoginPagina() {
    }

    public LoginPagina(Integer codiPagi) {
        this.codiPagi = codiPagi;
    }

    public LoginPagina(Integer codiPagi, String html, Character tipoMenu, int codmas, String nombAsig) {
        this.codiPagi = codiPagi;
        this.html = html;
        this.tipoMenu = tipoMenu;
        this.codmas = codmas;
        this.nombAsig = nombAsig;
    }

    public Integer getCodiPagi() {
        return codiPagi;
    }

    public void setCodiPagi(Integer codiPagi) {
        this.codiPagi = codiPagi;
    }

    public String getNombPagi() {
        return nombPagi;
    }

    public void setNombPagi(String nombPagi) {
        this.nombPagi = nombPagi;
    }

    public String getHtmlPagi() {
        return htmlPagi;
    }

    public void setHtmlPagi(String htmlPagi) {
        this.htmlPagi = htmlPagi;
    }

    public String getIclass() {
        return iclass;
    }

    public void setIclass(String iclass) {
        this.iclass = iclass;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public Character getTipoMenu() {
        return tipoMenu;
    }

    public void setTipoMenu(Character tipoMenu) {
        this.tipoMenu = tipoMenu;
    }

    public int getCodmas() {
        return codmas;
    }

    public void setCodmas(int codmas) {
        this.codmas = codmas;
    }

    public String getNombAsig() {
        return nombAsig;
    }

    public void setNombAsig(String nombAsig) {
        this.nombAsig = nombAsig;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiPagi != null ? codiPagi.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof LoginPagina)) {
            return false;
        }
        LoginPagina other = (LoginPagina) object;
        if ((this.codiPagi == null && other.codiPagi != null) || (this.codiPagi != null && !this.codiPagi.equals(other.codiPagi))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.login.dto.LoginPagina[ codiPagi=" + codiPagi + " ]";
    }
    
}
