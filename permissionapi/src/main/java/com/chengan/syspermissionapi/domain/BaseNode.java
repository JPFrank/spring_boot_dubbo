package com.chengan.syspermissionapi.domain;

import lombok.Data;

@Data
public class BaseNode{
  protected Long id;
  protected Long pid;
  protected Boolean isLeaf;
  protected String name;
}
