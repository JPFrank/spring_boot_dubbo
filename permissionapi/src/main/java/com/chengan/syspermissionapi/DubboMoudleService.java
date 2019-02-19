package com.chengan.syspermissionapi;

import com.chengan.syspermissionapi.dto.MoudleDTO;

import java.util.List;

public interface DubboMoudleService{
    MoudleDTO getById(Long id);
    List<MoudleDTO> getPidMoudle(List<MoudleDTO> resultList);
}