package com.cjack.oauth.server.dao.mapper;

import com.cjack.oauth.server.dao.domain.AccessTokenDto;
import com.cjack.oauth.server.dao.domain.OauthCodeDto;
import org.springframework.stereotype.Repository;

/**
 * 授权码和授权令牌共用一个mapper
 * Created by root on 3/30/19.
 */
@Repository
public interface AccessTokenMapper {

    public AccessTokenDto getByAccessToken( String accessToken);

    public void updateAccessToken( AccessTokenDto accessToken);

    public void addAccessToken( AccessTokenDto accessToken);

    public void addOauthCode( OauthCodeDto oauthCodeDto);

    public OauthCodeDto getOauthCode( String oauthCode);

    public OauthCodeDto getOauthCodeById( Long id);
}
