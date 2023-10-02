package Blind.Sight.Commnunity.dto.user;

import Blind.Sight.Commnunity.dto.validate.password.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginInput {
    @NotNull(message = "{User.loginId.notNull}")
    @NotEmpty(message = "{User.loginId.notEmpty}")
    @Email
    private String email;
    @NotNull(message = "{User.password.notNull}")
    @NotEmpty(message = "{User.password.notEmpty}")
    @ValidPassword
    private String password;
}
