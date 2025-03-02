/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import pe.gestor.login.dto.VistaLoginMenuDeta;

/**
 *
 * @author USER
 */
public class VistaLoginMenuDetaDAO extends VistaLoginMenuDetaJpaController {

    public VistaLoginMenuDetaDAO(EntityManagerFactory emf) {
        super(emf);
    }

    public List<VistaLoginMenuDeta> listarMenuXUsua(int usecod) {
        EntityManager em = null;
        try {
            em = getEntityManager();
            Query q = em.createNamedQuery("VistaLoginMenuDeta.findByCodiUsua");
            q.setParameter("codiUsua", usecod);

            List<VistaLoginMenuDeta> lista = q.getResultList();
            return lista;

        } catch (Exception ex) {
            String mensaje = "";
            return null;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

}
