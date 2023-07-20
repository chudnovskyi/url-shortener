package de.objego.urlshortener.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * Configuration class for loading property files.
 */
@Configuration
@PropertySource({
        "classpath:${envTarget:errors}.properties"
})
public class PropertiesConfig {
}
