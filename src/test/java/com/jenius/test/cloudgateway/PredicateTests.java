package com.jenius.test.cloudgateway;

import com.sun.net.httpserver.HttpServer;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PredicateTests {

    private static final Logger log = LoggerFactory.getLogger(PredicateTests.class);

    // Create a running HttpServer that echoes back the request URL.
    private static HttpServer startTestServer() {

        try {
            log.info("[I26] Starting mock server");
            HttpServer mockServer = HttpServer.create();
            mockServer.bind(new InetSocketAddress(0), 0);
            mockServer.createContext("/api", (xchg) -> {
                String uri = xchg.getRequestURI()
                        .toString();
                log.info("[I23] Backend called: uri={}", uri);
                xchg.getResponseHeaders()
                        .add("Content-Type", "text/plain");
                xchg.sendResponseHeaders(200, 0);
                OutputStream os = xchg.getResponseBody();
                os.write(uri.getBytes());
                os.flush();
                os.close();
            });

            mockServer.start();
            InetSocketAddress localAddr = mockServer.getAddress();
            log.info("[I36] mock server started: local address={}", localAddr);

            return mockServer;
        } catch (Exception ex) {
            throw new RuntimeException(ex);
        }

    }

    @LocalServerPort
    private int localPort;

    @DynamicPropertySource
    static void registerBackendServer(DynamicPropertyRegistry registry) {
        registry.add("rewrite.backend.uri", () -> {
            HttpServer s = startTestServer();
            return "http://localhost:" + s.getAddress().getPort();
        });
    }

//    params test
    @Test
    void test1(@Autowired WebTestClient webClient) {
        webClient.get()
                .uri("http://localhost:" + localPort + "?red=green")
                .exchange()
                .expectBody()
                .consumeWith((result) -> {
                    String body = new String(Objects.requireNonNull(result.getResponseBody()));
                    assertTrue(body.contains("google.com/?red=green"));
                });
    }

//    before, after and header test
    @Test
    void test2(@Autowired WebTestClient webClient) {
        webClient.get()
                .uri("http://localhost:" + localPort + "")
                .header("head", "beforeafter")
                .exchange()
                .expectBody()
                .consumeWith((result) -> {
                    String body = new String(Objects.requireNonNull(result.getResponseBody()));
                    assertTrue(body.contains("Example Domain"));
                });
    }

//    testing java predicates isnt working for now
//    java url rewriter
//    @Test
//    void testRewriteJava(@Autowired WebTestClient webClient) {
//        webClient.get()
//                .uri("http://localhost:" + localPort + "/facebook")
//                .exchange()
//                .expectBody()
//                .consumeWith((result) -> {
//                    String body = new String(Objects.requireNonNull(result.getResponseBody()));
//                    assertTrue(body.contains("facebook"));
//                });
//    }

//    yaml url rewriter test
    @Test
    void testRewriteYaml(@Autowired WebTestClient webClient) {
        webClient.get()
                .uri("http://localhost:" + localPort + "/baeldung/spring-cloud-gateway")
                .exchange()
                .expectBody()
                .consumeWith((result) -> {
                    String body = new String(Objects.requireNonNull(result.getResponseBody()));
                    assertTrue(body.contains("Spring Cloud Gateway"));
                });
    }

}
