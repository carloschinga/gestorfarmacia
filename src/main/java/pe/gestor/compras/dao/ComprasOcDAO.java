/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.compras.dao;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class ComprasOcDAO extends ComprasOcJpaController{
    
    public ComprasOcDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
}
