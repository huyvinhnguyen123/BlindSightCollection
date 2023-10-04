package Blind.Sight.Commnunity.web.controller;

import Blind.Sight.Commnunity.config.security.JwtUtil;
import Blind.Sight.Commnunity.domain.service.UserService;
import Blind.Sight.Commnunity.dto.user.LoginInput;
import Blind.Sight.Commnunity.dto.user.UserDataInput;
import Blind.Sight.Commnunity.web.controller.exception.HandleRequest;
import Blind.Sight.Commnunity.web.response.LoginResponse;
import Blind.Sight.Commnunity.web.response.common.ResponseDto;
import Blind.Sight.Commnunity.web.response.mapper.LoginMapper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AuthenticationController {
    private final UserService userService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping("/register")
    public ResponseEntity<ResponseDto<Object>> createUser(@Valid @RequestBody UserDataInput userDataInput, BindingResult bindingResult) {
        log.info("Request creating user...");

        // Check for validation errors in the input
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            return HandleRequest.validateRequest(HttpStatus.BAD_REQUEST, fieldErrors, bindingResult);
        }

        userService.createUser(userDataInput);
        return ResponseEntity.ok(ResponseDto.build().withMessage("OK"));
    }

    @PostMapping("/login")
    public ResponseEntity<ResponseDto<Object>> login(@Valid @RequestBody LoginInput loginInput, BindingResult bindingResult) {
        log.info("Request authenticating user...");

        // Check for validation errors in the input
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            return HandleRequest.validateRequest(HttpStatus.BAD_REQUEST, fieldErrors, bindingResult);
        }

        String token = userService.login(loginInput, authenticationManager, jwtUtil);
        String refreshToken = UUID.randomUUID().toString();
        LoginResponse loginResponse = LoginMapper.mapToLogin(token, refreshToken);
        log.info("Login successfully");
        return ResponseEntity.ok(ResponseDto.build().withData(loginResponse));
    }
}
