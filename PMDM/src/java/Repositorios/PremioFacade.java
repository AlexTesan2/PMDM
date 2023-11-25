/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorios;

import Modelos.Premio;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author alberto
 */
@Stateless
public class PremioFacade extends AbstractFacade<Premio> {

    @PersistenceContext(unitName = "BibliotecaPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PremioFacade() {
        super(Premio.class);
    }
    public List premiosOrdenados(){
        em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Premio.findAllOrdered");
        return q.getResultList();
    }
    public List premiosAutorOrdenados(){
        em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Premio.findAllOrderedA");
        return q.getResultList();
    }
    public List premiosLibroOrdenados(){
        em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Premio.findAllOrderedL");
        return q.getResultList();
    }
}
