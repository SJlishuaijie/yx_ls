package cn.baizhi.commont;

import java.util.HashMap;
import java.util.Map;

public class CommontResult2 {

    public static Map<String,Object> success(String message, Object date){
        HashMap<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("message", message);
        map.put("status", "100");
        return map;
    }

    public static Map<String,Object> fail(String message,Object date){
        HashMap<String, Object> map = new HashMap<>();
        map.put("date", date);
        map.put("message", message);
        map.put("status", "104");
        return map;
    }
}
