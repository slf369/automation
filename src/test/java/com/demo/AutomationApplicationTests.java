//package com.demo;
//
//import com.demo.entity.CaseInfo;
//import com.demo.entity.ParamInfo;
//import com.demo.entity.ProjectInfo;
//import com.demo.enums.PostTypeEnum;
//import com.demo.enums.ProtocolTypeEnum;
//import com.demo.enums.ReqMethodEnum;
//import com.demo.service.CaseService;
//import java.util.List;
//
//import com.demo.service.ParamService;
//import com.demo.service.ProjectService;
//import org.junit.Test;
//import org.junit.runner.RunWith;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.context.SpringBootTest;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.PageRequest;
//import org.springframework.test.context.junit4.SpringRunner;
//
//@RunWith(SpringRunner.class)
//@SpringBootTest
//public class AutomationApplicationTests {
//
//	@Autowired
//	CaseService caseService;
//
//	@Autowired
//	ParamService paramService;
//
//	@Autowired
//	ProjectService projectService;
//
//
//	//@Test
//	public void testSearch1() {
//		String str="info";
//		PageRequest request = new PageRequest(1, 5);
//		Page<ParamInfo> pageInfo = paramService.findSearch(str,request);
//		System.out.println("AAAAAAA "+pageInfo.toString());
//		Logger logger= LoggerFactory.getLogger(AutomationApplicationTests.class);
//		logger.info("This is logcat info");
//	}
//
//	//@Test
//	public void testFindAll() {
//		List<CaseInfo> caseInfo = caseService.findAll();
//		if(caseInfo==null || caseInfo.size()==0){
//			System.out.println("is null");
//		}else{
//			System.out.println("查询到的条数是"+caseInfo.size());
//		}
//	}
//
//	//@Test
//	public void testFindOne() {
//		Long i=1L;
//		CaseInfo users = caseService.findOne(i);
//		System.out.print(users.getAssertIds());
//	}
//
//
//	//@Test
//	public void testSave() {
//		CaseInfo case1=new CaseInfo();
//		case1.setId(22L);
//		case1.setAssertIds("a1,b2,c3");
//		case1.setCaseDesc("用例描述131");
//		case1.setCaseName("用例131");
//		case1.setGetParams("a=1&b=2");
//		case1.setPostBody("{\n" +
//				"    \"FUNCODE\": \"CHECK\",\n" +
//				"    \"RPID\": \"R430000555962DPU\",\n" +
//				"    \"IP\": \"1.0.0.7\",\n" +
//				"    \"MOBILEID\": \"15000551400\",\n" +
//				"    \"REQDATE\": \"20171205\",\n" +
//				"    \"REQTIME\": \"141900\"\n" +
//				"}\n" +
//				"");
//		case1.setPostProcessorId("post111");
//		case1.setPostType(PostTypeEnum.JSON.getCode());
//		case1.setPreProcessorId("preadq");
//		case1.setProtocolType(ProtocolTypeEnum.HTTP.getCode());
//		case1.setReqHeader("json=heaer:/hhehwe");
//		case1.setReqHostUrl("http://www.baidu.com");
//		case1.setReqMethod(ReqMethodEnum.POST.getCode());
//		case1.setReqPath("/aad/aaa");
//		case1.setSuiteIds("[suit1,suit2]");
//		case1.setProjectName("项目130");
//		caseService.save(case1);
//	}
//
//
//	//@Test
//	public void testUpdate() {
//		CaseInfo case1=new CaseInfo();
//		case1.setId(5L);
//		case1.setAssertIds("assertIds");
//		case1.setCaseDesc("用例描述5");
//		case1.setCaseName("用例5");
//		case1.setGetParams("a=1&b=2");
//		case1.setPostBody("{postbody111121 12w123 12w12}");
//		case1.setPostProcessorId("post111");
//		case1.setPostType(PostTypeEnum.JSON.getCode());
//		case1.setPreProcessorId("preadq");
//		case1.setProtocolType(ProtocolTypeEnum.HTTP.getCode());
//		case1.setReqHeader("json=heaer:/hhehwe");
//		case1.setReqHostUrl("http://www.baidu.com");
//		case1.setReqMethod(ReqMethodEnum.POST.getCode());
//		case1.setReqPath("/aad/aaa");
//		case1.setSuiteIds("[suit1,suit2]");
//		case1.setProjectName("观点5");
//		caseService.save(case1);
//	}
//
//	//@Test
//	public void testSearch() {
//		PageRequest request = new PageRequest(1, 5);
//		Page<CaseInfo> pageInfo = caseService.findSearch("FengKong",request);
//		System.out.println("AAAAAAA "+pageInfo.toString());
//	}
//
//	//@Test
//	public void testDelete(){
//		caseService.delete(26L);
//	}
//
//
//	@Test
//	public void testFindAllProjectName() {
//		List<ProjectInfo> projectInfos = projectService.findAll();
//		if(projectInfos==null || projectInfos.size()==0){
//			System.out.println("is null");
//		}else{
//			System.out.println("查询到的条数是"+projectInfos.size());
//			System.out.println(projectInfos);
//		}
//	}
//}
