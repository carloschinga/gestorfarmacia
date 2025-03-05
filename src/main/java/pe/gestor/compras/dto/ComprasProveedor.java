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
@Table(name = "compras_proveedor")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComprasProveedor.findAll", query = "SELECT c FROM ComprasProveedor c"),
    @NamedQuery(name = "ComprasProveedor.findByCodiProv", query = "SELECT c FROM ComprasProveedor c WHERE c.codiProv = :codiProv"),
    @NamedQuery(name = "ComprasProveedor.findByNrucProv", query = "SELECT c FROM ComprasProveedor c WHERE c.nrucProv = :nrucProv"),
    @NamedQuery(name = "ComprasProveedor.findByNombProv", query = "SELECT c FROM ComprasProveedor c WHERE c.nombProv = :nombProv"),
    @NamedQuery(name = "ComprasProveedor.findByDireProv", query = "SELECT c FROM ComprasProveedor c WHERE c.direProv = :direProv"),
    @NamedQuery(name = "ComprasProveedor.findByCeluProv", query = "SELECT c FROM ComprasProveedor c WHERE c.celuProv = :celuProv"),
    @NamedQuery(name = "ComprasProveedor.findByObsvProv", query = "SELECT c FROM ComprasProveedor c WHERE c.obsvProv = :obsvProv"),
    @NamedQuery(name = "ComprasProveedor.findByEstdProv", query = "SELECT c FROM ComprasProveedor c WHERE c.estdProv = :estdProv")})
public class ComprasProveedor implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiProv")
    private Integer codiProv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 11)
    @Column(name = "nrucProv")
    private String nrucProv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "nombProv")
    private String nombProv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "direProv")
    private String direProv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 9)
    @Column(name = "celuProv")
    private String celuProv;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 150)
    @Column(name = "obsvProv")
    private String obsvProv;
    @Basic(optional = false)
    @NotNull
    @Column(name = "estdProv")
    private short estdProv;

    public ComprasProveedor() {
    }

    public ComprasProveedor(Integer codiProv) {
        this.codiProv = codiProv;
    }

    public ComprasProveedor(Integer codiProv, String nrucProv, String nombProv, String direProv, String celuProv, String obsvProv, short estdProv) {
        this.codiProv = codiProv;
        this.nrucProv = nrucProv;
        this.nombProv = nombProv;
        this.direProv = direProv;
        this.celuProv = celuProv;
        this.obsvProv = obsvProv;
        this.estdProv = estdProv;
    }

    public Integer getCodiProv() {
        return codiProv;
    }

    public void setCodiProv(Integer codiProv) {
        this.codiProv = codiProv;
    }

    public String getNrucProv() {
        return nrucProv;
    }

    public void setNrucProv(String nrucProv) {
        this.nrucProv = nrucProv;
    }

    public String getNombProv() {
        return nombProv;
    }

    public void setNombProv(String nombProv) {
        this.nombProv = nombProv;
    }

    public String getDireProv() {
        return direProv;
    }

    public void setDireProv(String direProv) {
        this.direProv = direProv;
    }

    public String getCeluProv() {
        return celuProv;
    }

    public void setCeluProv(String celuProv) {
        this.celuProv = celuProv;
    }

    public String getObsvProv() {
        return obsvProv;
    }

    public void setObsvProv(String obsvProv) {
        this.obsvProv = obsvProv;
    }

    public short getEstdProv() {
        return estdProv;
    }

    public void setEstdProv(short estdProv) {
        this.estdProv = estdProv;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiProv != null ? codiProv.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComprasProveedor)) {
            return false;
        }
        ComprasProveedor other = (ComprasProveedor) object;
        if ((this.codiProv == null && other.codiProv != null) || (this.codiProv != null && !this.codiProv.equals(other.codiProv))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.compras.dto.ComprasProveedor[ codiProv=" + codiProv + " ]";
    }
    
}
