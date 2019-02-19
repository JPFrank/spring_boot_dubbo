package com.chengan.syspermissionapi.dto;

import com.alibaba.fastjson.annotation.JSONField;
import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.vo.Gender;
import com.chengan.syspermissionapi.vo.UserStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@JsonInclude(Include.NON_EMPTY)
@ApiModel(value="UserDTO", description="用户返回结果")
public class UserDTO implements Serializable {
  private Long id;
  @ApiModelProperty(value="编码", required=true)
  @NonNull
  private String code;
  @ApiModelProperty(value="姓名", required=true)
  @NonNull
  private String name;
  @ApiModelProperty(value="密码", required=true)
  @NonNull
  private String password;
  @ApiModelProperty(value="性别 UNKNOW-未知 MALE-男 FEMALE-女")
  @NonNull
  private Gender gender;
  @ApiModelProperty(value="用户状态 UNKNOW-未知 AVAILABLE-可用 FREEZE-冻结 UNAVAILABLE-不可用")
  @NonNull
  private UserStatus status;
  @ApiModelProperty(value="部门ID")
  private Long departmentId;
  @ApiModelProperty(value="是否为部门主管")
  private Boolean isDepartmentMaster;
  @ApiModelProperty(value="职位")
  private String position;
  @ApiModelProperty(value="创建人")
  private Long createdBy;
  @ApiModelProperty(value="修改人")
  private Long updatedBy;
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  @ApiModelProperty(example="2017-01-01 00:00:00")
  private LocalDateTime createdAt;
  @JSONField(format = "yyyy-MM-dd HH:mm:ss")
  @JsonFormat(pattern="yyyy-MM-dd HH:mm:ss", timezone="GMT+8")
  @ApiModelProperty(example="2017-01-01 00:00:00")
  private LocalDateTime updatedAt;
  @ApiModelProperty(value="版本信息")
  private Integer version;
  @ApiModelProperty(value="分配角色版本")
  private Integer dispatchRoleVersion;

  public User toConvertEntity() {
    User user = new User();
    user.setId(id);
    user.setCode(code);
    user.setName(name);
    user.setGender(gender);
    user.setStatus(status);
    user.setPassword(password);
    user.setDepartmentId(departmentId);
    user.setIsDepartmentMaster(isDepartmentMaster);
    user.setPosition(position);
    user.setCreatedBy(createdBy);
    user.setCreatedAt(createdAt);
    user.setUpdatedBy(updatedBy);
    user.setUpdatedAt(updatedAt);
    user.setVersion(version);
    user.setDispatchRoleVersion(dispatchRoleVersion);
    return user;
  }
}