package de.objego.urlshortener.builder;

public record EncodedRequestJson(
        String encoded
) implements Product {

    private static final String TEMPLATE = """
            {
                "encoded": "%s"
            }
            """;

    @Override
    public String toJson() {
        return TEMPLATE.formatted(encoded);
    }
}
