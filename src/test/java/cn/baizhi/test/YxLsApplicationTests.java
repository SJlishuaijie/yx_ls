package cn.baizhi.test;

import cn.baizhi.dao.AdminDao;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.entity.Category;
import cn.baizhi.entity.YxAdmin;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.sql.DataSource;
import java.util.List;

@SpringBootTest
class YxLsApplicationTests {
    @Autowired
    private AdminDao ad;
    @Autowired
    private CategoryDao cd;

    @Test
    public void contextLoads() {

        //YxAdmin yxAdmin = ad.queryByUserName("123");
        System.out.println(ad.queryByUserName("123"));
    }

    @Autowired
    DataSource dataSource;
    @Test
    public void contextLoads1() throws Exception{
        System.out.println("获取的数据库连接为:"+dataSource.getConnection());
    }

    @Test
    public void C1() throws Exception{
        List<Category> categories = cd.queryByLevels(1);
        for (Category category : categories) {
            for (Category category1 : categories) {
                
            }
        }
    }

}
