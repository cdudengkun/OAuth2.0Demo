package com.cjack.oauth.server.service;

import com.cjack.oauth.server.dao.domain.AccessTokenDto;
import com.cjack.oauth.server.dao.domain.ClientAppInfoDto;
import com.cjack.oauth.server.dao.domain.OauthCodeDto;
import com.cjack.oauth.server.dao.mapper.AccessTokenMapper;
import com.cjack.oauth.server.dao.mapper.ClientAppInfoMapper;
import com.cjack.oauth.server.util.OauthUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created by root on 3/30/19.
 */
@Service
public class OauthService {

    @Autowired
    private AccessTokenMapper accessTokenMapper;
    @Autowired
    private ClientAppInfoMapper clientAppInfoMapper;

    /**
     * 校验第三方客户端是否是信任的客户端
     * @param ip
     * @param clientId
     * @return
     */
    public boolean checkClient( String ip, String clientId){
        ClientAppInfoDto clientAppInfoDto = clientAppInfoMapper.getByClientId( clientId);
        if( clientAppInfoDto == null){
            return false;
        }
        //校验第三方客户端的ip是否是信任的
        if( !clientAppInfoDto.getTrustIp().contains( ip)){
            return false;
        }
        return true;
    }

    public boolean checkAccessToken( String accessToken, String clientId, String ip){
        AccessTokenDto accessTokenDto = accessTokenMapper.getByAccessToken( accessToken);
        if( accessTokenDto == null){
            return false;
        }
        //校验令牌是否被用户收回
        if( !accessTokenDto.getStatus().equals( 1)){
            return false;
        }
        //校验令牌有效期
        if( accessTokenDto.getExpireTime() - System.currentTimeMillis() > 24 * 60 * 60 * 1000){
            return false;
        }
        //校验令牌的客户端的有效性
        OauthCodeDto oauthCodeDto = accessTokenMapper.getOauthCodeById( accessTokenDto.getOauthCodeId());
        ClientAppInfoDto clientAppInfoDto = clientAppInfoMapper.getByClientId( clientId);
        if( oauthCodeDto == null || clientAppInfoDto == null){
            return false;
        }
        if( !clientAppInfoDto.getClientId().equals( clientId)){
            return false;
        }
        if( !clientAppInfoDto.getTrustIp().contains(ip)){
            return false;
        }
        return true;
    }

    public boolean checkOauthCode( String oauthCode, String redirectUrl){
        OauthCodeDto oauthCodeDto = accessTokenMapper.getOauthCode( oauthCode);
        if( oauthCodeDto == null){
            return false;
        }
        if( !oauthCodeDto.getRedirectUrl().equals( redirectUrl)){
            return false;
        }
        if( oauthCodeDto.getExpireTime() - System.currentTimeMillis() > 10 * 60 * 1000){
            return false;
        }
        return true;
    }


    public String generatedOauthCode( String redirectUrl, String clientId, Long userId, String scope) throws Exception {
        String oauthCode = OauthUtil.generateOauthCode();

        OauthCodeDto oauthCodeDto = new OauthCodeDto();
        oauthCodeDto.setExpireTime( System.currentTimeMillis() + 10 * 60 * 1000);//授权码超时时间设置为10分支
        oauthCodeDto.setRedirectUrl( redirectUrl);
        oauthCodeDto.setOauthCode( oauthCode);
        oauthCodeDto.setClientId( clientId);
        oauthCodeDto.setUserId( userId);
        oauthCodeDto.setScope( scope);
        accessTokenMapper.addOauthCode( oauthCodeDto);

        return oauthCode;
    }

    public String generatedAccessToken( String oauthCode) throws Exception {
        OauthCodeDto oauthCodeDto = accessTokenMapper.getOauthCode( oauthCode);

        String accessToken = OauthUtil.generateAccessToken();

        AccessTokenDto accessTokenDto = new AccessTokenDto();
        accessTokenDto.setExpireTime( System.currentTimeMillis() + 24 * 60 * 60 * 1000);//设置为24个小时

        accessTokenDto.setAccessToken(accessToken);
        accessTokenDto.setStatus(1);
        accessTokenDto.setOauthCodeId( oauthCodeDto.getId());
        accessTokenMapper.addAccessToken( accessTokenDto);

        return accessToken;
    }
}
