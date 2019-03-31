package com.cjack.oauth.server.request;

import lombok.Data;

/**
 * Created by root on 3/31/19.
 */
@Data
public class OauthRequest {
    String redirectUrl;
    String clientId;
    String responseType;
    Long userId;
    String scope;
}
