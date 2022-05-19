# 메세징

## html에서 사용될 단어들을 properties 파일에 key=value 로 저장해둔 뒤, 가져다 쓰는 것

```html

<label for="itemName" th:text="#{item.itemName}"></label>

```


# 국제화

## 언어별로 메세지를 별도 관리하여 국제화 서비스를 구현하는 것

message properties 파일을 언어별로 관리한다.

> 어떻게 언어를 감지하여 국가별로 다른 언어를 뿌려주는가?

`http accept-language` 헤더 값을 사용하거나 사용자가 직접 언어를 선택하게 하거나, 쿠키등을 사용하여 처리


> 감지된 언어별로 다른 properties 파일 관리

`messages_en.properties` 영어 국가에서 접근하면, 선택하면 동작

```html
item=Item
item.id=Item ID

item.itemName=Item Name
item.price=price
item.quantity=quantity
```

`message_ko.properties` 한국어 페이지 개발자

```html
item=상품 
item.id=상품 ID 
item.itemName=상품명 
item.price=가격 
item.quantity=수량
   
```


> MessageSource 인터페이스를 스프링 빈으로 등록

스프링은 Bean으로 따로 등록해야 하지만, 스프링 부트는 설정 등록으로 자동으로 `MessageSource`가 스프링 빈으로 등록된다.

별도 설정하지 않으면 messages라는 이름으로 기본 등록된다.
그래서 messages_en.properties, messages.properteis(디폴트) 등의 파일을 resources 하위에
등록하면 바로 적용된다.

* messages프로퍼티 파일들의 default 파일명을 설정한다. 
`spring.messages.basename=messages`



> MessageSource 인터페이스 (자바에서 언어별로 꺼내쓰는법)

```java
public interface MessageSource {
  String getMessage(String code, @Nullable Object[] args, @Nullable String
      defaultMessage, Locale locale);

  String getMessage(String code, @Nullable Object[] args, Locale locale) throws
      NoSuchMessageException;

}
```

getMessage를 사용하여(키값, 인자들, 없을 경우 디폴트 메세지, 지역)을 넘겨주면
해당 프로퍼티 메세지의 내용을 가져와준다.

```java
public class MessageSourceT{
  
  @Autowired
  MessageSource ms; //스프링 부트가 자동으로 빈에 등록해준 MessageSource
  
  
  @Test
  void messageTest(){
    ms.getMessage("hello")
  }
  
}

```