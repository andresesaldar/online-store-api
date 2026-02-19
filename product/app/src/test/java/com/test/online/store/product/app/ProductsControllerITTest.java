package com.test.online.store.product.app;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.online.store.product.domain.repository.ProductsRepository;

@SpringBootTest(properties = {
        "spring.datasource.url=jdbc:h2:mem:product-it;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DATABASE_TO_UPPER=false",
        "spring.datasource.driver-class-name=org.h2.Driver",
        "spring.datasource.username=sa",
        "spring.datasource.password=",
        "spring.jpa.hibernate.ddl-auto=create-drop",
        "eureka.client.enabled=false",
        "eureka.client.register-with-eureka=false",
        "eureka.client.fetch-registry=false"
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductsControllerITTest {

    private static final String API_KEY = "kxMcvCpeRe7Nv6WSrFDmvA37FWG04sz3cyRMA0y8";

    @LocalServerPort
    private int port;

    @Autowired
    private ProductsRepository productsRepository;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        productsRepository.deleteAll();
    }

    @Test
    void shouldCreate() throws Exception {
        // Arrange
        final String payload = "{\"name\":\"Gaming Mouse\",\"price\":89.90,\"description\":\"Wireless\"}";
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/v1/products"))
                .header("X-API-KEY", API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(payload))
                .build();

        // Act
        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        final JsonNode root = objectMapper.readTree(response.body());

        // Assert
        assertEquals("gaming-mouse", root.path("data").path("slug").asText());
    }

    @Test
    void shouldGetBySlug() throws Exception {
        // Arrange
        final String createPayload = "{\"name\":\"Mechanical Keyboard\",\"price\":129.90,\"description\":\"RGB\"}";
        final HttpRequest createRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/v1/products"))
                .header("X-API-KEY", API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(createPayload))
                .build();
        httpClient.send(createRequest, HttpResponse.BodyHandlers.ofString());

        final HttpRequest getRequest = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/v1/products/mechanical-keyboard"))
                .header("X-API-KEY", API_KEY)
                .GET()
                .build();

        // Act
        final HttpResponse<String> response = httpClient.send(getRequest, HttpResponse.BodyHandlers.ofString());
        final JsonNode root = objectMapper.readTree(response.body());

        // Assert
        assertEquals("mechanical-keyboard", root.path("data").path("slug").asText());
    }
}
