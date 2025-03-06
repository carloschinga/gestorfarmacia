/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pe.gestor.compras.dto;

import java.util.List;

/**
 *
 * @author USER
 */
public class ComprasOcDTO {

    private int codiProv;
    private String fechOC;
    private List<ComprasOcdDTO> detalles;

    public int getCodiProv() {
        return codiProv;
    }

    public void setCodiProv(int codiProv) {
        this.codiProv = codiProv;
    }

    public String getFechOC() {
        return fechOC;
    }

    public void setFechOC(String fechOC) {
        this.fechOC = fechOC;
    }

    public List<ComprasOcdDTO> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<ComprasOcdDTO> detalles) {
        this.detalles = detalles;
    }

}
