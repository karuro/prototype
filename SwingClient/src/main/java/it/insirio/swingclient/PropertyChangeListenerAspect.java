/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.insirio.swingclient;

/**
 *
 * @author karuro
 */
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.PropertyUtils;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.DeclareParents;

public @Aspect
class PropertyChangeListenerAspect {

    public interface ChangeListener {

        public void addPropertyChangeListener(PropertyChangeListener l);

        public void removePropertyChangeListener(PropertyChangeListener l);

        public String getPropertyName(String setterMethodName);

        public PropertyChangeSupport getPropertyChangeSupport();
    }

    public static class ChangeListenerImpl implements ChangeListener {

        protected PropertyChangeSupport props = new PropertyChangeSupport(this);

        @Override
        public void addPropertyChangeListener(PropertyChangeListener l) {
            props.addPropertyChangeListener(l);
        }

        @Override
        public void removePropertyChangeListener(PropertyChangeListener l) {
            props.removePropertyChangeListener(l);
        }

        @Override
        public String getPropertyName(String setterMethodName) {
            setterMethodName = setterMethodName.substring(3);
            String firstLetter = setterMethodName.substring(0, 1);
            setterMethodName = firstLetter.toLowerCase() + setterMethodName.substring(1);
            return setterMethodName;
        }

        @Override
        public PropertyChangeSupport getPropertyChangeSupport() {
            return props;
        }
    }
    @DeclareParents(value = "@it.insirio.prototype.model.util.Observable *", defaultImpl = ChangeListenerImpl.class)
    private ChangeListener implInterface;

    @Around("within(@it.insirio.prototype.model.util.Observable *) && execution(void *..*.set*(..))")
    public void interceptSetterAndFirePropertyChange(ProceedingJoinPoint pjp) throws Throwable {
        String setterMethodName = pjp.getSignature().getName().substring(3);
        String firstLetter = setterMethodName.substring(0, 1);
        String propertyName = firstLetter.toLowerCase() + setterMethodName.substring(1);
        Object oldValue = null;
        try {
            oldValue = PropertyUtils.getProperty(pjp.getTarget(), propertyName);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
        }
        Object newValue = pjp.getArgs()[0];

        pjp.proceed();

        PropertyChangeEvent pe = new PropertyChangeEvent(pjp.getTarget(), propertyName, oldValue, newValue);
        ((ChangeListener) pjp.getTarget()).getPropertyChangeSupport().firePropertyChange(pe);
    }
}
