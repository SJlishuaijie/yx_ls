package cn.baizhi.test;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

@SpringBootTest
public class EasyPOI {

    @Test
    public void test() throws IOException {
        ArrayList<Student> list = new ArrayList<>();
        list.add(new Student("1", "张三", 18, new Date()));
        list.add(new Student("2", "李四", 18, new Date()));
        list.add(new Student("3", "王五", 18, new Date()));
        list.add(new Student("4", "赵六", 18, new Date()));

        org.apache.poi.ss.usermodel.Workbook workbook = ExcelExportUtil.exportBigExcel(new ExportParams("学生信息", "学生信息表"),
                Student.class, list);
        workbook.write(new FileOutputStream("D:\\easypoi.xls"));
    }
}
