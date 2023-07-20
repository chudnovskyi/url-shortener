package de.objego.urlshortener.exception.url;

/**
 * Exception class to be thrown when an encoded URL is not found in the cache.
 */
public class EncodedUrlNotFoundException extends RuntimeException {

    /**
     * Constructs an EncodedUrlNotFoundException with the specified error message.
     *
     * @param message The error message to be associated with the exception.
     */
    public EncodedUrlNotFoundException(String message) {
        super(message);
    }
}
