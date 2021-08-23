package cn.baizhi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class CacheHashAspect {
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
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        HashOperations hashOperations = redisTemplate.opsForHash();

        Object obj = null;
        if(hashOperations.hasKey(className, sb.toString())){
//            如果有这个key
            obj = hashOperations.get(className,sb.toString());
        }else{
//            没有这个key
            try {
                obj = joinPoint.proceed();
            } catch (Throwable throwable) {
                throwable.printStackTrace();
            }
            hashOperations.put(className, sb.toString(), obj);
        }

        return obj;






    }

    @After("@annotation(cn.baizhi.annotation.DeleteCache)")
    public void delCache(JoinPoint joinPoint){
//        类的全限定名
        String name = joinPoint.getTarget().getClass().getName();
        redisTemplate.delete(name);
//        Set keys = redisTemplate.keys("*");
//        for (Object key : keys) {
//            String newKey = (String)key;
//            if(newKey.startsWith(name)){
//                redisTemplate.delete(key);
//            }
//        }

    }


}
