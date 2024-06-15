package com.bessonov.musicappserver.user;

import com.bessonov.musicappserver.database.userData.UserData;
import com.bessonov.musicappserver.database.userData.UserDataDTO;
import com.bessonov.musicappserver.database.userData.UserDataRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

            return new UserResponseDTO(errorMessage.toString(), 400);
        }
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            userLoginDTO.getUsername(),
                            userLoginDTO.getPassword()
                    )
            );

            Optional<UserData> userData = userDataRepository.findByUsername(userLoginDTO.getUsername());

            if (userData.isEmpty()) {
                return new UserResponseDTO("There is no more user with this username", 400);
            }

            String jwtToken = userService.createJwtToken(userData.get());

            return new UserResponseDTO(jwtToken, 200);
        } catch (Exception ex) {
            //System.out.println("There is an Exception :");
            //ex.printStackTrace();
        }

        return new UserResponseDTO("Bad username or password", 400);
    }

    @PostMapping("/register")
    public UserResponseDTO register(@Valid @RequestBody UserRegisterDTO userRegisterDTO, BindingResult result) {
        if (result.hasErrors()) {
            var errorsList = result.getAllErrors();
            var errorMessage = new StringBuilder();

            for (var objectError : errorsList) {
                var error = (FieldError) objectError;
                errorMessage.append(error.getField()).append(": ").append(error.getDefaultMessage()).append("; ");
            }

            return new UserResponseDTO(errorMessage.toString(), 400);
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
                return new UserResponseDTO("Username already used", 400);
            }

            otherUser = userDataRepository.findByEmail(userRegisterDTO.getEmail());
            if (otherUser.isPresent()) {
                return new UserResponseDTO("Email address already used", 400);
            }

            userDataRepository.save(userData);

            String jwtToken = userService.createJwtToken(userData);

            return new UserResponseDTO(jwtToken, 200);
        } catch (Exception ex) {
            //System.out.println("There is an Exception :");
            //ex.printStackTrace();
        }

        return new UserResponseDTO("Failed to register", 400);
    }

    @PostMapping("/edit")
    private UserEditResponseDTO edit(@RequestBody UserEditRequestDTO userEditRequestDTO, Authentication authentication) {
        Map<String, String> editMap = new HashMap<>();

        Optional<UserData> userData = userDataRepository.findByUsername(authentication.getName());

        if (userData.isEmpty()) {
            editMap.put("error", "User not found");
            return new UserEditResponseDTO(editMap);
        }

        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                            authentication.getName(),
                            userEditRequestDTO.getPassword()
                    )
            );
        } catch (Exception ex) {
            editMap.put("error", "Invalid password");
            return new UserEditResponseDTO(editMap);
        }

        return new UserEditResponseDTO(userService.edit(authentication.getName(), userEditRequestDTO));
    }
}
