package cn.baizhi.controller;

import cn.baizhi.entity.Category;
import cn.baizhi.service.VideoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin
@RequestMapping("/video")
public class VideoController {
    @Autowired
    private VideoService vs;


    @RequestMapping("/add")
    public void add(MultipartFile video,String title,String brief,String id){
        vs.addVideo(video, title, brief, id);
    }

    @RequestMapping("/findAll")
    public Map<String, Object> findAll(int page){
        int size = 2;
        return vs.SelectAll(page, size);
    }

    @RequestMapping("/deleteVideo")
    public void deleteById(String id){
        vs.deleteById(id);
    }

    @RequestMapping("/categoryOne")
    public List<Category> categoryOne(){
        List<Category> categories = vs.queryByLevels(1);
        return categories;
    }
    @RequestMapping("/queryByParentId")
    public List<Category> queryByParentId(String id){
        List<Category> categories = vs.queryByParentId(id);
        System.out.println(categories);
        return categories;
    }


}
