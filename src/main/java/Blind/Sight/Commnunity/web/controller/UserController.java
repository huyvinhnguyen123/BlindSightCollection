package Blind.Sight.Commnunity.web.controller;

import Blind.Sight.Commnunity.domain.service.UserService;
import Blind.Sight.Commnunity.dto.user.UserData;
import Blind.Sight.Commnunity.dto.user.UserDataInput;
import Blind.Sight.Commnunity.web.response.UserResponse;
import Blind.Sight.Commnunity.web.response.common.ResponseDto;
import Blind.Sight.Commnunity.web.response.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.GeneralSecurityException;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1/users")
public class UserController {
    private final UserService userService;

    @PostMapping("/user/update")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<ResponseDto<Object>> updateUser(@RequestBody UserDataInput userDataInput) throws GeneralSecurityException, IOException {
        log.info("Request updating user...");
        userService.updateUserAndUpdateImage(userDataInput.getFileId(), userDataInput);
        return ResponseEntity.ok(ResponseDto.build().withMessage("OK"));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<ResponseDto<Object>> findAllUsers() {
        log.info("Request selecting user...");
        Iterable<UserData> userDataList = userService.findAllUsers();
        UserResponse userResponse = UserMapper.mapToUser(userDataList);
        return ResponseEntity.ok(ResponseDto.build().withData(userResponse));
    }
}
