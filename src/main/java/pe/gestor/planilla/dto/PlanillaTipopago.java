/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pe.gestor.planilla.dto;

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
@Table(name = "planilla_tipopago")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PlanillaTipopago.findAll", query = "SELECT p FROM PlanillaTipopago p"),
    @NamedQuery(name = "PlanillaTipopago.findByCodiTipoPago", query = "SELECT p FROM PlanillaTipopago p WHERE p.codiTipoPago = :codiTipoPago"),
    @NamedQuery(name = "PlanillaTipopago.findByNombTipoPago", query = "SELECT p FROM PlanillaTipopago p WHERE p.nombTipoPago = :nombTipoPago")})
public class PlanillaTipopago implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 1)
    @Column(name = "codiTipoPago")
    private String codiTipoPago;
    @Size(max = 50)
    @Column(name = "nombTipoPago")
    private String nombTipoPago;

    public PlanillaTipopago() {
    }

    public PlanillaTipopago(String codiTipoPago) {
        this.codiTipoPago = codiTipoPago;
    }

    public String getCodiTipoPago() {
        return codiTipoPago;
    }

    public void setCodiTipoPago(String codiTipoPago) {
        this.codiTipoPago = codiTipoPago;
    }

    public String getNombTipoPago() {
        return nombTipoPago;
    }

    public void setNombTipoPago(String nombTipoPago) {
        this.nombTipoPago = nombTipoPago;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiTipoPago != null ? codiTipoPago.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaTipopago)) {
            return false;
        }
        PlanillaTipopago other = (PlanillaTipopago) object;
        if ((this.codiTipoPago == null && other.codiTipoPago != null) || (this.codiTipoPago != null && !this.codiTipoPago.equals(other.codiTipoPago))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaTipopago[ codiTipoPago=" + codiTipoPago + " ]";
    }
    
}
