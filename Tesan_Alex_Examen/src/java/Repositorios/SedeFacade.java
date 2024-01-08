package Repositorios;

import Modelos.Inspectoria;
import Modelos.Responsable;
import Modelos.Sede;
import Modelos.Sederesponsable;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class SedeFacade extends AbstractFacade<Sede> {

    @PersistenceContext(unitName = "Tesan_Alex_ExamenPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public SedeFacade() {
        super(Sede.class);
    }
    
    public List<Sede> cargarListaSedes(Inspectoria inspectoria){
        em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Sede.findByCodInspectoria").setParameter("codInspectoria", inspectoria.getCodInspectoria());
        return q.getResultList();
    }
    
    public List<Sederesponsable> cargarListaResponsables(Sede sede){
        em = getEntityManager();
        Query q;
        q = em.createNamedQuery("Sederesponsable.findByCodSede").setParameter("codSede", sede.getCodSede());
        return q.getResultList();
    }
}
