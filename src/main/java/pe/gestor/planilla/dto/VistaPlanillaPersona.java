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
@Table(name = "vista_planilla_persona")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "VistaPlanillaPersona.findAll", query = "SELECT v FROM VistaPlanillaPersona v"),
        @NamedQuery(name = "VistaPlanillaPersona.findByCodiPers", query = "SELECT v FROM VistaPlanillaPersona v WHERE v.codiPers = :codiPers"),
        @NamedQuery(name = "VistaPlanillaPersona.findByNombTipoDoc", query = "SELECT v FROM VistaPlanillaPersona v WHERE v.nombTipoDoc = :nombTipoDoc"),
        @NamedQuery(name = "VistaPlanillaPersona.findByNumeDocu", query = "SELECT v FROM VistaPlanillaPersona v WHERE v.numeDocu = :numeDocu"),
        @NamedQuery(name = "VistaPlanillaPersona.findByNombreCompleto", query = "SELECT v FROM VistaPlanillaPersona v WHERE v.nombreCompleto = :nombreCompleto"),
        @NamedQuery(name = "VistaPlanillaPersona.findByActiPers", query = "SELECT v FROM VistaPlanillaPersona v WHERE v.actiPers = :actiPers") })
public class VistaPlanillaPersona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codiPers")
    private int codiPers;
    @Size(max = 50)
    @Column(name = "nombTipoDoc")
    private String nombTipoDoc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "numeDocu")
    private String numeDocu;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 122)
    @Column(name = "nombre_completo")
    private String nombreCompleto;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiPers")
    private boolean actiPers;

    public VistaPlanillaPersona() {
    }

    public int getCodiPers() {
        return codiPers;
    }

    public void setCodiPers(int codiPers) {
        this.codiPers = codiPers;
    }

    public String getNombTipoDoc() {
        return nombTipoDoc;
    }

    public void setNombTipoDoc(String nombTipoDoc) {
        this.nombTipoDoc = nombTipoDoc;
    }

    public String getNumeDocu() {
        return numeDocu;
    }

    public void setNumeDocu(String numeDocu) {
        this.numeDocu = numeDocu;
    }

    public String getNombreCompleto() {
        return nombreCompleto;
    }

    public void setNombreCompleto(String nombreCompleto) {
        this.nombreCompleto = nombreCompleto;
    }

    public boolean getActiPers() {
        return actiPers;
    }

    public void setActiPers(boolean actiPers) {
        this.actiPers = actiPers;
    }

}
