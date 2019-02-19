package com.chengan.syspermissionapi.query.filter;

import com.chengan.syspermissionapi.vo.PermissionAction;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@Data
@ToString
public class PermissionFilter implements Serializable {
  private String code;
  private String codePartten;
  private String name;
  private String namePartten;
  private String resource;
  private String resourcePrefix;
  private Long roleId;
  private PermissionAction action;
}