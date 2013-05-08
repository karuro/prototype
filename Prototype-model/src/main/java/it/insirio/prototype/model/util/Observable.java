/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package it.insirio.prototype.model.util;

import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 *
 * @author karuro
 */
@Inherited
@Retention( RetentionPolicy.RUNTIME )
@Target({ ElementType.TYPE })

public @interface Observable {

}
