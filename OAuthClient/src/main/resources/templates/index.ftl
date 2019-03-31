<#import "macro/macro.ftl" as macro>

<meta charset="utf-8"/>

<title>第三方客户端</title>

<meta content="width=device-width, initial-scale=1.0" name="viewport"/>
<meta content="" name="description"/>
<meta content="" name="author"/>
<script src="${contextPath}/js/tools/jquery-1.10.1.min.js" type="text/javascript"></script>
<script src="${contextPath}/js/tools/bootstrap.min.js" type="text/javascript"></script>
<script src="${contextPath}/js/tools/jquery.uniform.min.js" type="text/javascript"></script>
<script src="${contextPath}/js/tools/jquery.validate.min.js" type="text/javascript"></script>
<style type="text/css">

</style>
<script>
    $(function(){
        $("#getUserInfoBtn").click(function(){
            $.post(
                    "http://192.168.0.103:7501/client/getUserInfo" ,
                    {
                        "accessToken" : $("#accessTokenInp").val(),
                        "clientId" : $("#clientIdInp").val()
                    } ,
                    function(data){
                        alert( data);
                    }
            );
        });
        $("#goOauthBtn").click(function(){
            var clientId = $("#clientIdInp").val();
            var redirect_uri = $("#redirectUrlInp").val();
            window.location.href="http://192.168.0.103:7502/oauth/userOauth.htm?responseType=code&clientId=" +
            clientId + "&redirectUrl=" + redirect_uri;
        });
    });
</script>
<body>
    <input type="button" id="getUserInfoBtn" value="获取用户信息"/>

    <input type="button" id="goOauthBtn" value="请求认证服务器授权"/>

    <input type="hidden" id="accessTokenInp" value="${accessToken}"/>

    <input type="hidden" id="clientIdInp" value="${clientId}"/>

    <input type="hidden" id="redirectUrlInp" value="${redirectUrl}"/>
</body>
