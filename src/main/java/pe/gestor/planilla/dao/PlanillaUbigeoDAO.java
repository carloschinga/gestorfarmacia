/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.planilla.dao;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class PlanillaUbigeoDAO extends PlanillaUbigeoJpaController{
    
    public PlanillaUbigeoDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
}
