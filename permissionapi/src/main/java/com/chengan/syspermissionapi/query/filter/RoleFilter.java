package com.chengan.syspermissionapi.query.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@Data
@ToString
public class RoleFilter implements Serializable {
  private String name;
  private String namePartten;
  private Long userId;
  private Long permissionId;
}