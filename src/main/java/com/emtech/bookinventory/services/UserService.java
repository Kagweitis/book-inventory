package com.emtech.bookinventory.services;

import com.emtech.bookinventory.Role;
import com.emtech.bookinventory.config.JwtService;
import com.emtech.bookinventory.entities.UserEntity;
import com.emtech.bookinventory.repositories.UserRepository;
import com.smattme.requestvalidator.RequestValidator;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;


    public HashMap openAccount(HashMap params) {
        HashMap<String, Object> response=new HashMap<>();
        try {
            Optional<UserEntity> existingUser = userRepository.findByEmail(params.get("email").toString());
            if (existingUser.isPresent()){
                response.put("message", "This user already exist");
                response.put("status", false);
            }
            else {
                String encodedPassword  = passwordEncoder.encode(params.get("password").toString());
                UserEntity newUser = UserEntity.builder()
                        .email(params.get("email").toString().toLowerCase())
                        .fullName(params.get("fullName").toString())
                        .password(encodedPassword)
                        .role(Role.USER)
                        .build();
                userRepository.save(newUser);
                var jwtToken = jwtService.generateToken(newUser);
                response.put("message", "Account has created successfully for "+newUser.getFullName());
                response.put("status", true);
                response.put("token", jwtToken);
            }

        }
        catch (Exception e){
            e.printStackTrace();
            log.error("error::::"+e.getMessage());
            response.put("message", "Ooops! Something went wrong");
            response.put("status", false);
        }
        return response;
    }

    public HashMap loginUser(HashMap params) {
        HashMap<String, Object> response = new HashMap();
        try {
            Optional<UserEntity> user = userRepository.findByEmail(params.get("email").toString().toLowerCase());
            if(user.isEmpty()){
                response.put("message", "User does not exist");
                response.put("status", false);
                log.info("...User not found....");
            }
            else if(!passwordEncoder.matches(params.get("password").toString(), user.get().getPassword())){
                response.put("message", "invalid credentials");
                response.put("status", "false");
                log.info("....invalid credentials...");
            }
            else {
                var jwtToken = jwtService.generateToken((UserEntity) user.get());
                response.put("message", "login successful");
                response.put("status", true);
                response.put("token", jwtToken);
                log.info("...Login successful....");
            }

        }catch (Exception e){
            log.info(e.getMessage());
            e.printStackTrace();
            response.put("message", "Oops! An error occurred!");
            response.put("status", false);
            return response;
        }
        return response;
    }


    public HashMap validateUser(HashMap params) {

        HashMap<String, Object> response = new HashMap<>();

        HashMap<String, String> rules = new HashMap<>();

        try {
            rules.put("fullName", "required");
            rules.put("email", "required");
            rules.put("password", "required");
            List<String> errors = RequestValidator.validate(params, rules);
            if (!errors.isEmpty()) {
                log.info("An error occurred...");
                response.put("message", "All fields are required");
                response.put("errors", errors);
                response.put("status", false);
                return response;
            } else {
                log.info("Parameters validated");
                return openAccount(params);
            }

        } catch (Exception e) {
            log.info(e.getMessage());
            e.printStackTrace();
            response.put("message", "Oops! An error occurred!");
            response.put("status", false);
            return response;
        }
    }



}
