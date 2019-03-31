package com.cjack.oauth.client.common;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by root on 3/30/19.
 */
public class RestfullResponse {

    public final static Integer SUCCESS_CODE = 200;//请求成功
    public final static String SUCCESS_MSG = "请求成功";//请求成功
    public final static Integer ERROR_CODE = 500;//请求失败

    private Integer code;
    private String msg;
    private JSONObject data;

    public RestfullResponse(){

    }

    public RestfullResponse(Integer code, String msg, JSONObject data){
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public RestfullResponse(Integer code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public static RestfullResponse SUCCESS( JSONObject data){
        return new RestfullResponse( SUCCESS_CODE, SUCCESS_MSG, data);
    }

    public static RestfullResponse ERROR( String msg){
        return new RestfullResponse( ERROR_CODE, msg);
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public JSONObject getData() {
        return data;
    }

    public void setData(JSONObject data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
