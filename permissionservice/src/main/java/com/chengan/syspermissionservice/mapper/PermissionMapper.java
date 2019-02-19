package com.chengan.syspermissionservice.mapper;

import com.chengan.syspermissionapi.domain.Permission;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.PermissionFilter;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Mapper
@Repository
public interface PermissionMapper {
    Long add(Permission permission);
    Integer update(Permission permission);
    Integer delete(
            @Param("id") Long id,
            @Param("removedBy") Long removedBy,
            @Param("removedAt") LocalDateTime removedAt,
            @Param("version") Integer version);
    Permission getById(Long id);
    Permission getByCode(String code);
    List<Permission> list(Query<PermissionFilter> query);
}
