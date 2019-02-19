package com.chengan.syspermissionservice.mapper;

import java.util.List;

import com.chengan.syspermissionapi.domain.User;
import com.chengan.syspermissionapi.query.Query;
import com.chengan.syspermissionapi.query.filter.UserFilter;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface UserMapper {
    Long add(User user);
    Integer update(User user);
    User getById(Long userId);
    User getByCode(String code);
    List<User> list(Query<UserFilter> query);
    Integer incDispatchRoleVersion(Integer version);
}