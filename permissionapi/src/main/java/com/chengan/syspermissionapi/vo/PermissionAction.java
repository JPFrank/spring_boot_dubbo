package com.chengan.syspermissionapi.vo;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.chengan.syspermissionapi.utils.EnumUtil;

import lombok.AllArgsConstructor;



@AllArgsConstructor
public enum PermissionAction {
  UNKNOW(0),
  READ(1),
  WRITE(2),
  REMOVE(3);

  private final int value;

  public int value() {
    return this.value;
  }

  @JsonCreator
  public static Gender forValue(String value) {
    return EnumUtil.getEnumFromString(Gender.class, value);
  }

  @JsonValue
  public String toString() {
    return name().toUpperCase();
  }
}