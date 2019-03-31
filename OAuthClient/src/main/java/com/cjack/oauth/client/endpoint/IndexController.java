package com.cjack.oauth.client.endpoint;

import com.alibaba.fastjson.JSONObject;
import com.cjack.oauth.client.common.RestfullResponse;
import com.cjack.oauth.client.util.HttpClientUtil;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by root on 5/28/18.
 */
@Controller
@RequestMapping("/client")
public class IndexController{

    public final static String REDIRECT_URL = "http://192.168.0.103:7501/client/callback";
    public final static String CLIENT_ID = "12345ABCDE";

    @RequestMapping(value = "/index.htm", method = RequestMethod.GET)
    public String index( ModelMap model) throws UnsupportedEncodingException {
        model.put( "redirectUrl", URLEncoder.encode( REDIRECT_URL, "utf-8"));
        model.put( "clientId", CLIENT_ID);
        model.put( "accessToken", "");
        return "index";
    }

    @RequestMapping(value = "/callback", method = RequestMethod.GET)
    public String callback( ModelMap model, String code) throws UnsupportedEncodingException {
        //在回调方法里面用授权码去获取授权token
        JSONObject param = new JSONObject();
        param.put( "grant_type", "authorization_code");
        param.put( "oauthCode", code);
        param.put( "redirectUrl", URLEncoder.encode( REDIRECT_URL, "utf-8"));
        param.put( "cliendId", "12345ABCDE");
        String url = "http://192.168.0.103:7502/oauth/generateAccessToken";
        JSONObject res = JSONObject.parseObject(HttpClientUtil.doPost( url, param.toJSONString()));
        System.out.print( "res=" + res);
        model.put( "accessToken", res.getJSONObject( "data").getString( "accessToken"));
        model.put( "redirectUrl", URLEncoder.encode( REDIRECT_URL, "utf-8"));
        model.put( "clientId", CLIENT_ID);
        return "index";
    }

    @RequestMapping(value = "/getUserInfo", method = RequestMethod.POST)
    @ResponseBody
    public String getUserInfo( String accessToken, String clientId) throws UnsupportedEncodingException {
        //调用资源服务器查询用户信息
        JSONObject param = new JSONObject();
        param.put( "accessToken", accessToken);
        param.put( "clientId", clientId);
        String url = "http://192.168.0.103:7502/resource/getUserInfo";
        JSONObject res = JSONObject.parseObject( HttpClientUtil.doPost( url, param.toJSONString()));
        System.out.println("res=" + res);
        return res.toJSONString();
    }
}
