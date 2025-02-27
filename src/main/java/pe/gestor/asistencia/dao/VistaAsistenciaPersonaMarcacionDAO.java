/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.asistencia.dao;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class VistaAsistenciaPersonaMarcacionDAO extends VistaAsistenciaPersonaMarcacionJpaController{
    
    public VistaAsistenciaPersonaMarcacionDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
}
