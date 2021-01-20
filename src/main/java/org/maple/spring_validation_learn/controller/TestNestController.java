package org.maple.spring_validation_learn.controller;

import org.maple.spring_validation_learn.common.ReturnResult;
import org.maple.spring_validation_learn.dto.nest.PersonDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 针对嵌套校验
 */
@RequestMapping("nest")
@RestController
public class TestNestController {

    @PostMapping("save")
    public ReturnResult<Object> save(@Validated(PersonDTO.Save.class) @RequestBody PersonDTO personDTO) {
        return new ReturnResult<>();
    }

    @PostMapping("update")
    public ReturnResult<Object> update(@Validated(PersonDTO.Update.class) @RequestBody PersonDTO personDTO) {
        return new ReturnResult<>();
    }
}
