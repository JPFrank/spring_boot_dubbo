package com.chengan.syspermissionapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value="LoginReq", description="登录请求")
public class LoginDTO implements Serializable {
  @ApiModelProperty(value="用户编码")
  private String code;
  @ApiModelProperty(value="用户密码")
  private String password;
}