package com.chengan.syspermissionapi.vo;

import com.chengan.syspermissionapi.utils.EnumUtil;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum ExecPartnerType {
    UNKNOW(0),
    //基金管理人
    FUND_MANAGER(1),
    //内部机构
    INTERNAL_AGENCY(2),
    //外部机构
    EXTERNAL_AGENCY(3);
    private final int value;

    public int value() {
        return this.value;
    }

    @JsonCreator
    public static ExecPartnerType forValue(String value) {
        return EnumUtil.getEnumFromString(ExecPartnerType.class, value);
    }

    @JsonValue
    public String toJson() {
        return name().toUpperCase();
    }
}