package Blind.Sight.Commnunity.web.controller;

import Blind.Sight.Commnunity.domain.service.AdminService;
import Blind.Sight.Commnunity.dto.user.UserDataInput;
import Blind.Sight.Commnunity.web.controller.exception.HandleRequest;
import Blind.Sight.Commnunity.web.response.common.ResponseDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("api/v1")
public class AdminController {
    private final AdminService adminService;
    private static final String DEFAULT_URL = "/create/admin/ZbGkKnmOqkllQIe9";

    @Value("${ValidInput}")
    private String validInput;

    @PostMapping(DEFAULT_URL)
    public ResponseEntity<ResponseDto<Object>> createAdmin(@Valid @RequestBody UserDataInput userDataInput, BindingResult bindingResult) {
        log.info("Request creating admin...");

        // Check for validation errors in the input
        if (bindingResult.hasErrors()) {
            Map<String, String> fieldErrors = new HashMap<>();
            return HandleRequest.validateRequest(HttpStatus.BAD_REQUEST, fieldErrors, bindingResult);
        }

        adminService.createAdmin(userDataInput);
        return ResponseEntity.ok(ResponseDto.build().withMessage("OK"));
    }
}
