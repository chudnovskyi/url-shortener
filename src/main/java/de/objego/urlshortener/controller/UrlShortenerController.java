package de.objego.urlshortener.controller;

import de.objego.urlshortener.dto.request.DecodedRequest;
import de.objego.urlshortener.dto.request.EncodedRequest;
import de.objego.urlshortener.dto.response.DecodedResponse;
import de.objego.urlshortener.dto.response.EncodedResponse;
import de.objego.urlshortener.service.UrlShortenerService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

/**
 * REST API controller class for the URL Shortener application.
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/shortener")
public class UrlShortenerController {

    /**
     * The service responsible for URL encoding and decoding operations.
     */
    private final UrlShortenerService shortenerService;

    /**
     * Endpoint to encode a URL.
     *
     * @param request The DTO containing the original URL.
     * @return The ResponseEntity containing the encoded response.
     */
    @PostMapping("/encode")
    @Operation(summary = "Encodes a URL")
    public ResponseEntity<EncodedResponse> encode(
            @RequestBody @Valid DecodedRequest request
    ) {
        return ResponseEntity.status(CREATED).body(shortenerService.encode(request));
    }

    /**
     * Endpoint to decode an encoded URL.
     *
     * @param request The DTO containing the short code.
     * @return The ResponseEntity containing the decoded response.
     */
    @GetMapping("/decode")
    @Operation(summary = "Decodes an encoded URL")
    public ResponseEntity<DecodedResponse> decode(
            @RequestBody @Valid EncodedRequest request
    ) {
        return ResponseEntity.status(OK).body(shortenerService.decode(request));
    }
}
