# Spring Validation 入参校验

数据校验不仅仅需要在前台进行，为了防止用户绕开浏览器，后台也应该在进入方法前，对用户传递的参数进行校验。

`Java API` 规范 (`JSR303`) 定义了 `Bean` 校验的标准 `validation-api`，但没有提供实现。`hibernate validation `是对这个规范的实现，并增加了校验注解如 `@Email`、`@Length` 等。`Spring Validation `是对`hibernate validation `的二次封装，用于支持 `spring mvc` 参数自动校验。接下来，我们以`spring-boot`项目为例，介绍`Spring Validation`的使用。

引入依赖

```xml
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-validation</artifactId>
</dependency>
<dependency>
<groupId>org.springframework.boot</groupId>
<artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

## 1. 定义全局异常处理

前面说过，如果校验失败，会抛出`MethodArgumentNotValidException`或者`ConstraintViolationException`异常。在实际项目开发中，通常会用**统一异常处理**来返回一个更友好的提示。比如我们系统要求无论发送什么异常，`http`的状态码必须返回`200`，由业务码去区分系统的异常情况。

```java
@ExceptionHandler({MethodArgumentNotValidException.class})
@ResponseStatus(HttpStatus.OK)
@ResponseBody
public ReturnResult<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex) {
    ReturnResult<Object> ret = new ReturnResult<>();
    BindingResult bindingResult = ex.getBindingResult();
    StringBuilder sb = new StringBuilder();
    for (FieldError fieldError : bindingResult.getFieldErrors()) {
        sb.append(fieldError.getField()).append("：").append(fieldError.getDefaultMessage()).append(",");
    }
    sb.deleteCharAt(sb.length() - 1);
    String msg = sb.toString();
    ret.setMessage(ReturnMsg.PARAMS_VALIDATE_FAILED + "," + msg, ReturnCode.PARAMS_VALIDATE_FAILED);
    return ret;
}

@ExceptionHandler({ConstraintViolationException.class})
@ResponseStatus(HttpStatus.OK)
@ResponseBody
public ReturnResult<Object> handleConstraintViolationException(ConstraintViolationException ex) {
    ReturnResult<Object> ret = new ReturnResult<>();
    Iterator<ConstraintViolation<?>> iterator = ex.getConstraintViolations().iterator();
    StringBuilder sb = new StringBuilder();
    while (iterator.hasNext()) {
        sb.append(iterator.next().getMessage()).append(";");
    }
    sb.deleteCharAt(sb.length() - 1);
    ret.setMessage(ReturnMsg.PARAMS_VALIDATE_FAILED + "," + sb.toString(), ReturnCode.PARAMS_VALIDATE_FAILED);
    return ret;
}
```



## 2. 基于 Get

**必须在`Controller`类上标注`@Validated`注解。**

1. **通过 Url 拼接传参**

   ```java
   @GetMapping("test1")
   public ReturnResult<Object> test1(@NotBlank(message = "p1 不能为空值") String p1,
                                     @NotNull(message = "p2 不能为 null") Integer p2) {
       System.out.printf("p1 的值为 %s%n", p1);
       System.out.printf("p2 的值为 %d%n", p2);
       return new ReturnResult<>();
   }
   ```

   当传入两个都是空参时，返回结果如下：

   ```json
   {
       "code": 500,
       "message": "参数校验失败,p2 不能为 null;p1 不能为空值",
       "result": null
   }
   ```

2. **通过 PathVariable 传参**

   ```java
   @GetMapping("test2/{id}")
   public ReturnResult<Object> test1(@PathVariable("id") @Min(value = 0, message = "id 的值不能小于 0") Integer id) {
       System.out.printf("id 的值为 %s%n", id);
       return new ReturnResult<>();
   }
   ```

   当传入的值小于 0 时，返回结果如下：

   ```json
   {
       "code": 500,
       "message": "参数校验失败,id 的值不能小于 0",
       "result": null
   }
   ```



## 3. 基于 Post

1. **基于表单方式**

   **必须在`Controller`类上标注`@Validated`注解。**

   ```java
   @PostMapping("test1")
   public ReturnResult<Object> test1(@NotBlank(message = "p1 不能为空值") String p1,
                                     @NotNull(message = "p2 不能为 null") Integer p2) {
       System.out.printf("p1 的值为 %s%n", p1);
       System.out.printf("p2 的值为 %d%n", p2);
       return new ReturnResult<>();
   }
   ```

   当传入两个参数都是空值时，返回结果如下：

   ```json
   {
       "code": 500,
       "message": "参数校验失败,p1 不能为空值;p2 不能为 null",
       "result": null
   }
   ```

2. **基于 JSON 方式**

   **在实体类上进行配置**

   ```java
   public class UserDTO {
   
       @NotNull(message = "用户 id 不能为空")
       @Min(value = 0, message = "最小值为 0")
       private Integer id;
   
       @NotNull(message = "用户名不能为空")
       @Length(min = 6, max =  12 , message = "用户名长度应该在 6 ~ 12 个字符之间")
       private String username;
   	
   	...
   	getter/setter/toString()
   }
   ```

   **在参数上使用 `@Validated` 或 `@Valid`**

   ```java
   /**
    * 此时使用 @Valid 或 @Validated 都可以
    */
   @PostMapping("test1")
   public ReturnResult<Object> test1(@RequestBody @Validated UserDTO userDTO) {
       System.out.println(userDTO);
    return new ReturnResult<>();
   }
   ```

   当什么都不传递时，抛出 `HttpMessageNotReadableException`，Spring Boot 默认返回如下

      ```json
   {
       "timestamp": "2021-01-19T09:27:49.526+00:00",
       "status": 400,
       "error": "Bad Request",
       "message": "",
       "path": "/postJSON/test1"
   }
      ```

   当传递的参数有误，则抛出 `MethodArgumentNotValidException` ，返回由全局异常捕获，结果如下：

   ```json
   参数如下
   {
       "id": 1,
       "username": "ak"
   }
   
   ------ 
   
   返回结果
   {
       "code": 500,
       "message": "参数校验失败,校验失败:username：用户名长度应该在 6 ~ 12 个字符之间, ",
       "result": null
   }
   ```

## 4. 分组校验

有时候一个 `DTO` 对象需要在多个接口中使用，而每次校验的内容不同，例如一个 `AccountDTO` 对象在保存时不用传 `id`，而在修改的时候则需要传递 `id`，这时候就需要使用分组校验。

首先现在类中定义两个接口，对应两个分组，然后将校验的值指定到需要的分组下：

```java
public class AccountDTO {

