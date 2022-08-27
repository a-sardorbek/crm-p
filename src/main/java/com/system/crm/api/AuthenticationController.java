package com.system.crm.api;

import com.system.crm.constant.SecurityConstant;
import com.system.crm.domain.entity.SystemUser;
import com.system.crm.dto.authentication.LoginSystemUser;
import com.system.crm.dto.authentication.ResponseLogin;
import com.system.crm.dto.authentication.SystemUserPrincipal;
import com.system.crm.exception.custom.NotCorrectException;
import com.system.crm.exception.custom.SuccessResponse;
import com.system.crm.jwt.JwtTokenProvider;
import com.system.crm.service.SystemUserService;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/user")
public class AuthenticationController {

    private SystemUserService systemUserService;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;


    public AuthenticationController(SystemUserService systemUserService,
                                    AuthenticationManager authenticationManager,
                                    JwtTokenProvider jwtTokenProvider) {
        this.systemUserService = systemUserService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }



    @PostMapping("/login")
    public ResponseEntity<ResponseLogin> login(@RequestBody LoginSystemUser loginUser){
        authenticateUser(loginUser.getPhoneNumber(),loginUser.getPassword());
        SystemUser systemUser =systemUserService.findByPhone(loginUser.getPhoneNumber());
        if(systemUser.isDeleted()){
            throw new NotCorrectException("You are not allowed to login, contact with ");
        }
        SystemUserPrincipal systemUserPrincipal = new SystemUserPrincipal(systemUser);
        String token =  jwtTokenProvider.generateJwtToken(systemUserPrincipal);
        ResponseLogin responseLogin = systemUserService.getLoginResponse(systemUserPrincipal,token);
        return new ResponseEntity<>(responseLogin, HttpStatus.OK);
    }
    private void authenticateUser(String username, String password) {
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
    }

}
