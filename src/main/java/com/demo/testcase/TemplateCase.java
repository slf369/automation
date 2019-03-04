package com.demo.testcase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.demo.util.*;
import com.demo.util.common.HttpClientUtil;
import com.demo.util.common.JdbcUtils;
import org.testng.annotations.*;
import org.testng.Reporter;

@Listeners({com.demo.util.AssertionListener.class})
public class TemplateCase {

    public static Long num;
    public String responseStr = "";
    public static List<String[]> expectedResultList = new ArrayList<String[]>();
    public static List<String> expectedResultListSimple = new ArrayList<String>();

    public static void setNum(Long number) {
        num = number;
    }

    @BeforeMethod
    public void beforeMethod() {
    }

    @Test
    public void test() throws SQLException {

        HttpClientUtil httpClient = HttpClientUtil.getInstance();

        StringBuffer sb = new StringBuffer();
        JdbcUtils jdbcUtils = JdbcUtils.getInstance();
//        num=51L;
        String sql = "select * from case_info where id= ? ";

        List<Object> params = new ArrayList<Object>();
        params.add(num);
        Map<String, Object> maps = jdbcUtils.findSimpleResult(sql, params);

        String req_host_url = String.valueOf(maps.get("req_host_url")).trim();
        String req_path = String.valueOf(maps.get("req_path")).trim();
        String assert_ids = String.valueOf(maps.get("assert_ids")).trim();
        Integer req_method = Integer.valueOf(String.valueOf(maps.get("req_method")).trim());
        String req_header = String.valueOf(maps.get("req_header")).trim();
        String get_params = String.valueOf(maps.get("get_params")).trim();
        String extractor_name = String.valueOf(maps.get("post_processor_id"));
        String extractor_name_pre = String.valueOf(maps.get("pre_processor_id"));
        Integer post_type = Integer.valueOf(String.valueOf(maps.get("post_type")).trim());
        List<String> extractorList;
        String url = "";

        if (req_host_url.endsWith("/") && req_path.startsWith("/")) {
            url = req_host_url.substring(0, req_host_url.length() - 1) + req_path;
        } else if (!req_host_url.endsWith("/") && !req_path.startsWith("/")) {
            url = req_host_url + "/" + req_path;
        } else {
            url = req_host_url + req_path;
        }

        // 判断GetUrl请求是否参数替换
        if (PreParameter.needReplace(get_params)) {
            get_params = PreParameter.replaceParams(get_params);
        }

        sb.append("============发送的请求是:================\n")
                .append("<font color=\"blue\">")
                .append(url).append("</font>").append("\n");

        String post_body = String.valueOf(maps.get("post_body")).trim();
        // 判断Post请求体是否参数替换
        if (PreParameter.needReplace(post_body)) {
            post_body = PreParameter.replaceParams(post_body);
        }
        sb.append("============发送的报文Post报文体是:================\n")
                .append(post_body).append("\n");

        Map<String, String> header = new HashMap<String, String>();
        ParseCellContent.parseHeader(req_header);
        header = ParseCellContent.getHeadrMap();


        // 处理请求前置动作
        if (ExtractUtils.needExtract(extractor_name_pre)) {
            extractorList = ParseCellContent.parseExtractorCell(extractor_name_pre);
            sb.append("====================================================================\n");
            for (String each_extractor : extractorList) {
                if (each_extractor != null && !each_extractor.equals("")) {
                    String returnInfo = ExtractUtils.extract(responseStr, each_extractor);
                    sb.append("提取的名称：" + each_extractor + ":")
                            .append("实际值为：" + returnInfo).append("\n");
                    ExtractUtils.writeResult(each_extractor, returnInfo);
                }
            }
            sb.append("====================================================================\n\n");
        }

        //发送请求
        try {
            if (req_method == 1) { // post请求
                if (post_type != 3) {  // 不是Form表单类型
                    String jsonBody = post_body;
                    responseStr = httpClient.postJson(url, header, jsonBody);
                } else {
                    String jsonBody = post_body;
//                    responseStr= httpClient.postForm(url, header,jsonBody);
                }
            } else if (req_method == 0) { //get请求
                Map<String, String> pathParam = new HashMap<String, String>();
                String pathParamStr = get_params;
                ParseCellContent.parsePathParam(pathParamStr);
                pathParam = ParseCellContent.getPathParamsMap();
                responseStr = httpClient.getRequest(url, header, pathParam);
            }
            sb.append("====================================================================\n")
                    .append("实际返回报文是:\n" + responseStr + "\n")
                    .append("====================================================================\n\n");
            Reporter.log("====================================================================");
            Reporter.log("实际返回报文是:\n" + responseStr);
            Reporter.log("====================================================================\n\n");

            if (assert_ids.contains("##")) { // 断言使用
                ParseCellContent.parseExpectedResult(assert_ids);
                expectedResultList = ParseCellContent.getExpectedResultList();
                for (int m = 0; m < expectedResultList.size(); m++) {
                    String assertType = ParseCellContent.getExpectedResultSubContent(m, 0);
                    String jsonPathExpress = ParseCellContent.getExpectedResultSubContent(m, 1);
                    String expectedResultDetail = ParseCellContent.getExpectedResultSubContent(m, 2);
                    String actualResultDetail = "";
                    //如果是all,不提取
                    if (jsonPathExpress.trim().equalsIgnoreCase("all")) {
                        actualResultDetail = responseStr;
                    } else {
                        actualResultDetail = JsonPathUtil.parseJsonPath(responseStr, jsonPathExpress, 1);
                    }
                    String curClassName = this.getClass().getSimpleName();

                    // 断言处理
                    if (assertType.equalsIgnoreCase("equal")) {  // 精确匹配
                        if (Assertion.verifyEquals(actualResultDetail, expectedResultDetail, "预期结果跟实际结果比对: ")) {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, true));
                        } else {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, false));
                        }
                    } else if (assertType.equalsIgnoreCase("contain")) { // 模糊匹配
                        if (Assertion.verifyContains(actualResultDetail, expectedResultDetail)) {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, true));
                        } else {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, false));
                        }
                    } else if (assertType.equalsIgnoreCase("regex")) { // 正则表达式匹配
                        if (Assertion.verifyRegex(actualResultDetail, expectedResultDetail)) {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, true));
                        } else {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, false));
                        }
                    }
                }
            } else {  // 每行断言全部包含匹配
                assert_ids = StringUtils.replaceSpace(assert_ids);
                ParseCellContent.parseExpectedResultSimple(assert_ids);
                expectedResultListSimple = ParseCellContent.parseMultipleLineSimple(assert_ids);
                for (int m = 0; m < expectedResultListSimple.size(); m++) {
                    String curClassName = this.getClass().getSimpleName();
                    String expectedResultDetail = ParseCellContent.getExpectedResultSubContentSimple(m);
                    String actualResultDetail = responseStr;
                    // 断言处理-只处理包含类型
                    if (Assertion.verifyContains(actualResultDetail, expectedResultDetail)) {
                        sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, "contain", "无", expectedResultDetail, "已包含该字符串", true));
                    } else {
                        sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, "contain", "无", expectedResultDetail, "未包含该字符串", false));
                    }
                }
            }

            // 处理请求后置动作
            if (ExtractUtils.needExtract(extractor_name)) {
                extractorList = ParseCellContent.parseExtractorCell(extractor_name);
                sb.append("====================================================================\n");
                for (String each_extractor : extractorList) {
                    if (each_extractor != null && !each_extractor.equals("")) {
                        String returnInfo = ExtractUtils.extract(responseStr, each_extractor);
                        sb.append("提取的名称：" + each_extractor + ":")
                                .append("实际值为：" + returnInfo).append("\n");
                        ExtractUtils.writeResult(each_extractor, returnInfo);
                    }
                }
                sb.append("====================================================================\n\n");
            }

            // 写入测试结果到数据库
            String sql2 = "update case_info set assert_result = ? where id = ? ";
            List<Object> params2 = new ArrayList<Object>();
            params2.add(sb.toString());
            params2.add(num);
            boolean flag = jdbcUtils.updateByPreparedStatement(sql2, params2);
            // 打印结果
            System.out.println(sb.toString());
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
    }

    @AfterClass
    public void testDown() {
        expectedResultList.clear();
    }
}