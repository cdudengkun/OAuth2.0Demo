package com.cjack.oauth.server.request;

import lombok.Data;

/**
 * Created by root on 3/31/19.
 */
@Data
public class GetUserInfoRequest {
    private String accessToken;
    private String clientId;
}
