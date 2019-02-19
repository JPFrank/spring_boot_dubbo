package com.chengan.syspermissionapi.domain;

import com.chengan.syspermissionapi.dto.PermissionDTO;
import com.chengan.syspermissionapi.vo.PermissionAction;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of="id")
public class Permission {
  private Long id;
  private Long mid;
  private String code;
  private String resource;
  private String name;
  private String descritpion;
  private PermissionAction action;
  private Boolean isOnlyMaster;
  private Long createdBy;
  private Long updatedBy;
  private Long removedBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private LocalDateTime removedAt;
  private Integer version;

  public PermissionDTO toConvertDTO() {
    PermissionDTO permissionDTO = new PermissionDTO();
    permissionDTO.setId(id);
    permissionDTO.setCode(code);
    permissionDTO.setMid(mid);
    permissionDTO.setResource(resource);
    permissionDTO.setAction(action);
    permissionDTO.setName(name);
    permissionDTO.setDescritpion(descritpion);
    permissionDTO.setIsOnlyMaster(isOnlyMaster);
    permissionDTO.setCreatedBy(createdBy);
    permissionDTO.setCreatedAt(createdAt);
    permissionDTO.setUpdatedBy(updatedBy);
    permissionDTO.setUpdatedAt(updatedAt);
    permissionDTO.setRemovedBy(removedBy);
    permissionDTO.setRemovedAt(removedAt);
    permissionDTO.setVersion(version);
    return permissionDTO;
  }
}
