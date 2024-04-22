package de.workshops.bookshelf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.EnabledIf;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ActiveProfiles("prod")
class EnvironmentTest {

	@Value("${server.port:8080}")
	private int port;

	@Test
	@EnabledIf(expression = "#{environment.acceptsProfiles('prod')}", loadContext = true)
	void verifyProdPort() {
		assertEquals(8090, port);
	}
}
