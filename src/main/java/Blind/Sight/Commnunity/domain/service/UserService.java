package Blind.Sight.Commnunity.domain.service;

import Blind.Sight.Commnunity.config.security.JwtUtil;
import Blind.Sight.Commnunity.config.security.PasswordEncrypt;
import Blind.Sight.Commnunity.domain.entity.User;
import Blind.Sight.Commnunity.domain.repository.UserRepository;
import Blind.Sight.Commnunity.dto.user.LoginInput;
import Blind.Sight.Commnunity.dto.user.UserData;
import Blind.Sight.Commnunity.dto.user.UserDataInput;
import Blind.Sight.Commnunity.util.common.DeleteFlag;
import Blind.Sight.Commnunity.util.common.LockFlag;
import Blind.Sight.Commnunity.util.common.RoleData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public void createUser(UserDataInput userDataInput) {
        User user = new User();
        user.setUserName(userDataInput.getName());
        user.setEmail(userDataInput.getEmail());
        user.setPassword(PasswordEncrypt.bcryptPassword(userDataInput.getPassword()));
        user.setRole(RoleData.USER.getRole());
        user.setLogFlag(LockFlag.NON_LOCK.getCode());
        user.setDeleteFlag(DeleteFlag.NON_DELETE.getCode());
        userRepository.save(user);
        log.info("Save new user success");

        log.info("Register new user success");
    }

    public Iterable<UserData> findAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserData> userDataList = new ArrayList<>();
        for(User user: users) {
            UserData userData = new UserData();
            userData.setId(user.getUserId());
            userData.setName(user.getUsername());
            userData.setEmail(user.getEmail());
            userData.setRole(user.getRole());
            userDataList.add(userData);
        }

        log.info("Get list users success");
        return userDataList;
    }

    public String login(LoginInput loginInput, AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        Authentication authentication = new UsernamePasswordAuthenticationToken(
                loginInput.getEmail(),
                loginInput.getPassword()
        );

        Authentication login = authenticationManager.authenticate(authentication);

        // Check if the user is deleted or locked
        User user = (User) login.getPrincipal();

        String token = jwtUtil.createToken(user);
        log.info("Create token success: {}", token);

        return token;
    }
}
