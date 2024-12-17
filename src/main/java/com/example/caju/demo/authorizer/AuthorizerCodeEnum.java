package com.example.caju.demo.authorizer;

public enum AuthorizerCodeEnum {
    APPROVED("00"),
    DISAPPROVED("51"),
    FATAL("07");

    private final String code;

    AuthorizerCodeEnum(String code) {
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
