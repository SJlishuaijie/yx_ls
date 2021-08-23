package cn.baizhi.entity;

import lombok.*;

import java.io.Serializable;

//管理员实体类
//@Setter    //set方法
//@Getter    //get方法
//@ToString  //toString方法
@Data
@AllArgsConstructor //生成全参构造
@NoArgsConstructor  //生成无参构造
public class YxAdmin implements Serializable {

  private String id;
  private String username;
  private String password;
  private long status;

}
