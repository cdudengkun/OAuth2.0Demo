package com.cjack.oauth.server.dao.domain;

import lombok.Data;

/**
 * 授权码信息
 * Created by root on 3/30/19.
 */
@Data
public class OauthCodeDto {
    private Long id;
    private String oauthCode;
    private Long expireTime;//授权码过期时间
    private Long userId;//授权码对应用户
    private String redirectUrl;//申请用户授权的时候附带的重定向的地址
    private String clientId;//请求授权码的第三方应用id
    private String scope;//授权范围
}
