package com.lmh.demo.excel;

import com.alibaba.excel.EasyExcel;

import java.util.ArrayList;
import java.util.List;

/**
 * 测试EasyExcel使用读写
 */
public class testExcel {
    public static void main(String[] args) {
        //读写的文件路径
        String filename="D:\\write.xlsx";
        //写入操作
        //EasyExcel.write(filename,ExcelData.class).sheet("联系人").doWrite(getList());
        //读取操作
        EasyExcel.read(filename,ExcelData.class,new ExcelListener()).sheet().doRead();
    }
    //设置要写入的对象list
    private static List<ExcelData> getList(){
        List<ExcelData> list=new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            ExcelData excelData=new ExcelData();
            excelData.setId(i);
            excelData.setAge(i);
            excelData.setName("kk"+i);
            list.add(excelData);
        }
        return list;
    }
}
