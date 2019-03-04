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
	<h1>新增/编辑项目组</h1>
    <form action="/project/save" name="project" class="form" method="post">
	<input type="hidden" name="id" value="${(project.id)!''}" /><br>
		<div class="formField clearfix">
            <label for="projectName">项目组名: **</label>
            <input type="text" name="projectName" id="projectName" value="${(project.projectName)!''}" size="60" />
        </div>
        <div class="formField clearfix">
            <label for="projectDesc">描述: </label>
            <input type="text" name="projectDesc" id="projectDesc" value="${(project.projectDesc)!''}" size="60" />
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