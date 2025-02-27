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
public class VistaAsistenciaMarcacionpersonaDAO extends VistaAsistenciaMarcacionpersonaJpaController{
    
    public VistaAsistenciaMarcacionpersonaDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
}
