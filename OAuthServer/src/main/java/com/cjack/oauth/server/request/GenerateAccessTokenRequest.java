package com.cjack.oauth.server.request;

import lombok.Data;

/**
 * Created by root on 3/31/19.
 */
@Data
public class GenerateAccessTokenRequest {
    private String grant_type;
    private String oauthCode;
    private String redirectUrl;
    private String clientId;

}
