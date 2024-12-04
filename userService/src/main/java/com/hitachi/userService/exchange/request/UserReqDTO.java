package com.hitachi.userService.exchange.request;

import com.hitachi.userService.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class UserReqDTO {
    @NotNull
    private String name;
    @NotNull
    private String email;

    private int age;

    private User.UserQualification qualification;

}
