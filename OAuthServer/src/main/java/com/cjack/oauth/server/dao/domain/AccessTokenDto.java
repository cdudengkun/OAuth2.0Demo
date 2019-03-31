package com.cjack.oauth.server.dao.domain;

import lombok.Data;

import java.util.Date;

/**
 * 授权令牌信息
 * Created by root on 3/30/19.
 */
@Data
public class AccessTokenDto {

    private Long id;
    private String accessToken;//授权令牌
    private Long expireTime;//授权令牌过期时间,毫秒时间戳
    private Date createTime;
    private Long oauthCodeId;//授权码id
    private Integer status;//1-使用中，2-撤回
}
