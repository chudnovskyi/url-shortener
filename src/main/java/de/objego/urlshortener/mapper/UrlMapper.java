package de.objego.urlshortener.mapper;

import de.objego.urlshortener.dto.response.DecodedResponse;
import de.objego.urlshortener.dto.response.EncodedResponse;
import org.mapstruct.Mapper;

/**
 * Mapper interface for converting between URL DTOs and responses.
 */
@Mapper(componentModel = "spring")
public interface UrlMapper {

    /**
     * Converts an encoded URL to an EncodedResponse DTO.
     *
     * @param encoded The encoded URL.
     * @return The EncodedResponse DTO.
     */
    EncodedResponse toEncodedResponse(String encoded);

    /**
     * Converts a URL to a DecodedResponse DTO.
     *
     * @param url The original URL.
     * @return The DecodedResponse DTO.
     */
    DecodedResponse toDecodedResponse(String url);
}
