package org.maple.spring_validation_learn.controller;

import org.maple.spring_validation_learn.common.ReturnResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * 针对 requestParam/PathVariable
 * 抛出 ConstraintViolationException 异常
 */
@Validated
@RequestMapping("get")
@RestController
public class TestGetController {

    /**
     * 针对 Get 拼接参数
     */
    @GetMapping("test1")
    public ReturnResult<Object> test1(@NotBlank(message = "p1 不能为空值") String p1,
                                      @NotNull(message = "p2 不能为 null") Integer p2) {
        System.out.printf("p1 的值为 %s%n", p1);
        System.out.printf("p2 的值为 %d%n", p2);
        return new ReturnResult<>();
    }

    /**
     * 针对 Get PathVariable
     */
    @GetMapping("test2/{id}")
    public ReturnResult<Object> test1(@PathVariable("id") @Min(value = 0, message = "id 的值不能小于 0") Integer id) {
        System.out.printf("id 的值为 %s%n", id);
        return new ReturnResult<>();
    }
}
