package org.example.excel;

public class LangExcelData extends ExcelData{

    private String langCode;

    @ExcelValidation(message = "test", columName = "네임")
    private String langName;
    private String areaName;


    public void setLangCode(String langCode) {
        this.langCode = langCode;
    }

    public void setLangName(String langName) {
        this.langName = langName;
    }

    public void setAreaName(String areaName) {
        this.areaName = areaName;
    }

}
