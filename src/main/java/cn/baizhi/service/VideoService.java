package cn.baizhi.service;

import cn.baizhi.entity.Category;
import cn.baizhi.vo.VideoVo;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Map;

public interface VideoService {
    //查所有视频
    Map<String, Object> SelectAll(int page, int size);

    //删除视频
    void deleteById(String id);

    //    添加
    void addVideo(MultipartFile video, String title, String brief, String id);

    //    根据级别 查 类型 的业务
    List<Category> queryByLevels(int levens);

    //    根据父项id 查2级类别
    List<Category> queryByParentId(String id);


//    根据视频的上传时间降序业务
    List<VideoVo> queryByCreateDate();
}
