package de.objego.urlshortener.integration;


import de.objego.urlshortener.ITBase;
import de.objego.urlshortener.builder.DecodedRequestJson;
import de.objego.urlshortener.builder.DecodedRequestJsonBuilder;
import de.objego.urlshortener.builder.EncodedRequestJson;
import de.objego.urlshortener.service.MessageSourceService;
import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import static de.objego.urlshortener.enums.Url.DECODE;
import static de.objego.urlshortener.enums.Url.ENCODE;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RequiredArgsConstructor
public class UrlShortenerControllerIT extends ITBase {

    private final MockMvc mockMvc;
    private final MessageSourceService message;

    static final DecodedRequestJson DEC_YT_JSON = DecodedRequestJsonBuilder.builder()
            .url("https://www.youtube.com/feed/subscriptions")
            .build();

    static final DecodedRequestJson DEC_GM_JSON = DecodedRequestJsonBuilder.builder()
            .url("https://mail.google.com/mail")
            .build();

    @Value("${shortener.size}")
    private Integer size;

    @Value("${shortener.prefix}")
    private String prefix;

    @Test
    public void encodeTest() throws Exception {
        String encodedYT = encodeUrlAndGet(DEC_YT_JSON);
        assertThat(encodedYT).hasSize(prefix.length() + size);
    }

    @Test
    public void decodeTest() throws Exception {
        String encodedUrl = encodeUrlAndGet(DEC_GM_JSON);
        String decodedUrl = decodeUrlAndGet(encodedUrl);

        assertThat(decodedUrl).isEqualTo(DEC_GM_JSON.url());

        decodeUrlAndNotFoundException(encodedUrl + "dummy");
        decodeUrlAndBadRequestException("dummy");
    }

    private String encodeUrlAndGet(DecodedRequestJson request) throws Exception {
        ResultActions result = mockMvc.perform(post(ENCODE.getUrl())
                        .content(request.toJson())
                        .contentType(APPLICATION_JSON))
                .andExpectAll(
                        status().isCreated(),
                        jsonPath("$.encoded").exists()
                );
        return extractJsonValueByKey(result, "encoded");
    }

    private String decodeUrlAndGet(String encoded) throws Exception {
        ResultActions result = mockMvc.perform(get(DECODE.getUrl())
                        .content(new EncodedRequestJson(encoded).toJson())
                        .contentType(APPLICATION_JSON))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.url").exists()
                );
        return extractJsonValueByKey(result, "url");
    }

    private void decodeUrlAndNotFoundException(String encoded) throws Exception {
        mockMvc.perform(get(DECODE.getUrl())
                        .content(new EncodedRequestJson(encoded).toJson())
                        .contentType(APPLICATION_JSON))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(
                                message.getProperty("url.decode.not-found", encoded)
                        )
                );
    }

    private void decodeUrlAndBadRequestException(String encoded) throws Exception {
        mockMvc.perform(get(DECODE.getUrl())
                        .content(new EncodedRequestJson(encoded).toJson())
                        .contentType(APPLICATION_JSON))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.encoded").value(
                                "Invalid encoded format"
                        )
                );
    }
}
