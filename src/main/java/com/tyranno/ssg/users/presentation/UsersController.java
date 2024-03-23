package com.tyranno.ssg.users.presentation;

import com.tyranno.ssg.global.GlobalException;
import com.tyranno.ssg.global.ResponseEntity;
import com.tyranno.ssg.security.JwtTokenProvider;
import com.tyranno.ssg.users.application.UsersService;
import com.tyranno.ssg.users.dto.LoginDto;
import com.tyranno.ssg.users.dto.SignUpDto;
import com.tyranno.ssg.users.dto.UserIdentifyDto;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

//new ResponseEntity<>(message, headers, HttpStatus.OK);
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/v1/users")
public class UsersController {

    private final UsersService usersService;
    private final JwtTokenProvider jwtTokenProvider;

    @PostMapping("/signup")
    public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto signUpDto) {

        usersService.createUsers(signUpDto);

        return new ResponseEntity<>("회원가입 완료");
    }

    @PostMapping("/id_check")
    public ResponseEntity<?> checkIdExist(@RequestBody String loginId) {
        usersService.checkLoginId(loginId);

        return new ResponseEntity<>("사용할 수 있는 아이디입니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<?> logIn(@Valid @RequestBody LoginDto logInDto) {
        return new ResponseEntity<>(usersService.loginUsers(logInDto));
    }

    @PostMapping("/find_id")
    public ResponseEntity<?> findId(@Valid @RequestBody UserIdentifyDto userIdentifyDto) {
        return new ResponseEntity<>(usersService.findLoginId(userIdentifyDto));
    }

    @PostMapping("/change_pw")
    public ResponseEntity<?> changePassword(@RequestBody String password, Authentication authentication) {
        String uuid = authentication.getName();
        System.out.println(uuid);
        usersService.changePassword(password, uuid);
        return new ResponseEntity<>("비밀번호 변경 성공");
    }

}
