package com.system.crm.api;

import com.system.crm.dto.userSystem.*;
import com.system.crm.jwt.JwtTokenProvider;
import com.system.crm.service.SystemUserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("api/system-user")
public class SystemUserController {


    private SystemUserService systemUserService;
    private AuthenticationManager authenticationManager;
    private JwtTokenProvider jwtTokenProvider;


    public SystemUserController(SystemUserService systemUserService,
                                AuthenticationManager authenticationManager,
                                JwtTokenProvider jwtTokenProvider){
        this.systemUserService = systemUserService;
        this.authenticationManager = authenticationManager;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    //todo: forgot password can use api of update-password


    @PreAuthorize("hasAuthority('super_admin')")
    @PostMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> newUser(@Valid @RequestBody SystemUserDto systemUserDto){
        systemUserService.registerUser(systemUserDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @PutMapping(value = "/update-profile", produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateUser(@Valid @RequestBody SystemUserUpdateAdminDto systemUserUpdateAdminDto){
        return new ResponseEntity<>(systemUserService.updateUserWithoutPassword(systemUserUpdateAdminDto), HttpStatus.OK);
    }


    @PreAuthorize("hasAuthority('super_admin')")
    @PutMapping(value = "/update-activeness", produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateUserActive(@Valid @RequestBody SystemUserActiveDto systemUserActiveDto){
        systemUserService.updateUserActiveness(systemUserActiveDto);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @PutMapping(value = "/update-password", produces = {MediaType.APPLICATION_JSON_VALUE},consumes = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> updateUserPassword(@Valid @RequestBody SystemUserUpdatePasswordDto systemUserUpdatePasswordDto,
                                                @RequestHeader(name = "Authorization") String requestHeader){
        String phone = systemUserService.getPhoneFromCheckedToken(requestHeader);
        systemUserService.updatePassword(systemUserUpdatePasswordDto,phone);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @DeleteMapping(produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<?> deleteUser(@RequestParam("userId") String userId){
        systemUserService.deleteSystemUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasAnyAuthority('super_admin','admin')")
    @GetMapping(value = "/profile", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<SystemUserInfoProfileDto> userProfile(@RequestHeader(name = "Authorization") String requestHeader){
        String phone = systemUserService.getPhoneFromCheckedToken(requestHeader);
        return new ResponseEntity<>(systemUserService.getByPhoneForProfile(phone),HttpStatus.OK);
    }

    @PreAuthorize("hasAuthority('super_admin')")
    @GetMapping(value = "/all", produces = {MediaType.APPLICATION_JSON_VALUE})
    public ResponseEntity<List<SystemUserInfoDto>> allUsers(){
        return new ResponseEntity<>(systemUserService.getAllUsers(),HttpStatus.OK);
    }


}
