package com.chengan.syspermissionapi.query;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@Data
@ToString
public class Sorter implements Serializable {
  private String fieldName;
  private String sort;
}