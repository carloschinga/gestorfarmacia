package pe.gestor.planilla.dto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Adria
 */
@Entity
@Table(name = "planilla_persona")
@XmlRootElement
@NamedQueries({
        @NamedQuery(name = "PlanillaPersona.findAll", query = "SELECT p FROM PlanillaPersona p"),
        @NamedQuery(name = "PlanillaPersona.findByCodiPers", query = "SELECT p FROM PlanillaPersona p WHERE p.codiPers = :codiPers"),
        @NamedQuery(name = "PlanillaPersona.findByCodiTipoDoc", query = "SELECT p FROM PlanillaPersona p WHERE p.codiTipoDoc = :codiTipoDoc"),
        @NamedQuery(name = "PlanillaPersona.findByNumeDocu", query = "SELECT p FROM PlanillaPersona p WHERE p.numeDocu = :numeDocu and p.codiTipoDoc=:codiTipoDoc"),
        @NamedQuery(name = "PlanillaPersona.findByCodiPaisEmis", query = "SELECT p FROM PlanillaPersona p WHERE p.codiPaisEmis = :codiPaisEmis"),
        @NamedQuery(name = "PlanillaPersona.findByAppaPers", query = "SELECT p FROM PlanillaPersona p WHERE p.appaPers = :appaPers"),
        @NamedQuery(name = "PlanillaPersona.findByApmaPers", query = "SELECT p FROM PlanillaPersona p WHERE p.apmaPers = :apmaPers"),
        @NamedQuery(name = "PlanillaPersona.findByNombPers", query = "SELECT p FROM PlanillaPersona p WHERE p.nombPers = :nombPers"),
        @NamedQuery(name = "PlanillaPersona.findBySexoPers", query = "SELECT p FROM PlanillaPersona p WHERE p.sexoPers = :sexoPers"),
        @NamedQuery(name = "PlanillaPersona.findByCodiNaci", query = "SELECT p FROM PlanillaPersona p WHERE p.codiNaci = :codiNaci"),
        @NamedQuery(name = "PlanillaPersona.findByCodiCLDN", query = "SELECT p FROM PlanillaPersona p WHERE p.codiCLDN = :codiCLDN"),
        @NamedQuery(name = "PlanillaPersona.findByNumeCelu", query = "SELECT p FROM PlanillaPersona p WHERE p.numeCelu = :numeCelu"),
        @NamedQuery(name = "PlanillaPersona.findByMailPers", query = "SELECT p FROM PlanillaPersona p WHERE p.mailPers = :mailPers"),
        @NamedQuery(name = "PlanillaPersona.findByCodiTipoVia", query = "SELECT p FROM PlanillaPersona p WHERE p.codiTipoVia = :codiTipoVia"),
        @NamedQuery(name = "PlanillaPersona.findByNombVia", query = "SELECT p FROM PlanillaPersona p WHERE p.nombVia = :nombVia"),
        @NamedQuery(name = "PlanillaPersona.findByNumeVia", query = "SELECT p FROM PlanillaPersona p WHERE p.numeVia = :numeVia"),
        @NamedQuery(name = "PlanillaPersona.findByDepaPers", query = "SELECT p FROM PlanillaPersona p WHERE p.depaPers = :depaPers"),
        @NamedQuery(name = "PlanillaPersona.findByIntePers", query = "SELECT p FROM PlanillaPersona p WHERE p.intePers = :intePers"),
        @NamedQuery(name = "PlanillaPersona.findByManzPers", query = "SELECT p FROM PlanillaPersona p WHERE p.manzPers = :manzPers"),
        @NamedQuery(name = "PlanillaPersona.findByLotePers", query = "SELECT p FROM PlanillaPersona p WHERE p.lotePers = :lotePers"),
        @NamedQuery(name = "PlanillaPersona.findByKiloPers", query = "SELECT p FROM PlanillaPersona p WHERE p.kiloPers = :kiloPers"),
        @NamedQuery(name = "PlanillaPersona.findByBlocPers", query = "SELECT p FROM PlanillaPersona p WHERE p.blocPers = :blocPers"),
        @NamedQuery(name = "PlanillaPersona.findByEtapPers", query = "SELECT p FROM PlanillaPersona p WHERE p.etapPers = :etapPers"),
        @NamedQuery(name = "PlanillaPersona.findByTipoZona", query = "SELECT p FROM PlanillaPersona p WHERE p.tipoZona = :tipoZona"),
        @NamedQuery(name = "PlanillaPersona.findByNombZona", query = "SELECT p FROM PlanillaPersona p WHERE p.nombZona = :nombZona"),
        @NamedQuery(name = "PlanillaPersona.findByRefeZona", query = "SELECT p FROM PlanillaPersona p WHERE p.refeZona = :refeZona"),
        @NamedQuery(name = "PlanillaPersona.findByCodiUbig", query = "SELECT p FROM PlanillaPersona p WHERE p.codiUbig = :codiUbig"),
        @NamedQuery(name = "PlanillaPersona.findByCodiTipoVia2", query = "SELECT p FROM PlanillaPersona p WHERE p.codiTipoVia2 = :codiTipoVia2"),
        @NamedQuery(name = "PlanillaPersona.findByNombVia2", query = "SELECT p FROM PlanillaPersona p WHERE p.nombVia2 = :nombVia2"),
        @NamedQuery(name = "PlanillaPersona.findByNumeVia2", query = "SELECT p FROM PlanillaPersona p WHERE p.numeVia2 = :numeVia2"),
        @NamedQuery(name = "PlanillaPersona.findByDepaPers2", query = "SELECT p FROM PlanillaPersona p WHERE p.depaPers2 = :depaPers2"),
        @NamedQuery(name = "PlanillaPersona.findByIntePers2", query = "SELECT p FROM PlanillaPersona p WHERE p.intePers2 = :intePers2"),
        @NamedQuery(name = "PlanillaPersona.findByManzPers2", query = "SELECT p FROM PlanillaPersona p WHERE p.manzPers2 = :manzPers2"),
        @NamedQuery(name = "PlanillaPersona.findByLotePers2", query = "SELECT p FROM PlanillaPersona p WHERE p.lotePers2 = :lotePers2"),
        @NamedQuery(name = "PlanillaPersona.findByKiloPers2", query = "SELECT p FROM PlanillaPersona p WHERE p.kiloPers2 = :kiloPers2"),
        @NamedQuery(name = "PlanillaPersona.findByBlocPers2", query = "SELECT p FROM PlanillaPersona p WHERE p.blocPers2 = :blocPers2"),
        @NamedQuery(name = "PlanillaPersona.findByEtapPers2", query = "SELECT p FROM PlanillaPersona p WHERE p.etapPers2 = :etapPers2"),
        @NamedQuery(name = "PlanillaPersona.findByTipoZona2", query = "SELECT p FROM PlanillaPersona p WHERE p.tipoZona2 = :tipoZona2"),
        @NamedQuery(name = "PlanillaPersona.findByNombZona2", query = "SELECT p FROM PlanillaPersona p WHERE p.nombZona2 = :nombZona2"),
        @NamedQuery(name = "PlanillaPersona.findByRefeZona2", query = "SELECT p FROM PlanillaPersona p WHERE p.refeZona2 = :refeZona2"),
        @NamedQuery(name = "PlanillaPersona.findByCodiUbig2", query = "SELECT p FROM PlanillaPersona p WHERE p.codiUbig2 = :codiUbig2"),
        @NamedQuery(name = "PlanillaPersona.findByIndcCentAsis", query = "SELECT p FROM PlanillaPersona p WHERE p.indcCentAsis = :indcCentAsis"),
        @NamedQuery(name = "PlanillaPersona.findByCupsPers", query = "SELECT p FROM PlanillaPersona p WHERE p.cupsPers = :cupsPers"),
        @NamedQuery(name = "PlanillaPersona.findBySctrPers", query = "SELECT p FROM PlanillaPersona p WHERE p.sctrPers = :sctrPers"),
        @NamedQuery(name = "PlanillaPersona.findBySnpPers", query = "SELECT p FROM PlanillaPersona p WHERE p.snpPers = :snpPers"),
        @NamedQuery(name = "PlanillaPersona.findByCodiAFP", query = "SELECT p FROM PlanillaPersona p WHERE p.codiAFP = :codiAFP"),
        @NamedQuery(name = "PlanillaPersona.findByMontRemuPers", query = "SELECT p FROM PlanillaPersona p WHERE p.montRemuPers = :montRemuPers"),
        @NamedQuery(name = "PlanillaPersona.findByNumeRucTrab", query = "SELECT p FROM PlanillaPersona p WHERE p.numeRucTrab = :numeRucTrab"),
        @NamedQuery(name = "PlanillaPersona.findByAsigFamiPers", query = "SELECT p FROM PlanillaPersona p WHERE p.asigFamiPers = :asigFamiPers"),
        @NamedQuery(name = "PlanillaPersona.findByCodiEntiBanc", query = "SELECT p FROM PlanillaPersona p WHERE p.codiEntiBanc = :codiEntiBanc"),
        @NamedQuery(name = "PlanillaPersona.findByNumeCuen", query = "SELECT p FROM PlanillaPersona p WHERE p.numeCuen = :numeCuen"),
        @NamedQuery(name = "PlanillaPersona.findByCodiPlant", query = "SELECT p FROM PlanillaPersona p WHERE p.codiPlant = :codiPlant"),
        @NamedQuery(name = "PlanillaPersona.findByActiPers", query = "SELECT p FROM PlanillaPersona p WHERE p.actiPers = :actiPers"),
        @NamedQuery(name = "PlanillaPersona.findByCodiEstaCivil", query = "SELECT p FROM PlanillaPersona p WHERE p.codiEstaCivil = :codiEstaCivil"),
        @NamedQuery(name = "PlanillaPersona.findByCodiArea", query = "SELECT p FROM PlanillaPersona p WHERE p.codiArea = :codiArea"),
        @NamedQuery(name = "PlanillaPersona.findByCodiCargo", query = "SELECT p FROM PlanillaPersona p WHERE p.codiCargo = :codiCargo"),
        @NamedQuery(name = "PlanillaPersona.findByCodiCateOcup", query = "SELECT p FROM PlanillaPersona p WHERE p.codiCateOcup = :codiCateOcup"),
        @NamedQuery(name = "PlanillaPersona.findByCodiPeriRemu", query = "SELECT p FROM PlanillaPersona p WHERE p.codiPeriRemu = :codiPeriRemu"),
        @NamedQuery(name = "PlanillaPersona.findByCodiRegiLab", query = "SELECT p FROM PlanillaPersona p WHERE p.codiRegiLab = :codiRegiLab"),
        @NamedQuery(name = "PlanillaPersona.findByCodiRegiPens", query = "SELECT p FROM PlanillaPersona p WHERE p.codiRegiPens = :codiRegiPens"),
        @NamedQuery(name = "PlanillaPersona.findByCodiRegiSal", query = "SELECT p FROM PlanillaPersona p WHERE p.codiRegiSal = :codiRegiSal"),
        @NamedQuery(name = "PlanillaPersona.findByCodiSituEdu", query = "SELECT p FROM PlanillaPersona p WHERE p.codiSituEdu = :codiSituEdu"),
        @NamedQuery(name = "PlanillaPersona.findByCodiSituEspe", query = "SELECT p FROM PlanillaPersona p WHERE p.codiSituEspe = :codiSituEspe"),
        @NamedQuery(name = "PlanillaPersona.findByCodiTipoSitu", query = "SELECT p FROM PlanillaPersona p WHERE p.codiTipoSitu = :codiTipoSitu"),
        @NamedQuery(name = "PlanillaPersona.findByCodiTipoContrato", query = "SELECT p FROM PlanillaPersona p WHERE p.codiTipoContrato = :codiTipoContrato"),
        @NamedQuery(name = "PlanillaPersona.findByCodiTipoOcup", query = "SELECT p FROM PlanillaPersona p WHERE p.codiTipoOcup = :codiTipoOcup"),
        @NamedQuery(name = "PlanillaPersona.findByCodiTipoPago", query = "SELECT p FROM PlanillaPersona p WHERE p.codiTipoPago = :codiTipoPago"),
        @NamedQuery(name = "PlanillaPersona.findByCodiTipoTrab", query = "SELECT p FROM PlanillaPersona p WHERE p.codiTipoTrab = :codiTipoTrab"),
        @NamedQuery(name = "PlanillaPersona.findByCodiJornadLab", query = "SELECT p FROM PlanillaPersona p WHERE p.codiJornadLab = :codiJornadLab"),
        @NamedQuery(name = "PlanillaPersona.findByCodiSucurs", query = "SELECT p FROM PlanillaPersona p WHERE p.codiSucurs = :codiSucurs"),
        @NamedQuery(name = "PlanillaPersona.findByFechNaci", query = "SELECT p FROM PlanillaPersona p WHERE p.fechNaci = :fechNaci"),
        @NamedQuery(name = "PlanillaPersona.findByFechIngr", query = "SELECT p FROM PlanillaPersona p WHERE p.fechIngr = :fechIngr"),
        @NamedQuery(name = "PlanillaPersona.findByCuentaCTS", query = "SELECT p FROM PlanillaPersona p WHERE p.cuentaCTS = :cuentaCTS"),
        @NamedQuery(name = "PlanillaPersona.findByCodiCuenCTS", query = "SELECT p FROM PlanillaPersona p WHERE p.codiCuenCTS = :codiCuenCTS"),
        @NamedQuery(name = "PlanillaPersona.findByFechCese", query = "SELECT p FROM PlanillaPersona p WHERE p.fechCese = :fechCese"),
        @NamedQuery(name = "PlanillaPersona.findByDiscapacidad", query = "SELECT p FROM PlanillaPersona p WHERE p.discapacidad = :discapacidad"),
        @NamedQuery(name = "PlanillaPersona.findBySindicalizado", query = "SELECT p FROM PlanillaPersona p WHERE p.sindicalizado = :sindicalizado"),
        @NamedQuery(name = "PlanillaPersona.findByStatcExonerado", query = "SELECT p FROM PlanillaPersona p WHERE p.statcExonerado = :statcExonerado"),
        @NamedQuery(name = "PlanillaPersona.findByConvenio", query = "SELECT p FROM PlanillaPersona p WHERE p.convenio = :convenio"),
        @NamedQuery(name = "PlanillaPersona.findByAportaSctr", query = "SELECT p FROM PlanillaPersona p WHERE p.aportaSctr = :aportaSctr"),
        @NamedQuery(name = "PlanillaPersona.findByPolizaSeguro", query = "SELECT p FROM PlanillaPersona p WHERE p.polizaSeguro = :polizaSeguro"),
        @NamedQuery(name = "PlanillaPersona.findByAsignacionFamiliar", query = "SELECT p FROM PlanillaPersona p WHERE p.asignacionFamiliar = :asignacionFamiliar") })
