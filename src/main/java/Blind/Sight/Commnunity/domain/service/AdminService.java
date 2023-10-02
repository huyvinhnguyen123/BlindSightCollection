package Blind.Sight.Commnunity.domain.service;

import Blind.Sight.Commnunity.config.security.PasswordEncrypt;
import Blind.Sight.Commnunity.domain.entity.User;
import Blind.Sight.Commnunity.domain.repository.UserRepository;
import Blind.Sight.Commnunity.dto.user.UserDataInput;
import Blind.Sight.Commnunity.util.common.DeleteFlag;
import Blind.Sight.Commnunity.util.common.LockFlag;
import Blind.Sight.Commnunity.util.common.RoleData;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;

    public void createAdmin(UserDataInput userDataInput) {
        User user = new User();
        user.setUserName(userDataInput.getName());
        user.setEmail(userDataInput.getEmail());
        user.setPassword(PasswordEncrypt.bcryptPassword(userDataInput.getPassword()));
        user.setRole(RoleData.ADMIN.getRole());
        user.setLogFlag(LockFlag.NON_LOCK.getCode());
        user.setDeleteFlag(DeleteFlag.NON_DELETE.getCode());
        userRepository.save(user);
        log.info("Save new admin success");

        log.info("Register new admin success");
    }
}
