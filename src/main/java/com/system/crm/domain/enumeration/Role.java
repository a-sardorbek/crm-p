package com.system.crm.domain.enumeration;

public enum Role {

    SUPER_ADMIN("super_admin"),
    ADMIN("admin");

    private String roleName;

    Role(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleName() {
        return roleName;
    }
}
