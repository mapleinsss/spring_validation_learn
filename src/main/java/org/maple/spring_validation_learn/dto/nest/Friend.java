package org.maple.spring_validation_learn.dto.nest;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
