package com.github.singals.icecreammeter.resources;

import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.beans.factory.annotation.Value;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class PingControllerTest {

    @Value(value="${local.server.port}")
	private int port;

    @Autowired private TestRestTemplate restTemplate;

    @Test
    void testPing() {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/ping",
				String.class)).contains("pong");
    }
}