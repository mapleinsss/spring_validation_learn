package org.maple.spring_validation_learn.controller;

import org.maple.spring_validation_learn.common.ReturnResult;
import org.maple.spring_validation_learn.dto.AccountDTO;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 针对分组校验
 */
@RequestMapping("group")
@RestController
public class TestGroupController {


    @PostMapping("save")
    public ReturnResult<Object> save(@Validated(AccountDTO.Save.class) @RequestBody AccountDTO accountDTO) {
        return new ReturnResult<>();
    }

    @PostMapping("update")
    public ReturnResult<Object> update(@Validated(AccountDTO.Update.class) @RequestBody AccountDTO accountDTO) {
        return new ReturnResult<>();
    }
}
