<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <title>用例集信息</title>
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
            <h1 class="text-left"><strong>用例集管理</strong></h1>
            <p class="text-right"><a href="/home" class="btn btn-primary">返回首页</a></p>
        </div>
    </div>

    <div id="wrap">
        <!-- 查询部分，给表单定义个name属性，通过js提交 -->
        <form name="sform" action="/suite/search" method="get">
            <div><!-- 如果这里写个value，value值就会显示在页面上，但是我取不出来request作用域里的值，所以查询时，页面上就没有查询的内容了 -->
                用例集名搜索：<input type="text" id="suitename" name="suitename"> <input type="submit" value="查询">
            </div>
        </form>
        </form>
        &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        <form name="sform" action="/suite/searchById" method="get">
            <div><!-- 如果这里写个value，value值就会显示在页面上，但是我取不出来request作用域里的值，所以查询时，页面上就没有查询的内容了 -->
                用例集ID搜索：<input type="text" id="id" name="id"> <input type="submit" value="查询">
            </div>
        </form>
    </div>
    <br>

    <!-- 按钮 -->
    <#--<div class="text-left">-->
            <#--<a  class="btn btn-primary" href="/suite/index">新增</a>-->
    <#--</div>-->

    <p class="text-left"><a href="/suite/index" class="btn btn-primary">新增</a></p>
    
    <!-- 表格  -->
    <div class="row clearfix">
        <div class="col-md-12 column">
            <table class="table table-bordered table-striped table-hover">
                <tr>
                    <th>id</th>
                    <th style="white-space: nowrap;">用例集名</th>
                    <th style="white-space: nowrap;">用例</th>
                    <th style="white-space: nowrap;">描述</th>
                    <th style="white-space: nowrap;">通过的用例ID</th>
                    <th style="white-space: nowrap;">忽略的用例ID</th>
                    <th style="white-space: nowrap;">失败的用例ID</th>
                    <#--<th style="white-space: nowrap;">通过的用例结果</th>-->
                    <#--<th style="white-space: nowrap;">忽略的用例结果</th>-->
                    <#--<th style="white-space: nowrap;">失败的用例结果</th>-->
                    <#--<th style="white-space: nowrap;">用例集结果</th>-->
                    <#--<th style="white-space: nowrap;">最后修改时间</th>-->
                    <th>操作</th>
                </tr>

			<#list (pageInfo.content) as suite>
                <tr>
                    <td>${suite.id}</td>
                    <td style="white-space: nowrap;">${(suite.suiteName)!''}</td>
                    <td style="white-space: nowrap;">${(suite.caseIds)!''}</td>
                    <td style="white-space: nowrap;">${(suite.suiteDesc)!''}</td>
                    <td style="white-space: nowrap;">${(suite.casePassIds)!''}</td>
                    <td style="white-space: nowrap;">${(suite.caseSkipIds)!''}</td>
                    <td style="white-space: nowrap;">${(suite.caseFailIds)!''}</td>
                    <#--<td style="white-space: nowrap;">${(suite.casePassDesc)!''}</td>-->
                    <#--<td style="white-space: nowrap;">${(suite.caseSkipDesc)!''}</td>-->
                    <#--<td style="white-space: nowrap;">${(suite.caseFailDesc)!''}</td>-->
                    <#--<td style="white-space: nowrap;">${(suite.suiteResult)!''}</td>-->
                    <#--<td style="white-space: nowrap;">${(suite.updateTime)!'' }</td>-->
                    <td>
                        <a type="button"  href="/suite/index?id=${suite.id}" class="btn btn-info btn-sm">
                            <span class="glyphicon glyphicon-pencil" aria-hidden="true"></span>
                            编辑</a>
                        <a type="button"  href="/suite/delete?id=${suite.id}" class="btn btn-danger btn-sm" onclick="delcfm()">
                            <span class="glyphicon glyphicon-trash" aria-hidden="true" ></span>
                            删除</a>
                        <a type="button"  href="/suite/run?id=${suite.id}" class="btn btn-info btn-sm"">
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
                    <li><a href="/suite/list?page=1&size=${size}">首页</a></li>
                    <#if currentPage lte 1>
                        <li class="disabled"><a href="#">上一页</a></li>
                    <#else>
                        <li><a href="/suite/list?page=${currentPage - 1}&size=${size}">上一页</a></li>
                    </#if>

                    <#list 1..pageInfo.getTotalPages() as index>
                        <#if currentPage == index>
                            <li class="disabled"><a href="#">${index}</a></li>
                        <#else>
                            <li><a href="/suite/list?page=${index}&size=${size}">${index}</a></li>
                        </#if>
                    </#list>

                    <#if currentPage gte pageInfo.getTotalPages()>
                        <li class="disabled"><a href="#">下一页</a></li>
                    <#else>
                        <li><a href="/suite/list?page=${currentPage + 1}&size=${size}">下一页</a></li>
                    </#if>
                    <li><a href="/suite/list?page=${pageInfo.totalPages}&size=${size}">末页</a></li>
                    </ul>
                </div>
    </div>
</body>
</html>