package com.chengan.syspermissionservice.mapper;

import com.chengan.syspermissionapi.domain.Moudle;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

@Mapper
@Repository
public interface MoudleMapper{
    Moudle getById(Long id);
}