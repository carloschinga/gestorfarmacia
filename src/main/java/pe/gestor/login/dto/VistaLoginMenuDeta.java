/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dto;

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
@Table(name = "vista_login_menu_deta")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaLoginMenuDeta.findAll", query = "SELECT v FROM VistaLoginMenuDeta v"),
    @NamedQuery(name = "VistaLoginMenuDeta.findByCodiPagi", query = "SELECT v FROM VistaLoginMenuDeta v WHERE v.codiPagi = :codiPagi"),
    @NamedQuery(name = "VistaLoginMenuDeta.findByNombPagi", query = "SELECT v FROM VistaLoginMenuDeta v WHERE v.nombPagi = :nombPagi"),
    @NamedQuery(name = "VistaLoginMenuDeta.findByCodiRol", query = "SELECT v FROM VistaLoginMenuDeta v WHERE v.codiRol = :codiRol"),
    @NamedQuery(name = "VistaLoginMenuDeta.findByCodmas", query = "SELECT v FROM VistaLoginMenuDeta v WHERE v.codmas = :codmas"),
    @NamedQuery(name = "VistaLoginMenuDeta.findByAsigPerm", query = "SELECT v FROM VistaLoginMenuDeta v WHERE v.asigPerm = :asigPerm"),
    @NamedQuery(name = "VistaLoginMenuDeta.findByTipoMenu", query = "SELECT v FROM VistaLoginMenuDeta v WHERE v.tipoMenu = :tipoMenu"),
    @NamedQuery(name = "VistaLoginMenuDeta.findByHtml", query = "SELECT v FROM VistaLoginMenuDeta v WHERE v.html = :html"),
    @NamedQuery(name = "VistaLoginMenuDeta.findByIclass", query = "SELECT v FROM VistaLoginMenuDeta v WHERE v.iclass = :iclass"),
    @NamedQuery(name = "VistaLoginMenuDeta.findByCodiUsua", query = "SELECT v FROM VistaLoginMenuDeta v WHERE v.codiUsua = :codiUsua")})
public class VistaLoginMenuDeta implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiPagi")
    private int codiPagi;
    @Size(max = 50)
    @Column(name = "nombPagi")
    private String nombPagi;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiRol")
    private int codiRol;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codmas")
    private int codmas;
    @Basic(optional = false)
    @NotNull
    @Column(name = "asigPerm")
    private int asigPerm;
    @Basic(optional = false)
    @NotNull
    @Column(name = "tipoMenu")
    private Character tipoMenu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "html")
    private String html;
    @Size(max = 30)
    @Column(name = "iclass")
    private String iclass;
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiUsua")
    private int codiUsua;

    public VistaLoginMenuDeta() {
    }

    public int getCodiPagi() {
        return codiPagi;
    }

    public void setCodiPagi(int codiPagi) {
        this.codiPagi = codiPagi;
    }

    public String getNombPagi() {
        return nombPagi;
    }

    public void setNombPagi(String nombPagi) {
        this.nombPagi = nombPagi;
    }

    public int getCodiRol() {
        return codiRol;
    }

    public void setCodiRol(int codiRol) {
        this.codiRol = codiRol;
    }

    public int getCodmas() {
        return codmas;
    }

    public void setCodmas(int codmas) {
        this.codmas = codmas;
    }

    public int getAsigPerm() {
        return asigPerm;
    }

    public void setAsigPerm(int asigPerm) {
        this.asigPerm = asigPerm;
    }

    public Character getTipoMenu() {
        return tipoMenu;
    }

    public void setTipoMenu(Character tipoMenu) {
        this.tipoMenu = tipoMenu;
    }

    public String getHtml() {
        return html;
    }

    public void setHtml(String html) {
        this.html = html;
    }

    public String getIclass() {
        return iclass;
    }

    public void setIclass(String iclass) {
        this.iclass = iclass;
    }

    public int getCodiUsua() {
        return codiUsua;
    }

    public void setCodiUsua(int codiUsua) {
        this.codiUsua = codiUsua;
    }
    
}
