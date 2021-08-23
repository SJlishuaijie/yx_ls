package cn.baizhi.controller;

import cn.baizhi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Map;

@RestController
@RequestMapping("/user")
@CrossOrigin
public class UserController {
    @Autowired
    private UserService us;

    @RequestMapping("/queryByPage")
    public Map<String, Object> queryByPage(int page){
        int size = 3;
        return us.queryByPage(page, size);
    }

    @RequestMapping("/updateStatus")
    public void updateStatus(String id){
//        us.updateStatus(id, status);
        us.updateStatus(id);
    }

    @RequestMapping("/add")
    public void add(MultipartFile photo,String username,String phone,String brief) throws IOException {
        us.addUser(photo, username, phone, brief);
    }

    @RequestMapping("/deleteUser")
    public void deleteUser(String userId){
        us.deleteUser(userId);
    }

    @RequestMapping("/dowunload")
    public void dowunload(){
//        System.out.println("到dowunload了");
        us.downloadUser();
    }

    @RequestMapping("/registCount")
    public Map<String, Object> registCount(){
//        System.out.println("到registCount了");

        return us.queryUserSexCount();

    }
}
