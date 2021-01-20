package org.maple.spring_validation_learn.controller;

import org.maple.spring_validation_learn.common.ReturnResult;
import org.maple.spring_validation_learn.dto.UserDTO;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Set;

/**
 * 自己在代码里进行校验
 */
@RequestMapping("code")
@RestController
public class TestCodeController {

    @Resource
    private Validator globalValidator;

    @PostMapping("test1")
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
}
