/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dao;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import pe.gestor.login.dto.VistaLoginUsuarioRol;

/**
 *
 * @author USER
 */
public class VistaLoginUsuarioRolDAO extends VistaLoginUsuarioRolJpaController{
    
    public VistaLoginUsuarioRolDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    public VistaLoginUsuarioRol validarUsuario(String logiUsua, String passUsua) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createNamedQuery("VistaLoginUsuarioRol.validar", VistaLoginUsuarioRol.class);
            q.setParameter("logiUsua", logiUsua);
            q.setParameter("passUsua", passUsua);
            List<VistaLoginUsuarioRol> lista = q.getResultList();
            return lista.get(0);
        } catch (Exception ex) {
            String mensaje= ex.getMessage();
            return null;
        } finally {
            em.close();
        }
    }
   

   
}

