package com.demo.controller;

import com.demo.entity.ExtractInfo;
import com.demo.enums.ExtractTypeEnum;
import com.demo.service.ExtractService;
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
import java.util.HashMap;
import java.util.Map;

/**
 * Created by shi.lingfeng on 2018/3/20
 */
@Controller
@RequestMapping("/extract")
public class ExtractController {
    @Autowired
    ExtractService extractService;

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
        Page<ExtractInfo> pageInfo = extractService.findAll(request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/extract/list";
    }


    /**
     * 根据用例名称查询
     */
    @RequestMapping("/search")
    public String getSelect(@RequestParam(value = "page", defaultValue = "1") Integer page,
                            @RequestParam(value = "size", defaultValue = "10") Integer size,
                            @RequestParam(value="extractname") String extractname,
                            ModelMap map){
        PageRequest request = new PageRequest(page - 1, size);
        Page<ExtractInfo> pageInfo = extractService.findSearch(extractname,request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/extract/list";
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
        Page<ExtractInfo> pageInfo = extractService.findSearchById(longId, request);
        map.put("pageInfo", pageInfo);
        map.put("currentPage", page);
        map.put("size", size);
        return "/extract/list";
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
            ExtractInfo extractInfo = extractService.findOne(id);
            model.addAttribute("extract", extractInfo);
        }

        Map<String,Object> extractTypeeMap=new HashMap<String,Object>();

        for(ExtractTypeEnum type:ExtractTypeEnum.values()){
            extractTypeeMap.put(type.getMessage(),type.getCode());
        }
        model.addAttribute("extractTypeeMaps",extractTypeeMap);

        return "extract/index";
    }


    /**
     * 新增/修改
     * @param extractInfo
     * @param bindingResult
     * @param map
     * @return
     */
    @RequestMapping(value="/save",method=RequestMethod.POST)
    public String save(@Valid ExtractInfo extractInfo,
                       BindingResult bindingResult,
                       Map<String, Object> map) {
        if (bindingResult.hasErrors()) {
            map.put("msg", bindingResult.getFieldError().getDefaultMessage());
            map.put("url", "/extract/index");
            return "common/error";
        }
        ExtractInfo extractInfoDes= new ExtractInfo();
        try {
            if (extractInfo.getId() != null) {
                extractInfoDes = extractService.findOne(extractInfo.getId());
            }
            BeanUtils.copyProperties(extractInfo, extractInfoDes);
            extractService.save(extractInfoDes);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/extract/index");
            return "common/error";
        }
        map.put("url", "/extract/list");
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
            extractService.delete(id);
        } catch (Exception e) {
            map.put("msg", e.getMessage());
            map.put("url", "/extract/list");
            return "common/error";
        }
        map.put("url", "/extract/list");
        return "common/success";
    }
}
