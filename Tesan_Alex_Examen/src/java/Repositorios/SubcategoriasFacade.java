/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorios;

import Modelos.Subcategorias;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author alext
 */
@Stateless
public class SubcategoriasFacade extends AbstractFacade<Subcategorias> {

    @PersistenceContext(unitName = "Tesan_Alex_ExamenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SubcategoriasFacade() {
        super(Subcategorias.class);
    }
    
}
