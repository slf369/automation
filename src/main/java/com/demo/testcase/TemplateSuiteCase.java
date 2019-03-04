package com.demo.testcase;

import com.demo.util.*;
import com.demo.util.common.HttpClientUtil;
import com.demo.util.common.JdbcUtils;
import org.testng.ITestContext;
import org.testng.Reporter;
import org.testng.annotations.*;

import java.sql.SQLException;
import java.util.*;

/**
 * Created by shi.lingfeng on 2018/3/7.
 */
@Listeners({com.demo.util.AssertionListener.class})
public class TemplateSuiteCase {

    public static String case_ids;
    public static StringBuffer sb1 = new StringBuffer();
    public static Long num;
    public static String responseStr = "";
    public static List<String[]> expectedResultList = new ArrayList<String[]>();
    public static List<String> expectedResultListSimple = new ArrayList<String>();
    public static List<String> passList = new ArrayList<String>();
    public static List<String> failList = new ArrayList<String>();
    public static List<String> ignoreList = new ArrayList<String>();

    JdbcUtils jdbcUtils = JdbcUtils.getInstance();

    public static void setCaseIds(String caseIds) {
        case_ids = caseIds;
    }

    public static void setNum(Long num1) {
        num = num1;
    }

    public static void init() {
        sb1.delete(0, sb1.length()); //每次请求前，清空Buffer,防止多次测试集结果累加
        passList.clear();
        failList.clear();
        ignoreList.clear();
    }

    @BeforeClass
    public void beforeMethod() throws SQLException {
        String sql2 = "update suite_info set suite_result = '' where id = ? ";
        List<Object> params2 = new ArrayList<Object>();
        params2.add(num);
        jdbcUtils.updateByPreparedStatement(sql2, params2);
        ;
    }

