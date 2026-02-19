package com.test.online.store.inventory.app;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.testcontainers.containers.MongoDBContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.test.online.store.inventory.domain.document.Inventory;
import com.test.online.store.inventory.domain.repository.BillsRepository;
import com.test.online.store.inventory.domain.repository.InventoriesRepository;
import com.test.online.store.inventory.service.integration.ProductsIntegrationService;
import com.test.online.store.product.model.ProductBean;

@Testcontainers
@SpringBootTest(properties = {
        "eureka.client.enabled=false",
        "eureka.client.register-with-eureka=false",
        "eureka.client.fetch-registry=false"
}, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class BillsControllerITTest {

    private static final String API_KEY = "kxMcvCpeRe7Nv6WSrFDmvA37FWG04sz3cyRMA0y8";

    @Container
    static final MongoDBContainer mongo = new MongoDBContainer("mongo:7.0");

    @DynamicPropertySource
    static void configure(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", mongo::getReplicaSetUrl);
        registry.add("spring.data.mongodb.database", () -> "inventory_it");
    }

    @LocalServerPort
    private int port;

    @Autowired
    private InventoriesRepository inventoriesRepository;

    @Autowired
    private BillsRepository billsRepository;

    @MockitoBean
    private ProductsIntegrationService productsIntegrationService;

    private final HttpClient httpClient = HttpClient.newHttpClient();
    private final ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
        billsRepository.deleteAll();
        inventoriesRepository.deleteAll();
    }

    @Test
    void shouldCreateByProductSlug() throws Exception {
        // Arrange
        final String slug = "gaming-mouse";
        final ProductBean product = ProductBean.builder()
                .name("Gaming Mouse")
                .price(BigDecimal.valueOf(10))
                .description("Wireless")
                .build();
        when(productsIntegrationService.getBySlug(slug)).thenReturn(product);

        final Inventory inventory = new Inventory();
        inventory.setProductSlug(slug);
        inventory.setQuantity(5L);
        inventoriesRepository.save(inventory);

        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + "/api/v1/bills/" + slug))
                .header("X-API-KEY", API_KEY)
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString("{\"quantity\":2}"))
                .build();

        // Act
        final HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
        final JsonNode root = objectMapper.readTree(response.body());

        // Assert
        assertEquals("20", root.path("data").path("totalPrice").asText());
        assertEquals(3L, inventoriesRepository.findByProductSlug(slug).orElseThrow().getQuantity());
    }
}
