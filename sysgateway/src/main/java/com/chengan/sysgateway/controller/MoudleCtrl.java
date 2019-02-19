package com.chengan.sysgateway.controller;

import com.chengan.syspermissionapi.DubboMoudleService;
import com.chengan.syspermissionapi.common.Reply;
import com.chengan.syspermissionapi.common.ReplyBizStatus;
import com.chengan.syspermissionapi.common.ReqAttrKey;

import com.chengan.syspermissionapi.dto.MoudleDTO;
import com.chengan.syspermissionapi.dto.PermissionDTO;
import com.chengan.syspermissionapi.dto.UserAuthDTO;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RestController;
import io.swagger.annotations.ApiOperation;
import com.alibaba.dubbo.config.annotation.Reference;


import java.util.List;
import java.util.ArrayList;

@RestController
@RequestMapping("/api/v1/moudle")
public class MoudleCtrl {
    // 根据用户信息获取所有的模块信息
    @Reference(version = "${demo.service.version}",
            application = "${dubbo.application.id}")
    private DubboMoudleService moudleService;

    @ApiOperation(value="模块列表", notes="模块列表")
    @RequestMapping(value="/list", method=RequestMethod.POST, produces=MediaType.APPLICATION_JSON_VALUE)
    public Reply<List<MoudleDTO>> moudleList(
            @RequestAttribute(ReqAttrKey.REQ_USER_AUTH_KEY) UserAuthDTO userAuth) throws Exception {

        List<PermissionDTO> permissionDTOList = userAuth.getPermissionList();
        // permissionDTOList.stream().map(tp -> tp.toConvertTemp()).cocat(entityPermissionDTOList.stream().map(tp -> tp.toConvertTemp()));
        List<PermissionDTO> tmpPermissionAllList = new ArrayList<>();
        if (permissionDTOList != null){
            tmpPermissionAllList.addAll(permissionDTOList);
        }
        List<MoudleDTO> result = new ArrayList<>();

        for (PermissionDTO tmpPermissionAll : tmpPermissionAllList){
            MoudleDTO moudledto = moudleService.getById(tmpPermissionAll.getMid());
//            Moudle moudle = moudledto.toConvertEntity();
            if (!result.contains(moudledto)){
                result.add(moudledto);
            }
        }
        List<MoudleDTO> finalResult = moudleService.getPidMoudle(result);

//        List<MoudleDTO> moudleResultDTO = finalResult.stream().map(tp -> tp.toConvertDTO()).collect(Collectors.toList());
        return new Reply<>(ReplyBizStatus.OK, "success", finalResult);
    }
}