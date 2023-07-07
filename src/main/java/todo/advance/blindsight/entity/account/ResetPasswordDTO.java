package todo.advance.blindsight.entity.account;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ResetPasswordDTO {
    private String codeOTP;
    private String password;
    private String retypePassword;
}
