/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dao;

import pe.gestor.login.dto.Empresa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;

/**
 *
 * @author USER
 */
public class EmpresaDAO extends EmpresaJpaController{
    
    public EmpresaDAO(EntityManagerFactory emf) {
        super(emf);
    }
      public List<Empresa> listaEmpresaActivas() {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("Empresa.findByDefaEmpr");
            q.setParameter("defaEmpr", true);
            return q.getResultList();
        } catch(Exception ex){
        return null;}
            finally {
            em.close();
        }
    }
}
