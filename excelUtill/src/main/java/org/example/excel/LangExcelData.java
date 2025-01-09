package org.example.excel;

public class LangExcelData extends ExcelData{

    @ExcelValidation(pattern = "^[a-zA-Z]+$", message = "영문 입력가능", columName = "언어 코드", multi = true)
    private String langCode;

    @ExcelValidation(isRegex = false, message = "공백일 수 없습니다.", columName = "언어 명")
    private String langName;
    private String areaName;
    private String apiLang;
    private String airsLangCode;

}