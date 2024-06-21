package com.bessonov.musicappserver.user;

import com.bessonov.musicappserver.database.userData.UserData;
import com.bessonov.musicappserver.database.userData.UserDataRepository;
import com.nimbusds.jose.jwk.source.ImmutableSecret;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
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
                case "Имя пользователя": {
                    String regex = "^[a-zA-Z0-9_]+$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(entry.getValue());

                    if (!matcher.matches()) {
                        editMap.put("Имя пользователя", "Ошибка: Найдены неразрешенные символы");
                        break;
                    }

                    if (entry.getValue().length() < 4) {
                        editMap.put("Имя пользователя", "Ошибка: Минимальная длина 4 символа");
                        break;
                    }

                    userData.get().setNickname(entry.getValue());
                    userData.get().setLastUpdateDate(new Date());
                    userDataRepository.save(userData.get());
                    editMap.put("Имя пользователя", "Успешно изменено");
                    break;
                }
                case "Логин": {
                    String regex = "^[a-zA-Z0-9_]+$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(entry.getValue());

                    if (!matcher.matches()) {
                        editMap.put("Логин", "Ошибка: Найдены неразрешенные символы");
                        break;
                    }

                    if (entry.getValue().length() < 4) {
                        editMap.put("Логин", "Ошибка: Минимальная длина 4 символа");
                        break;
                    }

                    Optional<UserData> existingUserData = userDataRepository.findByUsername(entry.getValue());
                    if (existingUserData.isEmpty()) {
                        userData.get().setUsername(entry.getValue());
                        userData.get().setLastUpdateDate(new Date());
                        userDataRepository.save(userData.get());
                        editMap.put("Логин", createJwtToken(userData.get()));
                    } else {
                        editMap.put("Логин", "Ошибка: Существует пользователь с таким логином");
                    }
                    break;
                }
                case "Адрес электронной почты": {
                    String regex = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(entry.getValue());

                    if (!matcher.matches()) {
                        editMap.put("Адрес электронной почты", "Ошибка: Неверный формат");
                        break;
                    }

                    Optional<UserData> existingUserData = userDataRepository.findByEmail(entry.getValue());
                    if (existingUserData.isEmpty()) {
                        userData.get().setEmail(entry.getValue());
                        userData.get().setLastUpdateDate(new Date());
                        userDataRepository.save(userData.get());
                        editMap.put("Адрес электронной почты", "Успешно изменен");
                    } else {
                        editMap.put("Адрес электронной почты", "Ошибка: Существует пользователем с таким адресом");
                    }
                    break;
                }
                case "Пароль": {
                    String regex = "^[a-zA-Z0-9_!@#$%^&*()-+=?]+$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(entry.getValue());

                    if (!matcher.matches()) {
                        editMap.put("Пароль", "Ошибка: Найдены неразрешенные символы");
                        break;
                    }

                    if (entry.getValue().length() < 8) {
                        editMap.put("Пароль", "Ошибка: Минимальная длина 8 символов");
                        break;
                    }

                    var bCryptPasswordEncoder = new BCryptPasswordEncoder();
                    userData.get().setPassword(bCryptPasswordEncoder.encode(entry.getValue()));
                    userData.get().setLastUpdateDate(new Date());
                    userDataRepository.save(userData.get());
                    editMap.put("Пароль", "Успешно изменен");
                    break;
                }
                default: {
                    if (editMap.get("Ошибка") == null)
                        editMap.put("Ошибка", "Нет поля " + entry.getValue());
                    else
                        editMap.put("Ошибка", editMap.get("Ошибка") + "; Нет поля '" + entry.getValue() + "'");
                }
            }
        }
        return editMap;
    }
}
