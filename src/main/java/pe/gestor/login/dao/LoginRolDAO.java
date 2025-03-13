/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.login.dao;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author USER
 */
public class LoginRolDAO extends LoginRolJpaController{
    
    public LoginRolDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
    
}
