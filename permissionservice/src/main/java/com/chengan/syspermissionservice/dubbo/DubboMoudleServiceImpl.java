package com.chengan.syspermissionservice.dubbo;

import com.chengan.syspermissionapi.DubboMoudleService;
import com.chengan.syspermissionapi.domain.Moudle;
import com.chengan.syspermissionapi.dto.MoudleDTO;
import com.chengan.syspermissionservice.service.MoudleService;

import lombok.extern.slf4j.Slf4j;
import com.alibaba.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.stream.Collectors;

@Service(
        version = "${demo.service.version}",
        application = "${dubbo.application.id}",
        protocol = "${dubbo.protocol.id}",
        registry = "${dubbo.registry.id}"
)
@Slf4j
public class DubboMoudleServiceImpl implements DubboMoudleService{

    @Autowired
    private MoudleService moudleService;

    @Override
    public MoudleDTO getById(Long id){
        Moudle moudle = moudleService.getById(id);
        return moudle.toConvertDTO();
    }

    @Override
    public List<MoudleDTO> getPidMoudle(List<MoudleDTO> resultList){
        List<Moudle> moudleList = resultList.stream().map(ep -> ep.toConvertEntity()).collect(Collectors.toList());
        List<Moudle> tmp = moudleService.getPidMoudle(moudleList);
        return tmp.stream().map(ep -> ep.toConvertDTO()).collect(Collectors.toList());
    }
}