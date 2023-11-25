/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Repositorios;

import Modelos.Pais;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class PaisFacade extends AbstractFacade<Pais> {

    @PersistenceContext(unitName = "BibliotecaPU")
    /*Este objeto de la clase EntityManager es capaz de manipular el contenido
    del proyecto, porque es el proyecto, gracias a él puedo moverme por todo
    el proyecto*/
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PaisFacade() {
        super(Pais.class);
    }
  
    //Creamos un método que llamará a la nueva consulta ordenada
    public List<Pais> paisesOrdenados(){
        em = getEntityManager();//accedemos al proyecto que tiene que ver con pais
        Query q;//Objeto de la clase consulta
        q = em.createNamedQuery("Pais.findAllOrdered");//ejecuta la query y el resultado lo carga en un objeto de la clase Query
        return q.getResultList();//convertimos el resultado en lista y lo devolvemos
    }
}
