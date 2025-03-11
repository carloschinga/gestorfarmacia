package pe.gestor.planilla.dao;

import javax.persistence.EntityManagerFactory;

public class VistaPlanillaPersonaDAO extends VistaPlanillaPersonaJpaController {
    public VistaPlanillaPersonaDAO(EntityManagerFactory emf) {
        super(emf);
    }
}
