package org.maple.spring_validation_learn.controller;

import org.maple.spring_validation_learn.common.ReturnResult;
import org.maple.spring_validation_learn.dto.InterfaceInfoDTO;
import org.maple.spring_validation_learn.dto.nest.PersonDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * 自定义注解校验
 */
@RequestMapping("self")
@RestController
public class TestSelfController {

    @PostMapping("save")
    public ReturnResult<Object> save(@Valid @RequestBody InterfaceInfoDTO interfaceInfoDTO) {
        return new ReturnResult<>();
    }
}
