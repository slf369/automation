package com.demo.controller;

import com.demo.entity.SuiteInfo;
import com.demo.service.SuiteService;
import com.demo.testcase.TemplateSuiteCase;
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
import org.testng.TestNG;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.Map;

/**
 * Created by shi.lingfeng on 2018/3/2.
 */
@Controller
@RequestMapping("/suite")
public class SuiteController {

    @Autowired
    SuiteService suiteService;

    /**
     * 列表显示
     * @suite map
     * @return
     */
    @RequestMapping(value="/list")
    public String list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Map<String, Object> map){

        Sort sort=new Sort(Sort.Direction.DESC,"id");
        PageRequest request = new PageRequest(page - 1, size,sort);
        Page<SuiteInfo> pageInfo = suiteService.findAll(request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/suite/list";
    }


    /**
     * 根据提取名查询
     */
    @RequestMapping("/search")
    public String getSelect(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            @RequestParam(value="suitename") String suitename,
                            ModelMap map){
        PageRequest request = new PageRequest(page - 1, size);
        Page<SuiteInfo> pageInfo = suiteService.findSearch(suitename,request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/suite/list";
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
        Page<SuiteInfo> pageInfo = suiteService.findSearchById(longId, request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/suite/list";
    }

    /**
     * 新增/修改-编辑
     * @suite id
     * @suite model
     * @return
     */
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (id != null) {
            SuiteInfo suiteInfo = suiteService.findOne(id);
            model.addAttribute("suite", suiteInfo);
        }
        return "suite/index";
    }


    /**
     * 新增/修改
     * @suite suiteInfo
     * @suite bindingResult
     * @suite map
     * @return
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
    public String save(@Valid SuiteInfo suiteInfo,
                       BindingResult bindingResult,
                       Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/suite/index");
            return "common/error";
        }
        SuiteInfo suiteInfoDes= new SuiteInfo();
        try {
            if (suiteInfo.getId() != null) {
                suiteInfoDes = suiteService.findOne(suiteInfo.getId());
            }
            BeanUtils.copyProperties(suiteInfo, suiteInfoDes);
            suiteService.save(suiteInfoDes);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/suite/index");
            return "common/error";
        }
        map.put("url", "/suite/list");
        return "common/success";
    }

    /**
     * 删除
     * @suite id
     * @suite map
     * @return
     */
    @RequestMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id, ModelMap map){
        try {
            suiteService.delete(id);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/suite/list");
            return "common/error";
        }
        map.put("url", "/suite/list");
        return "common/success";
    }


    /**
     * 运行Case
     * @param id
     * @param map
     * @return
     * @throws IOException
     */
    @RequestMapping("/run")
    public String  executeCase(@RequestParam(value = "id") Long id,ModelMap map) throws IOException {
        runSuiteCase(id);
        return "redirect:/suite/result?id="+id;
    }

    public  void runSuiteCase(Long id){
        SuiteInfo suiteInfo=suiteService.findOne(id);
        System.out.println("run:当前的ID是："+id);
        TemplateSuiteCase.setCaseIds(suiteInfo.getCaseIds());
        TemplateSuiteCase.setNum(id);
        TemplateSuiteCase.init();

//        List<String> caseList= ParseCellContent.parseSingleCellWithComma(suiteInfo.getCaseIds());
        String str="com.demo.testcase.TemplateSuiteCase";
        TestNG testNG=new TestNG();
        try {
            testNG.setTestClasses(new Class[]{Class.forName(str)});
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        testNG.run();
    }

    @RequestMapping(value = "/result",method = RequestMethod.GET)
    public void writeStream(@RequestParam(value = "id") Long id,HttpServletResponse response) throws IOException, InterruptedException {
        response.setContentType("text/html;charset=utf-8");
        SuiteInfo suiteInfo= suiteService.findOne(id);
        String info="";
        if(suiteInfo.getSuiteResult()!=null && !suiteInfo.equals("")){
            if(suiteInfo.getSuiteResult().contains("\n"))
                info=suiteInfo.getSuiteResult().replace("\n","<br/>");
        }
        String resultInfo="<!DOCTYPE html>\n" +
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
                "                <p>" +info
                +"</p>\n" +
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
        write(response,resultInfo);
        response.getWriter().close();
    }

    private void write(HttpServletResponse response,String content) throws IOException {
        response.getWriter().write(content+"<br/>");
        response.flushBuffer();
        response.getWriter().flush();
    }
}
