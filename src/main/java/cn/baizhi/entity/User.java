package cn.baizhi.entity;

import cn.afterturn.easypoi.excel.annotation.Excel;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

@ToString
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    @Excel(name = "用户的id")
    private String id;
    @Excel(name = "用户的姓名")
    private String username;
    @Excel(name = "用户的手机号")
    private String phone;
    @Excel(name = "头像",type = 2)
    private String headimg;
    @Excel(name = "用户的描述")
    private String brief;
    @Excel(name = "用户的微信")
    private String wechat;
    @Excel(name = "用户的注册时间",exportFormat = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date createdate;
    @Excel(name = "用户的状态")
    private Integer status;
    @Excel(name = "用户的性别")
    private String sex;
}
