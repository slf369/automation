<!DOCTYPE html>
<html lang="en">
<head>
	<meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="/bootstrap-3.3.7-dist/js/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
	<title>新增/编辑</title>
    <style type="text/css">
        form {
            margin-bottom: 18px;
        }
        .formField {
            overflow: auto;
            width: 100%;
            margin: 5px 0;
        }
        label {
            min-width: 130px;
            float: left;
            display: block;
            margin-right: 5px;
            text-transform: capitalize;
        }
        .formText {
            display: block;
            float: left;
        }
    </style>
</head>
<body background="/background.png">
<div class="container" style="max-width: 690px;margin-top: 50px;">

    <div class="well">
	<h1>新增/编辑用例</h1>
    <form action="/case/save" name="case" method="post">
	<input type="hidden" name="id" value="${(case.id)!''}" /><br>
		<div class="formField clearfix">
            <label for="teamName">项目组: **</label>
            <#--<input type="text" name="projectName" id="projectName" value="${(case.projectName)!''}" size="60" />-->
            <select name="projectId">
            <#list projectList as project>
                <option value="${project.id}"
                    <#if (case.projectId)?? && case.projectId == project.id>
                        selected
                    </#if>
                >${project.projectName}
                </option>
            </#list>
            </select>
        </div>
        <div class="formField clearfix">
            <label for="teamName">测试环境:</label>
            <input type="text" name="testEnv" id="testEnv" value="${(case.testEnv)!''}" size="60" />
        </div>
        <div class="formField clearfix">
            <label for="caseName">用例名称: **</label>
            <input type="text" name="caseName" id="caseName" value="${(case.caseName)!''}" size="60" />
        </div>
        <div class="formField clearfix">
            <label for="caseDesc">  描述:</label>
            <input type="text" name="caseDesc" id="caseDesc" value="${(case.caseDesc)!''}" size="60"/>
        </div>
        <#--<div class="formField clearfix">-->
            <#--<label for="protocolType">协议:</label>-->
            <#--<select id="protocolType" name="protocolType">-->
                <#--&lt;#&ndash;<#list protocolTypeMaps?keys as key>&ndash;&gt;-->
                <#--&lt;#&ndash;　　<option value="${protocolTypeMaps[key]}">${key}</option>&ndash;&gt;-->
                <#--&lt;#&ndash;</#list>&ndash;&gt;-->
                <#--<#list protocolTypeMaps?keys as key>-->
                    <#--<option value="${protocolTypeMaps[key]}"-->
                        <#--<#if (case.protocolType)?? && (case.protocolType) == (protocolTypeMaps[key]) >-->
                            <#--selected-->
                        <#--</#if>-->
                    <#-->${key}-->
                    <#--</option>-->
                <#--</#list>-->
            <#--</select>-->
        <#--</div>-->
        <div class="formField clearfix">
            <label for="reqMethod">请求类型:</label>
            <select id="reqMethod" name="reqMethod">
                <#list reqMethodMaps?keys as key>
                    <option value="${reqMethodMaps[key]}"
                        <#if (case.reqMethod)?? && (case.reqMethod) == (reqMethodMaps[key]) >
                            selected
                        </#if>
                    >${key}
                    </option>
                </#list>
            </select>
        </div>
        <div class="formField clearfix">
            <label for="reqHostUrl">服务器名称/IP: **</label>
            <input type="text" name="reqHostUrl" id="reqHostUrl" value="${(case.reqHostUrl)!''}" size="60"/>
        </div>
        <div class="formField clearfix">
            <label for="reqPath">  子路径:</label>
            <input type="text" name="reqPath" id="reqPath" value="${(case.reqPath)!''}" size="60"/>
        </div>
        <div class="formField clearfix">
            <label for="reqHeader">请求头:</label>
            <input type="text" name="reqHeader" id="reqHeader" value="${(case.reqHeader)!''}" size="60"/>
        </div>
        <div class="formField clearfix">
            <label for="getParams">Get请求参数:</label>
            <textarea cols="62" rows="5" name="getParams" id="getParams">${(case.getParams)!''}</textarea>
        </div>

        <div class="formField clearfix">
            <label for="postType">Post请求格式:</label>
            <select id="postType" name="postType">
                <option selected="selected" disabled="disabled"  style='display: none' value=''></option>
                    <#list postTypeMaps?keys as key>
                    <#--　　<option value="${postTypeMaps[key]}">${key}</option>-->
                        <option value="${postTypeMaps[key]}"
                            <#if (case.postType)?? && (case.postType) == (postTypeMaps[key]) >
                                selected
                            </#if>
                        >${key}
                        </option>
                    </#list>
            </select>
        </div>
        <div class="formField clearfix">
            <label for="postBody">请求体:</label>
            <textarea cols="62" rows="10" name="postBody" id="postBody" value="${(case.postBody)!''}">${(case.postBody)!''}</textarea>
        </div>
        <div class="formField clearfix">
            <label for="suiteIds" style="display:none">所属用例集:</label>
            <input type="text" name="suiteIds" id="suiteIds" value="${(case.suiteIds)!''}" size="60" style="display:none"/>
        </div>
        <div class="formField clearfix">
            <label for="preProcessorId">前置处理:</label>
            <input type="text" name="preProcessorId" id="preProcessorId" value="${(case.preProcessorId)!''}" size="60"/>
        </div>
        <div class="formField clearfix">
            <label for="postProcessorId">后置处理:</label>
            <input type="text" name="postProcessorId" id="postProcessorId" value="${(case.postProcessorId)!''}" size="60"/>
        </div>
        <div class="formField clearfix">
            <label for="assertIds">断言:</label>
            <#--<input type="text" name="assertIds" id="assertIds" value="${(case.assertIds)!''}" size="60"/>-->
            <textarea cols="62" rows="10" name="assertIds" id="assertIds" value="${(case.assertIds)!''}">${(case.assertIds)!''}</textarea>
        </div>
        <div class="formField clearfix">
            <label for="userId" style="display:none">操作者:</label>
            <input type="text" name="userId" id="userId"size="60" style="display:none"/>
        </div>
		 <#--<input type="submit" value="提交"/>-->
        <p class="text-center">
            <button type="button" class="btn btn-default" onclick="history.go(-1)">返 回</button>
            <button type="submit" class="btn btn-primary">提交</button>
        </p>
	</form>
    </div>
</div>
</body>
</html>