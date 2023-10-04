package Blind.Sight.Commnunity.web.response.mapper;

import Blind.Sight.Commnunity.dto.user.UserData;
import Blind.Sight.Commnunity.web.response.UserResponse;

public class UserMapper {
    private UserMapper() {}
    public static UserResponse mapToUser(Iterable<UserData> userDataList) {
        return UserResponse.builder()
                .userData(userDataList)
                .build();
    }
}
