package com.lmh.demo.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;

import java.util.Map;

/**
 * Excel监听器，执行该方法
 */
public class ExcelListener extends AnalysisEventListener<ExcelData> {
    //一行一行的读
    @Override
    public void invoke(ExcelData excelData, AnalysisContext analysisContext) {
        System.out.println(excelData);
    }
    //读取标题
    @Override
    public void invokeHeadMap(Map<Integer, String> headMap, AnalysisContext context) {
        System.out.println(headMap);
    }
    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
