package cn.baizhi.service;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.entity.YxAdmin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@Service
@Transactional
public class AdminServiceImpl implements AdminService {
    @Autowired
    private AdminDao ad;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByName(String username, String password) {
        Map<String,Object> map = new HashMap<>();
        YxAdmin yxAdmin = ad.queryByUserName(username);
        if( yxAdmin != null ){
//            有这个用户
            if(yxAdmin.getPassword().equals(password)){
//                登陆成功
                map.put("flag", true);
                map.put("msg", "登陆成功");
            }else{
//                密码错误
                map.put("flag", false);
                map.put("msg", "密码错误");
            }
        }else{
//            没有这个用户
            map.put("flag", false);
            map.put("msg", "账号错误");
        }
        return map;
    }
}
