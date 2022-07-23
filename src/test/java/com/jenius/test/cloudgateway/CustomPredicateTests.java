//package com.jenius.test.cloudgateway;
//
//import static org.assertj.core.api.Assertions.assertThat;
//import static org.junit.jupiter.api.Assertions.*;
//
//import java.net.URI;
//import java.util.Objects;
//
//import org.json.JSONException;
//import org.json.JSONObject;
//import org.json.JSONTokener;
//import org.junit.jupiter.api.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
//import org.springframework.boot.test.web.client.TestRestTemplate;
//import org.springframework.boot.web.server.LocalServerPort;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.RequestEntity;
//import org.springframework.http.ResponseEntity;
//import org.springframework.test.context.ActiveProfiles;
//
//@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
//public class CustomPredicateTests {
//
//    @LocalServerPort
//    String serverPort;
//
//    @Autowired
//    private TestRestTemplate restTemplate;
//
//    @Test
//    void testGolden() throws JSONException {
//
//        String url = "http://localhost:" + serverPort + "/bank/tst";
//        ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
////        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
//
//        String body = Objects.requireNonNull(response.getBody());
//        assertTrue(body.contains("facebook"));
//
//    }
//
//}
