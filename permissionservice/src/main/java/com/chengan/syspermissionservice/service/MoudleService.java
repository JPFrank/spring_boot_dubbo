package com.chengan.syspermissionservice.service;

import java.util.List;

import com.chengan.syspermissionapi.domain.Moudle;

public interface MoudleService{
    Moudle getById(Long id);
    List<Moudle> getPidMoudle(List<Moudle> resultList);
}