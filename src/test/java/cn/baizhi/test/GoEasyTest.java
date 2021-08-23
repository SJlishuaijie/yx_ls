package cn.baizhi.test;

import io.goeasy.GoEasy;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.batch.BatchDataSource;

@BatchDataSource
public class GoEasyTest {
    @Test
    public void test(){
        GoEasy goEasy = new GoEasy("http://rest-hangzhou.goeasy.io", "BC-867ee00bd4e548cfbdb06156ec0568bd");
        goEasy.publish("my_channel", "Hello,GoEasy 大家好！");
    }
}
