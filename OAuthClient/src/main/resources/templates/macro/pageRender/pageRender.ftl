<#-- 这里提供一些项目中可复用的数据渲染组件 可能是多个UI组件的复合体 -->
<#import "../common/constant.ftl" as constant>
<#import "../common/utils.ftl" as utils>
<#import "../components/tag.ftl" as tag>
<#assign contextPath=constant.contextPath/>
<#assign webName=constant.webName/>

<#--
    header渲染器
    head={
        title:""
        css:[],
        js:[],
        pageFlag:""
    }
-->
<#macro headerRender head={}>
    <#local appVersion="2.0.8"/>

    <#local orderContextPath=contextPath+"/order"/>
    <#if promotionAble?? && promotionAble>
        <#local orderContextPath=orderContextPath+"/promotion"/>
    </#if>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html xmlns="http://www.w3.org/1999/xhtml" lang="zh-CN">
<head>
    <meta id="J_pageContextPath" neme="pagecontextpath" content="${contextPath}"/>
    <meta id="J_orderPageContextPath" neme="orderpagecontextpath" content="${orderContextPath}"/>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <meta http-equiv="cache-control" content="no-cache">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta content="width=device-width, height=device-height, initial-scale=1.0" name="viewport"/>
    <title>${(head.title)!"首页"}</title>


<#-- TODO CSS  -->
    <link href="${contextPath}/css/bootstrap.min.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/bootstrap-responsive.min.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/font-awesome.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/style-metro.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/style.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/showLoading.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/style-responsive.css" rel="stylesheet" type="text/css"/>
    <link href="${contextPath}/css/default.css" rel="stylesheet" type="text/css" id="style_color"/>
    <link href="${contextPath}/css/uniform.default.css" rel="stylesheet" type="text/css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/datepicker.css"/>
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/datetimepicker.css"/>
    <!-- END GLOBAL MANDATORY STYLES -->

    <!-- BEGIN PAGE LEVEL STYLES -->
    <link rel="stylesheet" type="text/css" href="${contextPath}/css/select2_metro.css"/>
    <link rel="stylesheet" href="${contextPath}/css/DT_bootstrap.css"/>

    <!-- END PAGE LEVEL STYLES -->

    <link rel="shortcut icon" href="${contextPath}/image/favicon.ico"/>

    <link rel="stylesheet" href="${contextPath}/css/location.css"/>

    <#list head.css as css>
        <link rel="stylesheet" href="${contextPath}/static/css/${css}.css?${appVersion}">
    </#list>


<#-- TODO JS  -->

    <script src="${contextPath}/js/tools/jquery-1.10.1.min.js" type="text/javascript"></script>
    <script src="${contextPath}/js/tools/jquery-migrate-1.2.1.min.js" type="text/javascript"></script>

    <!-- IMPORTANT! Load jquery-ui-1.10.1.custom.min.js before bootstrap.min.js to fix bootstrap tooltip conflict with jquery ui tooltip -->

    <script src="${contextPath}/js/tools/jquery-ui-1.10.1.custom.min.js" type="text/javascript"></script>

    <script src="${contextPath}/js/tools/bootstrap.min.js" type="text/javascript"></script>
    <link href="${contextPath}/css/jquery.alerts.css" rel="stylesheet" type="text/css"/>
    <script src="${contextPath}/js/tools/jquery.alerts.js" type="text/javascript"></script>
    <!--[if lt IE 9]>

    <script src="${contextPath}/js/tools/excanvas.js"></script>

    <script src="${contextPath}/js/tools/respond.min.js"></script>

    <![endif]-->

    <script src="${contextPath}/js/tools/jquery.slimscroll.min.js" type="text/javascript"></script>

    <script src="${contextPath}/js/tools/jquery.blockui.min.js" type="text/javascript"></script>

    <script src="${contextPath}/js/tools/jquery.cookie.min.js" type="text/javascript"></script>

    <script src="${contextPath}/js/tools/jquery.uniform.min.js" type="text/javascript"></script>

    <!-- date -->
    <script type="text/javascript" src="${contextPath}/js/tools/bootstrap-datepicker.js"></script>

    <script type="text/javascript" src="${contextPath}/js/tools/bootstrap-datetimepicker.js"></script>

    <!-- END CORE PLUGINS -->
    <!-- BEGIN PAGE LEVEL PLUGINS -->

    <script type="text/javascript" src="${contextPath}/js/tools/select2.min.js"></script>

    <script type="text/javascript" src="${contextPath}/js/tools/jquery.dataTables.js"></script>

    <script type="text/javascript" src="${contextPath}/js/tools/jquery.showLoading.min.js"></script>

    <script type="text/javascript" src="${contextPath}/js/tools/DT_bootstrap.js"></script>

    <script type="text/javascript" src="${contextPath}/js/tools/bootstrap-paginator.js"></script>

    <script type="text/javascript" src="${contextPath}/js/tools/bootbox.js"></script>
    <#list head.js as js>
        <script src="${contextPath}/static/js/${js}.js?${appVersion}" type="text/javascript"></script>
    </#list>

</head>
</#macro>