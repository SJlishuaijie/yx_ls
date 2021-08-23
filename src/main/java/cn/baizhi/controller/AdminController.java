package cn.baizhi.controller;


import cn.baizhi.entity.YxAdmin;
import cn.baizhi.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private AdminService as;

    @RequestMapping("/login")
    public Map<String, Object> login(@RequestBody YxAdmin yxAdmin){
        System.out.println(yxAdmin);

        return as.queryByName(yxAdmin.getUsername(), yxAdmin.getPassword());
    }
}
