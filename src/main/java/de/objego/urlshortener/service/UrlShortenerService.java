package de.objego.urlshortener.service;

import de.objego.urlshortener.dto.request.DecodedRequest;
import de.objego.urlshortener.dto.request.EncodedRequest;
import de.objego.urlshortener.dto.response.DecodedResponse;
import de.objego.urlshortener.dto.response.EncodedResponse;
import de.objego.urlshortener.exception.url.EncodedUrlNotFoundException;
import de.objego.urlshortener.exception.url.UrlEncodingException;
import de.objego.urlshortener.mapper.UrlMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * Service class responsible for URL encoding and decoding operations.
 */
@Service
@RequiredArgsConstructor
public class UrlShortenerService {

    /**
     * The cache that stores mappings between short codes and original URLs.
     */
    private final Map<String, String> cache;

    /**
     * The mapper used for converting between URL DTOs and responses.
     */
    private final UrlMapper urlMapper;

    /**
     * The service to retrieve messages from property files.
     */
    private final MessageSourceService message;

    /**
     * The prefix used for short codes in the cache. It is retrieved from the property file.
     */
    @Value("${shortener.prefix}")
    private String prefix;

    /**
     * The characters allowed in the generated short code for the URL. It is retrieved from the property file.
     */
    @Value("${shortener.key}")
    private String key;

    /**
     * The size of the generated short code for the URL. It is retrieved from the property file.
     */
    @Value("${shortener.size}")
    private Long size;

    /**
     * Encodes the given URL into a short code.
     *
     * @param request The DTO containing the original URL.
     * @return The DTO containing the encoded response.
     * @throws UrlEncodingException If the encoding process fails.
     */
    public EncodedResponse encode(DecodedRequest request) {
        return Optional.of(request)
                .map(DecodedRequest::url)
                .map(this::cacheUrlWithShortCode)
                .map(urlMapper::toEncodedResponse)
                .orElseThrow(() -> new UrlEncodingException(
                        message.getProperty("url.encode.error", request.url())
                ));
    }

    /**
     * Decodes the given short code and retrieves the original URL.
     *
     * @param request The DTO containing the short code.
     * @return The DTO containing the decoded response.
     * @throws EncodedUrlNotFoundException If the short code does not exist in the cache.
     */
    public DecodedResponse decode(EncodedRequest request) {
        return Optional.of(request)
                .map(EncodedRequest::encoded)
                .map(cache::get)
                .map(urlMapper::toDecodedResponse)
                .orElseThrow(() -> new EncodedUrlNotFoundException(
                        message.getProperty("url.decode.not-found", request.encoded())
                ));
    }

    /**
     * Caches the URL with a generated short code as the key.
     *
     * @param originalUrl The URL to be cached.
     * @return The generated short code used as the key for the cached URL.
     */
    private String cacheUrlWithShortCode(String originalUrl) {
        StringBuilder generatedValue = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < size; i++) {
            generatedValue.append(
                    key.charAt(random.nextInt(key.length()))
            );
        }
        String shortCode = prefix + generatedValue;
        cache.put(shortCode, originalUrl);
        return shortCode;
    }
}
