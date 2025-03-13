/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.planilla.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import pe.gestor.planilla.dto.PlanillaPersona;

/**
 *
 * @author USER
 */
public class PlanillaPersonaDAO extends PlanillaPersonaJpaController {

    public PlanillaPersonaDAO(EntityManagerFactory emf) {
        super(emf);
    }

    public List<PlanillaPersona> listarActivos() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Persona.findByActiPers");
            q.setParameter("actiPers", true);
            return q.getResultList();
        } finally {
            em.close();
        }
    }

}
