package com.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by shi.lingfeng on 2018/3/29.
 */
@Controller
public class HomeController {

    @RequestMapping(value="/home")
    public String list(){
        return "/main/home";
    }
}
