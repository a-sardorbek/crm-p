package com.system.crm.dto.authentication;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.system.crm.domain.entity.SystemUser;
import com.system.crm.domain.enumeration.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SystemUserPrincipal implements UserDetails {
    private SystemUser systemUser;
    public SystemUserPrincipal(SystemUser systemUser) {
        this.systemUser = systemUser;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = this.systemUser.getRole();
        List<SimpleGrantedAuthority> list = new ArrayList<>();
        if(role.equals(Role.SUPER_ADMIN.getRoleName())){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("super_admin");
            list.add(simpleGrantedAuthority);
        }else if(role.equals(Role.ADMIN.getRoleName())){
            SimpleGrantedAuthority simpleGrantedAuthority = new SimpleGrantedAuthority("admin");
            list.add(simpleGrantedAuthority);
        }
        return list;
    }

    @Override
    public String getPassword() {
        return this.systemUser.getPassword();
    }

    @Override
    public String getUsername() {
        return this.systemUser.getPhoneNumber();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.systemUser.isActive();
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @JsonIgnore
    public String getFirstNameUser(){
        return this.systemUser.getFirstName();
    }

    @JsonIgnore
    public String getLastNameUser(){
        return this.systemUser.getLastName();
    }

    @JsonIgnore
    public Long getIdUser(){
        return this.systemUser.getId();
    }

    @JsonIgnore
    public String getRole() {
        return this.systemUser.getRole();
    }
}
