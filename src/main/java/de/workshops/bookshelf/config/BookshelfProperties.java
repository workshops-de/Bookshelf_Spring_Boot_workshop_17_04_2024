package de.workshops.bookshelf.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties("bookshelf")
@Getter
@Setter
public class BookshelfProperties {

  private Integer someNumber;

  private String someText;
}
