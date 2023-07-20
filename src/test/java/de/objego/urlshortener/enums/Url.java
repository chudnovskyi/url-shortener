package de.objego.urlshortener.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Url {
    ENCODE("/api/v1/shortener/encode"),
    DECODE("/api/v1/shortener/decode");

    private final String url;
}
