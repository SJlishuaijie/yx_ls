package cn.baizhi.util;

import com.alibaba.fastjson.JSONObject;
import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.aliyun.oss.model.GetObjectRequest;
import com.aliyun.oss.model.PutObjectRequest;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class AliYun {
    public static void uploadByBytes(MultipartFile file,String fileName){
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI5t9yYhvbCgGKxTwbxEkA";
        String accessKeySecret = "5o3d9Q8aX4FoTHXnI8pjwvrXNzQPrj";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
        // 填写字符串。
        byte[] content = null;
        try {
            content = file.getBytes();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 依次填写Bucket名称（例如examplebucket）和Object完整路径（例如exampledir/exampleobject.txt）。Object完整路径中不能包含Bucket名称。
        PutObjectRequest putObjectRequest = new PutObjectRequest("yx-lsj", fileName, new ByteArrayInputStream(content));
        // 上传字符串。
        ossClient.putObject(putObjectRequest);
        // 关闭OSSClient。
        ossClient.shutdown();
    }

//    删除文件
    public static void deleteFile(String fileName){
        String bucketName = "yx-lsj";//存储空间名
        String objectName = fileName;//文件名
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI5t9yYhvbCgGKxTwbxEkA";
        String accessKeySecret = "5o3d9Q8aX4FoTHXnI8pjwvrXNzQPrj";
        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);
//        删除文件
        ossClient.deleteObject(bucketName, objectName);
        ossClient.shutdown();
    }

        public static HashMap<String, Object> Sample(String phone){
            DefaultProfile profile = DefaultProfile.getProfile("cn-hangzhou", "LTAI5t9yYhvbCgGKxTwbxEkA", "5o3d9Q8aX4FoTHXnI8pjwvrXNzQPrj");
            IAcsClient client = new DefaultAcsClient(profile);
            HashMap<String, Object> map = new HashMap<>();
            CommonRequest request = new CommonRequest();
            request.setSysMethod(MethodType.POST);
            request.setSysDomain("dysmsapi.aliyuncs.com");
            request.setSysVersion("2017-05-25");
            request.setSysAction("SendSms");
            request.putQueryParameter("PhoneNumbers", phone);
            request.putQueryParameter("SignName", "阿里大于测试专用");
            request.putQueryParameter("TemplateCode", "SMS_209335004");
            String s = SecurityCode.getSecurityCode();//验证码
            System.out.println(s);
            map.put("yzm", s);
            String mes = "哈哈哈";
            request.putQueryParameter("TemplateParam", "{\"code\":\""+s+"\",\"product\":\"\"+mes+\"\"}");

            CommonResponse response = null;
            try {
                response = client.getCommonResponse(request);
            } catch (ClientException e) {
                e.printStackTrace();
            }
            System.out.println(response.getData());
            Map map1 = JSONObject.parseObject(response.getData(), Map.class);
            Object code = map1.get("Code");
            if(code.equals("OK")){
                map.put("code", true);
            }else{
                map.put("code", false);
            }
            return map;
        }


    public static void downLoad(String fileName){
        // yourEndpoint填写Bucket所在地域对应的Endpoint。以华东1（杭州）为例，Endpoint填写为https://oss-cn-hangzhou.aliyuncs.com。
        String endpoint = "https://oss-cn-beijing.aliyuncs.com";
        // 阿里云主账号AccessKey拥有所有API的访问权限，风险很高。强烈建议您创建并使用RAM账号进行API访问或日常运维，请登录RAM控制台创建RAM账号。
        String accessKeyId = "LTAI5t9yYhvbCgGKxTwbxEkA";
        String accessKeySecret = "5o3d9Q8aX4FoTHXnI8pjwvrXNzQPrj";

        String bucketName = "yx-lsj";  //存储空间名
        String objectName = fileName;  //文件名
        String localFile="D:\\bb\\"+objectName;  //下载本地地址  地址加保存名字

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        // 下载OSS文件到本地文件。如果指定的本地文件存在会覆盖，不存在则新建。
        ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
