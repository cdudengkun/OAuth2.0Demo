package com.cjack.oauth.server.dao.domain;

import lombok.Data;

import java.util.Date;

/**
 * Created by root on 3/30/19.
 */
@Data
public class ClientAppInfoDto {
    private Long id;
    private String name;
    private String trustIp;//ip白名单
    private String clientId;//client_id
    private Date createTime;
}
