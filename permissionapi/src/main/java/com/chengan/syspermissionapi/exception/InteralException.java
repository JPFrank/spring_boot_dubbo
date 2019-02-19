package com.chengan.syspermissionapi.exception;

import com.chengan.syspermissionapi.common.ReplyBizStatus;

import lombok.Getter;

@Getter
public class InteralException extends ApiException {
    public InteralException() {
        this("Interal Service error");
    }

    public InteralException(String msg) {
        super(ReplyBizStatus.INTERNAL_SERVER_ERROR, msg);
    }
}