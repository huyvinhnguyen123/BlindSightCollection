package todo.advance.blindsight.entity.profile.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdatePasswordDTO {
    private String profileId;
    private String password;
    private String updatePassword;
    private String retypePassword;
}
