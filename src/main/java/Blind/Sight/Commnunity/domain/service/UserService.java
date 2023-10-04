package Blind.Sight.Commnunity.domain.service;

import Blind.Sight.Commnunity.config.security.JwtUtil;
import Blind.Sight.Commnunity.config.security.PasswordEncrypt;
import Blind.Sight.Commnunity.domain.entity.User;
import Blind.Sight.Commnunity.domain.entity.many.UserImage;
import Blind.Sight.Commnunity.domain.repository.UserImageRepository;
import Blind.Sight.Commnunity.domain.repository.UserRepository;
import Blind.Sight.Commnunity.dto.user.LoginInput;
import Blind.Sight.Commnunity.dto.user.UserData;
import Blind.Sight.Commnunity.dto.user.UserDataInput;
import Blind.Sight.Commnunity.util.common.DeleteFlag;
import Blind.Sight.Commnunity.util.common.LockFlag;
import Blind.Sight.Commnunity.util.common.RoleData;
import Blind.Sight.Commnunity.util.format.CustomDateTimeFormatter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final UserImageRepository userImageRepository;
    private final ImageService imageService;
    private static final String NOT_AVAILABLE = "Not available";
    @Value("${drive.folder.user}")
    private String userPath;

    public User findUserById(String userId) {
        User existUser = userRepository.findById(userId).orElseThrow(
                () -> {
                    log.error("Not found this user: {}", userId);
                    return new NullPointerException("Nout found this user: " + userId);
                }
        );

        log.info("Found user");
        return existUser;
    }

    public void createUser(UserDataInput userDataInput) {
        User user = new User();
        user.setUserName(userDataInput.getName());
        user.setBirthDate(CustomDateTimeFormatter.dateOfBirthFormatter(userDataInput.getBirthDate()));
        user.setEmail(userDataInput.getEmail());
        user.setPassword(PasswordEncrypt.bcryptPassword(userDataInput.getPassword()));
        user.setRole(RoleData.USER.getRole());
        user.setLogFlag(LockFlag.NON_LOCK.getCode());
        user.setDeleteFlag(DeleteFlag.NON_DELETE.getCode());
        userRepository.save(user);
        log.info("Save new user success");

        log.info("Register new user success");
    }

    public void updateUserAndUpdateImage(String imageId, UserDataInput userDataInput) throws GeneralSecurityException, IOException {
        User existUser = findUserById(userDataInput.getUserId());
        existUser.setUserName(userDataInput.getName());
        existUser.setBirthDate(CustomDateTimeFormatter.dateOfBirthFormatter(userDataInput.getBirthDate()));
        userRepository.save(existUser);

        if(imageId != null) {
            for(UserImage userImage: userImageRepository.findAll()) {
                if(userImage.getUser().getUserId().equals(existUser.getUserId()) && userImage.getImage().getImageId().equals(imageId)) {
                    imageService.updateImage(imageId, userDataInput.getFile(), userPath);
                }
            }
        } else {
            for(UserImage userImage: userImageRepository.findAll()) {
                if(userImage.getUser().getUserId().equals(existUser.getUserId())) {
                    imageService.createImage(userDataInput.getFile(), userPath);
                }
            }
        }

        log.info("Update user success");
    }

    public void updateUserAndDeleteImage(String userId, String imageId) throws GeneralSecurityException, IOException {
        User existUser = findUserById(userId);
        for(UserImage userImage: userImageRepository.findAll()) {
            if(userImage.getUser().getUserId().equals(existUser.getUserId()) && userImage.getImage().getImageId().equals(imageId)) {
                imageService.deleteImage(imageId);
            }
        }
    }

    public void deleteUser(String userId, String imageId) throws GeneralSecurityException, IOException {
        User existUser = findUserById(userId);
        existUser.setOldLoginId(existUser.getEmail());
        existUser.setEmail(NOT_AVAILABLE);
        existUser.setPassword(NOT_AVAILABLE);
        userRepository.save(existUser);

        for(UserImage userImage: userImageRepository.findAll()) {
            if(userImage.getUser().getUserId().equals(existUser.getUserId()) && userImage.getImage().getImageId().equals(imageId)) {
                imageService.deleteImage(imageId);
            }
        }
    }

    public Iterable<UserData> findAllUsers() {
        List<User> users = userRepository.findAll();

        List<UserData> userDataList = new ArrayList<>();
        for(User user: users) {
            UserData userData = new UserData();
            userData.setId(user.getUserId());
            userData.setBirthDate(user.getBirthDate());
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
