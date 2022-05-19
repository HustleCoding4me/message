package hello.itemservice.message;

import org.assertj.core.api.Assertions;
import org.assertj.core.api.ThrowableAssert;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;

import java.util.Locale;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
public class MessageSourceTest {

  @Autowired
  MessageSource ms;

  @Test
  void helloMessage() {
    String result = ms.getMessage("hello", null, null);
    assertThat(result).isEqualTo("hello");
  }

  @Test
  void notFoundMessageCode() {
    assertThatThrownBy(() -> ms.getMessage("no_code", null, null))
        .isInstanceOf(NoSuchMessageException.class);
  }

  @Test
  void notFoundMessageCodeDefaultMessage() {
    String message = ms.getMessage("no_code", null, "기본 메세지", null);
    assertThat(message).isEqualTo("기본 메세지");
  }

  @Test
  void argumentMessage() {
    String message = ms.getMessage("hello.name", new Object[]{"Spring", 1}, Locale.getDefault());
    System.out.println(message);
  }

  @Test
  void defaultLang() {
    String message = ms.getMessage("hello.name", new Object[]{"Spring", 1}, Locale.KOREA);
    System.out.println("message = " + message);
  }

}
