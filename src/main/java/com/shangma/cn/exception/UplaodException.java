package com.shangma.cn.exception;

import com.shangma.cn.common.http.AxiosStatus;
public class UplaodException extends RuntimeException{
    private AxiosStatus axiosStatus;
    public UplaodException() {}
    public UplaodException(AxiosStatus axiosStatus) {
        this.axiosStatus = axiosStatus;
    }
    public AxiosStatus getAxiosStatus() {
        return axiosStatus;
    }
    public void setAxiosStatus(AxiosStatus axiosStatus) {
        this.axiosStatus = axiosStatus;
    }
}
