package org.maple.spring_validation_learn.dto;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

public class AccountDTO {

    /**
     * 保存的时候校验分组
     */
    public interface Save {
    }

    /**
     * 更新的时候校验分组
     */
    public interface Update {
    }

    @NotNull(message = "用户 id 不能为空", groups = Update.class)
    @Min(value = 0, message = "最小值为 0", groups = Update.class)
    private Long id;

    @NotNull(groups = {Save.class, Update.class})
    @Length(min = 6, max = 12, message = "长度应该在 6 ~ 12 个字符之间", groups = {Save.class, Update.class})
    private String username;

    @NotNull(groups = {Save.class, Update.class})
    @Length(min = 6, max = 12, message = "长度应该在 6 ~ 12 个字符之间", groups = {Save.class, Update.class})
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
