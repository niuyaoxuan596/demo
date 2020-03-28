package com.example.demo.controller;

import cn.hutool.json.JSONObject;
import com.example.demo.dao.Syudent;
import com.example.demo.servie.impl.SyudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController

public class HelloController {
    @Autowired
    private SyudentService syudentService;
    @RequestMapping("/nyx")
    public JSONObject hello(){
        Syudent syudent = syudentService.selectByPrimaryKey(new Integer(1));
        JSONObject jsonObject = new JSONObject(syudent);
        return jsonObject;
    }
}