public class PlanillaPersona implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "codiPers")
    private Integer codiPers;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 2)
    @Column(name = "codiTipoDoc")
    private String codiTipoDoc;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "numeDocu")
    private String numeDocu;
    @Size(max = 3)
    @Column(name = "codiPaisEmis")
    private String codiPaisEmis;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "appaPers")
    private String appaPers;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "apmaPers")
    private String apmaPers;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 40)
    @Column(name = "nombPers")
    private String nombPers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "sexoPers")
    private Character sexoPers;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 4)
    @Column(name = "codiNaci")
    private String codiNaci;
    @Size(max = 3)
    @Column(name = "codiCLDN")
    private String codiCLDN;
    @Size(max = 9)
    @Column(name = "numeCelu")
    private String numeCelu;
    @Size(max = 50)
    @Column(name = "mailPers")
    private String mailPers;
    @Size(max = 2)
    @Column(name = "codiTipoVia")
    private String codiTipoVia;
    @Size(max = 20)
    @Column(name = "nombVia")
    private String nombVia;
    @Size(max = 4)
    @Column(name = "numeVia")
    private String numeVia;
    @Size(max = 4)
    @Column(name = "depaPers")
    private String depaPers;
    @Size(max = 4)
    @Column(name = "intePers")
    private String intePers;
    @Size(max = 4)
    @Column(name = "manzPers")
    private String manzPers;
    @Size(max = 4)
    @Column(name = "lotePers")
    private String lotePers;
    @Size(max = 4)
    @Column(name = "kiloPers")
    private String kiloPers;
    @Size(max = 4)
    @Column(name = "blocPers")
    private String blocPers;
    @Size(max = 4)
    @Column(name = "etapPers")
    private String etapPers;
    @Size(max = 2)
    @Column(name = "tipoZona")
    private String tipoZona;
    @Size(max = 20)
    @Column(name = "nombZona")
    private String nombZona;
    @Size(max = 40)
    @Column(name = "refeZona")
    private String refeZona;
    @Size(max = 6)
    @Column(name = "codiUbig")
    private String codiUbig;
    @Size(max = 2)
    @Column(name = "codiTipoVia2")
    private String codiTipoVia2;
    @Size(max = 20)
    @Column(name = "nombVia2")
    private String nombVia2;
    @Size(max = 4)
    @Column(name = "numeVia2")
    private String numeVia2;
    @Size(max = 4)
    @Column(name = "depaPers2")
    private String depaPers2;
    @Size(max = 4)
    @Column(name = "intePers2")
    private String intePers2;
    @Size(max = 4)
    @Column(name = "manzPers2")
    private String manzPers2;
    @Size(max = 4)
    @Column(name = "lotePers2")
    private String lotePers2;
    @Size(max = 4)
    @Column(name = "kiloPers2")
    private String kiloPers2;
    @Size(max = 4)
    @Column(name = "blocPers2")
    private String blocPers2;
    @Size(max = 4)
    @Column(name = "etapPers2")
    private String etapPers2;
    @Size(max = 2)
    @Column(name = "tipoZona2")
    private String tipoZona2;
    @Size(max = 20)
    @Column(name = "nombZona2")
    private String nombZona2;
    @Size(max = 40)
    @Column(name = "refeZona2")
    private String refeZona2;
    @Size(max = 6)
    @Column(name = "codiUbig2")
    private String codiUbig2;
    @Column(name = "indcCentAsis")
    private Character indcCentAsis;
    @Size(max = 12)
    @Column(name = "cupsPers")
    private String cupsPers;
    @Column(name = "sctrPers")
    private Character sctrPers;
    @Basic(optional = false)
    @NotNull
    @Column(name = "snpPers")
    private boolean snpPers;
    @Column(name = "codiAFP")
    private Integer codiAFP;
    // @Max(value=?) @Min(value=?)//if you know range of your decimal fields
    // consider using these annotations to enforce field validation
    @Column(name = "montRemuPers")
    private BigDecimal montRemuPers;
    @Size(max = 11)
    @Column(name = "numeRucTrab")
    private String numeRucTrab;
    @Column(name = "asigFamiPers")
    private Integer asigFamiPers;
    @Size(max = 3)
    @Column(name = "codiEntiBanc", nullable = true)
    private String codiEntiBanc;
    @Size(max = 20)
    @Column(name = "numeCuen", nullable = true)
    private String numeCuen;
    @Column(name = "codiPlant")
    private Integer codiPlant;
    @Basic(optional = false)
    @NotNull
    @Column(name = "actiPers")
    private boolean actiPers;
    @Column(name = "codiEstaCivil")
    private Integer codiEstaCivil;
    @Column(name = "codiArea")
    private Integer codiArea;
    @Column(name = "codiCargo")
    private Integer codiCargo;
    @Size(max = 2)
    @Column(name = "codiCateOcup")
    private String codiCateOcup;
    @Column(name = "codiPeriRemu")
    private Character codiPeriRemu;
    @Size(max = 2)
    @Column(name = "codiRegiLab")
    private String codiRegiLab;
    @Size(max = 2)
    @Column(name = "codiRegiPens")
    private String codiRegiPens;
    @Size(max = 2)
    @Column(name = "codiRegiSal")
    private String codiRegiSal;
    @Size(max = 2)
    @Column(name = "codiSituEdu")
    private String codiSituEdu;
    @Column(name = "codiSituEspe")
    private Character codiSituEspe;
    @Column(name = "codiTipoSitu")
    private Character codiTipoSitu;
    @Size(max = 2)
    @Column(name = "codiTipoContrato")
    private String codiTipoContrato;
    @Column(name = "codiTipoOcup")
    private Character codiTipoOcup;
    @Column(name = "codiTipoPago")
    private Character codiTipoPago;
    @Size(max = 2)
    @Column(name = "codiTipoTrab")
    private String codiTipoTrab;
    @Column(name = "codiJornadLab")
    private Integer codiJornadLab;
    @Column(name = "codiSucurs")
    private Integer codiSucurs;
    @Column(name = "fechNaci")
    @Temporal(TemporalType.DATE)
    private Date fechNaci;
    @Column(name = "fechIngr")
    @Temporal(TemporalType.DATE)
    private Date fechIngr;
    @Size(max = 20)
    @Column(name = "cuentaCTS", nullable = true)
    private String cuentaCTS;
    @Size(max = 3)
    @Column(name = "codiCuenCTS", nullable = true)
    private String codiCuenCTS;
    @Column(name = "fechCese")
    @Temporal(TemporalType.DATE)
    private Date fechCese;
    @Column(name = "discapacidad")
    private Boolean discapacidad;
    @Column(name = "sindicalizado")
    private Boolean sindicalizado;
    @Column(name = "statcExonerado")
    private Boolean statcExonerado;
    @Column(name = "convenio")
    private Boolean convenio;
    @Column(name = "aportaSctr")
    private Boolean aportaSctr;
    @Column(name = "polizaSeguro")
    private Boolean polizaSeguro;
    @Column(name = "asignacionFamiliar")
    private Boolean asignacionFamiliar;

    public PlanillaPersona() {
    }

    public PlanillaPersona(Integer codiPers) {
        this.codiPers = codiPers;
    }

    public PlanillaPersona(Integer codiPers, String codiTipoDoc, String numeDocu, String appaPers, String apmaPers,
            String nombPers, Character sexoPers, String codiNaci, boolean snpPers, boolean actiPers) {
        this.codiPers = codiPers;
        this.codiTipoDoc = codiTipoDoc;
        this.numeDocu = numeDocu;
        this.appaPers = appaPers;
        this.apmaPers = apmaPers;
        this.nombPers = nombPers;
        this.sexoPers = sexoPers;
        this.codiNaci = codiNaci;
        this.snpPers = snpPers;
        this.actiPers = actiPers;
    }

    public Integer getCodiPers() {
        return codiPers;
    }

    public void setCodiPers(Integer codiPers) {
        this.codiPers = codiPers;
    }

    public String getCodiTipoDoc() {
        return codiTipoDoc;
    }

    public void setCodiTipoDoc(String codiTipoDoc) {
        this.codiTipoDoc = codiTipoDoc;
    }

    public String getNumeDocu() {
        return numeDocu;
    }

    public void setNumeDocu(String numeDocu) {
        this.numeDocu = numeDocu;
    }

    public String getCodiPaisEmis() {
        return codiPaisEmis;
    }

    public void setCodiPaisEmis(String codiPaisEmis) {
        this.codiPaisEmis = codiPaisEmis;
    }

    public String getAppaPers() {
        return appaPers;
    }

    public void setAppaPers(String appaPers) {
        this.appaPers = appaPers;
    }

    public String getApmaPers() {
        return apmaPers;
    }

    public void setApmaPers(String apmaPers) {
        this.apmaPers = apmaPers;
    }

    public String getNombPers() {
        return nombPers;
    }

    public void setNombPers(String nombPers) {
        this.nombPers = nombPers;
    }

    public Character getSexoPers() {
        return sexoPers;
    }

    public void setSexoPers(Character sexoPers) {
        this.sexoPers = sexoPers;
    }

    public String getCodiNaci() {
        return codiNaci;
    }

    public void setCodiNaci(String codiNaci) {
        this.codiNaci = codiNaci;
    }

    public String getCodiCLDN() {
        return codiCLDN;
    }

    public void setCodiCLDN(String codiCLDN) {
        this.codiCLDN = codiCLDN;
    }

    public String getNumeCelu() {
        return numeCelu;
    }

    public void setNumeCelu(String numeCelu) {
        this.numeCelu = numeCelu;
    }

    public String getMailPers() {
        return mailPers;
    }

    public void setMailPers(String mailPers) {
        this.mailPers = mailPers;
    }

    public String getCodiTipoVia() {
        return codiTipoVia;
    }

    public void setCodiTipoVia(String codiTipoVia) {
        this.codiTipoVia = codiTipoVia;
    }

    public String getNombVia() {
        return nombVia;
    }

    public void setNombVia(String nombVia) {
        this.nombVia = nombVia;
    }

    public String getNumeVia() {
        return numeVia;
    }

    public void setNumeVia(String numeVia) {
        this.numeVia = numeVia;
    }

    public String getDepaPers() {
        return depaPers;
    }

    public void setDepaPers(String depaPers) {
        this.depaPers = depaPers;
    }

    public String getIntePers() {
        return intePers;
    }

    public void setIntePers(String intePers) {
        this.intePers = intePers;
    }

    public String getManzPers() {
        return manzPers;
    }

    public void setManzPers(String manzPers) {
        this.manzPers = manzPers;
    }

    public String getLotePers() {
        return lotePers;
    }

    public void setLotePers(String lotePers) {
        this.lotePers = lotePers;
    }

    public String getKiloPers() {
        return kiloPers;
    }

    public void setKiloPers(String kiloPers) {
        this.kiloPers = kiloPers;
    }

    public String getBlocPers() {
        return blocPers;
    }

    public void setBlocPers(String blocPers) {
        this.blocPers = blocPers;
    }

    public String getEtapPers() {
        return etapPers;
    }

    public void setEtapPers(String etapPers) {
        this.etapPers = etapPers;
    }

    public String getTipoZona() {
        return tipoZona;
    }

    public void setTipoZona(String tipoZona) {
        this.tipoZona = tipoZona;
    }

    public String getNombZona() {
        return nombZona;
    }

    public void setNombZona(String nombZona) {
        this.nombZona = nombZona;
    }

    public String getRefeZona() {
        return refeZona;
    }

    public void setRefeZona(String refeZona) {
        this.refeZona = refeZona;
    }

    public String getCodiUbig() {
        return codiUbig;
    }

    public void setCodiUbig(String codiUbig) {
        this.codiUbig = codiUbig;
    }

    public String getCodiTipoVia2() {
        return codiTipoVia2;
    }

    public void setCodiTipoVia2(String codiTipoVia2) {
        this.codiTipoVia2 = codiTipoVia2;
    }

    public String getNombVia2() {
        return nombVia2;
    }

    public void setNombVia2(String nombVia2) {
        this.nombVia2 = nombVia2;
    }

    public String getNumeVia2() {
        return numeVia2;
    }

    public void setNumeVia2(String numeVia2) {
        this.numeVia2 = numeVia2;
    }

    public String getDepaPers2() {
        return depaPers2;
    }

    public void setDepaPers2(String depaPers2) {
        this.depaPers2 = depaPers2;
    }

    public String getIntePers2() {
        return intePers2;
    }

    public void setIntePers2(String intePers2) {
        this.intePers2 = intePers2;
    }

    public String getManzPers2() {
        return manzPers2;
    }

    public void setManzPers2(String manzPers2) {
        this.manzPers2 = manzPers2;
    }

    public String getLotePers2() {
        return lotePers2;
    }

    public void setLotePers2(String lotePers2) {
        this.lotePers2 = lotePers2;
    }

    public String getKiloPers2() {
        return kiloPers2;
    }

    public void setKiloPers2(String kiloPers2) {
        this.kiloPers2 = kiloPers2;
    }

    public String getBlocPers2() {
        return blocPers2;
    }

    public void setBlocPers2(String blocPers2) {
        this.blocPers2 = blocPers2;
    }

    public String getEtapPers2() {
        return etapPers2;
    }

    public void setEtapPers2(String etapPers2) {
        this.etapPers2 = etapPers2;
    }

    public String getTipoZona2() {
        return tipoZona2;
    }

    public void setTipoZona2(String tipoZona2) {
        this.tipoZona2 = tipoZona2;
    }

    public String getNombZona2() {
        return nombZona2;
    }

    public void setNombZona2(String nombZona2) {
        this.nombZona2 = nombZona2;
    }

    public String getRefeZona2() {
        return refeZona2;
    }

    public void setRefeZona2(String refeZona2) {
        this.refeZona2 = refeZona2;
    }

    public String getCodiUbig2() {
        return codiUbig2;
    }

    public void setCodiUbig2(String codiUbig2) {
        this.codiUbig2 = codiUbig2;
    }

    public Character getIndcCentAsis() {
        return indcCentAsis;
    }

    public void setIndcCentAsis(Character indcCentAsis) {
        this.indcCentAsis = indcCentAsis;
    }

    public String getCupsPers() {
        return cupsPers;
    }

    public void setCupsPers(String cupsPers) {
        this.cupsPers = cupsPers;
    }

    public Character getSctrPers() {
        return sctrPers;
    }

    public void setSctrPers(Character sctrPers) {
        this.sctrPers = sctrPers;
    }

    public boolean getSnpPers() {
        return snpPers;
    }

    public void setSnpPers(boolean snpPers) {
        this.snpPers = snpPers;
    }

    public Integer getCodiAFP() {
        return codiAFP;
    }

    public void setCodiAFP(Integer codiAFP) {
        this.codiAFP = codiAFP;
    }

    public BigDecimal getMontRemuPers() {
        return montRemuPers;
    }

    public void setMontRemuPers(BigDecimal montRemuPers) {
        this.montRemuPers = montRemuPers;
    }

    public String getNumeRucTrab() {
        return numeRucTrab;
    }

    public void setNumeRucTrab(String numeRucTrab) {
        this.numeRucTrab = numeRucTrab;
    }

    public Integer getAsigFamiPers() {
        return asigFamiPers;
    }

    public void setAsigFamiPers(Integer asigFamiPers) {
        this.asigFamiPers = asigFamiPers;
    }

    public String getCodiEntiBanc() {
        return codiEntiBanc;
    }

    public void setCodiEntiBanc(String codiEntiBanc) {
        this.codiEntiBanc = codiEntiBanc;
    }

    public String getNumeCuen() {
        return numeCuen;
    }

    public void setNumeCuen(String numeCuen) {
        this.numeCuen = numeCuen;
    }

    public Integer getCodiPlant() {
        return codiPlant;
    }

    public void setCodiPlant(Integer codiPlant) {
        this.codiPlant = codiPlant;
    }

    public boolean getActiPers() {
        return actiPers;
    }

    public void setActiPers(boolean actiPers) {
        this.actiPers = actiPers;
    }

    public Integer getCodiEstaCivil() {
        return codiEstaCivil;
    }

    public void setCodiEstaCivil(Integer codiEstaCivil) {
        this.codiEstaCivil = codiEstaCivil;
    }

    public Integer getCodiArea() {
        return codiArea;
    }

    public void setCodiArea(Integer codiArea) {
        this.codiArea = codiArea;
    }

    public Integer getCodiCargo() {
        return codiCargo;
    }

    public void setCodiCargo(Integer codiCargo) {
        this.codiCargo = codiCargo;
    }

    public String getCodiCateOcup() {
        return codiCateOcup;
    }

    public void setCodiCateOcup(String codiCateOcup) {
        this.codiCateOcup = codiCateOcup;
    }

    public Character getCodiPeriRemu() {
        return codiPeriRemu;
    }

    public void setCodiPeriRemu(Character codiPeriRemu) {
        this.codiPeriRemu = codiPeriRemu;
    }

    public String getCodiRegiLab() {
        return codiRegiLab;
    }

    public void setCodiRegiLab(String codiRegiLab) {
        this.codiRegiLab = codiRegiLab;
    }

    public String getCodiRegiPens() {
        return codiRegiPens;
    }

    public void setCodiRegiPens(String codiRegiPens) {
        this.codiRegiPens = codiRegiPens;
    }

    public String getCodiRegiSal() {
        return codiRegiSal;
    }

    public void setCodiRegiSal(String codiRegiSal) {
        this.codiRegiSal = codiRegiSal;
    }

    public String getCodiSituEdu() {
        return codiSituEdu;
    }

    public void setCodiSituEdu(String codiSituEdu) {
        this.codiSituEdu = codiSituEdu;
    }

    public Character getCodiSituEspe() {
        return codiSituEspe;
    }

    public void setCodiSituEspe(Character codiSituEspe) {
        this.codiSituEspe = codiSituEspe;
    }

    public Character getCodiTipoSitu() {
        return codiTipoSitu;
    }

    public void setCodiTipoSitu(Character codiTipoSitu) {
        this.codiTipoSitu = codiTipoSitu;
    }

    public String getCodiTipoContrato() {
        return codiTipoContrato;
    }

    public void setCodiTipoContrato(String codiTipoContrato) {
        this.codiTipoContrato = codiTipoContrato;
    }

    public Character getCodiTipoOcup() {
        return codiTipoOcup;
    }

    public void setCodiTipoOcup(Character codiTipoOcup) {
        this.codiTipoOcup = codiTipoOcup;
    }

    public Character getCodiTipoPago() {
        return codiTipoPago;
    }

    public void setCodiTipoPago(Character codiTipoPago) {
        this.codiTipoPago = codiTipoPago;
    }

    public String getCodiTipoTrab() {
        return codiTipoTrab;
    }

    public void setCodiTipoTrab(String codiTipoTrab) {
        this.codiTipoTrab = codiTipoTrab;
    }

    public Integer getCodiJornadLab() {
        return codiJornadLab;
    }

    public void setCodiJornadLab(Integer codiJornadLab) {
        this.codiJornadLab = codiJornadLab;
    }

    public Integer getCodiSucurs() {
        return codiSucurs;
    }

    public void setCodiSucurs(Integer codiSucurs) {
        this.codiSucurs = codiSucurs;
    }

    public Date getFechNaci() {
        return fechNaci;
    }

    public void setFechNaci(Date fechNaci) {
        this.fechNaci = fechNaci;
    }

    public Date getFechIngr() {
        return fechIngr;
    }

    public void setFechIngr(Date fechIngr) {
        this.fechIngr = fechIngr;
    }

    public String getCuentaCTS() {
        return cuentaCTS;
    }

    public void setCuentaCTS(String cuentaCTS) {
        this.cuentaCTS = cuentaCTS;
    }

    public String getCodiCuenCTS() {
        return codiCuenCTS;
    }

    public void setCodiCuenCTS(String codiCuenCTS) {
        this.codiCuenCTS = codiCuenCTS;
    }

    public Date getFechCese() {
        return fechCese;
    }

    public void setFechCese(Date fechCese) {
        this.fechCese = fechCese;
    }

    public Boolean getDiscapacidad() {
        return discapacidad;
    }

    public void setDiscapacidad(Boolean discapacidad) {
        this.discapacidad = discapacidad;
    }

    public Boolean getSindicalizado() {
        return sindicalizado;
    }

    public void setSindicalizado(Boolean sindicalizado) {
        this.sindicalizado = sindicalizado;
    }

    public Boolean getStatcExonerado() {
        return statcExonerado;
    }

    public void setStatcExonerado(Boolean statcExonerado) {
        this.statcExonerado = statcExonerado;
    }

    public Boolean getConvenio() {
        return convenio;
    }

    public void setConvenio(Boolean convenio) {
        this.convenio = convenio;
    }

    public Boolean getAportaSctr() {
        return aportaSctr;
    }

    public void setAportaSctr(Boolean aportaSctr) {
        this.aportaSctr = aportaSctr;
    }

    public Boolean getPolizaSeguro() {
        return polizaSeguro;
    }

    public void setPolizaSeguro(Boolean polizaSeguro) {
        this.polizaSeguro = polizaSeguro;
    }

    public Boolean getAsignacionFamiliar() {
        return asignacionFamiliar;
    }

    public void setAsignacionFamiliar(Boolean asignacionFamiliar) {
        this.asignacionFamiliar = asignacionFamiliar;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codiPers != null ? codiPers.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PlanillaPersona)) {
            return false;
        }
        PlanillaPersona other = (PlanillaPersona) object;
        if ((this.codiPers == null && other.codiPers != null)
                || (this.codiPers != null && !this.codiPers.equals(other.codiPers))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "pe.gestor.planilla.dto.PlanillaPersona[ codiPers=" + codiPers + " ]";
    }

}