    /**
     * 保存的时候校验分组
     */
    public interface Save {
    }

    /**
     * 更新的时候校验分组
     */
    public interface Update {
    }

    /**
     * 指定校验为哪个分组下
     */
    @NotNull(message = "用户 id 不能为空", groups = Update.class)
    @Min(value = 0, message = "最小值为 0", groups = Update.class)
    private Long id;

    @NotNull(groups = {Save.class, Update.class})
    @Length(min = 6, max = 12, message = "长度应该在 6 ~ 12 个字符之间", groups = {Save.class, Update.class})
    private String username;

    @NotNull(groups = {Save.class, Update.class})
    @Length(min = 6, max = 12, message = "长度应该在 6 ~ 12 个字符之间", groups = {Save.class, Update.class})
    private String password;
 
    ...
    getter/setter/toString()
}
```

校验时指定分组，**此时必须使用 `@Validated`**

```java
@PostMapping("save")
public ReturnResult<Object> save(@Validated(AccountDTO.Save.class) @RequestBody AccountDTO accountDTO) {
    return new ReturnResult<>();
}

@PostMapping("update")
public ReturnResult<Object> update(@Validated(AccountDTO.Update.class) @RequestBody AccountDTO accountDTO) {
    return new ReturnResult<>();
}
```

## 5. 嵌套校验

有时候一个 `DTO` 对象中有另一个实体类对象，对该 `DTO` 校验时同时需要嵌套校验该类中的另一个实体类

实体类如下：

需要校验的 `PersonDTO` 中嵌套一个 `Friend` 对象，嵌套校验需要在嵌套的字段上添加 @Valid

```java
public class PersonDTO {

    /**
     * 保存的时候校验分组
     */
    public interface Save {
    }

    /**
     * 更新的时候校验分组
     */
    public interface Update {
    }

    @NotNull(message = "用户 id 不能为空", groups = AccountDTO.Update.class)
    @Min(value = 0, message = "最小值为 0", groups = AccountDTO.Update.class)
    private Long id;

    @NotNull(groups = {AccountDTO.Save.class, AccountDTO.Update.class})
    @Length(min = 6, max = 12, message = "长度应该在 6 ~ 12 个字符之间", groups = {AccountDTO.Save.class, AccountDTO.Update.class})
    private String username;

    @NotNull(groups = {AccountDTO.Save.class, AccountDTO.Update.class})
    @Length(min = 6, max = 12, message = "长度应该在 6 ~ 12 个字符之间", groups = {AccountDTO.Save.class, AccountDTO.Update.class})
    private String password;

    @Valid
    @NotNull(groups = {Save.class, Update.class})
    private Friend friend;
 
    ...
    getter/setter/toString()
}
```

`Friend` 对象

```java
public class Friend {

    @NotNull(message = "用户 id 不能为空", groups = PersonDTO.Update.class)
    @Min(value = 0, message = "最小值为 0", groups = PersonDTO.Update.class)
    private Long id;