    @DataProvider(name = "CaseData")
    public Iterator<Object[]> getTestData(ITestContext context) {
//        case_ids="52,53";
//        num=2L;
        List<Object[]> test_list = new ArrayList<Object[]>();
        List<String> caseList = ParseCellContent.parseSingleCellWithComma(case_ids);
        StringBuffer sb = new StringBuffer();
        for (String str : caseList) {
            sb.append("'").append(str).append("'").append(",");
        }
        String bufferStr = sb.toString();
        String buf = bufferStr.substring(0, bufferStr.length() - 1);
        String sql = "select id, project_id, test_env, case_name, req_method, req_host_url, req_path, req_header, get_params, post_type, post_body, suite_ids, pre_processor_id, post_processor_id, assert_ids from case_info where id in (" + buf + ")";

        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        try {
            list = jdbcUtils.findMoreResult(sql, null);
            // 按照Case的排列顺序，执行Case
            for (int i = 0; i < caseList.size(); i++) {
                String currentID = caseList.get(i).trim();
                for (Map<String, Object> map : list) {
                    String id = String.valueOf(map.get("id")).trim();
                    if (id.equalsIgnoreCase(currentID)) {
                        id = String.valueOf(map.get("id"));
                        String project_id = String.valueOf(map.get("project_id"));
                        String test_env = String.valueOf(map.get("test_env"));
                        String case_name = String.valueOf(map.get("case_name"));
                        String req_method = String.valueOf(map.get("req_method"));
                        String req_host_url = String.valueOf(map.get("req_host_url"));
                        String req_path = String.valueOf(map.get("req_path"));
                        String req_header = String.valueOf(map.get("req_header"));
                        String get_params = String.valueOf(map.get("get_params"));
                        String post_type = String.valueOf(map.get("post_type"));
                        String post_body = String.valueOf(map.get("post_body"));
                        String suite_ids = String.valueOf(map.get("suite_ids"));
                        String pre_processor_id = String.valueOf(map.get("pre_processor_id"));
                        String post_processor_id = String.valueOf(map.get("post_processor_id"));
                        String assert_ids = String.valueOf(map.get("assert_ids"));
                        test_list.add(new Object[]{id, project_id, test_env, case_name, req_method, req_host_url, req_path, req_header, get_params, post_type, post_body, suite_ids, pre_processor_id, post_processor_id, assert_ids});
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return test_list.iterator();
    }

    @Test(dataProvider = "CaseData")
    public void api_test(String id, String project_id, String test_env, String case_name, String req_method,
                         String req_host_url, String req_path, String req_header, String get_params,
                         String post_type, String post_body, String suite_ids, String pre_processor_id, String post_processor_id, String assert_ids) throws Exception {
//        System.out.println(id + "  " + project_id+ "  " +  test_env+ "  " +  case_name+ "  " +  req_method+ "  " +  req_host_url+ "  " +  req_path+ "  " +  req_header+ "  " +  get_params+ "  " +  post_type+ "  " +  post_body+ "  " +  suite_ids+ "  " +  pre_processor_id+ "  " +  post_processor_id+ "  " +  assert_ids);
        HttpClientUtil httpClient = HttpClientUtil.getInstance();
        StringBuffer sb = new StringBuffer();

        // 判断GetUrl请求是否参数替换
        if (PreParameter.needReplace(get_params)) {
            get_params = PreParameter.replaceParams(get_params);
        }

        String url = "";
        if (req_host_url.endsWith("/") && req_path.startsWith("/")) {
            url = req_host_url.substring(0, req_host_url.length() - 1) + req_path;
        } else if (!req_host_url.endsWith("/") && !req_path.startsWith("/")) {
            url = req_host_url + "/" + req_path;
        } else {
            url = req_host_url + req_path;
        }

        sb.append("============当前执行的用例是:================\n")
                .append("<font color=\"blue\">")
                .append("CaseID:").append(id).append("&nbsp;&nbsp;&nbsp;")
                .append("CaseName:").append(case_name).append("</font>").append("\n");

        sb.append("============发送的请求是:================\n")
                .append(url)
                .append("\n");

        // 判断Post请求体是否参数替换
        if (PreParameter.needReplace(post_body)) {
            post_body = PreParameter.replaceParams(post_body);
        }
        sb.append("============发送的报文Post报文体是:================\n")
                .append(post_body).append("\n");

        String extractor_name = post_processor_id;
        String extractor_name_pre = pre_processor_id;

        Integer postType = 0;
        if (post_type != null && post_type.equals("")) {
            postType = Integer.valueOf(String.valueOf(post_type).trim());
        }
        Integer reqMethod = Integer.valueOf(String.valueOf(req_method).trim());
        List<String> extractorList;

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

        try {
            if (reqMethod == 1) { // post请求
                if (postType != 3) { // 不是Form表单类型
                    String jsonBody = post_body;
                    responseStr = httpClient.postJson(url, header, jsonBody);
                } else {
                    String jsonBody = post_body;
//                    responseStr= httpClient.postForm(url, header,jsonBody);
                }
            } else if (reqMethod == 0) { //get请求
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

            int assertCount = 0; //断言累计器
            int sum1 = 0; // expectedResultList的数量
            int sum2 = 0; // expectedResultListSimple的数量

            if (assert_ids.contains("##")) { // 断言使用
                ParseCellContent.parseExpectedResult(assert_ids);
                expectedResultList = ParseCellContent.getExpectedResultList();
                sum1 = expectedResultList.size();
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
                    if (assertType.equalsIgnoreCase("equal")) { // 精确匹配
                        if (Assertion.verifyEquals(actualResultDetail, expectedResultDetail, "预期结果跟实际结果比对: ")) {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, true));
                            assertCount++;
                        } else {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, false));
                        }
                    } else if (assertType.equalsIgnoreCase("contain")) { // 模糊匹配
                        if (Assertion.verifyContains(actualResultDetail, expectedResultDetail)) {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, true));
                            assertCount++;
                        } else {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, false));
                        }
                    } else if (assertType.equalsIgnoreCase("regex")) { // 正则表达式匹配
                        if (Assertion.verifyRegex(actualResultDetail, expectedResultDetail)) {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, true));
                            assertCount++;
                        } else {
                            sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, assertType, jsonPathExpress, expectedResultDetail, actualResultDetail, false));
                        }
                    }
                }
            } else { // 每行断言全部包含匹配
                assert_ids = StringUtils.replaceSpace(assert_ids);
                ParseCellContent.parseExpectedResultSimple(assert_ids);
                expectedResultListSimple = ParseCellContent.parseMultipleLineSimple(assert_ids);
                sum2 = expectedResultListSimple.size();
                for (int m = 0; m < expectedResultListSimple.size(); m++) {
                    String curClassName = this.getClass().getSimpleName();
                    String expectedResultDetail = ParseCellContent.getExpectedResultSubContentSimple(m);
                    String actualResultDetail = responseStr;
                    // 断言处理-只处理包含类型
                    if (Assertion.verifyContains(actualResultDetail, expectedResultDetail)) {
                        sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, "contain", "无", expectedResultDetail, "已包含该字符串", true));
                        assertCount++;
                    } else {
                        sb.append(ShowMessage.showAssertMessage(m + 1, curClassName, "contain", "无", expectedResultDetail, "未包含该字符串", false));
                    }
                }
            }

            // 处理请求后置动作
            if (ExtractUtils.needExtract(extractor_name)) {
                extractorList = ParseCellContent.parseExtractorCell(extractor_name);
                sb.append("===================================================================================\n");
                for (String each_extractor : extractorList) {
                    if (each_extractor != null && !each_extractor.equals("")) {
                        String returnInfo = ExtractUtils.extract(responseStr, each_extractor);
                        sb.append("提取的名称：" + each_extractor + ":")
                                .append("实际值为：" + returnInfo).append("\n");
                        ExtractUtils.writeResult(each_extractor, returnInfo);
                    }
                }
                sb.append("===================================================================================\n\n");
            }


            // 每个Case写入数据库
