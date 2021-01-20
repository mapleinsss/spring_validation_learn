package org.maple.spring_validation_learn.dto;

import org.maple.spring_validation_learn.self.IsEnumType;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class InterfaceInfoDTO {

    @NotNull(message = "用户 id 不能为空")
    @PositiveOrZero
    private Long id;

    @IsEnumType
    private String type;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
