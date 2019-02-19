package com.chengan.syspermissionapi.domain;

import lombok.Data;

import java.io.Serializable;


@Data
public class MetaData implements Serializable {
    private static final long serialVersionUID = -3258839839160856613L;
    private Long id;
    private Long sourceCode;
    private String sourceType;
    private String fieldName;
    private String fieldType;
}