package cn.baizhi.controller;

import cn.baizhi.commont.CommontResult2;
import cn.baizhi.service.VideoService;
import cn.baizhi.util.AliYun;
import cn.baizhi.vo.VideoVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/yingx/app")
@CrossOrigin
@RestController
public class AppController {

    @Autowired
    private VideoService vs;

    @RequestMapping("queryByReleaseTime")
    public Map<String, Object> queryByReleaseTime() {
        List<VideoVo> videoVos = new ArrayList<>();
        System.out.println("到这了");
        try {
            videoVos = vs.queryByCreateDate();
            return CommontResult2.success("查询成功", videoVos);
        }catch (Exception e){
            e.printStackTrace();
            return CommontResult2.fail("查询失败", videoVos);
        }
    }


    @RequestMapping("/getPhoneCode")
    public Map<String, Object> getPhoneCode(String phone){
        Map<String, Object> map =null;
            HashMap<String, Object> smsMap = AliYun.Sample(phone);
        CommontResult2 cr = new CommontResult2();
            if ((Boolean) smsMap.get("code")){
                //验证码发送成功
                map = CommontResult2.success("发送验证码成功", phone);
            }else{
                map = CommontResult2.fail("发送验证码失败", null);

            }


        return map;
    }
}
