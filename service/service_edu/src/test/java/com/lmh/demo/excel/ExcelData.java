package com.lmh.demo.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

/**
 * 读写相关数据的类，数据写入该类对象的，从该类对象中取出数据
 */
@Data
public class ExcelData {
    @ExcelProperty(index = 0,value = "id")
    private Integer id;
    @ExcelProperty(index = 1,value = "姓名")
    private String name;
    @ExcelProperty(index = 2,value = "年龄")
    private Integer age;
}
