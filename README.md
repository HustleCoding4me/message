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
     String message = ms.getMessage("hello.name", new Object[]{"Spring", 1}, Locale.KOREA);// messages_kr.properties
     String message = ms.getMessage("hello.name", new Object[]{"Spring", 1}, Locale.getDefault()); //기본 Locale설정시 messages.properties
     String message = ms.getMessage("no_code", null, "기본 메세지", null);//기본 메세지 설정하면 없어도 기본메세지를 출력, or Exception
  }
  
}

```

> 국제화에서 메세지 가져오는 순서

* 보통 Locale의 이름을 기반으로 가져온다. 
* ex) Locale이 en_US 인 경우, message_en_US -> message_en -> messages 순으로 찾는다.



* key 값으로 hello를 가지고 있는 모습

<img width="392" alt="Screen Shot 2022-05-19 at 3 30 55 PM" src="https://user-images.githubusercontent.com/37995817/169225446-9aa33575-938b-4fc0-87f1-4e2a8d3220a9.png">
<img width="260" alt="Screen Shot 2022-05-19 at 3 31 07 PM" src="https://user-images.githubusercontent.com/37995817/169225481-91cfff0d-dec9-401e-969b-717f5e8624c0.png">



> 현재 설정된 Locale 확인하는 방법 (크롬)

* network Http 통신의 헤더에 Accept-Language를 확인하면 된다.
* 언어가 나오는 순서는 우선순위 (ko -> en -> default 순으로 파일을 찾아 메세징 한다.)

![Screen Shot 2022-05-20 at 4 21 38 PM](https://user-images.githubusercontent.com/37995817/169475046-25f2e978-99f9-4283-a204-171386885130.png)


> 크롬에서 언어 설정 변경하여 인식되는지 파악하는 방법

* setting에 Language로 가서, 언어에 우클릭하여 Top으로 보내면 된다.

![Screen Shot 2022-05-20 at 4 27 17 PM](https://user-images.githubusercontent.com/37995817/169476119-ea62ae90-5235-4bff-835c-d2fcf778e93d.png)

---

## LocaleResolver

스프링은 기본적으로 Http의 AcceptHeader를 보고 언어를 판단하는데, 그 외에 강제적으로 언어를 변경하는 기능을 만든다거나 하는 방법을 사용하기 위해서는, 
LocaleResolver인터페이스의 setLocale을 구현하면 된다.

```java
public interface LocaleResolver {

    Locale resolveLocale(HttpServletRequest request);
    void setLocale(HttpServletRequest request, @Nullable HttpServletResponse
  response, @Nullable Locale locale);
}
```

