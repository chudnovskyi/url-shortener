package de.objego.urlshortener.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

/**
 * Service class responsible for retrieving messages from property files.
 */
@Service
@RequiredArgsConstructor
public class MessageSourceService {

    /**
     * The Spring Environment used to retrieve properties.
     */
    private final Environment environment;

    /**
     * Retrieves the message for the given property source.
     *
     * @param source The property source to retrieve the message from.
     * @return The retrieved message as a String.
     */
    public String getProperty(String source) {
        return environment.getProperty(source);
    }

    /**
     * Retrieves the message for the given property source with placeholders replaced by parameters.
     *
     * @param source The property source to retrieve the message from.
     * @param params The parameters to replace the placeholders in the message.
     * @return The retrieved message with placeholders replaced by parameters.
     * @throws RuntimeException If the property is not found for the given source.
     */
    public String getProperty(String source, Object... params) {
        String property = environment.getProperty(source);
        if (property == null) {
            throw new RuntimeException("Property not found: " + source);
        }
        return String.format(property, params);
    }
}
