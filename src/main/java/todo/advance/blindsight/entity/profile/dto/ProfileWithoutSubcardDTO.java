package todo.advance.blindsight.entity.profile.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ProfileWithoutSubcardDTO {
    private String accountId;
    private String profileId;
    private String email;
    private String username;
    private String avatar;
    private String bio;
    private String dateJoin;
}
