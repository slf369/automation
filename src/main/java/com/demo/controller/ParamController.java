package com.demo.controller;

import com.demo.entity.ParamInfo;
import com.demo.service.ParamService;
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
 * Created by shi.lingfeng on 2018/3/26.
 */

@Controller
@RequestMapping("/param")
public class ParamController {
    @Autowired
    ParamService paramService;

    /**
     * 列表显示
     * @param map
     * @return
     */
    @RequestMapping(value="/list")
    public String list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Map<String, Object> map){

        Sort sort=new Sort(Sort.Direction.DESC,"id");
        PageRequest request = new PageRequest(page - 1, size,sort);
        Page<ParamInfo> pageInfo = paramService.findAll(request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/param/list";
    }


    /**
     * 根据提取名查询
     */
    @RequestMapping("/search")
    public String getSelect(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            @RequestParam(value="paramname") String paramname,
                            ModelMap map){
        PageRequest request = new PageRequest(page - 1, size);
        Page<ParamInfo> pageInfo = paramService.findSearch(paramname,request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/param/list";
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
        Page<ParamInfo> pageInfo = paramService.findSearchById(longId, request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/param/list";
    }

    /**
     * 新增/修改-编辑
     * @param id
     * @param model
     * @return
     */
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (id != null) {
            ParamInfo paramInfo = paramService.findOne(id);
            model.addAttribute("param", paramInfo);
        }
        return "param/index";
    }


    /**
     * 新增/修改
     * @param paramInfo
     * @param bindingResult
     * @param map
     * @return
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
    public String save(@Valid ParamInfo paramInfo,
                       BindingResult bindingResult,
                       Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/param/index");
            return "common/error";
        }
        ParamInfo paramInfoDes= new ParamInfo();
        try {
            if (paramInfo.getId() != null) {
                paramInfoDes = paramService.findOne(paramInfo.getId());
            }
            BeanUtils.copyProperties(paramInfo, paramInfoDes);
            paramService.save(paramInfoDes);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/param/index");
            return "common/error";
        }
        map.put("url", "/param/list");
        return "common/success";
    }

    /**
     * 删除
     * @param id
     * @param map
     * @return
     */
    @RequestMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id, ModelMap map){
        try {
            paramService.delete(id);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/param/list");
            return "common/error";
        }
        map.put("url", "/param/list");
        return "common/success";
    }
}
