package com.demo.controller;

import com.demo.entity.FuncInfo;
import com.demo.service.FuncService;
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
 * Created by shi.lingfeng on 2018/3/30.
 */
@Controller
@RequestMapping("/func")
public class FuncController {

    @Autowired
    FuncService funcService;

    /**
     * 列表显示
     * @func map
     * @return
     */
    @RequestMapping(value="/list")
    public String list(
            @RequestParam(value = "page", defaultValue = "1") Integer page,
            @RequestParam(value = "size", defaultValue = "10") Integer size,
            Map<String, Object> map){
        Sort sort=new Sort(Sort.Direction.DESC,"id");
        PageRequest request = new PageRequest(page - 1, size,sort);
        Page<FuncInfo> pageInfo = funcService.findAll(request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/func/list";
    }


    /**
     * 根据提取名查询
     */
    @RequestMapping("/search")
    public String getSelect(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            @RequestParam(value="funcname") String funcname,
                            ModelMap map){
        PageRequest request = new PageRequest(page - 1, size);
        Page<FuncInfo> pageInfo = funcService.findSearch(funcname,request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/func/list";
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
        Page<FuncInfo> pageInfo = funcService.findSearchById(longId, request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/func/list";
    }

    /**
     * 新增/修改-编辑
     * @func id
     * @func model
     * @return
     */
    @RequestMapping(value="/index",method= RequestMethod.GET)
    public String index(@RequestParam(value = "id", required = false) Long id,
                        Model model) {
        if (id != null) {
            FuncInfo funcInfo = funcService.findOne(id);
            model.addAttribute("func", funcInfo);
        }
        return "func/index";
    }


    /**
     * 新增/修改
     * @func funcInfo
     * @func bindingResult
     * @func map
     * @return
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
    public String save(@Valid FuncInfo funcInfo,
                       BindingResult bindingResult,
                       Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/func/index");
            return "common/error";
        }
        FuncInfo funcInfoDes= new FuncInfo();

        try {
            if (funcInfo.getId() != null) {
                funcInfoDes = funcService.findOne(funcInfo.getId());
            }
            BeanUtils.copyProperties(funcInfo, funcInfoDes);
            funcService.save(funcInfoDes);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/func/index");
            return "common/error";
        }
        map.put("url", "/func/list");
        return "common/success";
    }

    /**
     * 删除
     * @func id
     * @func map
     * @return
     */
    @RequestMapping("/delete")
    public String deleteUser(@RequestParam(value = "id") Long id, ModelMap map){
        try {
            funcService.delete(id);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/func/list");
            return "common/error";
        }
        map.put("url", "/func/list");
        return "common/success";
    }
}
