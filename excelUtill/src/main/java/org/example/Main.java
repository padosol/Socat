package org.example;

import org.example.excel.ExcelUtil;
import org.example.excel.LangExcelData;

import java.io.File;

public class Main {
    public static void main(String[] args) {

        String excelFilePath = "src/main/resources/lang.xlsx";
        File excelFile = new File(excelFilePath);

        ExcelUtil.parseExcel(excelFile, LangExcelData.class);
    }
}