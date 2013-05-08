/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.insirio.dao;

import it.insirio.prototype.ejbclient.PersonFacadeRemote;
import it.insirio.prototype.model.Person;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author karuro
 */
@Stateless
public class PersonFacade extends AbstractFacade<Person> implements PersonFacadeRemote {
    @PersistenceContext(unitName = "prototypePU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PersonFacade() {
        super(Person.class);
    }
    
}
