/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorios;

import Modelos.Envio;
import Modelos.Proyecto;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author alext
 */
@Stateless
public class ProyectoFacade extends AbstractFacade<Proyecto> {

    @PersistenceContext(unitName = "Tesan_Alex_ExamenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ProyectoFacade() {
        super(Proyecto.class);
    }
    
    public List<Envio> cargarlistaEnvios(Proyecto proyecto){
        em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Envio.findByProyecto").setParameter("proyecto", proyecto);
        return q.getResultList();
    }
    
    public int cargarDinero(Proyecto proyecto){
        em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Envio.findDinero").setParameter("proyecto", proyecto);
        return q.getFirstResult();
    }
}
