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
@Table(name = "vista_login_usuario_rol")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaLoginUsuarioRol.findAll", query = "SELECT v FROM VistaLoginUsuarioRol v"),
    @NamedQuery(name = "VistaLoginUsuarioRol.findByCodiUsua", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.codiUsua = :codiUsua"),
    @NamedQuery(name = "VistaLoginUsuarioRol.validar", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.logiUsua = :logiUsua and v.passUsua = :passUsua"),
    @NamedQuery(name = "VistaLoginUsuarioRol.findByLogiUsua", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.logiUsua = :logiUsua"),
    @NamedQuery(name = "VistaLoginUsuarioRol.findByPassUsua", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.passUsua = :passUsua"),
    @NamedQuery(name = "VistaLoginUsuarioRol.findByNdniUsua", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.ndniUsua = :ndniUsua"),
    @NamedQuery(name = "VistaLoginUsuarioRol.findByNombUsua", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.nombUsua = :nombUsua"),
    @NamedQuery(name = "VistaLoginUsuarioRol.findByCeluUsua", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.celuUsua = :celuUsua"),
    @NamedQuery(name = "VistaLoginUsuarioRol.findByCodiRol", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.codiRol = :codiRol"),
    @NamedQuery(name = "VistaLoginUsuarioRol.findByActvUsua", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.actvUsua = :actvUsua"),
    @NamedQuery(name = "VistaLoginUsuarioRol.findByNombRol", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.nombRol = :nombRol"),
    @NamedQuery(name = "VistaLoginUsuarioRol.findByAdmiRol", query = "SELECT v FROM VistaLoginUsuarioRol v WHERE v.admiRol = :admiRol")})
public class VistaLoginUsuarioRol implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiUsua")
    private int codiUsua;
    @Size(max = 100)
    @Column(name = "logiUsua")
    private String logiUsua;
    @Size(max = 100)
    @Column(name = "passUsua")
    private String passUsua;
    @Size(max = 8)
    @Column(name = "ndniUsua")
    private String ndniUsua;
    @Size(max = 100)
    @Column(name = "nombUsua")
    private String nombUsua;
    @Size(max = 9)
    @Column(name = "celuUsua")
    private String celuUsua;
    @Column(name = "codiRol")
    private Integer codiRol;
    @Column(name = "actvUsua")
    private Boolean actvUsua;
    @Size(max = 10)
    @Column(name = "nombRol")
    private String nombRol;
    @Column(name = "admiRol")
    private Boolean admiRol;

    public VistaLoginUsuarioRol() {
    }

    public int getCodiUsua() {
        return codiUsua;
    }

    public void setCodiUsua(int codiUsua) {
        this.codiUsua = codiUsua;
    }

    public String getLogiUsua() {
        return logiUsua;
    }

    public void setLogiUsua(String logiUsua) {
        this.logiUsua = logiUsua;
    }

    public String getPassUsua() {
        return passUsua;
    }

    public void setPassUsua(String passUsua) {
        this.passUsua = passUsua;
    }

    public String getNdniUsua() {
        return ndniUsua;
    }

    public void setNdniUsua(String ndniUsua) {
        this.ndniUsua = ndniUsua;
    }

    public String getNombUsua() {
        return nombUsua;
    }

    public void setNombUsua(String nombUsua) {
        this.nombUsua = nombUsua;
    }

    public String getCeluUsua() {
        return celuUsua;
    }

    public void setCeluUsua(String celuUsua) {
        this.celuUsua = celuUsua;
    }

    public Integer getCodiRol() {
        return codiRol;
    }

    public void setCodiRol(Integer codiRol) {
        this.codiRol = codiRol;
    }

    public Boolean getActvUsua() {
        return actvUsua;
    }

    public void setActvUsua(Boolean actvUsua) {
        this.actvUsua = actvUsua;
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
    
}
