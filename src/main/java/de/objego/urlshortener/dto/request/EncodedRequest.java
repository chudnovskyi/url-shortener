package de.objego.urlshortener.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record EncodedRequest(
        @NotBlank(message = "{encoded.not-blank}")
        @Pattern( // TODO: map directly to property
                regexp = "http://short\\.est/.*",
                message = "{encoded.invalid-format}"
        )
        String encoded
) {
}
