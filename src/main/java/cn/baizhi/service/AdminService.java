package cn.baizhi.service;


import java.util.Map;

public interface AdminService {
//    登陆业务
    Map<String,Object> queryByName(String username,String password);
}
