/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.ventas.dao;

import javax.persistence.EntityManagerFactory;

/**
 *
 * @author san21
 */
public class VentasProductosStockDAO  extends VentasProductosStockJpaController{
    
    public VentasProductosStockDAO(EntityManagerFactory emf) {
        super(emf);
    }
    
}
