package cn.baizhi.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Category implements Serializable {
    private String id;
    private String cate_name;
    private Integer levels;
    private String parent_id;
}
