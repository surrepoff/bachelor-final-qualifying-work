package com.bessonov.musicappserver.user;

import com.bessonov.musicappserver.database.userData.UserData;
import com.bessonov.musicappserver.database.userData.UserDataDTO;
import com.bessonov.musicappserver.database.userData.UserDataRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/user")
public class UserController {
    @Autowired
    private UserDataRepository userDataRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @GetMapping("/info")
    public UserDataDTO info(Authentication authentication) {
        Optional<UserData> userData = userDataRepository.findByUsername(authentication.getName());
        return userData.map(UserDataDTO::new).orElse(null);
    }

    @PostMapping("/login")
    public UserResponseDTO login(@Valid @RequestBody UserLoginDTO userLoginDTO, BindingResult result) {
        if (result.hasErrors()) {
            var errorsList = result.getAllErrors();
            var errorMessage = new StringBuilder();

            for (var objectError : errorsList) {
                var error = (FieldError) objectError;
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }

            return new UserResponseDTO(false, errorMessage.toString());
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getUsername(),
                            userLoginDTO.getPassword()
                    )
            );

            Optional<UserData> userData = userDataRepository.findByUsername(userLoginDTO.getUsername());

            if (userData.isEmpty()) {
                return new UserResponseDTO(false, "Пользователя с таким логином не существует");
            }

            String jwtToken = userService.createJwtToken(userData.get());

            return new UserResponseDTO(true, jwtToken);
        } catch (Exception ignored) {
        }

        return new UserResponseDTO(false, "Неверный логин или пароль");
    }

    @Transactional
    @PostMapping("/register")
    public UserResponseDTO register(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult result) {
        if (result.hasErrors()) {
            var errorsList = result.getAllErrors();
            var errorMessage = new StringBuilder();

            for (var objectError : errorsList) {
                var error = (FieldError) objectError;
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }

            return new UserResponseDTO(false, errorMessage.toString());
        }

        var bCryptPasswordEncoder = new BCryptPasswordEncoder();

        UserData userData = new UserData();
        userData.setUsername(userRegisterDTO.getUsername());
        userData.setEmail(userRegisterDTO.getEmail());
        userData.setNickname(userRegisterDTO.getUsername());
        userData.setRegistrationDate(new Date());
        userData.setLastUpdateDate(new Date());
        userData.setPassword(bCryptPasswordEncoder.encode(userRegisterDTO.getPassword()));

        try {
            Optional<UserData> otherUser;

            otherUser = userDataRepository.findByUsername(userRegisterDTO.getUsername());
            if (otherUser.isPresent()) {
                return new UserResponseDTO(false, "Логин уже используется");
            }

            otherUser = userDataRepository.findByEmail(userRegisterDTO.getEmail());
            if (otherUser.isPresent()) {
                return new UserResponseDTO(false, "Адрес электронной почты уже используется");
            }

            userDataRepository.save(userData);

            String jwtToken = userService.createJwtToken(userData);

            return new UserResponseDTO(true, jwtToken);
        } catch (Exception ignored) {
        }

        return new UserResponseDTO(false, "Не удалось зарегистрировать пользователя");
    }

    @PostMapping("/edit")
    public UserEditResponseDTO edit(@RequestBody UserEditRequestDTO userEditRequestDTO, Authentication authentication) {
        Map<String, String> editMap = new HashMap<>();

        Optional<UserData> userData = userDataRepository.findByUsername(authentication.getName());

        if (userData.isEmpty()) {
            editMap.put("Ошибка", "Пользователя с таким логином не существует");
            return new UserEditResponseDTO(editMap);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authentication.getName(),
                            userEditRequestDTO.getPassword()
                    )
            );
        } catch (Exception ex) {
            editMap.put("Ошибка", "Неверный пароль");
            return new UserEditResponseDTO(editMap);
        }

        return new UserEditResponseDTO(userService.edit(authentication.getName(), userEditRequestDTO));
    }
}
