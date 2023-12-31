/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorios;

import Modelos.Cad;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CadFacade extends AbstractFacade<Cad> {

    @PersistenceContext(unitName = "Tesan_Alex_ExamenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CadFacade() {
        super(Cad.class);
    }

    public List<Cad> findCad() {
        em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Cad.findAll");
        return q.getResultList();
    }
    
}
