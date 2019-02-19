package com.chengan.syspermissionapi.domain;

 import com.chengan.syspermissionapi.dto.UserDTO;
 import com.chengan.syspermissionapi.vo.Gender;
 import com.chengan.syspermissionapi.vo.UserStatus;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.ToString;

 import java.io.Serializable;
 import java.time.LocalDateTime;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode(of="id")
public class User {
  private Long id;
  @NonNull String code;
  @NonNull private String password;
  @NonNull private String name;
  @NonNull private Gender gender;
  @NonNull private UserStatus status;
  private Long departmentId;
  private Boolean isDepartmentMaster;
  private String position;
  private Long createdBy;
  private Long updatedBy;
  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;
  private Integer version;
  private Integer dispatchRoleVersion;

  public UserDTO toConvertDTO() {
      UserDTO userdto = new UserDTO();
      userdto.setId(id);
      userdto.setCode(code);
      userdto.setName(name);
      userdto.setPassword(password);
      userdto.setGender(gender);
      userdto.setStatus(status);
      userdto.setDepartmentId(departmentId);
      userdto.setIsDepartmentMaster(isDepartmentMaster);
      userdto.setPosition(position);
      userdto.setCreatedBy(createdBy);
      userdto.setCreatedAt(createdAt);
      userdto.setUpdatedBy(updatedBy);
      userdto.setUpdatedAt(updatedAt);
      userdto.setVersion(version);
      userdto.setDispatchRoleVersion(dispatchRoleVersion);
      return userdto;
    }

   public void freeze() throws IllegalArgumentException {
     if (status != UserStatus.AVAILABLE) {
       throw new IllegalArgumentException("can only freeze [available] status user");
     }
     status = UserStatus.FREEZE;
   }

   public void unfreeze() throws IllegalArgumentException {
     if (status != UserStatus.FREEZE) {
       throw new IllegalArgumentException("can only unfreeze [freeze] status user");
     }
     status = UserStatus.AVAILABLE;
   }

   public void disable() throws IllegalArgumentException {
     if (status != UserStatus.AVAILABLE && status != UserStatus.FREEZE) {
       throw new IllegalArgumentException("can disable [available, freeze] status user");
     }
     status = UserStatus.UNAVAILABLE;
   }
}