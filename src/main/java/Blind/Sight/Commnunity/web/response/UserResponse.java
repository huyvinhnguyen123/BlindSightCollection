package Blind.Sight.Commnunity.web.response;

import Blind.Sight.Commnunity.dto.user.UserData;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserResponse {
    private Iterable<UserData> userData;
}
