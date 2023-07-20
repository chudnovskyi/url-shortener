package de.objego.urlshortener.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DecodedRequest(
        @NotBlank(message = "{url.not-blank}")
        @Pattern( // https://uibakery.io/regex-library/url-regex-java
                regexp = "^https?:\\/\\/(?:www\\.)?[-a-zA-Z0-9@:%._\\+~#=]{1,256}\\.[a-zA-Z0-9()]{1,6}\\b(?:[-a-zA-Z0-9()@:%_\\+.~#?&\\/=]*)$",
                message = "{url.invalid-format}"
        )
        String url
) {
}
