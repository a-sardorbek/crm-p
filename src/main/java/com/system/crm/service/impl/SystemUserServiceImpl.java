package com.system.crm.service.impl;

import com.system.crm.constant.SecurityConstant;
import com.system.crm.domain.entity.SystemUser;
import com.system.crm.dto.authentication.ResponseLogin;
import com.system.crm.dto.authentication.SystemUserPrincipal;
import com.system.crm.dto.userSystem.*;
import com.system.crm.exception.custom.CustomNotFoundException;
import com.system.crm.exception.custom.JwtAuthenticationException;
import com.system.crm.exception.custom.NotCorrectException;
import com.system.crm.exception.custom.SuccessResponse;
import com.system.crm.jwt.JwtTokenProvider;
import com.system.crm.repository.SystemUserRepository;
import com.system.crm.service.SystemUserService;
import com.system.crm.utils.ServiceUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class SystemUserServiceImpl implements SystemUserService, UserDetailsService {

    //todo: forgot password needs to be optimized
    //todo:
    private SystemUserRepository systemUserRepository;
    private BCryptPasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;


    public SystemUserServiceImpl(SystemUserRepository systemUserRepository,
                                 BCryptPasswordEncoder passwordEncoder,
                                 JwtTokenProvider jwtTokenProvider) {
        this.systemUserRepository = systemUserRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }


    @Override
    public UserDetails loadUserByUsername(String phone) throws UsernameNotFoundException {
        SystemUser systemUser = findByPhone(phone);
        if(systemUser == null) {
            throw new UsernameNotFoundException("User not found by phone number: " + phone);
        }else{
               // validateLoginAttempt(systemUser); // this is for login attempt count
                SystemUserPrincipal principal = new SystemUserPrincipal(systemUser);
                return principal;
            }
    }

    @PostConstruct
    public void insertFirstSystemUser(){
        boolean checkUserExist = systemUserRepository.checkUserCountIsZero();
        if(checkUserExist){
            SystemUser systemUser = new SystemUser();
            systemUser.setRole("super_admin");
            systemUser.setFirstName("Foydalanuvchi-Ism");
            systemUser.setLastName("Foydalanuvchi-Familiya");
            systemUser.setCustomSystemUserId(generateSystemUserId());
            systemUser.setPassword(passwordEncoder.encode("menin-parolim"));
            systemUser.setActive(true);
            systemUser.setPhoneNumber("901234567");
            systemUser.setLocalDate(LocalDate.now());
            systemUser.setDeleted(false);
            systemUserRepository.save(systemUser);
        }
    }


    @Override
    public SystemUser findByPhone(String phone) {
        SystemUser systemUser = systemUserRepository.findByPhoneNumber(phone);
        if(systemUser==null){
            throw new NotCorrectException("User not found");
        }
        return systemUser;
    }

    @Override
    public ResponseLogin getLoginResponse(SystemUserPrincipal systemUserPrincipal, String token) {
        ResponseLogin responseLogin = new ResponseLogin();
        responseLogin.setId(systemUserPrincipal.getIdUser());
        responseLogin.setFirstName(systemUserPrincipal.getFirstNameUser());
        responseLogin.setLastName(systemUserPrincipal.getLastNameUser());
        responseLogin.setToken(token);
        responseLogin.setPhoneNumber(systemUserPrincipal.getUsername());
        responseLogin.setRole(systemUserPrincipal.getRole());
        return responseLogin;
    }

    @Override
    public SystemUserInfoProfileDto getByPhoneForProfile(String phone) {
        SystemUser systemUser = findByPhone(phone);
        return new SystemUserInfoProfileDto(
                systemUser.getId(),
                systemUser.getFirstName(),
                systemUser.getLastName(),
                systemUser.getPhoneNumber()
        );
    }

    @Override
    public void updateUserActiveness(SystemUserActiveDto systemUserActiveDto) {
        int checkExist = systemUserRepository.checkUserExistsById(systemUserActiveDto.getId());
        if(checkExist>0){
            systemUserRepository.updateActivenessOfUser(Boolean.parseBoolean(
                    systemUserActiveDto.getIsActive()),
                    systemUserActiveDto.getId());
            throw new SuccessResponse("Super admin updated the admin activeness!");
        }
        throw new CustomNotFoundException("User not found by id: "+systemUserActiveDto.getId());
    }

    @Override
    public void registerUser(SystemUserDto systemUserDto) {
        int checkExists = systemUserRepository.checkUserExists(systemUserDto.getPhoneNumber());
        if(checkExists>0) {
            throw new NotCorrectException("User exists with phone number: "+systemUserDto.getPhoneNumber());
        }
            SystemUser systemUser = new SystemUser();
            if (Boolean.parseBoolean(systemUserDto.getIsSuperAdmin())) {
                systemUser.setRole("super_admin");
            } else {
                systemUser.setRole("admin");
            }
            systemUser.setFirstName(systemUserDto.getFirstName());
            systemUser.setLastName(systemUserDto.getLastName());
            systemUser.setCustomSystemUserId(generateSystemUserId());
            systemUser.setPassword(passwordEncoder.encode(systemUserDto.getPassword()));
            systemUser.setActive(true);
            systemUser.setPhoneNumber(systemUserDto.getPhoneNumber());
            systemUser.setLocalDate(LocalDate.now());
            systemUserRepository.save(systemUser);
            throw new SuccessResponse("System user successfully created");

    }

    @Override
    public SystemUserUpdateAdminDto updateUserWithoutPassword(SystemUserUpdateAdminDto systemUserUpdateAdminDto) {
        Optional<SystemUser> systemUser = systemUserRepository.findById(systemUserUpdateAdminDto.getId());
        if(!ServiceUtils.checkIsNumeric(systemUserUpdateAdminDto.getPhoneNumber())) {
            throw new NotCorrectException("Phone number should consist of digits!");
        }
            if (systemUser.isPresent()) {
                  int updated = systemUserRepository.updateSystemUser(systemUserUpdateAdminDto.getId(),
                        systemUserUpdateAdminDto.getFirstName(),
                        systemUserUpdateAdminDto.getLastName(),
                        systemUserUpdateAdminDto.getPhoneNumber());
                  if (updated > 0) {
                    return systemUserUpdateAdminDto;
                  }

        }
        throw new CustomNotFoundException("User not found by id: "+ systemUserUpdateAdminDto.getId());
    }

    @Override
    public void updatePassword(SystemUserUpdatePasswordDto systemUserUpdatePasswordDto,String phone) {
        SystemUser systemUser = findByPhone(phone);
        if(systemUser!=null) {
            if (systemUserRepository.updatePassword(
                    phone,
                    passwordEncoder.encode(systemUserUpdatePasswordDto.getPassword())) > 0) {
                throw new SuccessResponse("Password updated successfully");
            }
        }
        throw new CustomNotFoundException("User not found by id: "+phone);

    }

    @Override
    public void deleteSystemUser(String id) {
        if (ServiceUtils.checkIsNumeric(id)) {
            Optional<SystemUser> systemUser = systemUserRepository.findById(Long.parseLong(id));
            if (systemUser.isPresent()) {
                systemUserRepository.deleteByIdMakeFalse(Long.parseLong(id));
                throw new SuccessResponse("Successfully deleted!");
            }
        }
            throw new CustomNotFoundException("User not found by id: " + id);
    }


    @Override
    public List<SystemUserInfoDto> getAllUsers() {
        return systemUserRepository.findUsers();
    }

    @Override
    public String getPhoneFromCheckedToken(String token){
        String tokenWithoutBearer = token.substring(SecurityConstant.TOKEN_PREFIX.length());
        String userNameSubject = jwtTokenProvider.getSubjectFromToken(tokenWithoutBearer);
        if(jwtTokenProvider.isValidToken(userNameSubject,tokenWithoutBearer)){
            return userNameSubject;
        }
        throw new JwtAuthenticationException("You need to log in to access this page");
    }


    private String generateSystemUserId() {
        return RandomStringUtils.randomNumeric(7);
    }


}
