/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorios;

import Modelos.Cad;
import Modelos.Cadsub;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class CadsubFacade extends AbstractFacade<Cadsub> {

    @PersistenceContext(unitName = "Tesan_Alex_ExamenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CadsubFacade() {
        super(Cadsub.class);
    }
    

    public List<Cadsub> cargarSubcadsDelCad(Integer CodMiCad){
        em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Cadsub.findByCad").setParameter("CodmiCad", CodMiCad);
        return q.getResultList();
    }        
    
}
