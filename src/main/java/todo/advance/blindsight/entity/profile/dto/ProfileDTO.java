package todo.advance.blindsight.entity.profile.dto;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import todo.advance.blindsight.entity.subCard.SubCard;

@Getter
@Setter
@NoArgsConstructor
public class ProfileDTO {
    private String accountId;
    private String profileId;
    private String email;
    private String username;
    private String password;
    private String avatar;
    private String bio;
    private String dateJoin;
    private List<SubCard> subcards;
}
