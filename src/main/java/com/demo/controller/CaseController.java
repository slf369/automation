package com.demo.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.demo.entity.ProjectInfo;
import com.demo.enums.PostTypeEnum;
import com.demo.enums.ProtocolTypeEnum;
import com.demo.enums.ReqMethodEnum;
import com.demo.service.ProjectService;
import com.demo.testcase.TemplateCase;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import com.demo.entity.CaseInfo;
import com.demo.service.CaseService;
import org.testng.TestNG;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@Controller
@RequestMapping("/case")
public class CaseController {

    @Autowired
    CaseService caseService;

    @Autowired
    ProjectService projectService;

    /**
     * 列表显示
     *
     * @param map
     * @return
     */
    @RequestMapping(value = "/list")
    public String list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Map<String, Object> map) {

        List<ProjectInfo> projectList = projectService.findAll();
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        PageRequest request = new PageRequest(page - 1, size,sort);
        Page<CaseInfo> pageInfo = caseService.findAll(request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        map.put("projectList", projectList);
        return "/case/list";
    }


    /**
     * 根据用例名称查询
     */
    @RequestMapping("/search")
    public String getSelect(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            @RequestParam(value = "casename") String casename,
                            ModelMap map) {
        PageRequest request = new PageRequest(page - 1, size);
        Page<CaseInfo> pageInfo = caseService.findSearch(casename, request);
        List<ProjectInfo> projectList = projectService.findAll();
        map.put("projectList", projectList);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/case/list";
    }


    /**
     * 根据ID查询
     */
    @RequestMapping("/searchById")
    public String getSelectById(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            @RequestParam(value = "id") String id,
                            ModelMap map) {
        PageRequest request = new PageRequest(page - 1, size);
        Long longId;
        try{
            longId=Long.parseLong(id);
        }catch (Exception e){
            longId=0L;
        }
        Page<CaseInfo> pageInfo = caseService.findSearchById(longId, request);
        List<ProjectInfo> projectList = projectService.findAll();
        map.put("projectList", projectList);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/case/list";
    }

    /**
     * 新增/修改-编辑
     *
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (id != null) {
            CaseInfo caseInfo = caseService.findOne(id);
            model.addAttribute("case", caseInfo);
        }

        List<ProjectInfo> projectList = projectService.findAll();
        model.addAttribute("projectList", projectList);

        Map<String, Object> postTypeMap = new HashMap<String, Object>();
        Map<String, Object> protocolTypeMap = new HashMap<String, Object>();
        Map<String, Object> reqMethodMap = new HashMap<String, Object>();
        for (PostTypeEnum type : PostTypeEnum.values()) {
            postTypeMap.put(type.getMessage(), type.getCode());
        }
        for (ProtocolTypeEnum type : ProtocolTypeEnum.values()) {
            protocolTypeMap.put(type.getMessage(), type.getCode());
        }
        for (ReqMethodEnum type : ReqMethodEnum.values()) {
            reqMethodMap.put(type.getMessage(), type.getCode());
        }
        model.addAttribute("postTypeMaps", postTypeMap);
        model.addAttribute("protocolTypeMaps", protocolTypeMap);
        model.addAttribute("reqMethodMaps", reqMethodMap);

        return "case/index";
    }


    /**
     * 新增/修改
     *
     * @param caseInfo
     * @param bindingResult
     * @param map
     * @return
     */
    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String save(@Valid CaseInfo caseInfo,
                       BindingResult bindingResult,
                       Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/case/index");
            return "common/error";
        }
        CaseInfo caseInfoDes = new CaseInfo();
        try {
            if (caseInfo.getId() != null) {
                caseInfoDes = caseService.findOne(caseInfo.getId());
            }
            BeanUtils.copyProperties(caseInfo, caseInfoDes);
            caseService.save(caseInfoDes);
            System.out.println("当期的ID是:" + caseInfoDes.getId());
//			GenerateCase.generateCase(caseInfoDes.getCaseName(),String.valueOf(caseInfoDes.getId()));
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/case/index");
            return "common/error";
        }
        map.put("url", "/case/list");
        return "common/success";
    }


    /**
     * 删除
     *
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id, ModelMap map) {
        try {
            caseService.delete(id);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/case/list");
            return "common/error";
        }
        map.put("url", "/case/list");
        return "common/success";
    }

    /**
     * 运行Case
     *
     * @param id
     * @param map
     * @return
     * @throws IOException
     */
    @RequestMapping("/run")
    public String executeCase(@RequestParam(value = "id") Long id, ModelMap map) throws IOException {
        CaseInfo caseInfo = caseService.findOne(id);
        System.out.println("run:当前的ID是：" + id);
        runCase(caseInfo.getCaseName().trim(), id);
        return "redirect:/case/result?id=" + id;
    }

    public void runCase(String className, Long id) {
        System.out.println("runCase:当前的ID是：" + id);
        TemplateCase.setNum(id);
        className = "TemplateCase";
        String str = "com.demo.testcase." + className;
        TestNG testNG = new TestNG();
        try {
            testNG.setTestClasses(new Class[]{Class.forName(str)});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        testNG.run();
    }

//	public String runCasePostJson(CaseInfo caseInfo) throws IOException {
//		String url=caseInfo.getReqHostUrl()+caseInfo.getReqPath();
//		String postBody=caseInfo.getPostBody();
//		Map<String,String> header=new HashMap<String,String>();
//		String str= HttpClientUtil.postMethodWithRawBody(url,header,postBody);
//		System.out.println(str);
//		return str;
//	}


    @RequestMapping(value = "/result", method = RequestMethod.GET)
    public void writeStream(@RequestParam(value = "id") Long id, HttpServletResponse response) throws IOException, InterruptedException {
        response.setContentType("text/html;charset=utf-8");
        CaseInfo caseInfo = caseService.findOne(id);
        String info = "";
        if (caseInfo.getAssertResult() != null && !caseInfo.equals("")) {
            if (caseInfo.getAssertResult().contains("\n"))
                info = caseInfo.getAssertResult().replace("\n", "<br/>");
        }
        String resultInfo = "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "<head>\n" +
                "    <meta charset=\"UTF-8\">\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1\">\n" +
                "    <title>用例执行结果</title>\n" +
                "    <link href=\"https://cdn.bootcss.com/bootstrap/3.3.5/css/bootstrap.min.css\" rel=\"stylesheet\">\n" +
                "</head>\n" +
                "<body>\n" +
                "<div class=\"container\">\n" +
                "    <div class=\"row clearfix\">\n" +
                "        <div class=\"col-md-12 column\">\n" +
                "            <div class=\"jumbotron\">\n" +
                "                <h2>\n" +
                "                    以下是执行结果!\n" +
                "                </h2>\n" +
                "                <p>" + info
                + "</p>\n" +
                "                <p>\n" +
                "                    <button type=\"button\" class=\"btn btn-default\" onclick=\"history.go(-1)\">返 回</button>\n" +
                "                    <!--<a class=\"btn btn-primary btn-large\" href=\"#\">Learn more</a>-->\n" +
                "                </p>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "    </div>\n" +
                "</div>\n" +
                "</body>\n" +
                "</html>";
        write(response, resultInfo);
        response.getWriter().close();
    }

    private void write(HttpServletResponse response, String content) throws IOException {
        response.getWriter().write(content + "<br/>");
        response.flushBuffer();
        response.getWriter().flush();
    }
}
