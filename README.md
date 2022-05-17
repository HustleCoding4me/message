# 메세징

## html에서 사용될 단어들을 properties 파일에 key=value 로 저장해둔 뒤, 가져다 쓰는 것

```html

<label for="itemName" th:text="#{item.itemName}"></label>

```


# 국제화

## 언어별로 메세지를 별도 관리하여 국제화 서비스를 구현하는 것

message properties 파일을 언어별로 관리한다.

`http accept-language` 헤더 값을 사용하거나 사용자가 직접 언어를 선택하게 하거나, 쿠키등을 사용하여 처리


> 스프링은 기본적인 메세지와 국제화 기능을 모두 제공한다. + 타임리프 기능 첨가
