package com.chengan.syspermissionservice.service.impl;

import com.chengan.syspermissionservice.service.MoudleService;
import com.chengan.syspermissionapi.domain.Moudle;
import com.chengan.syspermissionservice.mapper.MoudleMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.ArrayList;

@Service("moudleService")
@Slf4j
public class MoudleServiceImpl implements MoudleService{

    @Autowired
    private MoudleMapper moudleMapper;

    @Override
    public Moudle getById(Long id) {
        return moudleMapper.getById(id);
    }

    @Override
    public List<Moudle> getPidMoudle(List<Moudle> resultList) {
        List<Moudle> temP = new ArrayList<>();
        temP.addAll(resultList);

        for (Moudle result : resultList){
            Moudle tempMoudle = result;
            while (tempMoudle.getPid() != 0){
                Moudle pidMoudle = moudleMapper.getById(tempMoudle.getPid());
                if (!temP.contains(pidMoudle)){
                    temP.add(pidMoudle);
                }
                tempMoudle = pidMoudle;

            }
        }
        return temP;
    }
}