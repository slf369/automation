//package com.demo.util;
//
//import org.testng.ITestResult;
//import org.testng.annotations.*;
//
//import java.sql.SQLException;
//import java.util.Iterator;
//import java.util.Map;
//
///**
// * Created by shi.lingfeng on 2018/1/10.
// */
//public class TestPigeon {
//    String sql;
//    int team_id = -1;
//
//    @Parameters({"sql", "team_id"})
//    @BeforeClass()
//    public void beforeClass(String sql, int team_id) {
//        this.sql = sql;
//        this.team_id = team_id;
//        ResultRecorder.cleanInfo();
//    }
//
//    /**
//     * XML中的SQL决定了执行什么用例, 执行多少条用例, SQL的搜索结果为需要测试的测试用例
//     */
//    @DataProvider(name = "testData")
//    private Iterator<Object[]> getData() throws SQLException, ClassNotFoundException {
//        return new DataProvider_forDB(TestConfig.DB_IP, TestConfig.DB_PORT,
//            TestConfig.DB_BASE_NAME,TestConfig.DB_USERNAME, TestConfig.DB_PASSWORD, sql);
//    }
//
//    @Test(dataProvider = "testData")
//    public void test(Map<String, String> data) {
//        new ExecPigeonTest().execTestCase(data, false);
//    }
//
//    @AfterMethod
//    public void afterMethod(ITestResult result, Object[] objs) {...}
//
//    @AfterClass
//    public void consoleLog() {...}
//}
