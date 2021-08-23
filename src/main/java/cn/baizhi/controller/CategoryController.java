package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/category")
public class CategoryController {
    @Autowired
    private CategoryService cs;

    @RequestMapping("/queryByLevels")
    public List<Category> queryByLevels(int levels){
        return cs.queryByLevels(levels);
    }

    @RequestMapping("/queryByParentId")
    public List<Category> queryByParentId(String id){
        return cs.queryByParentId(id);
    }

    @RequestMapping("/save")
    public void save(@RequestBody Category category){
        cs.save(category);
    }

    @RequestMapping("/delete")
    public void delete(String id){
        cs.delete(id);
    }
}