//            String sql2= "update case_info set assert_result = ? where id = ? ";
//            List<Object> params2 = new ArrayList<Object>();
//            params2.add(sb.toString());
//            params2.add(id);
//            boolean flag = jdbcUtils.updateByPreparedStatement(sql2, params2);
//            System.out.println(sb.toString());

            sb1.append(sb.toString());

            // 判断当前Case的通过情况
            if (assertCount != 0) {
                if ((assertCount == sum1 || assertCount == sum2)) {  // 全部断言通过
                    passList.add(id); //当前Case为Pass
                } else {
                    failList.add(id);  // 当前Case为Fail
                }
            } else {
                failList.add(id); // 当前Case为Fail
            }

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
    public void testDown() throws SQLException {
        // 写入执行结果入库
        String sql2 = "update suite_info set suite_result = ? where id = ? ";
        List<Object> params2 = new ArrayList<Object>();
        params2.add(sb1.toString());
        params2.add(num);
        jdbcUtils.updateByPreparedStatement(sql2, params2);

        StringBuffer pass = new StringBuffer();
        StringBuffer fail = new StringBuffer();
        String passStr = "";
        String failStr = "";

        // 写入通过/失败的用例结果入库
        if (passList.size() > 0) {
            for (String str : passList) {
                pass.append(str).append(",");
            }
        }
        if (failList.size() > 0) {
            for (String str : failList) {
                fail.append(str).append(",");
            }
        }
        passStr = pass.toString();
        failStr = fail.toString();

        if (passStr.endsWith(",")) {
            passStr = passStr.substring(0, passStr.length() - 1);
        }
        if (failStr.endsWith(",")) {
            failStr = failStr.substring(0, failStr.length() - 1);
        }

        String sql3 = "update suite_info set case_pass_ids = ? where id = ? ";
        List<Object> params3 = new ArrayList<Object>();
        params3.add(passStr);
        params3.add(num);
        jdbcUtils.updateByPreparedStatement(sql3, params3);


        String sql5 = "update suite_info set case_fail_ids = ? where id = ? ";
        List<Object> params5 = new ArrayList<Object>();
        params5.add(failStr);
        params5.add(num);
        jdbcUtils.updateByPreparedStatement(sql5, params5);

//        System.out.println(sb1.toString());
        System.out.println(passList);
        System.out.println(failList);
    }
}
