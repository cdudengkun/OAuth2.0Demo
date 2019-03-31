package com.cjack.oauth.server.dao.domain;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

/**
 * Created by root on 3/30/19.
 */
@Data
public class UserInfoDto {
    private Long id;
    private String nickName;
    private String avator;

    public JSONObject toJson(){
        JSONObject json = new JSONObject();
        json.put( "id", id);
        json.put( "nickName", nickName);
        json.put( "avator", avator);
        return json;
    }
}
