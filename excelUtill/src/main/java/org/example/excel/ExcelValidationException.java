package org.example.excel;

public class ExcelValidationException extends RuntimeException{
    private String message;

    ExcelValidationException(){}

    public ExcelValidationException(String message) {
        super(message);
        this.message = message;
    }

}
