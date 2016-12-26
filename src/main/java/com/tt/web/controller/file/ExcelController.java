package com.tt.web.controller.file;

import com.tt.ext.web.FileUploadProgressListener;
import com.tt.model.File;
import com.tt.service.FileService;
import com.tt.util.FileUtil;
import com.tt.util.SessionUtil;
import com.tt.web.controller.BaseController;
import com.tt.web.exception.NotFoundException;
import org.apache.poi.hssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by tt on 2016/11/28.
 */
@Controller
@RequestMapping("excel")
public class ExcelController extends BaseController {

    @Autowired
    private FileService fileService;


    @RequestMapping(value = "/download", method = RequestMethod.GET)
    public void download(HttpServletResponse response) throws Exception {
        String mimeType = URLConnection.guessContentTypeFromName("x.xls");

        if (mimeType == null) {
            mimeType = "application/octet-stream";
        }
        response.setContentType(mimeType);
        response.setHeader("Content-Disposition", String.format("afttachment; filename=\"%s\"", "x.xls"));
        System.out.println("x.xls");
        try (HSSFWorkbook excel = create()) {
            excel.write(response.getOutputStream());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private HSSFWorkbook create() throws Exception {
        List list = new ArrayList();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-mm-dd");

        Student user1 = new Student(1, "张三", 16, df.parse("1997-03-12"));
        Student user2 = new Student(2, "李四", 17, df.parse("1996-08-12"));
        Student user3 = new Student(3, "王五", 26, df.parse("1985-11-12"));
        list.add(user1);
        list.add(user2);
        list.add(user3);
// 第一步，创建一个webbook，对应一个Excel文件
        HSSFWorkbook wb = new HSSFWorkbook();
        // 第二步，在webbook中添加一个sheet,对应Excel文件中的sheet
        HSSFSheet sheet = wb.createSheet("学生表一");
        // 第三步，在sheet中添加表头第0行,注意老版本poi对Excel的行数列数有限制short
        HSSFRow row = sheet.createRow((int) 0);
        // 第四步，创建单元格，并设置值表头 设置表头居中
        HSSFCellStyle style = wb.createCellStyle();
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 创建一个居中格式

        HSSFCell cell = row.createCell( 0);
        cell.setCellValue("学号");
        cell.setCellStyle(style);
        cell = row.createCell( 1);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);
        cell = row.createCell( 2);
        cell.setCellValue("年龄");
        cell.setCellStyle(style);
        cell = row.createCell( 3);
        cell.setCellValue("生日");
        cell.setCellStyle(style);

        // 第五步，写入实体数据 实际应用中这些数据从数据库得到，

        for (int i = 0; i < list.size(); i++) {
            row = sheet.createRow((int) i + 1);
            Student stu = (Student) list.get(i);
            // 第四步，创建单元格，并设置值
            row.createCell( 0).setCellValue((double) stu.getId());
            row.createCell( 1).setCellValue(stu.getName());
            row.createCell( 2).setCellValue((double) stu.getAge());
            cell = row.createCell( 3);
            cell.setCellValue(new SimpleDateFormat("yyyy-mm-dd").format(stu
                    .getBirth()));
        }
        // 第六步，将文件存到指定位置
        return wb;
    }
}


class Student {
    private int id;
    private String name;
    private int age;
    private Date birth;

    public Student() {
    }

    public Student(int id, String name, int age, Date birth) {
        this.id = id;
        this.name = name;
        this.age = age;
        this.birth = birth;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }

}