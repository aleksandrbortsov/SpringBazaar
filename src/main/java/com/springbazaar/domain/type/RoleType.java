package com.springbazaar.domain.type;

import com.springbazaar.domain.Role;

public enum RoleType {
    ADMIN,
    USER,
    SELLER,
    BUYER;

    public static RoleType findByStringType(String roleType) {
        for (RoleType roleItem : values()) {
            if (roleItem.name().equals(roleType)) {
                return roleItem;
            }
        }
        return null;
    }

    public static String findByRole(Role role) {
        for (RoleType roleItem : values()) {
            if (roleItem.equals(role.getRoleType())) {
                return roleItem.name();
            }
        }
        return null;
    }
}
