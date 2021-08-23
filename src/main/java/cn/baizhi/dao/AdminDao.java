package cn.baizhi.dao;

import cn.baizhi.entity.YxAdmin;


public interface AdminDao {
    //根据名字查一个
    YxAdmin queryByUserName(String username);
}
