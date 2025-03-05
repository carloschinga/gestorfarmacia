package pe.gestor.planilla.dao;

import javax.persistence.EntityManagerFactory;

public class PlanillaAfpDAO extends PlanillaAfpJpaController {
    public PlanillaAfpDAO(EntityManagerFactory emf) {
        super(emf);
    }
}
