package com.chengan.syspermissionapi.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.chengan.syspermissionapi.domain.Permission;
import com.chengan.syspermissionapi.vo.PermissionAction;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@JsonInclude(Include.NON_EMPTY)
@ApiModel(value="PermissionDTO", description="权限")
public class PermissionDTO implements Serializable {
  private Long id;
  @ApiModelProperty(value="编码")
  private String code;
  @ApiModelProperty(value="类型ID")
  private Long objectId;
  @ApiModelProperty(value="模块ID")
  private Long mid;
  @ApiModelProperty(value="类型")
  private String typ;
  @ApiModelProperty(value="资源URL")
  private String resource;
  @ApiModelProperty(value="行为")
  private PermissionAction action;
  @ApiModelProperty(value="名称")
  private String name;
  @ApiModelProperty(value="描述")
  private String descritpion;
  @ApiModelProperty(value="创建者")
  private Long createdBy;
  @ApiModelProperty(value="是否管理员唯一")
  private Boolean isOnlyMaster;
  @ApiModelProperty(value="修改者")
  private Long updatedBy;
  @ApiModelProperty(value="删除者")
  private Long removedBy;
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  @ApiModelProperty(example="2017-01-01 00:00:00")
  private LocalDateTime createdAt;
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  @ApiModelProperty(example="2017-01-01 00:00:00")
  private LocalDateTime updatedAt;
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  @ApiModelProperty(example="2017-01-01 00:00:00")
  private LocalDateTime removedAt;
  @ApiModelProperty(value="版本信息")
  private Integer version;

  public Permission toConvertEntity() {
    Permission permission = new Permission();
    permission.setId(id);
    permission.setCode(code);
    permission.setMid(mid);
    permission.setResource(resource);
    permission.setAction(action);
    permission.setName(name);
    permission.setDescritpion(descritpion);
    permission.setIsOnlyMaster(isOnlyMaster);
    permission.setCreatedBy(createdBy);
    permission.setCreatedAt(createdAt);
    permission.setUpdatedBy(updatedBy);
    permission.setUpdatedAt(updatedAt);
    permission.setRemovedBy(removedBy);
    permission.setRemovedAt(removedAt);
    permission.setVersion(version);
    return permission;
  }
}