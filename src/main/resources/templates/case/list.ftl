<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <title>用例信息</title>
    <link href="/bootstrap-3.3.7-dist/css/bootstrap.min.css" rel="stylesheet">
    <script type="text/javascript" src="/bootstrap-3.3.7-dist/js/jquery-1.10.1.min.js"></script>
    <script type="text/javascript" src="/bootstrap-3.3.7-dist/js/bootstrap.min.js"></script>
    <script language="javascript">
        function delcfm() {
            if (!confirm("确认要删除？删除不可逆！！！")) {
                window.event.returnValue = false;
            }
        }
    </script>
    <style>
        #wrap{
            display: flex;
        }
    </style>
</head>

<body background="/background.png">
<div class="container">

    <!-- 标题 -->
    <div class="row">
        <div class="col-md-12">
            <h1>用例管理</h1>
            <p class="text-right"><a href="/home" class="btn btn-primary">返回首页</a></p>
        </div>
    </div>

    <!-- 查询部分，给表单定义个name属性，通过js提交 -->
    <div id="wrap">
        <form name="sform" action="/case/search" method="get">
            <div><!-- 如果这里写个value，value值就会显示在页面上，但是我取不出来request作用域里的值，所以查询时，页面上就没有查询的内容了 -->
                用例名搜索：<input type="text" id="casename" name="casename"> <input type="submit" value="查询">
            </div>
        </form>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <form name="sform" action="/case/searchById" method="get">
            <div><!-- 如果这里写个value，value值就会显示在页面上，但是我取不出来request作用域里的值，所以查询时，页面上就没有查询的内容了 -->
                用例ID搜索：<input type="text" id="id" name="id"> <input type="submit" value="查询">
            </div>
        </form>
    </div>
    <br>

    <!-- 按钮 -->
    <#--<div class="row" style="width:16px;height:40px;solid #000;float:left">-->
        <#--<div class="col-md-4 col-md-offset-8">-->
            <#--<a  class="btn btn-primary" href="/case/index">新增</a>-->
        <#--</div>-->
    <#--</div>-->

    <p class="text-left"><a href="/case/index" class="btn btn-primary">新增</a></p>
    
    <!-- 表格  -->
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered table-striped table-hover">
                <tr>
                    <th>id</th>
                    <th style="white-space: nowrap;">项目组</th>
                    <th style="white-space: nowrap;">测试环境</th>
                    <th style="white-space: nowrap;">用例名称</th>
                    <th style="white-space: nowrap;">描述</th>
                    <#--<th style="white-space: nowrap;">协议</th>-->
                    <th style="white-space: nowrap;">请求类型</th>
                    <th style="white-space: nowrap;">服务器名称或IP</th>
                    <th style="white-space: nowrap;">子路径</th>
                    <th style="white-space: nowrap;">请求头</th>
                    <th style="white-space: nowrap;">Get请求参数</th>
                    <th style="white-space: nowrap;">Post请求格式</th>
                    <th style="white-space: nowrap;">请求体</th>
                    <#--<th style="white-space: nowrap;">所属用例集</th>-->
                    <th style="white-space: nowrap;">前置处理</th>
                    <th style="white-space: nowrap;">后置处理</th>
                    <th style="white-space: nowrap;">断言</th>
                    <#--<th style="white-space: nowrap;">操作者</th>-->
                    <#--<th style="white-space: nowrap;">新建时间</th>-->
                    <th style="white-space: nowrap;">修改时间</th>
                    <th>操作</th>
                </tr>
			<#list (pageInfo.content) as case>
                <tr>
                    <td>${case.id}</td>
                    <#--<td style="white-space: nowrap;">${(case.projectId)!''}</td>-->
                    <td style="white-space: nowrap;"
                            <#list projectList as project>
                                <#if (case.projectId)?? && case.projectId == project.id>
                                    <#--赋值-->
                                    <#assign curProjectName=project.projectName />
                                </#if>
                            </#list>
                        >${(curProjectName)!''}</td>

                    <td style="white-space: nowrap;">${(case.testEnv)!''}</td>
                    <td style="white-space: nowrap;">${(case.caseName)!''}</td>
                    <td style="white-space: nowrap;">${(case.caseDesc)!''}</td>
                    <#--<td style="white-space: nowrap;">${(case.protocolType)!'' }</td>-->
                    <td style="white-space: nowrap;">${(case.reqMethod)!'' }</td>
                    <td style="white-space: nowrap;">${(case.reqHostUrl)!'' }</td>
                    <td>${(case.reqPath)!'' }</td>
                    <td>${(case.reqHeader)!'' }</td>
                    <td>${(case.getParams)!'' }</td>
                    <td style="white-space: nowrap;">${(case.postType)!'' }</td>
                    <td>${(case.postBody)!'' }</td>
                    <#--<td style="white-space: nowrap;">${(case.suiteIds)!'' }</td>-->
                    <td style="white-space: nowrap;">${(case.preProcessorId)!'' }</td>
                    <td style="white-space: nowrap;">${(case.postProcessorId)!'' }</td>
                    <td style="white-space: nowrap;">${(case.assertIds)!'' }</td> 
                    <#--<td>${(case.userId)!'' }</td> -->
                    <#--<td style="white-space: nowrap;">${(case.createTime)!'' }</td> -->
                    <td style="white-space: nowrap;">${(case.updateTime)!'' }</td>                  
                    <td>
                        <a type="button"  href="/case/index?id=${case.id}" class="btn btn-info btn-sm">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                            编辑</a>
                        <a type="button"  href="/case/delete?id=${case.id}" class="btn btn-danger btn-sm" onclick="delcfm()">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true" ></span>
                            删除</a>
                        <a type="button"  href="/case/run?id=${case.id}" class="btn btn-info btn-sm"">
                            <span class="glyphicon glyphicon-cog" aria-hidden="true" ></span>
                            执行</a>
                    </td>
                </tr>
			</#list>
            </table>
        </div>
    </div>

    <hr style="height:1px;border:none;border-top:1px solid #ccc;" />
    <!-- 分页导航栏 -->
    <!-- 分页信息 -->
    <div class="row">
        <!-- 分页文字信息，其中分页信息都封装在pageInfo中 -->
        <div class="col-md-6">
            当前第：${currentPage}页，总共：${pageInfo.totalPages}页，总共：${pageInfo.totalElements}条记录
        </div>

        <!-- 分页条 -->
	                <div class="col-md-12 column">
                    <ul class="pagination pull-right">
                    <li><a href="/case/list?page=1&size=${size}">首页</a></li>
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/case/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..pageInfo.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/case/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte pageInfo.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/case/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                    <li><a href="/case/list?page=${pageInfo.totalPages}&size=${size}">末页</a></li>
                    </ul>
                </div>
    </div>
</body>
</html>