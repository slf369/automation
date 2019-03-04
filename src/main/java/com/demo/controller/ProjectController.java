package com.demo.controller;

import com.demo.entity.ProjectInfo;
import com.demo.service.ProjectService;
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
import javax.validation.Valid;
import java.util.Map;

/**
 * Created by shi.lingfeng on 2018/3/2.
 */

@Controller
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    ProjectService projectService;

    /**
     * 列表显示
     * @project map
     * @return
     */
    @RequestMapping(value="/list")
    public String list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Map<String, Object> map){
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        PageRequest request = new PageRequest(page - 1, size,sort);
        Page<ProjectInfo> pageInfo = projectService.findAll(request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/project/list";
    }


    /**
     * 根据提取名查询
     */
    @RequestMapping("/search")
    public String getSelect(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            @RequestParam(value="projectname") String projectname,
                            ModelMap map){
        PageRequest request = new PageRequest(page - 1, size);
        Page<ProjectInfo> pageInfo = projectService.findSearch(projectname,request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/project/list";
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
        Page<ProjectInfo> pageInfo = projectService.findSearchById(longId, request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/project/list";
    }


    /**
     * 新增/修改-编辑
     * @project id
     * @project model
     * @return
     */
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (id != null) {
            ProjectInfo projectInfo = projectService.findOne(id);
            model.addAttribute("project", projectInfo);
        }
        return "project/index";
    }


    /**
     * 新增/修改
     * @project projectInfo
     * @project bindingResult
     * @project map
     * @return
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
    public String save(@Valid ProjectInfo projectInfo,
                       BindingResult bindingResult,
                       Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/project/index");
            return "common/error";
        }
        ProjectInfo projectInfoDes= new ProjectInfo();
        try {
            if (projectInfo.getId() != null) {
                projectInfoDes = projectService.findOne(projectInfo.getId());
            }
            BeanUtils.copyProperties(projectInfo, projectInfoDes);
            projectService.save(projectInfoDes);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/project/index");
            return "common/error";
        }
        map.put("url", "/project/list");
        return "common/success";
    }

    /**
     * 删除
     * @project id
     * @project map
     * @return
     */
    @RequestMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id, ModelMap map){
        try {
            projectService.delete(id);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/project/list");
            return "common/error";
        }
        map.put("url", "/project/list");
        return "common/success";
    }
}
