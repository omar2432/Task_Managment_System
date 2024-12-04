package com.hitachi.userService.exchange.response;

import com.hitachi.userService.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class UserResDTO {
    private Long id;

    private String name;

    private String email;

    private User.UserQualification qualification;

}
