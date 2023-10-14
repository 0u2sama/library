package com.hcl.library.api;


import com.hcl.library.entity.Token;
import com.hcl.library.entity.User;
import com.hcl.library.model.UserAuth;
import com.hcl.library.repository.TokenRepository;
import com.hcl.library.repository.UserRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.web.ReactivePageableHandlerMethodArgumentResolver;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
public class LoginController {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenRepository tokenRepository;

    @PostMapping("/login/")
    public ResponseEntity<Object> login(@RequestBody UserAuth userAuth , HttpSession session) {


        System.out.println(session.getAttribute("session-id").toString());
        User user = this.userRepository.findByUsername(userAuth.getUsername());

        if(user==null || !user.getPassword().equals(userAuth.getPassword())) {

            return new ResponseEntity<>("Not authorized" , HttpStatusCode.valueOf(401));
        }

        String randomString = UUID.randomUUID().toString();

        Token token = this.tokenRepository.findByUsername(userAuth.getUsername());

        if(token==null) {
            this.tokenRepository.save(new Token(null , userAuth.getUsername() , randomString));
        }
        else {
            token.setToken(randomString);
            this.tokenRepository.save(token);
        }


        session.setAttribute("session-id" , randomString);

        return new ResponseEntity<>("Login successful" , HttpStatusCode.valueOf(200));
    }


    @GetMapping("/logout/")
    public ResponseEntity<Object> logout(HttpSession session) {

        this.tokenRepository.deleteByToken(session.getAttribute("session-id").toString());

        session.removeAttribute("session-id");

        return new ResponseEntity<>("Logout successful" , HttpStatusCode.valueOf(200));
    }
}
