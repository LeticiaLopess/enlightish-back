package com.synchronia.enlightish.domain.enumeration;

public enum Status {
    ACTIVE("Active", 1),
    INACTIVE("Inactive", 0);

    private final String name;
    private final int code;

    Status(String name, int code) {
        this.name = name;
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public int getCode() {
        return code;
    }

    public static Status fromCode(int code) {
        for (Status status : Status.values()) {
            if (status.getCode() == code) {
                return status;
            }
        }
        throw new IllegalArgumentException("Invalid code: " + code);
    }
}