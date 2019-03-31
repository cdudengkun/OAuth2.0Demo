<#import "macro/macro.ftl" as macro>

<meta charset="utf-8"/>

<title>认证服务器授权页面</title>

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
        $("#oauthBtn").click(function(){
            var param = {
                "redirectUrl" : $("#redirectUrl").val(),
                "clientId" : $("#clientIdInp").val(),
                "responseType" : $("#responseTypeInp").val(),
                "scope" : "default",//授权范围可以让用户自行选择
                "userId" : "1"//完整版的流程，这里需要让用户先登录，再获取它的用户id
            };
            $.ajax({
                type: "POST",
                url: "http://192.168.0.103:7502/oauth/oauth" ,
                contentType: "application/json",  //发送信息至服务器时内容编码类型。
                dataType: "json",  // 预期服务器返回的数据类型。如果不指定，jQuery 将自动根据 HTTP 包 MIME 信息来智能判断，比如XML MIME类型就被识别为XML。
                data: JSON.stringify( param),
                success: function(data){
                    if( data.code == 200){
                        var code = data.data.code;
                        //授权码获取成功，重定向到第三方应用的方案
                        window.location.href = decodeURIComponent( $("#redirectUrl").val()) + "?code=" + code;
                    }else{
                        alert( data.msg);
                    }
                }
            });
        });
        $("#cancelOauthBtn").click(function(){
            alert( "拒绝授权");
        });
    });
</script>
<body>
    <input type="button" id="oauthBtn" value="确认授权"/>

    <input type="button" id="cancelOauthBtn" value="拒绝授权"/>

    <input type="hidden" id="accessTokenInp"/>

    <input type="hidden" id="redirectUrl" value="${redirectUrl}"/>

    <input type="hidden" id="responseTypeInp" value="${responseType}"/>

    <input type="hidden" id="clientIdInp" value="${clientId}"/>

</body>
