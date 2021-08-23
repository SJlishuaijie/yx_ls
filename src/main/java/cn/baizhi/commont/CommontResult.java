package cn.baizhi.commont;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommontResult {
    private String status;
    private String message;
    private Object date;

    //定义一个方法表示成功的状态
    public static CommontResult success(String message, Object date){
        CommontResult cr = new CommontResult();
        cr.setStatus("100");
        cr.setMessage(message);
        cr.setDate(date);
        return cr;
    }

    //定义一个方法表示失败的状态
    public static CommontResult fail(String message, Object date){
        CommontResult cr = new CommontResult();
        cr.setStatus("104");
        cr.setMessage(message);
        cr.setDate(date);
        return cr;
    }
}