    @NotNull(groups = {PersonDTO.Save.class, PersonDTO.Update.class})
    @Length(min = 6, max = 12, message = "长度应该在 6 ~ 12 个字符之间", groups = {PersonDTO.Save.class, PersonDTO.Update.class})
    private String username;

    @NotNull(groups = {PersonDTO.Save.class, PersonDTO.Update.class})
    @Length(min = 6, max = 12, message = "长度应该在 6 ~ 12 个字符之间", groups = {PersonDTO.Save.class, PersonDTO.Update.class})
    private String password;
    
    ...
    getter/setter/toString()
}
```

## 6. 代码校验

如果不希望通过在入参的地方标注注解实现校验，而是想拿到校验的结果，在代码中做处理，可以通过以下方式实现：

```java
@Resource
private Validator globalValidator;

public ReturnResult<Object> test1(@RequestBody UserDTO userDTO) {
    Set<ConstraintViolation<UserDTO>> validate = globalValidator.validate(userDTO);
    // 如果校验通过，validate为空
    if (validate.isEmpty()) {
        System.out.println("校验通过");
    } else {
        // 校验失败，遍历失败结果做处理
        for (ConstraintViolation<UserDTO> violation : validate) {
            System.out.println(violation);
        }
    }
    return new ReturnResult<>();
}
```

## 7. 自定义校验

当提供的校验注解不能满足业务需求时，需要提供自定义注解来实现校验。这里定义一个简单的注解来判断传入的参数是否是想要的字符串类型。

定义一个注解：

```java
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.ANNOTATION_TYPE, ElementType.CONSTRUCTOR, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Constraint(validatedBy = {EnumValidator.class})
public @interface IsEnumType {

    // 默认错误消息
    String message() default "枚举格式错误";

    // 分组
    Class<?>[] groups() default {};

    // 负载
    Class<? extends Payload>[] payload() default {};

}
```

编写校验逻辑：

```java
public class EnumValidator implements ConstraintValidator<IsEnumType, String> {

    /**
     * 校验参数是否是如下类型
     */
    @Override
    public boolean isValid(String value, ConstraintValidatorContext constraintValidatorContext) {
        return value.equals("interface") || value.equals("class") || value.equals("enum");
    }

}
```

## 8. 快速失败

当对多个参数校验时，如果当前校验的参数失败，直接返回异常，不再校验后面的参数。

```java
@Configuration
public class ValidationConfig {

    /**
     * 快速失败。当校验到当前参数失败直接返回，不会校验后面的
     */
    @Bean
    public Validator validator() {
        ValidatorFactory validatorFactory = Validation.byProvider(HibernateValidator.class)
                .configure()
                // 快速失败模式
                .failFast(true)
                .buildValidatorFactory();
        return validatorFactory.getValidator();
    }
}
```

可以再对基于 Get 方式的 Url 拼接接口进行校验，发现只对 `p1` 进行了校验:

```json
{
    "code": 500,
    "message": "参数校验失败,p1 不能为空值",
    "result": null
}
```

## 9. `@Valid` 和 `@Validated` 区别

| 区别         | @Valid                                          | @Validated              |
| :----------- | :---------------------------------------------- | :---------------------- |
| 提供者       | JSR-303规范                                     | Spring                  |
| 是否支持分组 | 不支持                                          | 支持                    |
| 标注位置     | METHOD, FIELD, CONSTRUCTOR, PARAMETER, TYPE_USE | TYPE, METHOD, PARAMETER |
| 嵌套校验     | 支持                                            | 不支持                  |

## 10. 常用注解

| 注解             | 校验功能                           |
| :--------------- | :--------------------------------- |
| @AssertFalse     | 必须是false                        |
| @AssertTrue      | 必须是true                         |
| @DecimalMax      | 小于等于给定的值                   |
| @DecimalMin      | 大于等于给定的值                   |
| @Digits          | 可设定最大整数位数和最大小数位数   |
| @Email           | 校验是否符合Email格式              |
| @Future          | 必须是将来的时间                   |
| @FutureOrPresent | 当前或将来时间                     |
| @Max             | 最大值                             |
| @Min             | 最小值                             |
| @Negative        | 负数（不包括0）                    |
| @NegativeOrZero  | 负数或0                            |
| @NotBlank        | 不为null并且包含至少一个非空白字符 |
| @NotEmpty        | 不为null并且不为空                 |
| @NotNull         | 不为null                           |
| @Null            | 为null                             |
| @Past            | 必须是过去的时间                   |
| @PastOrPresent   | 必须是过去的时间，包含现在         |
| @PositiveOrZero  | 正数或0                            |
| @Size            | 校验容器的元素个数                 |

## 11. 源码地址

https://github.com/mapleinsss/spring_validation_learn