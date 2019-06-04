package com.example.in2;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class IndexController2 {

    @RequestMapping("index2")
    @ResponseBody
    public String index(){
        return "haha";
    }
}
