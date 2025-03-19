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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
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
@Table(name = "ventas_cliente")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VentasCliente.findAll", query = "SELECT v FROM VentasCliente v"),
    @NamedQuery(name = "VentasCliente.findByCodiClie", query = "SELECT v FROM VentasCliente v WHERE v.codiClie = :codiClie"),
    @NamedQuery(name = "VentasCliente.findByCodiTipoDoc", query = "SELECT v FROM VentasCliente v WHERE v.codiTipoDoc = :codiTipoDoc"),
    @NamedQuery(name = "VentasCliente.findByNumeDoc", query = "SELECT v FROM VentasCliente v WHERE v.numeDoc = :numeDoc"),
    @NamedQuery(name = "VentasCliente.findByFonoClie", query = "SELECT v FROM VentasCliente v WHERE v.fonoClie = :fonoClie"),
    @NamedQuery(name = "VentasCliente.findByMailClie", query = "SELECT v FROM VentasCliente v WHERE v.mailClie = :mailClie"),
    @NamedQuery(name = "VentasCliente.findByActiClie", query = "SELECT v FROM VentasCliente v WHERE v.actiClie = :actiClie")})
public class VentasCliente implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiClie")
    private Integer codiClie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codiTipoDoc")
    private String codiTipoDoc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "numeDoc")
    private String numeDoc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "fonoClie")
    private String fonoClie;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "mailClie")
    private String mailClie;
    @Basic(optional = false)
    @NotNull
    @Lob
    @Size(min = 1, max = 65535)
    @Column(name = "direClie")
    private String direClie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiClie")
    private boolean actiClie;

    public VentasCliente() {
    }

    public VentasCliente(Integer codiClie) {
        this.codiClie = codiClie;
    }

    public VentasCliente(Integer codiClie, String codiTipoDoc, String numeDoc, String fonoClie, String mailClie, String direClie, boolean actiClie) {
        this.codiClie = codiClie;
        this.codiTipoDoc = codiTipoDoc;
        this.numeDoc = numeDoc;
        this.fonoClie = fonoClie;
        this.mailClie = mailClie;
        this.direClie = direClie;
        this.actiClie = actiClie;
    }

    public Integer getCodiClie() {
        return codiClie;
    }

    public void setCodiClie(Integer codiClie) {
        this.codiClie = codiClie;
    }

    public String getCodiTipoDoc() {
        return codiTipoDoc;
    }

    public void setCodiTipoDoc(String codiTipoDoc) {
        this.codiTipoDoc = codiTipoDoc;
    }

    public String getNumeDoc() {
        return numeDoc;
    }

    public void setNumeDoc(String numeDoc) {
        this.numeDoc = numeDoc;
    }

    public String getFonoClie() {
        return fonoClie;
    }

    public void setFonoClie(String fonoClie) {
        this.fonoClie = fonoClie;
    }

    public String getMailClie() {
        return mailClie;
    }

    public void setMailClie(String mailClie) {
        this.mailClie = mailClie;
    }

    public String getDireClie() {
        return direClie;
    }

    public void setDireClie(String direClie) {
        this.direClie = direClie;
    }

    public boolean getActiClie() {
        return actiClie;
    }

    public void setActiClie(boolean actiClie) {
        this.actiClie = actiClie;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiClie != null ? codiClie.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof VentasCliente)) {
            return false;
        }
        VentasCliente other = (VentasCliente) object;
        if ((this.codiClie == null && other.codiClie != null) || (this.codiClie != null && !this.codiClie.equals(other.codiClie))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.ventas.dto.VentasCliente[ codiClie=" + codiClie + " ]";
    }
    
}
