package org.example.excel;

public abstract class ExcelData {

    private Integer rowNum;


    public void setRowNum(Integer rowNum) {
        this.rowNum = rowNum;
    }

    public void validation() {
        Class<? extends ExcelData> aClass = this.getClass();

        System.out.println(aClass);
    }

}
