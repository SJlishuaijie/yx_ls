package cn.baizhi.dao;

import cn.baizhi.entity.Video;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface VideoDao {
//    分页查
    List<Video> queryByPage(@Param("start") int start,@Param("end") int end);

//    查总页数
    Integer selectNum();

//    删除
    void deleteById(String id);

//    添加
    void addVideo(Video video);

//    根据id 查询出这条视频信息
    Video queryById(String id);


//    查所有的一级类别

//    根据一级类别查属于它的二级类别（应该可以在Service中调用CategoryServiceImpl中的方法）



}
