package cn.baizhi.test;

import cn.baizhi.dao.UserDao;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.entity.Category;
import cn.baizhi.entity.User;
import cn.baizhi.entity.Video;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Date;
import java.util.List;

@SpringBootTest
public class UserDaoTest {
    @Autowired
    private UserDao ud;
    @Test
    public void testQueryRange(){
        List<User> users = ud.queryRange(0, 3);
        for (User user : users) {
            System.out.println(user);
        }
    }
    @Test
    public void update(){
        ud.updateStatus("3", 0);
    }

    @Test
    public void add(){
        ud.addUser(new User("159", "张三", "1235498", "", "", "", new Date(),1,"男"));
    }

    @Autowired
    private VideoDao vd;

    @Test
    public void testVD(){
        System.out.println(vd.queryById("1"));
    }
}
