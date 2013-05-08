/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.insirio.prototype.ejbclient;

import it.insirio.prototype.model.Person;
import java.util.List;
import javax.ejb.Remote;

/**
 *
 * @author karuro
 */
@Remote
public interface PersonFacadeRemote {

    void create(Person person);

    void edit(Person person);

    void remove(Person person);

    Person find(Object id);

    List<Person> findAll();

    List<Person> findRange(int[] range);

    int count();
    
}
