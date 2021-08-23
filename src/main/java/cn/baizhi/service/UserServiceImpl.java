package cn.baizhi.service;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.baizhi.annotation.DeleteCache;
import cn.baizhi.dao.UserDao;
import cn.baizhi.entity.User;
import cn.baizhi.util.AliYun;
import cn.baizhi.vo.MonthAndCount;
import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.PutObjectRequest;
import io.goeasy.GoEasy;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.*;

@Service
@Transactional
public class UserServiceImpl implements UserService {



    @Autowired
    private UserDao ud;
    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public Map<String, Object> queryByPage(int page, int size) {
        Map<String,Object> map = new HashMap<>();

//        totalNumber : 用数据库count(*)出的总条数数
        Integer totalNumber = ud.totalNumber();
//        currentPages：当前页数
        int currentPages = 0;

        //总页数   总条数/每页展示的条数   12/2=6 13/2=7
        /*
            if(总条数%每页展示的条数 == 0){
                //可以除尽，没有余页的情况
                总页数=总条数/每页展示的条数
            }else{
                //不可以除尽，有余页的情况，但再余也不会余处两页
                总页数=总条数/每页展示的条数+1
            }
        */
        int total = 0;
        if (totalNumber%size == 0){
            total = totalNumber/size;
        }else{
            total = totalNumber/size+1;
        }
        map.put("total", total);

//        当前第几页
        map.put("currentPages", page);

//        查出来的数据
        List<User> list = ud.queryRange((page - 1) * size, size);
        map.put("data",list );


        return map;

    }

    @DeleteCache
    @Override
    public void deleteUser(String userId) {
        ud.deleteUser(userId);
        HashMap<String, Object> map = queryUserSexCount();
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-867ee00bd4e548cfbdb06156ec0568bd");
        goEasy.publish("my_channel", JSONObject.toJSONString(map));
    }

    @DeleteCache
    @Override
    public void addUser(MultipartFile photo, String username, String phone, String brief) throws IOException {
//        图片文件名：phoyoName
        String photoName = new Date().getTime()+photo.getOriginalFilename();
//        prefix:文件后缀
        String prefix = photoName.substring(photoName.lastIndexOf("."));
        System.out.println("add1");
        /////////////
//        http://yx-lsj.oss-cn-beijing.aliyuncs.com/%E5%90%8D%E7%89%87-02.jpg

        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI5t9yYhvbCgGKxTwbxEkA";
        String accessKeySecret = "5o3d9Q8aX4FoTHXnI8pjwvrXNzQPrj";
        System.out.println("add2");
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        System.out.println("add3");
        // 创建PutObjectRequest对象。
        // 依次填写Bucket名称（例如examplebucket）、Object完整路径（例如exampledir/exampleobject.txt）和本地文件的完整路径。Object完整路径中不能包含Bucket名称。
        // 如果未指定本地路径，则默认从示例程序所属项目对应本地路径中上传文件。(上传到哪个存储空间中，在存储空间中文件叫什么，文件的路径)
//        =======M->F=======
        File file = File.createTempFile(photoName, prefix);
        photo.transferTo(file);
//        ==================
        PutObjectRequest putObjectRequest = new PutObjectRequest("yx-lsj", photoName,file);
        // 如果需要上传时设置存储类型和访问权限，请参考以下示例代码。
        // ObjectMetadata metadata = new ObjectMetadata();
        // metadata.setHeader(OSSHeaders.OSS_STORAGE_CLASS, StorageClass.Standard.toString());
        // metadata.setObjectAcl(CannedAccessControlList.Private);
        // putObjectRequest.setMetadata(metadata);
        // 上传文件。
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();
        /////////////
        String headimg = "http://yx-lsj.oss-cn-beijing.aliyuncs.com/"+photoName;

        User user = new User(UUID.randomUUID().toString(), username, phone, headimg, brief, "", new Date(),1,"女");

        ud.addUser(user);

        HashMap<String, Object> map = queryUserSexCount();
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-867ee00bd4e548cfbdb06156ec0568bd");
        goEasy.publish("my_channel", JSONObject.toJSONString(map));

    }

    @DeleteCache
    @Override
    public void updateStatus(String id) {
        int i = ud.selectZT(id);
        if(i==0){
            ud.updateStatus(id, 1);
        }else if(i==1){
            ud.updateStatus(id, 0);
        }
    }

    @Override
    public void downloadUser() {
        List<User> list = ud.queryAll();

        for (User user : list) {
            String headimg = user.getHeadimg();
            int i = headimg.lastIndexOf('/');
            String fileName = headimg.substring(i + 1);
            AliYun.downLoad(fileName);

            user.setHeadimg("D:\\bb\\"+fileName);
        }

        Workbook workbook = ExcelExportUtil.exportBigExcel(new ExportParams("用户信息", "用户信息表"),
                User.class, list);
        FileOutputStream stream = null;
        try {
            stream = new FileOutputStream("D:\\user信息"+new Date().getTime()+".xls");
            workbook.write(stream);
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                stream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public HashMap<String, Object> queryUserSexCount() {
        List<String> data = new ArrayList<>();
        List<Integer> manCount = new ArrayList<>();
        List<Integer> womanCount = new ArrayList<>();

        List<MonthAndCount> man = ud.queryMonthCount("男");
        List<MonthAndCount> woman = ud.queryMonthCount("女");

        //向data集合中存储1-12月
        for (int i=1;i<=12;i++){
            data.add(i+"月");

            boolean flag2 = false;
            for (MonthAndCount monthAndCount : woman) {
                if(monthAndCount.getMonth()==i){
                    womanCount.add(monthAndCount.getCount());
                    flag2 = true;
                }
                if(!flag2){
                    womanCount.add(0);
                }
            }
//            男
            boolean flag = false;
            for (MonthAndCount monthAndCount : man) {
                if(monthAndCount.getMonth()==i){
                    manCount.add(monthAndCount.getCount());
                    flag = true;
                }
                if(!flag){
                    manCount.add(0);
                }
            }

        }


        HashMap<String, Object> map = new HashMap<>();
        map.put("data", data);
        map.put("manCount", manCount);
        map.put("womanCount", womanCount);
//        System.out.println(map);
        return map;
    }
}
