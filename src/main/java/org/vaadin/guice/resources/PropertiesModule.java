package org.vaadin.guice.resources;

import com.google.common.io.CharStreams;
import com.google.inject.AbstractModule;

import org.reflections.Reflections;
import org.reflections.scanners.ResourcesScanner;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Objects;
import java.util.Properties;
import java.util.Set;

import static com.google.inject.name.Names.bindProperties;
import static com.google.inject.name.Names.named;

public class PropertiesModule extends AbstractModule {

    private final String[] propertiesResourcePath;

    PropertiesModule(EnableResourceBinding annotation){
        Objects.requireNonNull(annotation);
        this.propertiesResourcePath = annotation.value();
    }

    public PropertiesModule(String... propertiesResourcePath){
        this.propertiesResourcePath = Objects.requireNonNull(propertiesResourcePath);
    }

    @Override
    protected void configure() {

        for (String path : propertiesResourcePath) {

            final Set<String> resources = new HashSet<>(new Reflections(new ResourcesScanner(), path)
                    .getResources(name -> true));

            for (String resource : resources) {

                final InputStream resourceStream = getClass().getResourceAsStream("/" + resource);

                if(resource.endsWith(".properties")){
                    Properties properties = new Properties();

                    try {
                        properties.load(resourceStream);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    bindProperties(binder(), properties);

                    bind(Properties.class).annotatedWith(named(resource)).toInstance(properties);
                } else {
                    final String fileContent;

                    try {
                         fileContent = CharStreams.toString(new InputStreamReader(resourceStream));
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                    bind(String.class).annotatedWith(named(resource)).toInstance(fileContent);
                }
            }
        }
    }
}
