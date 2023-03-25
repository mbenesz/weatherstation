package com.gitub.mb.weatherstation.temperature;

import com.github.tomakehurst.wiremock.junit.WireMockRule;
import org.junit.Rule;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;

import static com.github.tomakehurst.wiremock.client.WireMock.*;
import static com.github.tomakehurst.wiremock.core.WireMockConfiguration.wireMockConfig;
import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ExternalApiTest {
    @Rule
    public WireMockRule wireMockRule = new WireMockRule(wireMockConfig().port(9090));

    @Test
    public void wiremock_with_junit_test() throws Exception {
        configStub();

        TestRestTemplate testRestTemplate = new TestRestTemplate();
        String response = testRestTemplate.getForObject("http://localhost:9090/some/thing", String.class);

        assertEquals("the weather is fine", response);
        verify(getRequestedFor(urlEqualTo("/some/thing")));
    }

    private void configStub() {
        configureFor("localhost", 9090);
        stubFor(get(urlEqualTo("/some/thing")).willReturn(aResponse().withBody("the weather is fine")));
    }
}
