/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.planilla.dto;

import java.io.Serializable;
import java.math.BigDecimal;
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
@Table(name = "vista_planilla_persona_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findAll", query = "SELECT v FROM VistaPlanillaPersonaDetalle v"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByCodiPers", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.codiPers = :codiPers"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByNumeDocu", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.numeDocu = :numeDocu"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByNombPers", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.nombPers = :nombPers"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByMontRemuPers", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.montRemuPers = :montRemuPers"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByCodiAFP", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.codiAFP = :codiAFP"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByNombAFP", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.nombAFP = :nombAFP"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByMontAFP", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.montAFP = :montAFP"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findBySeguAFP", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.seguAFP = :seguAFP"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByComiAFP", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.comiAFP = :comiAFP"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByActiPers", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.actiPers = :actiPers"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByAsigFamiPers", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.asigFamiPers = :asigFamiPers"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findBySnpPers", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.snpPers = :snpPers"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByCodiPlant", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.codiPlant = :codiPlant"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByNombPlant", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.nombPlant = :nombPlant")})
public class VistaPlanillaPersonaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
     @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiPers")
    private int codiPers;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "numeDocu")
    private String numeDocu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nombPers")
    private String nombPers;
    // @Max(value=?)  @Min(value=?)//if you know range of your decimal fields consider using these annotations to enforce field validation
    @Column(name = "montRemuPers")
    private BigDecimal montRemuPers;
    @Column(name = "codiAFP")
    private Integer codiAFP;
    @Size(max = 150)
    @Column(name = "nombAFP")
    private String nombAFP;
    @Column(name = "montAFP")
    private BigDecimal montAFP;
    @Column(name = "seguAFP")
    private BigDecimal seguAFP;
    @Column(name = "comiAFP")
    private BigDecimal comiAFP;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiPers")
    private boolean actiPers;
    @Column(name = "asigFamiPers")
    private Integer asigFamiPers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "snpPers")
    private boolean snpPers;
    @Column(name = "codiPlant")
    private Integer codiPlant;
    @Size(max = 50)
    @Column(name = "nombPlant")
    private String nombPlant;

    public VistaPlanillaPersonaDetalle() {
    }

    public int getCodiPers() {
        return codiPers;
    }

    public void setCodiPers(int codiPers) {
        this.codiPers = codiPers;
    }

    public String getNumeDocu() {
        return numeDocu;
    }

    public void setNumeDocu(String numeDocu) {
        this.numeDocu = numeDocu;
    }

    public String getNombPers() {
        return nombPers;
    }

    public void setNombPers(String nombPers) {
        this.nombPers = nombPers;
    }

    public BigDecimal getMontRemuPers() {
        return montRemuPers;
    }

    public void setMontRemuPers(BigDecimal montRemuPers) {
        this.montRemuPers = montRemuPers;
    }

    public Integer getCodiAFP() {
        return codiAFP;
    }

    public void setCodiAFP(Integer codiAFP) {
        this.codiAFP = codiAFP;
    }

    public String getNombAFP() {
        return nombAFP;
    }

    public void setNombAFP(String nombAFP) {
        this.nombAFP = nombAFP;
    }

    public BigDecimal getMontAFP() {
        return montAFP;
    }

    public void setMontAFP(BigDecimal montAFP) {
        this.montAFP = montAFP;
    }

    public BigDecimal getSeguAFP() {
        return seguAFP;
    }

    public void setSeguAFP(BigDecimal seguAFP) {
        this.seguAFP = seguAFP;
    }

    public BigDecimal getComiAFP() {
        return comiAFP;
    }

    public void setComiAFP(BigDecimal comiAFP) {
        this.comiAFP = comiAFP;
    }

    public boolean getActiPers() {
        return actiPers;
    }

    public void setActiPers(boolean actiPers) {
        this.actiPers = actiPers;
    }

    public Integer getAsigFamiPers() {
        return asigFamiPers;
    }

    public void setAsigFamiPers(Integer asigFamiPers) {
        this.asigFamiPers = asigFamiPers;
    }

    public boolean getSnpPers() {
        return snpPers;
    }

    public void setSnpPers(boolean snpPers) {
        this.snpPers = snpPers;
    }

    public Integer getCodiPlant() {
        return codiPlant;
    }

    public void setCodiPlant(Integer codiPlant) {
        this.codiPlant = codiPlant;
    }

    public String getNombPlant() {
        return nombPlant;
    }

    public void setNombPlant(String nombPlant) {
        this.nombPlant = nombPlant;
    }
    
}
