$(function () {
    $("li").removeClass("active");
    $("span").removeClass("open");
    $("#registIndex").addClass("active");
    $("#registEnv").addClass("active");
    $("#registIndex a span").addClass("open");
    goNextStep("chooseEnv");
    $("input[name='iccid']").blur(checkSimcard);
})
function submitStep(step){
    switch(step){
        case 1:
            //判断是否没有选择环境
            if(!$("input[name='envOne']").prop("checked") && !$("input[name='envTwo']").prop("checked")){
                jAlert("请至少选择一个环境！");
                return;
            }
            goNextStep("importSimcard");
            break;
        case 2:
            handleImportSimcard();
            break;
        case 3:
            handleImportTbox();
            break;
        case 4:
            handleImportVehicle();
            break;
        case 5:
            registService();
            break;
        default:
            break;
    }
}
function handleImportSimcard(){
    var envTwo = $("input[name='envTwo']").prop("checked");//9093
    //校验参数是否输入
    var iccid = $("input[name='iccid']").val();
    var phoneNumber = $("input[name='phoneNumber']").val();
    var type = $("select[name='type']").val();
    var simId = $("input[name='simId']").val();
    var reg = /^[a-zA-Z0-9]+$/;

    if (type == null || type == undefined || type == ''){
        $("#typeTipSpan").text("运营商不能为空！");
        return;
    }else{
        $("#typeTipSpan").text("");
    }
    if (iccid == null || iccid == undefined || iccid == ''){
        $("#iccidTipSpan").text("iccid不能为空！");
        return;
    }else if(!iccid.match(reg)){
        $("#iccidTipSpan").text("iccid格式不正确！");
        return;
    }else if(iccid.length !=20 && type == "2"){
        $("#iccidTipSpan").text("移动的Simcard的iccid只能为20位！");
        return;
    }else if(iccid.length !=20 && type == "1"){
        $("#iccidTipSpan").text("联通的Simcard的iccid只能为20位！");
        return;
    }else{
        $("#iccidTipSpan").text("");
    }
    if (phoneNumber == null || iccid == undefined || phoneNumber == ''){
        $("#phoneNumberTipSpan").text("电话号码不能为空！");
        return;
    }else{
        $("#phoneNumberTipSpan").text("");
    }
    if(!envTwo){//未选择9093直接去下一步
        jAlert("导入Simcard成功！",null,function(){
            goNextStep("importTbox");
        });
        return;
    }
    //向后台发送请求导入simcard，导入成功之后再跳转到第三步
    var url = $("#contextPath").val() + '/index/importSimcard.htm';
    $.post(
        url ,
        {
            "iccid":iccid,
            "phoneNumber":phoneNumber,
            "type":type,
            "simId":simId
        } ,
        function(data){
            var json = $.parseJSON(data);
            if (json.status == "SUCCESS") {
                jAlert("导入Simcard成功！",null,function(){
                    goNextStep("importTbox");
                });
            } else if (json.status == "FAILURE") {
                jAlert("导入Simcard失败！错误信息: " + json.message);
            }
        }
    );
}
function handleImportTbox() {
    var envTwo = $("input[name='envTwo']").prop("checked");//9093
    //校验参数是否输入
    var sn = $("input[name='sn']").val();
    var model = $("select[name='model']").val();
    var imei = $("input[name='imei']").val();
    var imsi = $("input[name='imsi']").val();
    var iccid = $("input[name='iccid']").val();
    var version5606 = $("input[name='version5606']").val();
    var versionIMX6 = $("input[name='versionIMX6']").val();

    if (sn == null || sn == undefined || sn == ''){
        $("#snTipSpan").text("TBox序列号不能为空！");
        return;
    }else if(sn.length !=20){
        $("#snTipSpan").text("TBox序列号格式不正确！");
        return;
    }else{
        $("#snTipSpan").text("");
    }
    if (model == null || model == undefined || model == ''){
        $("#modelTipSpan").text("TBox类型必选！");
        return;
    }else{
        $("#modelTipSpan").text("");
    }
    if (imei == null || imei == undefined || imei == ''){
        $("#imeiTipSpan").text("TBox的imei不能为空！");
        return;
    }else{
        $("#imeiTipSpan").text("");
    }
    if (imsi == null || imsi == undefined || imsi == ''){
        $("#imsiTipSpan").text("TBox的imsi不能为空！");
        return;
    }else{
        $("#imsiTipSpan").text("");
    }
    //如果是avnt的话必须输入版本号
    if(model == "2"){
        if (version5606 == null || version5606 == undefined || version5606 == ''){
            $("#version5606TipSpan").text("TBox的version5606版本号不能为空！");
            return;
        }else{
            $("#version5606TipSpan").text("");
        }
        if (versionIMX6 == null || versionIMX6 == undefined || versionIMX6 == ''){
            $("#versionIMX6TipSpan").text("TBox的versionIMX6版本号不能为空！");
            return;
        }else{
            $("#versionIMX6TipSpan").text("");
        }
    }
    if(!envTwo){//未选择9093直接去下一步
        jAlert("导入Simcard成功！",null,function(){
            //加载车系列表
            getSeriesAndModelRelationList();
            goNextStep("importVehicle");
        });
        return;
    }
    //向后台发送请求导入simcard，导入成功之后再跳转到第三步
    var url = $("#contextPath").val() + '/index/importTBox.htm';
    $.post(
        url ,
        {
            "sn":sn,
            "model":model,
            "imei":imei,
            "imsi":imsi,
            "iccid":iccid,
            "version5606":version5606,
            "versionIMX6":versionIMX6
        } ,
        function(data){
            var json = $.parseJSON(data);
            if (json.status == "SUCCESS") {
                jAlert("导入TBox成功！",null,function(){
                    goNextStep("importVehicle");
                    //加载车系列表
                    getSeriesAndModelRelationList();
                });
            } else if (json.status == "FAILURE") {
                jAlert("导入Tbox失败！错误信息: " + json.message);
            }
        }
    );
}
function handleImportVehicle() {
    //校验参数是否输入
    var vin = $("input[name='vin']").val();
    var modelCode = $("select[name='modelCode']").val();
    var engineNo = $("input[name='engineNo']").val();

    if (vin == null || vin == undefined || vin == ''){
        $("#vinTipSpan").text("vin码不能为空！");
        return;
    }else{
        $("#vinTipSpan").text("");
    }
    if (modelCode == null || modelCode == undefined || modelCode == ''){
        $("#modelCodeTipSpan").text("车辆车系必选！");
        return;
    }else{
        $("#modelCodeTipSpan").text("");
    }
    if (engineNo == null || engineNo == undefined || engineNo == ''){
        $("#engineNoTipSpan").text("车辆发动机号不能为空！");
        return;
    }else{
        $("#engineNoTipSpan").text("");
    }

    //之前输入的参数，这里也一并传过去，9092环境注册需要使用
    var iccid = $("input[name='iccid']").val();
    var phoneNumber = $("input[name='phoneNumber']").val();
    var imei = $("input[name='imei']").val();
    var imsi = $("input[name='imsi']").val();
    var sn = $("input[name='sn']").val();
    var envOne = $("input[name='envOne']").prop("checked");//9092
    var envTwo = $("input[name='envTwo']").prop("checked");//9093
    var seriescode = $("select[name='modelCode']").find("option:selected").text();//车系
    var tboxModel = $("select[name='model']").val();
    //向后台发送请求导入simcard，导入成功之后再跳转到第三步
    var url = $("#contextPath").val() + '/index/importVehicle.htm';
    $.post(
        url ,
        {
            "vin":vin,
            "modelCode":modelCode,
            "seriescode":seriescode,
            "engineNo":engineNo,
            "iccid":iccid,
            "phoneNumber":phoneNumber,
            "imei":imei,
            "imsi":imsi,
            "sn":sn,
            "envOne":envOne,
            "envTwo":envTwo,
            "tboxModel":tboxModel
        } ,
        function(data){
            var json = $.parseJSON(data);
            if (json.status == "SUCCESS") {
                //9092环境流程结束，9093环境进入服务开通
                //只选择了9092环境
                if(envOne && !envTwo){
                    jAlert("车辆注册成功！9092环境注册流程结束!");
                }else if(envOne && envTwo){//9093和9093环境都选择了
                    jConfirm("车辆注册成功，是否立即为9093环境的车辆开通服务？",null,function(result){
                        if(result){
                            goNextStep("registService");
                        }
                    });
                }else if(!envOne && envTwo){//只选择了9093环境
                    jConfirm("车辆注册成功，是否立即为9093环境的车辆开通服务？",null,function(result){
                        if(result){
                            goNextStep("registService");
                        }
                    });
                }
            } else if (json.status == "FAILURE") {
                jAlert("导入车辆失败！错误信息: " + json.message);
            }
        }
    );
}
function registService(){
    //校验参数是否输入
    var username = $("input[name='username']").val();
    var telephone = $("input[name='telephone']").val();
    var credentialType = $("select[name='credentialType']").val();
    var credentialNo = $("input[name='credentialNo']").val();

    //车辆信息
    var vin = $("input[name='vin']").val();
    var sn = $("input[name='sn']").val();
    var engineNo = $("input[name='engineNo']").val();
    var seriescode = $("select[name='modelCode']").find("option:selected").text();//车系
    var modelCode = $("select[name='modelCode']").val();
    var tboxModel = $("select[name='model']").val();

    if (username == null || username == undefined || username == ''){
        $("#usernameTipSpan").text("用户姓名不能为空！");
        return;
    }else{
        $("#usernameTipSpan").text("");
    }
    if (telephone == null || telephone == undefined || telephone == ''){
        $("#telephoneTipSpan").text("电话号码不能为空！");
        return;
    }else{
        $("#telephoneTipSpan").text("");
    }
    if (credentialType == null || credentialType == undefined || credentialType == ''){
        $("#credentialTypeTipSpan").text("证件类型必选！");
        return;
    }else{
        $("#credentialTypeTipSpan").text("");
    }
    if (credentialNo == null || credentialNo == undefined || credentialNo == ''){
        $("#credentialNoTipSpan").text("证件号号不能为空！");
        return;
    }else{
        $("#credentialNoTipSpan").text("");
    }
    var url = $("#contextPath").val() + '/index/registService.htm';
    $.post(
        url ,
        {
            "username":username,
            "telephone":telephone,
            "credentialType":credentialType,
            "credentialNo":credentialNo,
            "vin":vin,
            "sn":sn,
            "seriescode":seriescode,
            "engineNo":engineNo,
            "modelCode":modelCode,
            "tboxModel":tboxModel

        } ,
        function(data){
            var json = $.parseJSON(data);
            if (json.status == "SUCCESS") {
                jAlert("服务开通成功,9093注册流程结束！", null, function(){
                    location.reload();
                });
            } else if (json.status == "FAILURE") {
                jAlert("服务开通失败！错误信息: " + json.message);
            }
        }
    );
}
/*
检查simcard是否存在，如果存在则将他的一些信息放到输入框
 */
