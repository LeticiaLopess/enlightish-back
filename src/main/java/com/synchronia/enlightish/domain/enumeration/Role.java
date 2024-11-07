package com.synchronia.enlightish.domain.enumeration;

public enum Role {
    ADMIN(1, "ADMIN", "Administrator"),
    USER(2, "USER", "User"),
    LEAD(3, "LEAD", "Lead");

    private final int code;
    private final String name;
    private final String description;

    Role(int code, String name, String description) {
        this.code = code;
        this.name = name;
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public int getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static Role fromCode(int code) {
        for (Role role : Role.values()) {
            if (role.getCode() == code) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}