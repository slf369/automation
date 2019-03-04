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
<div class="container" style="max-width: 690px;margin-top: 60px;">
    <div class="well">
	<h1>新增/编辑前-后置</h1>
    <form action="/extract/save" name="extract" class="form" method="post">
	<input type="hidden" name="id" value="${(extract.id)!''}" /><br>
		<div class="formField clearfix">
            <label for="extractName">提取器名: **</label>
            <input type="text" name="extractName" id="extractName" value="${(extract.extractName)!''}" size="60" />
        </div>
        <div class="formField clearfix">
            <label for="extractType">类型:</label>
            <select id="extractType" name="extractType">
            <#list extractTypeeMaps?keys as key>
                <option value="${extractTypeeMaps[key]}"
                    <#if (extract.extractType)?? && (extract.extractType) == (extractTypeeMaps[key]) >
                        selected
                    </#if>
                >${key}
                </option>
            </#list>
            </select>
        </div>
        <div class="formField clearfix">
            <label for="jsonpathExpress">Jsonpath表达式:</label>
            <input type="text" name="jsonpathExpress" id="jsonpathExpress" value="${(extract.jsonpathExpress)!''}" size="60"/>
        </div>
        <#--<div class="formField clearfix">-->
            <#--<label for="regexExpress">正则表达式:</label>-->
            <#--<input type="text" name="regexExpress" id="regexExpress" value="${(extract.regexExpress)!''}" size="60"/>-->
        <#--</div>-->
        <div class="formField clearfix">
            <label for="leftExpress">  前缀:</label>
            <input type="text" name="leftExpress" id="leftExpress" value="${(extract.leftExpress)!''}" size="60"/>
        </div>
        <div class="formField clearfix">
            <label for="rightExpress">后缀:(结合前缀使用)</label>
            <input type="text" name="rightExpress" id="rightExpress" value="${(extract.rightExpress)!''}" size="60"/>
        </div>

        <div class="formField clearfix">
            <label for="databaseUrl">数据库URL:</label>
            <input type="text" name="databaseUrl" id="databaseUrl" value="${(extract.databaseUrl)!''}" size="60"/>
        </div>
        <div class="formField clearfix">
            <label for="databaseUsername">数据库用户名:</label>
            <input type="text" name="databaseUsername" id="databaseUsername" value="${(extract.databaseUsername)!''}" size="60"/>
        </div>
        <div class="formField clearfix">
            <label for="databasePassword">数据库密码:</label>
            <input type="text" name="databasePassword" id="databasePassword" value="${(extract.databasePassword)!''}" size="60"/>
        </div>

        <div class="formField clearfix">
            <label for="sqlExpress">SQL表达式:</label>
            <textarea cols="62" rows="5" name="sqlExpress" id="sqlExpress">${(extract.sqlExpress)!''}</textarea>
        </div>
        <div class="formField clearfix">
            <label for="extractValue">提取值:</label>
            <textarea cols="62" rows="5" name="extractValue" id="extractValue">${(extract.extractValue)!''}</textarea>
        </div>
        <p class="text-center">
            <button type="button" class="btn btn-default" onclick="history.go(-1)">返 回</button>
            <button type="submit" class="btn btn-primary">提交</button>
        </p>
	</form>
    </div>
</div>
</body>
</html>