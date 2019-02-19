package com.chengan.syspermissionapi.query.filter;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@Data
@ToString
public class UserFilter implements Serializable {
  private String name;
}