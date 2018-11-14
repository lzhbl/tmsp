package com.zheng.upms.common.constant;

import com.zheng.common.base.BaseResult;

/**
 * upms系统常量枚举类
 * Created by lx on 2017/2/18.
 */
public class UpmsResult extends BaseResult {
    private String info;//结果描述

    /**
     * 默认构造一个请求失败的JSON对象
     */
    public UpmsResult(){
        super(UpmsResultConstant.HTTPFAIL.getCode(),UpmsResultConstant.HTTPFAIL.getMessage(),null);
    }

    public UpmsResult(UpmsResultConstant upmsResultConstant, Object data) {
        super(upmsResultConstant.getCode(), upmsResultConstant.getMessage(), data);
    }

    public UpmsResult(UpmsResultConstant upmsResultConstant, Object data,String info) {
        super(upmsResultConstant.getCode(), upmsResultConstant.getMessage(), data);
        this.info=info;
    }

    public void setResult(UpmsResultConstant constant){
        this.setCode(constant.getCode());
        this.setMessage(constant.getMessage());
    }

    public void setResult(UpmsResultConstant constant,Object data){
        this.setCode(constant.getCode());
        this.setMessage(constant.getMessage());
        this.setData(data);
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}
