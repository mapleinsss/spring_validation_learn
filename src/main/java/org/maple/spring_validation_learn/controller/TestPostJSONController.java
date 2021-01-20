package org.maple.spring_validation_learn.controller;

import org.maple.spring_validation_learn.common.ReturnResult;
import org.maple.spring_validation_learn.dto.UserDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 出现异常时，抛出 MethodArgumentNotValidException
 */
@RequestMapping("postJSON")
@RestController
public class TestPostJSONController {

    /**
     * 此时使用 @Valid 或 @Validated 都可以
     */
    @PostMapping("test1")
    public ReturnResult<Object> test1(@RequestBody @Valid UserDTO userDTO) {
        System.out.println(userDTO);
        return new ReturnResult<>();
    }
}
