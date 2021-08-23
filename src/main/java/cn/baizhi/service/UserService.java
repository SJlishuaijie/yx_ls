package cn.baizhi.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public interface UserService {
    //分页查业务
    Map<String,Object> queryByPage(int page,int size);

    //修改状态的业务
    void updateStatus(String id);

    //添加
    void addUser(MultipartFile photo, String username, String phone, String brief) throws IOException;

    //删除
    void deleteUser(String userId);

    //查所有
    void downloadUser();

    //提供用户分析数据业务
    HashMap<String, Object> queryUserSexCount();

}
