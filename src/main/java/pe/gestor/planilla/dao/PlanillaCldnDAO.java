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
public class PlanillaCldnDAO extends PlanillaCldnJpaController{
    
    public PlanillaCldnDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
}
