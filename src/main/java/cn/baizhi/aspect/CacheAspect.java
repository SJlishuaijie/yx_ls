package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.data.redis.serializer.StringRedisSerializer;

import java.util.Set;

//@Aspect
//@Component
public class CacheAspect {
    @Autowired
    private RedisTemplate redisTemplate;

    @Around("execution(* cn.baizhi.service.*Impl.query*(..))")
    public Object addCache(ProceedingJoinPoint joinPoint){
        System.out.println("进入环绕通知");

        StringBuilder sb = new StringBuilder();
//        System.out.println(obj);
//      获取类的全路径
        String className = joinPoint.getTarget().getClass().getName();


//      获取方法名
        String methdName = joinPoint.getSignature().getName();
        sb.append(className).append(methdName);
//      实参值
        Object[] args = joinPoint.getArgs();
        for (Object arg : args) {
            sb.append(arg);
        }

        redisTemplate.setKeySerializer(new StringRedisSerializer());
        ValueOperations valueOperations = redisTemplate.opsForValue();

        Object obj = null;
        if(redisTemplate.hasKey(sb.toString())){
//            如果有这个key
            obj = valueOperations.get(sb.toString());
        }else{
//            没有这个key
            try {
                obj = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            valueOperations.set(sb.toString(), obj);
        }

        return obj;






    }

    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint){
//        类的全限定名
        String name = joinPoint.getTarget().getClass().getName();
        Set keys = redisTemplate.keys("*");
        for (Object key : keys) {
            String newKey = (String)key;
            if(newKey.startsWith(name)){
                redisTemplate.delete(key);
            }
        }

    }


}
