package org.maple.spring_validation_learn.controller;

import org.maple.spring_validation_learn.common.ReturnResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@RequestMapping("postForm")
@RestController
@Validated
public class TestPostFormController {

    /**
     * 针对 Post 表单参数
     */
    @PostMapping("test1")
    public ReturnResult<Object> test1(@NotBlank(message = "p1 不能为空值") String p1,
                                      @NotNull(message = "p2 不能为 null") Integer p2) {
        System.out.printf("p1 的值为 %s%n", p1);
        System.out.printf("p2 的值为 %d%n", p2);
        return new ReturnResult<>();
    }
}
