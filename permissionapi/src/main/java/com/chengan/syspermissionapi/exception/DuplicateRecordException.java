package com.chengan.syspermissionapi.exception;

import com.chengan.syspermissionapi.common.ReplyBizStatus;

import lombok.Getter;

@Getter
public class DuplicateRecordException extends ApiException {
    private static final long serialVersionUID = 7784303438684941314L;

    public DuplicateRecordException() {
        this("duplicate record");
    }

    public DuplicateRecordException(String msg) {
        super(ReplyBizStatus.DUPLICATE_RECORD, msg);
    }
}