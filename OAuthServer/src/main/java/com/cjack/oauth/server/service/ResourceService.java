package com.cjack.oauth.server.service;

import com.cjack.oauth.server.dao.domain.AccessTokenDto;
import com.cjack.oauth.server.dao.domain.OauthCodeDto;
import com.cjack.oauth.server.dao.domain.UserInfoDto;
import com.cjack.oauth.server.dao.mapper.AccessTokenMapper;
import com.cjack.oauth.server.dao.mapper.UserInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by root on 3/30/19.
 */
@Service
public class ResourceService {

    @Autowired
    private AccessTokenMapper accessTokenMapper;
    @Autowired
    private UserInfoMapper userInfoMapper;

    public UserInfoDto getuserInfo( String accessToken){
        AccessTokenDto accessTokenDto = accessTokenMapper.getByAccessToken( accessToken);
        if( accessTokenDto == null){
            return null;
        }
        OauthCodeDto oauthCodeDto = accessTokenMapper.getOauthCodeById( accessTokenDto.getOauthCodeId());
        if( oauthCodeDto == null){
            return null;
        }
        return userInfoMapper.getUserInfo( oauthCodeDto.getUserId());
    }
}
