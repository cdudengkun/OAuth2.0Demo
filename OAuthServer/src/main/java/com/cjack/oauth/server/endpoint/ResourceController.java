package com.cjack.oauth.server.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.cjack.oauth.server.common.RestfullResponse;
import com.cjack.oauth.server.dao.domain.UserInfoDto;
import com.cjack.oauth.server.request.GetUserInfoRequest;
import com.cjack.oauth.server.service.OauthService;
import com.cjack.oauth.server.service.ResourceService;
import com.cjack.oauth.server.util.IpUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import javax.servlet.http.HttpServletRequest;

/**
 * 资源服务器
 * Created by root on 5/28/18.
 */
@Controller
@RequestMapping("/resource")
public class ResourceController{

    @Autowired
    private ResourceService resourceService;
    @Autowired
    private OauthService oauthService;

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public RestfullResponse getUserInfo( HttpServletRequest request, @RequestBody GetUserInfoRequest getUserInfoRequest) throws Exception {

        //校验请求来源是否正确
        if( !oauthService.checkClient( IpUtil.getIpAddress( request), getUserInfoRequest.getClientId())){
            return RestfullResponse.ERROR( "非信任客户端,请求拒绝");
        }
        //校验token
        if( !oauthService.checkAccessToken( getUserInfoRequest.getAccessToken(), getUserInfoRequest.getClientId(), IpUtil.getIpAddress(request))){
            return RestfullResponse.ERROR( "access_token校验失败，请求拒绝");
        }
        //通过token获取用户的信息
        UserInfoDto userInfoDto = resourceService.getuserInfo( getUserInfoRequest.getAccessToken());
        if( userInfoDto != null){
            return RestfullResponse.SUCCESS( userInfoDto.toJson());
        }else{
            return RestfullResponse.ERROR( "用户不存在");
        }
    }
}
