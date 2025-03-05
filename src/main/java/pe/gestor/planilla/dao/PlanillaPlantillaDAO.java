package pe.gestor.planilla.dao;

import javax.persistence.EntityManagerFactory;

public class PlanillaPlantillaDAO extends PlanillaPlantillaJpaController {
    public PlanillaPlantillaDAO(EntityManagerFactory emf) {
        super(emf);
    }
}
