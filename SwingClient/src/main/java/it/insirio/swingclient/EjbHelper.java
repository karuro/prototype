/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.insirio.swingclient;

import it.insirio.prototype.ejbclient.PersonFacadeRemote;
import java.util.Hashtable;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.enterprise.inject.Produces;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import org.jboss.weld.environment.se.events.ContainerInitialized;

/**
 *
 * @author karuro
 */
@ApplicationScoped
public class EjbHelper {

    private InitialContext initiContext = getInitialContext();
    
    public InitialContext getInitialContext() {
        Hashtable<String, String> env = new Hashtable<>();
        env.put("java.naming.factory.initial", "weblogic.jndi.WLInitialContextFactory");
        env.put("java.naming.provider.url", "t3://localhost:7001");
        try {
            return new InitialContext(env);
        } catch (NamingException ne) {
            throw new RuntimeException(ne);
        }
    }
    
    @Produces 
    public PersonFacadeRemote getPersonFacadeRemote() throws NamingException {
        
        return (PersonFacadeRemote) initiContext.lookup("java:global/it.insirio_Prototype-ear_ear_1.0/PrototypeEjb/PersonFacade");
    }
    
    public void start(@Observes ContainerInitialized event) {
        System.out.print("sss");
    }
}
