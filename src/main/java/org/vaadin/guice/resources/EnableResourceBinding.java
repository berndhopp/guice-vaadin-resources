package org.vaadin.guice.resources;

import com.vaadin.guice.annotation.Import;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * bind Resources to injections
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Import(PropertiesModule.class)
public @interface EnableResourceBinding {
    /**
     * the resource-folders to scan for resource-files. If all files are placed under
     * src/main/resources/conf in a typical maven project, the value of this
     * annotation should just be 'conf'
     */
    String[] value();
}
