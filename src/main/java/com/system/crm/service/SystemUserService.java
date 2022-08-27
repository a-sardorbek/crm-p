package com.system.crm.service;

import com.system.crm.domain.entity.SystemUser;
import com.system.crm.dto.authentication.ResponseLogin;
import com.system.crm.dto.authentication.SystemUserPrincipal;
import com.system.crm.dto.userSystem.*;
import com.system.crm.exception.custom.SuccessResponse;

import java.util.List;

public interface SystemUserService {

    void registerUser(SystemUserDto systemUserDto);
    SystemUserUpdateAdminDto updateUserWithoutPassword(SystemUserUpdateAdminDto systemUserUpdateAdminDto);

    void updatePassword(SystemUserUpdatePasswordDto systemUserUpdatePasswordDto,String phone);

    public String getPhoneFromCheckedToken(String token);

    void deleteSystemUser(String id);

//    SystemUserInfoDto getById(String id);

    List<SystemUserInfoDto> getAllUsers();

    SystemUser findByPhone(String phone);

    ResponseLogin getLoginResponse(SystemUserPrincipal systemUserPrincipal,String token);

    SystemUserInfoProfileDto getByPhoneForProfile(String phone);

    void updateUserActiveness(SystemUserActiveDto systemUserActiveDto);


}