function checkSimcard(){
    var url = $("#contextPath").val() + '/index/getSimcard.htm';
    var iccid = $("input[name='iccid']").val();
    $.get(
        url ,
        {
            "iccid":iccid
        } ,
        function(data){
            var json = $.parseJSON(data);
            if (json.status == "FAILURE") {
                //没有simcard数据
            }else{
                //如果获取到simcard，则将simcard的信息带出来
                var simCard = json;
                $("input[name='phoneNumber']").val(simCard.telephone);
                $("select[name='type']").val(simCard.type);
                $("input[name='simId']").val(simCard.simId);
            }
        }
    );
}
/**
 * 获取车系列表
 */
function getSeriesAndModelRelationList(){
    var url = $("#contextPath").val() + '/index/getSeriesAndModelRelationList.htm';
    $.get(
        url , {}, function(data){
            var series = $.parseJSON(data);
            var select = $("select[name='modelCode']");
            select.append("<option value=\"\">选择车系</option>");
            for( var i=0; i<series.length; i++){
                select.append("<option value=\""+ series[i].modelCode +"\">"+ series[i].seriesCode +"</option>");
            }
        }
    );
}
function goNextStep(stepClass){
    $(".registertools").css("display", "none");
    $("."+stepClass).css("display", "block");
}
function selectTboxModel(select){
    var model = $(select).val();
    if(model == "2"){
        $("#avntDiv").css("display", "block");
    }else{
        $("#avntDiv").css("display", "none");
    }
}
