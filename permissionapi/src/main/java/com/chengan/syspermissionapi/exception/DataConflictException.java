package com.chengan.syspermissionapi.exception;

import com.chengan.syspermissionapi.common.ReplyBizStatus;

import lombok.Getter;

@Getter
public class DataConflictException extends ApiException {
    private static final long serialVersionUID = 7784303438684941314L;

    public DataConflictException() {
        this("data conflict");
    }

    public DataConflictException(String msg) {
        super(ReplyBizStatus.DATA_CONFLICT, msg);
    }
}