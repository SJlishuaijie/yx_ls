package cn.baizhi.dao;

import cn.baizhi.entity.User;
import cn.baizhi.vo.MonthAndCount;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserDao {
    //范围查询            从哪开始，到哪结束
    List<User> queryRange(@Param("start") int start,@Param("end") int end);

    //查询总页数
    Integer totalNumber();

    //修改状态
    void updateStatus(@Param("id") String id,@Param("status") int status);

    //添加
    void addUser(User user);

    //删除
    void deleteUser(String userId);

    //根据id查一个
    int selectZT(String id);

    //查所有
    List<User> queryAll();
    //    查询注册人数
    List<MonthAndCount> queryMonthCount(String sex);

}
