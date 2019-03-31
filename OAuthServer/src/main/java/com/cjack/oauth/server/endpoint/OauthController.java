package com.cjack.oauth.server.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.cjack.oauth.server.common.RestfullResponse;
import com.cjack.oauth.server.request.GenerateAccessTokenRequest;
import com.cjack.oauth.server.request.OauthRequest;
import com.cjack.oauth.server.service.OauthService;
import com.cjack.oauth.server.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by root on 5/28/18.
 */
@Controller
@RequestMapping("/oauth")
public class OauthController {

    @Autowired
    private OauthService oauthService;

    @RequestMapping(value = "/userOauth.htm", method = RequestMethod.GET)
    public String login( ModelMap model, String redirectUrl, String responseType, String clientId) {
        model.addAttribute( "redirectUrl", redirectUrl);
        model.addAttribute( "responseType", responseType);
        model.addAttribute( "clientId", clientId);
        return "userOauth";
    }

    /**
     * 生成授权码
     * @param request
     * @param oauthRequest
     * @return
     */
    @RequestMapping(value = "/oauth", method = RequestMethod.POST)
    @ResponseBody
    public RestfullResponse oauth( HttpServletRequest request, @RequestBody OauthRequest oauthRequest) {
        if( !"code".equals( oauthRequest.getResponseType())){
            return RestfullResponse.ERROR( "response_type错误,请求拒绝");
        }
        //校验请求来源是否正确
        if( !oauthService.checkClient( IpUtil.getIpAddress(request), oauthRequest.getClientId())){
            return RestfullResponse.ERROR( "非信任客户端,请求拒绝");
        }
        String code;
        try {
            code = oauthService.generatedOauthCode( oauthRequest.getRedirectUrl(),
                    oauthRequest.getClientId(), oauthRequest.getUserId(), oauthRequest.getScope());
        } catch (Exception e) {
            e.printStackTrace();
            return RestfullResponse.ERROR( "生成token失败，请稍后再试");
        }
        JSONObject data = new JSONObject();
        data.put( "code", code);
        return RestfullResponse.SUCCESS( data);
    }

    /**
     * 生成授权token
     * @param generateAccessTokenRequest
     * @return
     */
    @RequestMapping(value = "/generateAccessToken", method = RequestMethod.POST)
    @ResponseBody
    public RestfullResponse generateAccessToken( HttpServletRequest request, @RequestBody GenerateAccessTokenRequest generateAccessTokenRequest) {
        if( !"authorization_code".equals( generateAccessTokenRequest.getGrant_type())){
            return RestfullResponse.ERROR( "grant_type错误,请求拒绝");
        }
        //校验请求来源是否正确
        if( oauthService.checkClient( IpUtil.getIpAddress( request), generateAccessTokenRequest.getClientId())){
            return RestfullResponse.ERROR( "非信任客户端,请求拒绝");
        }
        //校验授权码和redirectUrl是否正确
        if( oauthService.checkOauthCode( generateAccessTokenRequest.getOauthCode(), generateAccessTokenRequest.getRedirectUrl())){
            return RestfullResponse.ERROR( "授权码错误,请求拒绝");
        }
        String accessToken;
        try {
            accessToken = oauthService.generatedAccessToken( generateAccessTokenRequest.getOauthCode());
        } catch (Exception e) {
            return RestfullResponse.ERROR( "生成授权令牌失败,请稍后再试");
        }
        JSONObject data = new JSONObject();
        data.put( "accessToken", accessToken);
        return RestfullResponse.SUCCESS( data);
    }
}
