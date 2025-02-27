/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dao;

import pe.gestor.login.dto.PlanillaEmpresa;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 *
 * @author USER
 */
public class EmpresaDAO extends PlanillaEmpresaJpaController{
    
    public EmpresaDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    public List<PlanillaEmpresa> listaEmpresaActivas() {
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
     public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("com.mycompany_GestorFarmacia_war_1.0-SNAPSHOTPU");
    
        EmpresaDAO empresaDAO= new EmpresaDAO(emf);
        List<PlanillaEmpresa> lista=empresaDAO.listaEmpresaActivas();
         for (PlanillaEmpresa empresa : lista) {
             System.out.println(empresa.getNombEmpr());
         }
    }

}