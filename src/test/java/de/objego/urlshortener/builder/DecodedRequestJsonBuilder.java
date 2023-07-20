package de.objego.urlshortener.builder;

public class DecodedRequestJsonBuilder extends Builder<DecodedRequestJson> {

    private String url;

    public static DecodedRequestJsonBuilder builder() {
        return new DecodedRequestJsonBuilder();
    }

    public DecodedRequestJsonBuilder url(String url) {
        this.url = url;
        return this;
    }

    @Override
    public DecodedRequestJson build() {
        return new DecodedRequestJson(url);
    }
}
