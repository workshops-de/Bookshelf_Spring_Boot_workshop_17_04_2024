package de.workshops.bookshelf;

import static org.junit.jupiter.api.Assertions.assertEquals;

import de.workshops.bookshelf.config.BookshelfProperties;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class BookshelfApplicationTests {

	@Autowired
	private BookshelfProperties bookshelfProperties;

	@Test
	void contextLoads() {
		assertEquals(11, bookshelfProperties.getSomeNumber());
		assertEquals("More information", bookshelfProperties.getSomeText());
	}
}
