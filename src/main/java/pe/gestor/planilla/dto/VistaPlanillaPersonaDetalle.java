/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
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
 * @author san21
 */
@Entity
@Table(name = "vista_planilla_persona_detalle")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findAll", query = "SELECT v FROM VistaPlanillaPersonaDetalle v"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByNumeDocu", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.numeDocu = :numeDocu"),
    @NamedQuery(name = "VistaPlanillaPersonaDetalle.findByNombreCompleto", query = "SELECT v FROM VistaPlanillaPersonaDetalle v WHERE v.nombreCompleto = :nombreCompleto")})
public class VistaPlanillaPersonaDetalle implements Serializable {

    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "numeDocu")
    @Id
    private String numeDocu;
    @Size(max = 122)
    @Column(name = "nombre_completo")
    private String nombreCompleto;

    public VistaPlanillaPersonaDetalle() {
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
    
}
