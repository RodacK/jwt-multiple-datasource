package com.rob.security.controller;

import com.rob.security.jdbc.JdbcManagerFactory;
import com.rob.security.model.UserLoginRequest;
import com.rob.security.model.UserLoginResponse;
import com.rob.security.service.JwtService;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@org.springframework.web.bind.annotation.RestController
@RequiredArgsConstructor
public class RestController {

    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final JdbcManagerFactory jdbcManagerFactory;

    @PostMapping("/login")
    public UserLoginResponse createToken(@RequestBody UserLoginRequest authenticationRequest) throws Exception {
        try {

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(authenticationRequest.getNick(), authenticationRequest.getPassword());
            authenticationManager.authenticate(authentication);
        } catch (Exception e) {
            throw new Exception("Invalid username or password", e);
        }
        UserDetails userDetails = jdbcManagerFactory.getUserManager(JdbcManagerFactory.ACCOUNTS.valueOf(authenticationRequest.getAccount())).loadUserByUsername(authenticationRequest.getNick());
        String token = jwtService.createToken(userDetails, JdbcManagerFactory.ACCOUNTS.valueOf(authenticationRequest.getAccount()));
        return new UserLoginResponse(token);
    }

    @GetMapping("/test")
    String uid(HttpSession session) {
        return session.getId();
    }

    @GetMapping("/products/{name}")
    public ResponseEntity getProduct(HttpSession session, @PathVariable String name) {
        try {
            return new ResponseEntity<>(jdbcManagerFactory.getProductConfig(JdbcManagerFactory.ACCOUNTS.valueOf((String) session.getAttribute("account"))).loadProductByName(name), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(e,HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }


}
