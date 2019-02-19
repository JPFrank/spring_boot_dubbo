package com.chengan.syspermissionapi.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.io.Serializable;

@AllArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value="TokenResp", description="token返回对象")
public class TokenRespDTO implements Serializable {
  @ApiModelProperty(value="验权token")
  private String accessToken;
  @ApiModelProperty(value="刷新token")
  private String refreshToken;
  @ApiModelProperty(value="过期时间")
  private Long expireIn;
}