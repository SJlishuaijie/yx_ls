package cn.baizhi.service;

import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.CategoryDao;
import cn.baizhi.dao.VideoDao;
import cn.baizhi.dao.VideoVoDao;
import cn.baizhi.entity.Category;
import cn.baizhi.entity.Video;
import cn.baizhi.util.AliYun;
import cn.baizhi.vo.VideoVo;
import com.aliyun.oss.HttpMethod;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GeneratePresignedUrlRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Service
@Transactional
public class VideoServiceImpl implements VideoService {

    @Autowired
    private VideoDao vd;

    @Autowired
    private VideoVoDao vvd;

    //借用CategoryDao查一二级类别
    @Autowired
    private CategoryDao cd;

    @DeleteCache
    @Override
    public void addVideo(MultipartFile video, String title, String brief, String id) {
        String fileName = new Date().getTime()+video.getOriginalFilename();
        AliYun.uploadByBytes(video, "video/"+fileName);
//        http://yx-lsj.oss-cn-beijing.aliyuncs.com/video/1629169247860QQ%E7%9F%AD%E8%A7%86%E9%A2%9120210816172840.mp4
        String videoPath = "http://yx-lsj.oss-cn-beijing.aliyuncs.com/video/"+fileName;
////////////////////////////////////视频截帧

        /// yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI5t9yYhvbCgGKxTwbxEkA";
        String accessKeySecret = "5o3d9Q8aX4FoTHXnI8pjwvrXNzQPrj";

// 填写视频文件所在的Bucket名称。
        String bucketName = "yx-lsj";
// 填写视频文件的完整路径。若视频文件不在Bucket根目录，需携带文件访问路径，例如examplefolder/videotest.mp4。
        String objectName = "video/"+fileName;
// 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
// 使用精确时间模式截取视频50s处的内容，输出为JPG格式的图片，宽度为800，高度为600。
        String style = "video/snapshot,t_1000,f_jpg,w_500,h_800";
// 指定过期时间为10分钟。
        Date expiration = new Date(new Date().getTime() + 1000 * 60 * 10 );
        GeneratePresignedUrlRequest req = new GeneratePresignedUrlRequest(bucketName, objectName, HttpMethod.GET);
        req.setExpiration(expiration);
        req.setProcess(style);
        URL signedUrl = ossClient.generatePresignedUrl(req);
        System.out.println(signedUrl);

////////////////////////////////////
//        =========================

// 填写网络流地址。
        InputStream inputStream = null;
        try {
            inputStream = new URL(signedUrl.toString()).openStream();
        } catch (Exception e) {
            e.printStackTrace();
        }
// 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        String[] split = fileName.split("\\.");
        ossClient.putObject("yx-lsj","video/"+split[0]+".jpg", inputStream);



//        =========================

        // 关闭OSSClient。
        ossClient.shutdown();
        String coverPath = "http://yx-lsj.oss-cn-beijing.aliyuncs.com/video/"+split[0]+".jpg";

        Video video1 = new Video(UUID.randomUUID().toString(),
                title, brief, coverPath,
                videoPath, new Date(),
                new Category(id, "", 2, ""), null, "");
        vd.addVideo(video1);
    }

    @DeleteCache
    @Override
    public void deleteById(String id) {
//         删除视频
//         删除封面
//         删除表中的信息
        Video video = vd.queryById(id);

        String coverPath = video.getCoverPath();//视频封面的路径
        String[] split = coverPath.split("//");
        AliYun.deleteFile("video/"+split[split.length-1]);

        String videoPath = video.getVideoPath();//视频的路径
        String[] split1 = videoPath.split("//");
        AliYun.deleteFile("video/"+split1[split.length-1]);

        vd.deleteById(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> SelectAll(int page, int size) {
        Map<String,Object> map = new HashMap<>();

//        总页数pageNum
        Integer pageNum = vd.selectNum();
        int total = 0;
        if (pageNum%size == 0){
            total = pageNum/size;
        }else{
            total = pageNum/size+1;
        }
//        当前第几页
        map.put("page", page);
//        总页数
        map.put("pageNum", total);
//        查出来的数据
        List<Video> list = vd.queryByPage((page - 1) * size, size);
        map.put("data",list );
        System.out.println("当前页"+page);
        System.out.println("总页"+pageNum);
        return map;
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByLevels(int levens) {
        return cd.queryByLevels(levens);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<Category> queryByParentId(String id) {
        return cd.queryByParendId(id);
    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public List<VideoVo> queryByCreateDate() {

        return vvd.queryAllVideo();
    }
}
