package org.maple.spring_validation_learn.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class UserDTO {

    @NotNull(message = "用户 id 不能为空")
    @Min(value = 0, message = "最小值为 0")
    private Integer id;

    @NotNull(message = "用户名不能为空")
    @Length(min = 6, max =  12 , message = "用户名长度应该在 6 ~ 12 个字符之间")
    private String username;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "id=" + id +
                ", username='" + username + '\'' +
                '}';
    }
}
