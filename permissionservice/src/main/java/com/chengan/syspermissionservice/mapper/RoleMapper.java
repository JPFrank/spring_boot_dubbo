package com.chengan.syspermissionservice.mapper;

import com.chengan.syspermissionapi.domain.Role;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.RoleFilter;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface RoleMapper {
    Long add(Role role);
    Integer update(Role role);
    Integer delete(
            @Param("id") Long id,
            @Param("removedBy") Long removedBy,
            @Param("removedAt") LocalDateTime removedAt,
            @Param("version") Integer version);
    Role getById(Long id);
    List<Role> list(Query<RoleFilter> query);
    Integer incDispatchPermissionVersion(Integer version);
}
