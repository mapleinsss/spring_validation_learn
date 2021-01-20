package org.maple.spring_validation_learn.dto.nest;

import org.hibernate.validator.constraints.Length;
import org.maple.spring_validation_learn.dto.AccountDTO;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 嵌套校验需要在嵌套的字段上添加 @Valid
 */
public class PersonDTO {

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

    @NotNull(message = "用户 id 不能为空", groups = AccountDTO.Update.class)
    @Min(value = 0, message = "最小值为 0", groups = AccountDTO.Update.class)
    private Long id;

    @NotNull(groups = {AccountDTO.Save.class, AccountDTO.Update.class})
    @Length(min = 6, max = 12, message = "长度应该在 6 ~ 12 个字符之间", groups = {AccountDTO.Save.class, AccountDTO.Update.class})
    private String username;

    @NotNull(groups = {AccountDTO.Save.class, AccountDTO.Update.class})
    @Length(min = 6, max = 12, message = "长度应该在 6 ~ 12 个字符之间", groups = {AccountDTO.Save.class, AccountDTO.Update.class})
    private String password;

    @Valid
    @NotNull(groups = {Save.class, Update.class})
    private Friend friend;

    public Friend getFriend() {
        return friend;
    }

    public void setFriend(Friend friend) {
        this.friend = friend;
    }

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
