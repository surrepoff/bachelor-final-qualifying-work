package com.bessonov.musicappserver.user;

import com.bessonov.musicappserver.database.userData.UserData;
import com.bessonov.musicappserver.database.userData.UserDataRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jose.jws.MacAlgorithm;
import org.springframework.security.oauth2.jwt.JwsHeader;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@Transactional
public class UserService implements UserDetailsService {
    @Value("${security.jwt.secret-key}")
    private String jwtSecretKey;

    @Value("${security.jwt.issuer}")
    private String jwtIssuer;

    @Autowired
    private UserDataRepository userDataRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<UserData> userData = userDataRepository.findByUsername(username);

        return userData.map(data -> User.withUsername(data.getUsername())
                .password(data.getPassword())
                .build()).orElse(null);

    }

    public String createJwtToken(UserData userData) {
        Instant now = Instant.now();

        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer(jwtIssuer)
                .issuedAt(now)
                .expiresAt(now.plus(8, ChronoUnit.HOURS))
                .subject(userData.getUsername())
                .build();

        var encoder = new NimbusJwtEncoder(new ImmutableSecret<>(jwtSecretKey.getBytes()));
        var params = JwtEncoderParameters.from(JwsHeader.with(MacAlgorithm.HS256).build(), claims);

        return encoder.encode(params).getTokenValue();
    }

    public Map<String, String> edit(String username, UserEditRequestDTO userEditRequestDTO) {
        Map<String, String> editMap = new HashMap<>();

        Optional<UserData> userData = userDataRepository.findByUsername(username);

        if (userData.isEmpty())
            return editMap;

        for (Map.Entry<String, String> entry : userEditRequestDTO.getEditMap().entrySet()) {
            switch (entry.getKey()) {
                case "nickname": {
                    String regex = "^[a-zA-Z0-9_]+$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(entry.getValue());

                    if (!matcher.matches()) {
                        editMap.put("nickname", "Error: Founded invalid characters");
                        break;
                    }

                    if (entry.getValue().length() < 4) {
                        editMap.put("nickname", "Error: Minimum nickname length is 4 characters");
                        break;
                    }

                    userData.get().setNickname(entry.getValue());
                    userData.get().setLastUpdateDate(new Date());
                    userDataRepository.save(userData.get());
                    editMap.put("nickname", "Successfully changed");
                    break;
                }
                case "username": {
                    String regex = "^[a-zA-Z0-9_]+$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(entry.getValue());

                    if (!matcher.matches()) {
                        editMap.put("username", "Error: Founded invalid characters");
                        break;
                    }

                    if (entry.getValue().length() < 4) {
                        editMap.put("username", "Error: Minimum username length is 4 characters");
                        break;
                    }

                    Optional<UserData> existingUserData = userDataRepository.findByUsername(entry.getValue());
                    if (existingUserData.isEmpty()) {
                        userData.get().setUsername(entry.getValue());
                        userData.get().setLastUpdateDate(new Date());
                        userDataRepository.save(userData.get());
                        editMap.put("username", createJwtToken(userData.get()));
                    } else {
                        editMap.put("username", "Error: Founded existing user with this username");
                    }
                    break;
                }
                case "email": {
                    String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(entry.getValue());

                    if (!matcher.matches()) {
                        editMap.put("username", "Error: Founded invalid email");
                        break;
                    }

                    Optional<UserData> existingUserData = userDataRepository.findByEmail(entry.getValue());
                    if (existingUserData.isEmpty()) {
                        userData.get().setEmail(entry.getValue());
                        userData.get().setLastUpdateDate(new Date());
                        userDataRepository.save(userData.get());
                        editMap.put("email", "Successfully changed");
                    } else {
                        editMap.put("email", "Error: Founded existing user with this email");
                    }
                    break;
                }
                case "password": {
                    String regex = "^[a-zA-Z0-9_!@#$%^&*()-+=?]+$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(entry.getValue());

                    if (!matcher.matches()) {
                        editMap.put("password", "Error: Founded invalid characters");
                        break;
                    }

                    if (entry.getValue().length() < 8) {
                        editMap.put("password", "Error: Minimum password length is 8 characters");
                        break;
                    }

                    var bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    userData.get().setPassword(bCryptPasswordEncoder.encode(entry.getValue()));
                    userData.get().setLastUpdateDate(new Date());
                    userDataRepository.save(userData.get());
                    editMap.put("password", "Successfully changed");
                    break;
                }
                default: {
                    if (editMap.get("error") == null)
                        editMap.put("error", "No field " + entry.getValue());
                    else
                        editMap.put("error", editMap.get("error") + "; No field '" + entry.getValue() + "'");
                }
            }
        }
        return editMap;
    }
}
