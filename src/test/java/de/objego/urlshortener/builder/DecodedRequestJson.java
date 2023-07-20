package de.objego.urlshortener.builder;

public record DecodedRequestJson(
        String url
) implements Product {

    private static final String TEMPLATE = """
            {
                "url": %s
            }
            """;

    @Override
    public String toJson() {
        String urlVal = format(url);
        return TEMPLATE.formatted(urlVal);
    }
}
